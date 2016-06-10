package com.taoswork.tallybook.dataservice.server.io.translator.response.entity.field.processor;

import com.taoswork.tallybook.dataservice.server.io.translator.response.entity.field.IFieldValueProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Gao Yuan on 2015/6/1.
 */
public class UnknownFieldValueProcessor implements IFieldValueProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(UnknownFieldValueProcessor.class);

    @Override
    public String getStringValue(Object value) {
        LOGGER.error("Handling unknown field value: {}", value);
        if (null == value) {
            return "";
        }
        return value.toString();
    }
}
