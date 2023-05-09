package com.shopspiker.auth.jpa.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopspiker.auth.jpa.constants.AuthoritiesEnum;
import com.shopspiker.common.jpa.converter.AbstractJPAListConverter;

public class AuthoritiesEnumListConverter extends AbstractJPAListConverter<AuthoritiesEnum> {

    public AuthoritiesEnumListConverter(ObjectMapper mapper) {
        super(AuthoritiesEnum.class, mapper);
    }
}
