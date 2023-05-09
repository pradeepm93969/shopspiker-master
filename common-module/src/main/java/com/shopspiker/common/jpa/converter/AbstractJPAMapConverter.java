package com.shopspiker.common.jpa.converter;

import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.persistence.AttributeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractJPAMapConverter<K, V> implements AttributeConverter<Map<K, V>, String> {

    private ObjectMapper objectMapper;
    private MapType mapType;

    public AbstractJPAMapConverter(Class<?> keyType, Class<?> valueType, ObjectMapper mapper) {
        this.objectMapper = mapper;
        this.mapType = this.objectMapper.getTypeFactory()
                .constructMapType(LinkedHashMap.class, keyType, valueType);
    }

    @Override
    public String convertToDatabaseColumn(Map<K, V> attribute) {
        String valueJson = "{}";
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
    public Map<K, V> convertToEntityAttribute(String dbData) {
        Map<K, V> value = new LinkedHashMap<>();
        if (dbData != null) {
            try {
                value = objectMapper.readValue(dbData, this.mapType);
            } catch (final JsonProcessingException e) {
                log.error("JSON reading error", e);
            }
        }
        return value;
    }
}
