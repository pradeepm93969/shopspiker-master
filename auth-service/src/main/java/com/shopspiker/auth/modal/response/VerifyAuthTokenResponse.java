package com.shopspiker.auth.modal.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class VerifyAuthTokenResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private String subject;
    private List<String> permissions = new ArrayList<>();

}
