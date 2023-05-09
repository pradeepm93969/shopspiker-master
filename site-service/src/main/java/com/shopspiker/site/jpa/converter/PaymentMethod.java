package com.shopspiker.site.jpa.converter;

import com.shopspiker.site.jpa.constants.CardTypesEnum;
import com.shopspiker.site.jpa.constants.PaymentMethodsEnum;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PaymentMethod implements Serializable {

    private static final long serialVersionUID = 1L;

    private PaymentMethodsEnum paymentMethodType;

    private List<TieredRate> rates;

    private List<CardTypesEnum> allowedCardTypes;

}
