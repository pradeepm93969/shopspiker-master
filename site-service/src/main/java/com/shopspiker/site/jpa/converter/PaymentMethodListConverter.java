package com.shopspiker.site.jpa.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopspiker.common.jpa.converter.AbstractJPAListConverter;

import jakarta.persistence.Converter;

@Converter
public class PaymentMethodListConverter extends AbstractJPAListConverter<PaymentMethod> {

    public PaymentMethodListConverter(ObjectMapper mapper) {
        super(PaymentMethod.class, mapper);
    }
}
