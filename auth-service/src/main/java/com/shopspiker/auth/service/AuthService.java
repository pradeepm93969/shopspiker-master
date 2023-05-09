package com.shopspiker.auth.service;

import com.shopspiker.auth.modal.UserModal;
import com.shopspiker.auth.modal.request.ResetPasswordRequest;
import jakarta.servlet.http.HttpServletRequest;

import com.shopspiker.auth.modal.request.LoginRequest;
import com.shopspiker.auth.modal.response.TokenResponse;
import com.shopspiker.auth.modal.response.VerifyAuthTokenResponse;

public interface AuthService {

    public TokenResponse login(HttpServletRequest httpServletRequest, LoginRequest request);

    TokenResponse registration(HttpServletRequest httpServletRequest, UserModal request);

    void initiateResetPassword(ResetPasswordRequest request);

    void verifyResetPasswordToken();

    void resetPassword(String password);

    public VerifyAuthTokenResponse verifyAuthToken();
}
