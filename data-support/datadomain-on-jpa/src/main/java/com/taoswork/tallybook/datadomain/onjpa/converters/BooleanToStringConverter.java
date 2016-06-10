package com.taoswork.tallybook.datadomain.onjpa.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanToStringConverter implements AttributeConverter<Boolean, String> {
    public final static String TRUE = "Y";
    public final static String FALSE = "N";

    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        if (attribute == null)
            return "";
        return attribute ? TRUE : FALSE;
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return TRUE.equals(dbData);
    }
}
