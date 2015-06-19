package com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response.entity.field;

import com.taoswork.tallybook.dynamic.datadomain.presentation.client.SupportedFieldType;
import com.taoswork.tallybook.dynamic.dataservice.entity.edo.FieldEdo;
import com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response.entity.field.processor.PhoneFieldValueProcessor;
import com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response.entity.field.processor.SimpleFieldValueProcessor;
import com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response.entity.field.processor.UnknownFieldValueProcessor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/6/1.
 */
public class FieldValueProcessorManager {
    private static final FieldValueProcessorManager _instance = new FieldValueProcessorManager();
    private final Map<String, IFieldValueProcessor> processors = new HashMap<String, IFieldValueProcessor>();
    private static final Set<SupportedFieldType> basicFieldTypes;

    static {
        basicFieldTypes = new HashSet<SupportedFieldType>();
        basicFieldTypes.add(SupportedFieldType.ID);
        basicFieldTypes.add(SupportedFieldType.BOOLEAN);
        basicFieldTypes.add(SupportedFieldType.INTEGER);
        basicFieldTypes.add(SupportedFieldType.DECIMAL);
        basicFieldTypes.add(SupportedFieldType.STRING);

        basicFieldTypes.add(SupportedFieldType.NAME);
        basicFieldTypes.add(SupportedFieldType.EMAIL);
    }

    private FieldValueProcessorManager(){
        processors.put(SimpleFieldValueProcessor.PROCESSOR_NAME, new SimpleFieldValueProcessor());
        processors.put(SupportedFieldType.PHONE.name(), new PhoneFieldValueProcessor());
        processors.put(SupportedFieldType.UNKNOWN.name(), new UnknownFieldValueProcessor());
    }

    public static FieldValueProcessorManager instance(){
        return _instance;
    }

    public IFieldValueProcessor getProperProcessor(FieldEdo fieldEdo){
        SupportedFieldType fieldType = fieldEdo.getFieldType();
        if(basicFieldTypes.contains(fieldType)){
            return processors.getOrDefault(SimpleFieldValueProcessor.PROCESSOR_NAME, null);
        }

        IFieldValueProcessor processor = processors.getOrDefault(fieldType.name(), null);
        if(processor == null){
            processor = processors.getOrDefault(SupportedFieldType.UNKNOWN.name(), null);
        }

        return processor;
    }
}
