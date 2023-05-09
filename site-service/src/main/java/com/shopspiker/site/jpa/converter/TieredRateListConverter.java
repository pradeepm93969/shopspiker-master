package com.shopspiker.site.jpa.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopspiker.common.jpa.converter.AbstractJPAListConverter;

import jakarta.persistence.Converter;

@Converter
public class TieredRateListConverter extends AbstractJPAListConverter<TieredRate> {

    public TieredRateListConverter(ObjectMapper mapper) {
        super(TieredRate.class, mapper);
    }
}
