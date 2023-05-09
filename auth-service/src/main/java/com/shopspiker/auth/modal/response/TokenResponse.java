package com.shopspiker.auth.modal.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class TokenResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private boolean isRegistered;
    private String accessToken;
    private String tokenType = "bearer";
    private String refreshToken;

}
