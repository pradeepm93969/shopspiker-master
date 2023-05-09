package com.shopspiker.common.jpa.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Converter;

@Converter
public class LongListConverter extends AbstractJPAListConverter<Long> {

    public LongListConverter(ObjectMapper mapper) {
        super(Long.class, mapper);
    }
}
