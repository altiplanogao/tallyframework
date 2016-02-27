package com.taosworks.tallybook.descriptor.mongo.metadata.processor.field;

import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.FieldMetaMediate;
import com.taoswork.tallybook.descriptor.metadata.processor.ProcessResult;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.BaseFieldHandler;

import java.lang.reflect.Field;

import com.taosworks.tallybook.descriptor.mongo.metadata.fieldmetadata.ObjectIdFieldMeta;
import org.bson.types.ObjectId;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
public class ObjectIdFieldMetaHandler extends BaseFieldHandler{
    @Override
    protected boolean canProcess(Field field, FieldMetaMediate metaMediate) {
        return ObjectId.class.equals(field.getType());
    }

    @Override
    protected ProcessResult doProcess(Field field, FieldMetaMediate metaMediate) {
        metaMediate.setMetaSeed(new ObjectIdFieldMeta.Seed());
        return ProcessResult.HANDLED;
    }
}
