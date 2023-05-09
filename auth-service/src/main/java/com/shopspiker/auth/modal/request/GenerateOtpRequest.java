package com.shopspiker.auth.modal.request;

import java.io.Serializable;

import com.shopspiker.common.annotation.ValidPhoneNumber;
import com.shopspiker.common.jpa.entity.Phone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerateOtpRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ValidPhoneNumber
    private Phone phone;

}
