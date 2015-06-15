package com.taoswork.tallybook.dynamic.dataservice.entity.metadata.processor.handler.fields;

import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.processor.ProcessResult;

import javax.persistence.Id;
import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class IdFieldHandler implements IFieldHandler {
    @Override
    public ProcessResult process(Field field, FieldMetadata fieldMetadata) {
        Id idAnnoation = field.getDeclaredAnnotation(Id.class);
        if(null == idAnnoation){
            fieldMetadata.setId(false);
            return ProcessResult.INAPPLICABLE;
        }else {
            fieldMetadata.setId(true);
            return ProcessResult.HANDLED;
        }
    }
}
