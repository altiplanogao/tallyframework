package com.taoswork.tallybook.descriptor.description.builder.m2i.map;

import com.taoswork.tallybook.descriptor.description.descriptor.field.base.IFieldInfoRW;
import com.taoswork.tallybook.descriptor.description.descriptor.field.typedcollection.BasicCollectionFieldInfo;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.map.BasicMapFieldMeta;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/2/22.
 */
public class BasicMapFM2I extends MapFM2I<BasicMapFieldMeta> {
    @Override
    public IFieldInfoRW doCreateInfo(IClassMeta topMeta, BasicMapFieldMeta fieldMeta, String name, String friendlyName, Collection<Class> collectionTypeReferenced) {
        boolean editable = fieldMeta.isEditable();
        final Class referencingCollectionEntryCls = fieldMeta.getPresentationClass();
        BasicCollectionFieldInfo mapFieldInfo = new BasicCollectionFieldInfo(name, friendlyName, editable,
                referencingCollectionEntryCls.getName());
        return mapFieldInfo;
    }

    @Override
    public Class<? extends IFieldMeta> targetMeta() {
        return BasicMapFieldMeta.class;
    }
}
