package com.shopspiker.site.jpa.entity;

import com.shopspiker.common.jpa.Auditable;
import com.shopspiker.common.jpa.constant.JpaConstants;
import com.shopspiker.common.jpa.converter.StringListConverter;
import com.shopspiker.common.jpa.entity.AuditSection;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@Table(name = "TAX_RATE")
public class TaxRate implements Auditable, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", nullable = false, length = JpaConstants.ID_LENGTH)
    private String id;

    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    @Column(name = "ACTIVE")
    private boolean active;

    @Column(name = "RATE" , nullable= false , precision=7, scale=4)
    private BigDecimal rate;

    @Column(name = "PAYMENT_METHODS", length = 2000)
    @Convert(converter = StringListConverter.class)
    private List<String> productTypes;

    @Column(name = "CURRENCY")
    private Currency currency;

    @Column(name = "SHIPPING_PRICE_INCLUSIVE_OF_TAX")
    private boolean shippingPriceInclusiveOfTax;

    @Column(name = "ITEM_PRICE_INCLUSIVE_OF_TAX")
    private boolean itemPriceInclusiveOfTax;

    @Column(name = "SORT_ORDER")
    private Integer sortOrder;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="SITE_ID", nullable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Site site;

    @Embedded
    private AuditSection auditSection = new AuditSection();

}
