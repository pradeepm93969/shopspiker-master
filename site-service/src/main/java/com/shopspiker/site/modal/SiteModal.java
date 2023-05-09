package com.shopspiker.site.modal;

import com.shopspiker.common.jpa.constant.JpaConstants;
import com.shopspiker.common.jpa.entity.AuditSection;
import com.shopspiker.site.jpa.converter.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SiteModal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(min = 3, max = 3)
    @Pattern(regexp = "^[A-Z]{3}$")
    @NotBlank
    private String id;

    @Size(min = 3, max = 50)
    @NotBlank
    private String name;

    private boolean active;

    private List<Locale> locales;

    private List<Currency> currencies;

    private List<PaymentMethod> paymentMethods;

    private List<ShippingMethod> shippingMethods;

    private List<TaxRateModal> taxes;

    @Size(min = 3, max = 36)
    @NotBlank
    private String catalogId;

    @Builder.Default
    private AuditSection auditSection = new AuditSection();
}
