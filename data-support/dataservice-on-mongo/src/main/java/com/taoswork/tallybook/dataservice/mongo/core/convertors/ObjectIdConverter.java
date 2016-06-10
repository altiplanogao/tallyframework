package com.taoswork.tallybook.dataservice.mongo.core.convertors;

import org.apache.commons.beanutils.converters.AbstractConverter;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

/**
 * Created by Gao Yuan on 2016/3/21.
 */
public class ObjectIdConverter extends AbstractConverter {

    @Override
    protected <T> T convertToType(Class<T> type, Object value) throws Throwable {
        if(ObjectId.class.equals(type)){
            String stringValue = value.toString();
            if(StringUtils.isEmpty(stringValue)){
                return null;
            }
            return type.cast(new ObjectId(stringValue));
        }
        throw conversionException(type, value);
    }

    @Override
    protected Class<?> getDefaultType() {
        return ObjectId.class;
    }
}
