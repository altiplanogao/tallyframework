package com.taoswork.tallybook.descriptor.description.builder.m2i.map;

import com.taoswork.tallybook.descriptor.description.builder.m2i.FM2I;
import com.taoswork.tallybook.descriptor.description.descriptor.field.IFieldInfo;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.exception.MetadataException;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.map.MapFieldMeta;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/2/22.
 */
public abstract class MapFM2I<Fm extends MapFieldMeta> extends FM2I<Fm> {

    @Override
    public IFieldInfo createInfo(IClassMeta topMeta, String prefix, IFieldMeta fieldMeta, Collection<Class> collectionTypeReferenced) {
        if(fieldMeta instanceof MapFieldMeta){
            final MapFieldMeta typedFieldMeta = (MapFieldMeta) fieldMeta;
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
