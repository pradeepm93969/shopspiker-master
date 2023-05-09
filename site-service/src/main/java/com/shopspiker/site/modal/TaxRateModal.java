package com.shopspiker.site.modal;

import com.shopspiker.common.jpa.entity.AuditSection;
import com.shopspiker.site.jpa.converter.PaymentMethod;
import com.shopspiker.site.jpa.converter.ShippingMethod;
import com.shopspiker.site.jpa.converter.TieredRate;
import com.shopspiker.site.jpa.converter.TieredRateListConverter;
import com.shopspiker.site.jpa.entity.Site;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.money.CurrencyUnit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaxRateModal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(min = 3, max = 36)
    @Pattern(regexp = "^[A-Z]+(?:_[A-Z]+)*$")
    @NotBlank
    private String id;

    @Size(min = 3, max = 50)
    @NotBlank
    private String name;

    @Builder.Default
    private boolean active = true;

    private BigDecimal rate;

    private Currency currency;

    private List<String> productTypes;

    @Builder.Default
    private boolean shippingPriceInclusiveOfTax = true;

    @Builder.Default
    private boolean itemPriceInclusiveOfTax = true;

    @Builder.Default
    private Integer sortOrder = 999;

    private SiteModal site;

    @Builder.Default
    private AuditSection auditSection = new AuditSection();
}
