package com.taoswork.tallybook.descriptor.description.builder.m2i.list;

import com.taoswork.tallybook.descriptor.description.builder.m2i.FM2I;
import com.taoswork.tallybook.descriptor.description.descriptor.field.IFieldInfo;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.exception.MetadataException;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.list.ListFieldMeta;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/2/22.
 */
public abstract class ListFM2I<Fm extends ListFieldMeta> extends FM2I<Fm> {

    @Override
    public IFieldInfo createInfo(IClassMeta topMeta, String prefix, IFieldMeta fieldMeta, Collection<Class> collectionTypeReferenced) {
        if(fieldMeta instanceof ListFieldMeta){
            final ListFieldMeta typedFieldMeta = (ListFieldMeta) fieldMeta;
            final Class referencingCollectionEntryCls = typedFieldMeta.getPresentationClass();
            if (referencingCollectionEntryCls != null) {
                collectionTypeReferenced.add(referencingCollectionEntryCls);
            }
        } else {
            throw new MetadataException("Unproper FM2I registered");
        }
        return super.createInfo(topMeta, prefix, fieldMeta, collectionTypeReferenced);
    }
}
