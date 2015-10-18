package com.taoswork.tallybook.dynamic.dataservice.core.dao.query.criteria.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LongFilterValueConverter implements FilterValueConverter<Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LongFilterValueConverter.class);

    @Override
    public Long convert(Class type, String stringValue) {
        if(Long.class.isAssignableFrom(type)){
            try{
                return Long.decode(stringValue);
            }catch (Exception e){
                return null;
            }
        }else {
            return null;
        }
    }

}
