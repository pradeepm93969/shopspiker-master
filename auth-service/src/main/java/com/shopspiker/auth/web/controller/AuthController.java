package com.shopspiker.auth.web.controller;

import com.shopspiker.auth.modal.UserModal;
import com.shopspiker.auth.modal.request.ChangePasswordRequest;
import com.shopspiker.auth.modal.request.ResetPasswordRequest;
import com.shopspiker.auth.service.PasswordService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopspiker.auth.web.annotation.BasicAuth;
import com.shopspiker.auth.modal.request.LoginRequest;
import com.shopspiker.auth.modal.response.TokenResponse;
import com.shopspiker.auth.modal.response.VerifyAuthTokenResponse;
import com.shopspiker.auth.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth/v1/")
@Tag(name = "Auth Management")
@SecurityRequirement(name = "basicAuth")
@Validated
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("login")
    @Operation(summary = "All Login")
    @BasicAuth(permission = "LOGIN")
    public ResponseEntity<TokenResponse> login(HttpServletRequest httpServletRequest,
                                               @Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(httpServletRequest, request));
    }

    @PostMapping("registration")
    @Operation(summary = "Registration from OTP")
    @BasicAuth(permission = "REGISTRATION")
    public ResponseEntity<TokenResponse> registration(HttpServletRequest httpServletRequest,
                                                      @Valid @RequestBody UserModal request) {
        return ResponseEntity.ok(authService.registration(httpServletRequest, request));
    }

    @PostMapping("/initiateResetPassword")
    @BasicAuth(permission = "RESET_PASSWORD")
    public void initiateResetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        authService.initiateResetPassword(request);
    }

    @GetMapping("/verifyResetPasswordToken")
    @BasicAuth(permission = "RESET_PASSWORD")
    public void verifyResetPasswordToken() {
        authService.verifyResetPasswordToken();
    }

    @PostMapping("/resetPassword")
    @BasicAuth(permission = "RESET_PASSWORD")
    public void resetPassword(@Valid @RequestBody ChangePasswordRequest request) {
        authService.resetPassword(request.getPassword());
    }

    @GetMapping("/verifyAuthToken")
    public ResponseEntity<VerifyAuthTokenResponse> verifyAuthToken() {
        return ResponseEntity.status(HttpStatus.OK).body(authService.verifyAuthToken());
    }
}
