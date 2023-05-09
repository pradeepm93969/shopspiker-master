package com.shopspiker.common.jpa.converter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.Converter;
import java.util.Locale;

@Converter
public class TranslationMapConverter extends AbstractJPAMapConverter<Locale, String> {

    public TranslationMapConverter(ObjectMapper mapper) {
        super(Locale.class, String.class, mapper);
    }
}
