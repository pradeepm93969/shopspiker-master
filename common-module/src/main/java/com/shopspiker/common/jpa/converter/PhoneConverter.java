package com.shopspiker.common.jpa.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopspiker.common.jpa.entity.Phone;

import jakarta.persistence.Converter;

@Converter
public class PhoneConverter extends AbstractJPAConverter<Phone> {

    public PhoneConverter(ObjectMapper mapper) {
        super(Phone.class, mapper);
    }

}
