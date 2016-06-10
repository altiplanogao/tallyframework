package com.taoswork.tallybook.dataservice.core.dao.query.criteria.converter;

public class DoNothingValueConverter implements FilterValueConverter<String> {

    @Override
    public String convert(Class type, String stringValue) {
        return stringValue;
    }

}
