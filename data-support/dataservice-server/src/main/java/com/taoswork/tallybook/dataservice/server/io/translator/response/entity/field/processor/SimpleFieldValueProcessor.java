package com.taoswork.tallybook.dataservice.server.io.translator.response.entity.field.processor;

import com.taoswork.tallybook.dataservice.server.io.translator.response.entity.field.IFieldValueProcessor;

/**
 * Created by Gao Yuan on 2015/6/1.
 */
public class SimpleFieldValueProcessor implements IFieldValueProcessor {
    public static final String PROCESSOR_NAME = "SimpleFieldValueProcessor";

    @Override
    public String getStringValue(Object value) {
        if (null == value) {
            return "";
        }
        return value.toString();
    }
}
