package com.shopspiker.auth.service.impl;

import java.time.OffsetDateTime;
import java.util.Random;

import com.shopspiker.auth.configuration.AuthConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopspiker.auth.jpa.constants.AuthorizedGrantTypeEnum;
import com.shopspiker.auth.jpa.constants.JwtTokenTypeEnum;
import com.shopspiker.auth.jpa.entity.Client;
import com.shopspiker.auth.jpa.entity.Otp;
import com.shopspiker.auth.jpa.entity.User;
import com.shopspiker.auth.modal.request.GenerateOtpRequest;
import com.shopspiker.auth.modal.request.ValidateOtpRequest;
import com.shopspiker.auth.modal.response.TokenResponse;
import com.shopspiker.auth.jpa.repository.OtpRepository;
import com.shopspiker.auth.service.ClientService;
import com.shopspiker.auth.service.OtpAuthService;
import com.shopspiker.auth.service.UserService;
import com.shopspiker.auth.service.util.JwtUtil;
import com.shopspiker.common.web.exception.CustomApplicationException;
import com.shopspiker.common.service.validation.PhoneValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OtpAuthServiceImpl implements OtpAuthService {

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthConfig authConfig;

    @Override
    public void generateOtp(GenerateOtpRequest request) {

        PhoneValidator.validate(request.getPhone());

        // Generate OTP
        Random random = new Random();
        String otpString = String.format("%04d", random.nextInt(10000));
        log.info("OTP: " + otpString);

        // TODO Send OTP

        // Save OTP to DB
        Otp otp = new Otp();
        otp.setPhone(request.getPhone());
        otp.setOtp(passwordEncoder.encode(otpString));
        otp.setNoOfRetries(0);
        otp.setOtpVerified(false);
        otp.setValidTill(OffsetDateTime.now().plusMinutes(authConfig.getOtpValidityInMin()));
        otpRepository.save(otp);

    }

    @Override
    public void resendOtp(GenerateOtpRequest request) {

        Otp otp = otpRepository
                .findByPhone(request.getPhone()).orElseThrow(
                        () -> new CustomApplicationException(HttpStatus.NOT_FOUND, "OTP_NOT_FOUND", "Otp not found"));

        if (otp.getNoOfRetries() == authConfig.getOtpMaxRetries()) {
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, "MAX_RETRY_LIMIT",
                    "You have reached the max retries");
        }
        if (otp.getValidTill().isBefore(OffsetDateTime.now())) {
            throw new CustomApplicationException(HttpStatus.BAD_REQUEST, "OTP_EXPIRED", "OTP Expired");
        }
        // Generate OTP
        Random random = new Random();
        String otpString = String.format("%04d", random.nextInt(10000));

        // TODO Send OTP

        // Save OTP to DB
        otp.setOtp(passwordEncoder.encode(otpString));
        otp.setNoOfRetries(otp.getNoOfRetries() + 1);
        otp.setOtpVerified(false);
        otp.setValidTill(OffsetDateTime.now().plusMinutes(authConfig.getOtpValidityInMin()));
        otpRepository.save(otp);

    }

    @Override
    public TokenResponse validateOtp(ValidateOtpRequest request) {

        Otp otp = otpRepository
                .findByPhoneAndOtpVerifiedAndValidTillAfter(request.getPhone(), false,
                        OffsetDateTime.now())
                .orElseThrow(
                        () -> new CustomApplicationException(HttpStatus.NOT_FOUND, "OTP_NOT_FOUND",
                                "Otp not found"));

        if (!passwordEncoder.matches(request.getOtp(), otp.getOtp())) {
            throw new CustomApplicationException(HttpStatus.UNAUTHORIZED, "INVALID_OTP", "Invalid Otp");
        }

        TokenResponse response = new TokenResponse();
        Client client = clientService.getClient();
        User user = null;
        try {
            user = userService.findByEmailOrPhoneAndActive("", request.getPhone());
            response.setRegistered(true);
            // If user found update their login attempt
            user.setLastSuccessfulLoginAt(OffsetDateTime.now());
            userService.save(user);

            // Generate Token
            response.setAccessToken(jwtUtil.generateToken(user, client, JwtTokenTypeEnum.ACCESS_TOKEN));
            response.setRefreshToken(jwtUtil.generateToken(user, client, JwtTokenTypeEnum.REFRESH_TOKEN));

        } catch (CustomApplicationException e) {
            if (e.getHttpStatus() == HttpStatus.NOT_FOUND) {
                if (!clientService.checkAuthorizedGrantType(AuthorizedGrantTypeEnum.REGISTRATION_TOKEN.toString())) {
                    throw new CustomApplicationException(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "UNAUTHORIZED");
                }
                response.setRegistered(false);
                response.setAccessToken(jwtUtil.generateRegistrationToken(
                        request.getPhone().toString(), client));
            } else {
                throw e;
            }
        }

        // update OTP as verified
        otp.setOtpVerified(true);
        otpRepository.save(otp);

        return response;
    }
}
