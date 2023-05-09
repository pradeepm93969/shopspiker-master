package com.shopspiker.auth.modal;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.shopspiker.common.annotation.ValidPassword;
import com.shopspiker.common.annotation.ValidPhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopspiker.common.jpa.entity.AuditSection;
import com.shopspiker.common.jpa.entity.Phone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModal implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;

    @Size(min = 3, max = 50)
    @NotBlank
    private String firstName;

    @Size(min = 3, max = 50)
    @NotBlank
    private String lastName;

    @Email
    private String email;

    @ValidPassword
    private String password;

    @ValidPhoneNumber
    private Phone phone;

    @JsonProperty("isActive")
    private boolean active;

    private OffsetDateTime lastSuccessfulLoginAt;

    @Builder.Default
    private List<RoleModal> roles = new ArrayList<>();

    @Builder.Default
    private AuditSection auditSection = new AuditSection();
}
