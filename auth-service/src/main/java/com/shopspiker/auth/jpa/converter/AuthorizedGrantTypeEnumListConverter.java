package com.shopspiker.auth.jpa.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopspiker.auth.jpa.constants.AuthorizedGrantTypeEnum;
import com.shopspiker.common.jpa.converter.AbstractJPAListConverter;

public class AuthorizedGrantTypeEnumListConverter extends AbstractJPAListConverter<AuthorizedGrantTypeEnum> {

    public AuthorizedGrantTypeEnumListConverter(ObjectMapper mapper) {
        super(AuthorizedGrantTypeEnum.class, mapper);
    }
}
