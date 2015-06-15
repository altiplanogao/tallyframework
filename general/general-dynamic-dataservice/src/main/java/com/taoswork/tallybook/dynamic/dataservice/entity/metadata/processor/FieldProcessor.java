package com.taoswork.tallybook.dynamic.dataservice.entity.metadata.processor;

import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.processor.handler.basic.MultiMetadataHandler;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.processor.handler.fields.*;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.FieldMetadata;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class FieldProcessor
        extends MultiMetadataHandler<Field, FieldMetadata>
        implements IFieldHandler{

    public FieldProcessor(){
        metaHandlers.add(new BasicFieldHandler());
        metaHandlers.add(new IdFieldHandler());
        metaHandlers.add(new GeneralFieldHandler());
        metaHandlers.add(new EmbeddedFieldHandler());
        metaHandlers.add(new ArrayFieldHandler());
        metaHandlers.add(new ListFieldHandler());
        metaHandlers.add(new SetFieldHandler());
        metaHandlers.add(new MapFieldHandler());
    }
}
