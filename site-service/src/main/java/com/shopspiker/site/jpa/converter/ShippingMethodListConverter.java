package com.shopspiker.site.jpa.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopspiker.common.jpa.converter.AbstractJPAListConverter;

import jakarta.persistence.Converter;

@Converter
public class ShippingMethodListConverter extends AbstractJPAListConverter<ShippingMethod> {

    public ShippingMethodListConverter(ObjectMapper mapper) {
        super(ShippingMethod.class, mapper);
    }
}
