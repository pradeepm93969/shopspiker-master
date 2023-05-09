package com.shopspiker.common.jpa.converter;

import jakarta.persistence.AttributeConverter;

public class PrimitiveBooleanConverter implements AttributeConverter<Boolean, Character> {

    @Override
    public Character convertToDatabaseColumn(Boolean attribute) {
        if (attribute != null) {
            if (attribute) {
                return 'Y';
            } else {
                return 'N';
            }
        }
        return 'N';
    }

    @Override
    public Boolean convertToEntityAttribute(Character dbData) {
        if (dbData != null) {
            return dbData.equals('Y');
        }
        return false;
    }
}
