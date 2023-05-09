package com.shopspiker.common.jpa.converter;

import jakarta.persistence.AttributeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractJPAConverter<T> implements AttributeConverter<T, String> {

    private ObjectMapper objectMapper;
    private JavaType objectType;

    public AbstractJPAConverter(Class<?> elementType, ObjectMapper mapper) {
        this.objectMapper = mapper;
        this.objectType = this.objectMapper.getTypeFactory()
                .constructType(elementType);
    }

    @Override
    public String convertToDatabaseColumn(T attribute) {
        String valueJson = null;
        if (attribute != null) {
            try {
                valueJson = objectMapper.writeValueAsString(attribute);
            } catch (final JsonProcessingException e) {
                log.error("JSON writing error", e);
            }
        }
        return valueJson;
    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        T value = null;
        if (dbData != null) {
            try {
                value = objectMapper.readValue(dbData, objectType);
            } catch (final JsonProcessingException e) {
                log.error("JSON reading error", e);
            }
        }
        return value;
    }
}
