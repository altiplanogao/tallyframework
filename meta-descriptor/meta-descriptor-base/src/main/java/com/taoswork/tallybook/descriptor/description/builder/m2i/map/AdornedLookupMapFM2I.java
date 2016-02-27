package com.taoswork.tallybook.descriptor.description.builder.m2i.map;

import com.taoswork.tallybook.descriptor.description.descriptor.field.base.IFieldInfoRW;
import com.taoswork.tallybook.descriptor.description.descriptor.field.typedcollection.AdornedLookupCollectionFieldInfo;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.map.AdornedLookupMapFieldMeta;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/2/22.
 */
public class AdornedLookupMapFM2I extends MapFM2I<AdornedLookupMapFieldMeta> {
    @Override
    public IFieldInfoRW doCreateInfo(IClassMeta topMeta, AdornedLookupMapFieldMeta fieldMeta, String name, String friendlyName, Collection<Class> collectionTypeReferenced) {
        boolean editable = fieldMeta.isEditable();
        final Class referencingCollectionEntryCls = fieldMeta.getPresentationClass();
        AdornedLookupCollectionFieldInfo fieldInfo = new AdornedLookupCollectionFieldInfo(name, friendlyName, editable,
                referencingCollectionEntryCls.getName());
        return fieldInfo;
    }

    @Override
    public Class<? extends IFieldMeta> targetMeta() {
        return AdornedLookupMapFieldMeta.class;
    }
}
