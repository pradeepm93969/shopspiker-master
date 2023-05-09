package com.shopspiker.auth.web.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopspiker.auth.web.annotation.BasicAuth;
import com.shopspiker.auth.modal.request.GenerateOtpRequest;
import com.shopspiker.auth.modal.request.ValidateOtpRequest;
import com.shopspiker.auth.modal.response.TokenResponse;
import com.shopspiker.auth.service.OtpAuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth/v1/")
@Tag(name = "Auth Management")
@SecurityRequirement(name = "basicAuth")
@Validated
public class OtpAuthController {

    @Autowired
    private OtpAuthService otpAuthService;

    @PostMapping("generateOtp")
    @Operation(summary = "Generate Auth Otp")
    @BasicAuth(permission = "otp")
    public ResponseEntity<String> generateOtp(@Valid @RequestBody GenerateOtpRequest request) {
        otpAuthService.generateOtp(request);
        return ResponseEntity.ok("SUCCESS");
    }

    @PostMapping("resendOtp")
    @Operation(summary = "Resend Auth Otp")
    @BasicAuth(permission = "otp")
    public ResponseEntity<String> resendOtp(@Valid @RequestBody GenerateOtpRequest request) {
        otpAuthService.resendOtp(request);
        return ResponseEntity.ok("SUCCESS");
    }

    @PostMapping("validateOtp")
    @Operation(summary = "Validate Auth Otp")
    @BasicAuth(permission = "otp")
    public ResponseEntity<TokenResponse> validateOtp(@Valid @RequestBody ValidateOtpRequest request) {
        return ResponseEntity.ok(otpAuthService.validateOtp(request));
    }

}
