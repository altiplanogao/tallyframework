package com.taoswork.tallybook.descriptor.metadata.processor.handler.fields.list;

import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.FieldMetaMediate;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.BaseCollectionFieldHandlerDispatcher;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/2/20.
 */
public class ListFieldHandlerDispatcher
        extends BaseCollectionFieldHandlerDispatcher {

    public ListFieldHandlerDispatcher() {
        fieldHandlerList.add(new PrimitiveListFieldHandler());
        fieldHandlerList.add(new BasicListFieldHandler());
        fieldHandlerList.add(new EntityListFieldHandler());
        fieldHandlerList.add(new LookupListFieldHandler());
        fieldHandlerList.add(new AdornedLookupListFieldHandler());
    }

    @Override
    protected boolean canProcess(Field field, FieldMetaMediate metaMediate) {
        Class collectionType = field.getType();
        if (Collection.class.isAssignableFrom(collectionType)) {
            return true;
        }
        return false;
    }
}
