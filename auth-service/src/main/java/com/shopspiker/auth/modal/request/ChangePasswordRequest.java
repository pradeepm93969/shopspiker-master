package com.shopspiker.auth.modal.request;

import com.shopspiker.common.annotation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChangePasswordRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @ValidPassword
    private String password;

}
