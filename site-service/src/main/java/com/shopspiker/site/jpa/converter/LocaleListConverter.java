package com.shopspiker.site.jpa.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopspiker.common.jpa.converter.AbstractJPAListConverter;

import jakarta.persistence.Converter;
import java.util.Locale;

@Converter
public class LocaleListConverter extends AbstractJPAListConverter<Locale> {

    public LocaleListConverter(ObjectMapper mapper) {
        super(Locale.class, mapper);
    }

}
