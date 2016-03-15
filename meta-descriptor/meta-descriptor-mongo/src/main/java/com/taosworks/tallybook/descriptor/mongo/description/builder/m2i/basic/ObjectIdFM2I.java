package com.taosworks.tallybook.descriptor.mongo.description.builder.m2i.basic;

import com.taoswork.tallybook.descriptor.description.builder.m2i.FM2I;
import com.taoswork.tallybook.descriptor.description.descriptor.field.base.IFieldInfoRW;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taosworks.tallybook.descriptor.mongo.description.descriptor.field.typed.ObjectIdFieldInfo;
import com.taosworks.tallybook.descriptor.mongo.metadata.fieldmetadata.ObjectIdFieldMeta;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/3/15.
 */
public class ObjectIdFM2I extends FM2I<ObjectIdFieldMeta> {

    @Override
    public Class<? extends IFieldMeta> targetMeta() {
        return ObjectIdFieldMeta.class;
    }

    @Override
    public IFieldInfoRW doCreateInfo(IClassMeta topMeta, ObjectIdFieldMeta fieldMeta, String name, String friendlyName, Collection<Class> collectionTypeReferenced) {
        boolean editable = fieldMeta.isEditable();
        ObjectIdFieldInfo stringFieldInfo = new ObjectIdFieldInfo(name, friendlyName, editable);
        return stringFieldInfo;
    }
}
