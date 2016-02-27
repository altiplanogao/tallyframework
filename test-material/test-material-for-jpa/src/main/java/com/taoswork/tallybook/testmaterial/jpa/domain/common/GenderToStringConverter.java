package com.taoswork.tallybook.testmaterial.jpa.domain.common;

import javax.persistence.AttributeConverter;

public class GenderToStringConverter implements AttributeConverter<Gender, String> {
    @Override
    public String convertToDatabaseColumn(Gender attribute) {
        return attribute == null ? "" : attribute.getType();
    }

    @Override
    public Gender convertToEntityAttribute(String dbData) {
        return Gender.fromType(dbData);
    }
}
