package com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response.entity.field;

import com.taoswork.tallybook.dynamic.datadomain.presentation.client.FieldType;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.FieldInfo;
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
    private static final Set<FieldType> basicFieldTypes;

    static {
        basicFieldTypes = new HashSet<FieldType>();
        basicFieldTypes.add(FieldType.ID);
        basicFieldTypes.add(FieldType.BOOLEAN);
        basicFieldTypes.add(FieldType.INTEGER);
        basicFieldTypes.add(FieldType.DECIMAL);
        basicFieldTypes.add(FieldType.STRING);

        basicFieldTypes.add(FieldType.NAME);
        basicFieldTypes.add(FieldType.EMAIL);
    }

    private FieldValueProcessorManager(){
        processors.put(SimpleFieldValueProcessor.PROCESSOR_NAME, new SimpleFieldValueProcessor());
        processors.put(FieldType.PHONE.name(), new PhoneFieldValueProcessor());
        processors.put(FieldType.UNKNOWN.name(), new UnknownFieldValueProcessor());
    }

    public static FieldValueProcessorManager instance(){
        return _instance;
    }

    public IFieldValueProcessor getProperProcessor(FieldInfo fieldInfo){
        FieldType fieldType = fieldInfo.getFieldType();
        if(basicFieldTypes.contains(fieldType)){
            return processors.getOrDefault(SimpleFieldValueProcessor.PROCESSOR_NAME, null);
        }

        IFieldValueProcessor processor = processors.getOrDefault(fieldType.name(), null);
        if(processor == null){
            processor = processors.getOrDefault(FieldType.UNKNOWN.name(), null);
        }

        return processor;
    }
}
