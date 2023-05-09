package com.shopspiker.auth.modal;

import java.io.Serializable;
import java.util.List;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopspiker.auth.jpa.constants.AuthoritiesEnum;
import com.shopspiker.auth.jpa.constants.AuthorizedGrantTypeEnum;
import com.shopspiker.common.jpa.entity.AuditSection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientModal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(min = 3, max = 36)
    @Pattern(regexp = "^[A-Z]+(?:_[A-Z]+)*$")
    @NotBlank
    private String id;

    @Size(min = 3, max = 36)
    @Pattern(regexp = "^[A-Z]+(?:_[A-Z]+)*$")
    @NotBlank
    private String secret;

    private List<AuthorizedGrantTypeEnum> authorizedGrantTypes;
    private List<AuthoritiesEnum> authorities;

    @Min(1)
    @Max(3600)
    @Positive(message = "accessTokenValiditySeconds must be positive")
    private Integer accessTokenValiditySeconds;

    @Min(1)
    @Max(86400)
    @Positive(message = "refreshTokenValiditySeconds must be positive")
    private Integer refreshTokenValiditySeconds;

    @JsonProperty("isActive")
    private boolean active;
    
    @Builder.Default
    private AuditSection auditSection = new AuditSection();
}
