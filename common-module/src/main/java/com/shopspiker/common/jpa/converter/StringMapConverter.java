package com.shopspiker.common.jpa.converter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.Converter;

@Converter
public class StringMapConverter extends AbstractJPAMapConverter<String, String> {

    public StringMapConverter(ObjectMapper mapper) {
        super(String.class, String.class, mapper);
    }
}
