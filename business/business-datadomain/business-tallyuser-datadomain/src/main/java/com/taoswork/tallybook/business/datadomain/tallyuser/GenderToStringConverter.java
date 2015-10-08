package com.taoswork.tallybook.business.datadomain.tallyuser;

import javax.persistence.AttributeConverter;

public class GenderToStringConverter implements AttributeConverter<Gender, String> {
    @Override
    public String convertToDatabaseColumn(Gender attribute) {
        if(attribute == null)
            return "";
        return attribute.getType();
    }

    @Override
    public Gender convertToEntityAttribute(String dbData) {
        return Gender.fromType(dbData);
    }
}
