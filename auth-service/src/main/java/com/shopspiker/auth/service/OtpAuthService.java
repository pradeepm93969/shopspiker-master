package com.shopspiker.auth.service;

import com.shopspiker.auth.modal.request.GenerateOtpRequest;
import com.shopspiker.auth.modal.request.ValidateOtpRequest;
import com.shopspiker.auth.modal.response.TokenResponse;

public interface OtpAuthService {

    public void generateOtp(GenerateOtpRequest request);

    public void resendOtp(GenerateOtpRequest request);

    public TokenResponse validateOtp(ValidateOtpRequest request);
}
