package com.shopspiker.auth.jpa.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopspiker.auth.jpa.constants.PermissionsEnum;
import com.shopspiker.common.jpa.converter.AbstractJPAListConverter;

public class PermissionsEnumListConverter extends AbstractJPAListConverter<PermissionsEnum> {

    public PermissionsEnumListConverter(ObjectMapper mapper) {
        super(PermissionsEnum.class, mapper);
    }
}
