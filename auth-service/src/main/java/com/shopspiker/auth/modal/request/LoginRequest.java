package com.shopspiker.auth.modal.request;

import java.io.Serializable;

import com.shopspiker.common.annotation.ValidPassword;
import com.shopspiker.common.annotation.ValidPhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import com.shopspiker.common.jpa.entity.Phone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Pattern(regexp = "PASSWORD|REFRESH_TOKEN|CLIENT_CREDENTIALS")
    private String grantType;

    @ValidPassword
    private String password;

    @Email
    private String email;

    @ValidPhoneNumber
    private Phone phone;

}
