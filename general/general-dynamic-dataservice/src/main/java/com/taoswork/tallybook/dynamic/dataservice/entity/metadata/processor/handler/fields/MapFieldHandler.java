package com.taoswork.tallybook.dynamic.dataservice.entity.metadata.processor.handler.fields;

import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.facet.MapFieldFacet;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.processor.ProcessResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class MapFieldHandler extends CollectionFieldHanalder {
    private static final Logger LOGGER = LoggerFactory.getLogger(MapFieldHandler.class);

    @Override
    public ProcessResult processCollectionField(Field field, FieldMetadata fieldMetadata) {
        Class type = field.getType();
        if(Map.class.isAssignableFrom(type)){
            Type genericType = field.getGenericType();
            if(!genericType.equals(type)){
                LOGGER.error("The Map field should specify its parameter type.");
            }
            MapFieldFacet facet = new MapFieldFacet(genericType);
            fieldMetadata.addFacet(facet);
            return ProcessResult.HANDLED;
        }
        return ProcessResult.INAPPLICABLE;
    }
}
