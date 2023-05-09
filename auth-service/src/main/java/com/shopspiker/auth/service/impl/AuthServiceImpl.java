package com.shopspiker.auth.service.impl;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.shopspiker.auth.jpa.constants.AuthoritiesEnum;
import com.shopspiker.auth.modal.RoleModal;
import com.shopspiker.auth.modal.UserModal;
import com.shopspiker.auth.modal.request.ResetPasswordRequest;
import com.shopspiker.auth.service.util.IpAddressUtils;
import com.shopspiker.auth.service.validator.UserValidator;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.shopspiker.auth.configuration.AuthConfig;
import com.shopspiker.auth.jpa.constants.AuthorizedGrantTypeEnum;
import com.shopspiker.auth.jpa.constants.JwtTokenTypeEnum;
import com.shopspiker.auth.jpa.entity.Client;
import com.shopspiker.auth.jpa.entity.User;
import com.shopspiker.auth.modal.request.LoginRequest;
import com.shopspiker.auth.modal.response.TokenResponse;
import com.shopspiker.auth.modal.response.VerifyAuthTokenResponse;
import com.shopspiker.auth.service.AuthService;
import com.shopspiker.auth.service.ClientService;
import com.shopspiker.auth.service.UserService;
import com.shopspiker.auth.service.util.JwtUtil;
import com.shopspiker.common.service.util.ApplicationUtil;
import com.shopspiker.common.web.exception.CustomApplicationException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthConfig authConfig;

    @Override
    public TokenResponse login(HttpServletRequest httpServletRequest, LoginRequest request) {

        TokenResponse response = new TokenResponse();
        // Validate if Client has Authorized Grant type
        clientService.checkAuthorizedGrantType(request.getGrantType());
        Client client = clientService.getClient();

        if (request.getGrantType().equalsIgnoreCase(AuthorizedGrantTypeEnum.CLIENT_CREDENTIALS.toString())) {
            response.setAccessToken(jwtUtil.generateToken(null, client, JwtTokenTypeEnum.CLIENT_CREDENTIALS));

        } else if (request.getGrantType().equalsIgnoreCase(AuthorizedGrantTypeEnum.REFRESH_TOKEN.toString())) {

            if (jwtUtil.checkCurrentTokenType(JwtTokenTypeEnum.REFRESH_TOKEN)) {

                User user = userService.findByIdAndActive(jwtUtil.getSubjectFromToken());
                response.setAccessToken(jwtUtil.generateToken(user, client, JwtTokenTypeEnum.ACCESS_TOKEN));
                response.setRefreshToken(jwtUtil.generateToken(user, client, JwtTokenTypeEnum.REFRESH_TOKEN));
            }

        } else if (request.getGrantType().equalsIgnoreCase(AuthorizedGrantTypeEnum.PASSWORD.toString())) {

            String ipAddress = IpAddressUtils.getIpAddress();

            // Check for Brute force attack
            int loginAttempts = loginAttemptService.getById(ipAddress);
            if (loginAttempts == authConfig.getMaxInvalidLoginAttemptsFromIP()) {
                throw new CustomApplicationException(HttpStatus.FORBIDDEN, "IP is Blocked", "IP is blocked");
            }

            User user = userService
                    .findByEmailOrPhoneAndActive(request.getEmail(), request.getPhone());

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                loginAttemptService.update(ipAddress,
                        Math.min(loginAttempts + 1, authConfig.getMaxInvalidLoginAttemptsFromIP()));
                throw new CustomApplicationException(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "UNAUTHORIZED");
            }

            user.setLastSuccessfulLoginAt(OffsetDateTime.now());
            userService.save(user);

            loginAttemptService.delete(ApplicationUtil.getIp(httpServletRequest));

            response.setRegistered(true);
            response.setAccessToken(jwtUtil.generateToken(user, client, JwtTokenTypeEnum.ACCESS_TOKEN));
            response.setRefreshToken(jwtUtil.generateToken(user, client, JwtTokenTypeEnum.REFRESH_TOKEN));
        }

        return response;
    }

    @Override
    public TokenResponse registration(HttpServletRequest httpServletRequest, UserModal request) {
        Client client = clientService.getClient();
        clientService.checkAuthority(AuthoritiesEnum.REGISTRATION.toString());

        request.setRoles(new ArrayList<>(Arrays.asList(
                RoleModal.builder().id("ROLE_B2C_USER").build())));
        userService.create(request);
        log.info("User:" + request.getEmail() + " Registered Successfully");

        User user = userService.findByEmailOrPhoneAndActive(request.getEmail(), request.getPhone());
        // Generate Token
        TokenResponse response = new TokenResponse();
        response.setRegistered(true);
        response.setAccessToken(jwtUtil.generateToken(user, client, JwtTokenTypeEnum.ACCESS_TOKEN));
        response.setRefreshToken(jwtUtil.generateToken(user, client, JwtTokenTypeEnum.REFRESH_TOKEN));
        return response;
    }

    @Override
    public void initiateResetPassword(ResetPasswordRequest request) {
        // Validate if Client has Authorized Grant type
        Client client = clientService.getClient();
        User user = userService.findByEmailOrPhoneAndActive(request.getEmail(), request.getPhone());
        String token = jwtUtil.generateToken(user, client, JwtTokenTypeEnum.RESET_PASSWORD_TOKEN);
        // TODO: Send email
        log.info("token:" + token);
    }

    @Override
    public void verifyResetPasswordToken() {
        if (!jwtUtil.checkCurrentTokenType(JwtTokenTypeEnum.RESET_PASSWORD_TOKEN)) {
            throw new CustomApplicationException(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "UNAUTHORIZED");
        }
    }

    @Override
    public void resetPassword(String newPassword) {
        verifyResetPasswordToken();

        User user = userService.findById(jwtUtil.getSubjectFromToken());
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.save(user);
    }

    @Override
    public VerifyAuthTokenResponse verifyAuthToken() {
        VerifyAuthTokenResponse response = new VerifyAuthTokenResponse();
        // validate Token
        if (!jwtUtil.checkCurrentTokenType(JwtTokenTypeEnum.ACCESS_TOKEN)) {
            throw new CustomApplicationException(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "UNAUTHORIZED");
        }
        response.setSubject(jwtUtil.getSubjectFromToken());

        List<SimpleGrantedAuthority> roles = jwtUtil.getRolesFromToken();
        for (SimpleGrantedAuthority role : roles) {
            response.getPermissions().add(role.getAuthority());
        }
        return response;
    }

}
