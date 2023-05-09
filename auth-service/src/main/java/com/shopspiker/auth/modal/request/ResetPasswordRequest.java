package com.shopspiker.auth.modal.request;

import java.io.Serializable;

import com.shopspiker.common.annotation.ValidPassword;
import com.shopspiker.common.annotation.ValidPhoneNumber;
import jakarta.validation.constraints.Email;

import com.shopspiker.common.jpa.entity.Phone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResetPasswordRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Email
    private String email;

    @ValidPhoneNumber
    private Phone phone;
}
