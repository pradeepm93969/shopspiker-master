package com.shopspiker.common.jpa.converter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.Converter;

@Converter
public class StringListConverter extends AbstractJPAListConverter<String> {

    public StringListConverter(ObjectMapper mapper) {
        super(String.class, mapper);
    }
}
