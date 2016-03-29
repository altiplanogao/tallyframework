package com.taoswork.tallybook.business.datadomain.tallyuser.convert;

import com.taoswork.tallybook.business.datadomain.tallyuser.Gender;

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
