package com.shopspiker.site.jpa.entity;

import com.shopspiker.common.jpa.Auditable;
import com.shopspiker.common.jpa.constant.JpaConstants;
import com.shopspiker.common.jpa.entity.AuditSection;
import com.shopspiker.site.jpa.converter.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@Table(name = "SITE")
public class Site implements Auditable, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = JpaConstants.SITE_ID_LENGTH)
    private String id;

    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    @Column(name = "ACTIVE")
    private boolean active;

    @Column(name = "CATALOG_ID", length = JpaConstants.ID_LENGTH)
    private String catalogId;

    @Column(name = "LOCALES", length = 100)
    @Convert(converter = LocaleListConverter.class)
    private List<Locale> locales;

    @Column(name = "CURRENCIES", length = 40)
    @Convert(converter = CurrencyListConverter.class)
    private List<Currency> currencies;

    @Column(name = "PAYMENT_METHODS", length = 2000)
    @Convert(converter = PaymentMethodListConverter.class)
    private List<PaymentMethod> paymentMethods;

    @Column(name = "SHIPPING_METHODS", length = 2000)
    @Convert(converter = ShippingMethodListConverter.class)
    private List<ShippingMethod> shippingMethods;



    @Embedded
    private AuditSection auditSection = new AuditSection();
}
