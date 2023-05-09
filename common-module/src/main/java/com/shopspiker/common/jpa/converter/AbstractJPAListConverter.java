package com.shopspiker.common.jpa.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import jakarta.persistence.AttributeConverter;
import lombok.extern.slf4j.Slf4j;

import jakarta.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class AbstractJPAListConverter<T> implements AttributeConverter<List<T>, String> {

    private ObjectMapper objectMapper;
    private CollectionType collectionType;

    public AbstractJPAListConverter(Class<?> elementType, ObjectMapper mapper) {
        this.objectMapper = mapper;
        this.collectionType = this.objectMapper.getTypeFactory()
                .constructCollectionType(ArrayList.class, elementType);
    }

    @Override
    public String convertToDatabaseColumn(List<T> attribute) {
        String valueJson = "[]";
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
    public List<T> convertToEntityAttribute(String dbData) {
        List<T> value = new ArrayList<>();
        if (dbData != null) {
            try {
                value = objectMapper.readValue(dbData, this.collectionType);
            } catch (final JsonProcessingException e) {
                log.error("JSON reading error", e);
            }
        }
        return value;
    }
}
