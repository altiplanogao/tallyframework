package com.taoswork.tallybook.dynamic.dataservice.entity.metadata.processor.handler.fields;

import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.facet.ListFieldFacet;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.processor.ProcessResult;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.FieldMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class SetFieldHandler extends CollectionFieldHanalder {
    private static final Logger LOGGER = LoggerFactory.getLogger(ListFieldFacet.class);

    @Override
    public ProcessResult processCollectionField(Field field, FieldMetadata fieldMetadata) {
        Class type = field.getType();
        if(Set.class.isAssignableFrom(type)){
            Type genericType = field.getGenericType();
            if(!genericType.equals(type)){
                LOGGER.error("The List field should specify its parameter type.");
            }
            ListFieldFacet facet = new ListFieldFacet(genericType);

            fieldMetadata.addFacet(facet);
            return ProcessResult.HANDLED;
        }
        return ProcessResult.INAPPLICABLE;
    }
}
