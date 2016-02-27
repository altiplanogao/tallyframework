package com.taoswork.tallybook.descriptor.description.builder.m2i.map;

import com.taoswork.tallybook.descriptor.description.descriptor.field.base.IFieldInfoRW;
import com.taoswork.tallybook.descriptor.description.descriptor.field.typedcollection.EntityCollectionFieldInfo;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.map.EntityMapFieldMeta;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/2/22.
 */
public class EntityMapFM2I extends MapFM2I<EntityMapFieldMeta> {
    @Override
    public IFieldInfoRW doCreateInfo(IClassMeta topMeta, EntityMapFieldMeta fieldMeta, String name, String friendlyName, Collection<Class> collectionTypeReferenced) {
        boolean editable = fieldMeta.isEditable();
        final Class referencingCollectionEntryCls = fieldMeta.getPresentationClass();
        EntityCollectionFieldInfo fieldInfo = new EntityCollectionFieldInfo(name, friendlyName, editable,
                referencingCollectionEntryCls.getName());
        return fieldInfo;
    }

    @Override
    public Class<? extends IFieldMeta> targetMeta() {
        return EntityMapFieldMeta.class;
    }
}
