package com.shopspiker.auth.modal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shopspiker.auth.jpa.constants.PermissionsEnum;
import com.shopspiker.common.jpa.entity.AuditSection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleModal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(min = 3, max = 50)
    @Pattern(regexp = "^[ROLE]+(?:_[A-Z0-9]+)+$")
    @NotBlank
    private String id;

    @Size(min = 3, max = 50)
    @NotBlank(message = "Name is mandatory")
    private String name;

    @JsonProperty("isActive")
    private boolean active;

    @Builder.Default
    private List<PermissionsEnum> permissions = new ArrayList<>();

    @Builder.Default
    private AuditSection auditSection = new AuditSection();

}
