package com.taoswork.tallybook.dataservice.mongo.core.query.criteria.converter;

import com.taoswork.tallybook.dataservice.core.dao.query.criteria.converter.FilterValueConverter;

public class StringLikeFilterValueConverter implements FilterValueConverter<String> {

    @Override
    public String convert(Class type, String stringValue) {
        if(null == stringValue)
            return null;
        return stringValue.toLowerCase();
    }

}
