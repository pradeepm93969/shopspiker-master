package com.shopspiker.site.jpa.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopspiker.common.jpa.converter.AbstractJPAListConverter;

import jakarta.persistence.Converter;
import java.util.Currency;
import java.util.Locale;

@Converter
public class CurrencyListConverter extends AbstractJPAListConverter<Currency> {

    public CurrencyListConverter(ObjectMapper mapper) {
        super(Currency.class, mapper);
    }
}
