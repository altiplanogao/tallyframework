package com.taoswork.tallybook.descriptor.metadata.processor.handler.fields.map;

import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.FieldMetaMediate;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.BaseCollectionFieldHandlerDispatcher;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by Gao Yuan on 2016/2/20.
 */
public class MapFieldHandlerDispatcher
        extends BaseCollectionFieldHandlerDispatcher {

    public MapFieldHandlerDispatcher() {
        fieldHandlerList.add(new SlothMapFieldHandler());
        fieldHandlerList.add(new BasicMapFieldHandler());
        fieldHandlerList.add(new EntityMapFieldHandler());
        fieldHandlerList.add(new LookupMapFieldHandler());
        fieldHandlerList.add(new AdornedLookupMapFieldHandler());
    }

    @Override
    protected boolean canProcess(Field field, FieldMetaMediate metaMediate) {
        Class collectionType = field.getType();
        if (Map.class.isAssignableFrom(collectionType)) {
            return true;
        }
        return false;
    }
}
