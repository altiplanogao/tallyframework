package com.taoswork.tallybook.descriptor.description.builder.m2i.list;

import com.taoswork.tallybook.descriptor.description.descriptor.field.base.IFieldInfoRW;
import com.taoswork.tallybook.descriptor.description.descriptor.field.typedcollection.BasicCollectionFieldInfo;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.list.BasicListFieldMeta;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/2/22.
 */
public class BasicListFM2I extends ListFM2I<BasicListFieldMeta> {
    @Override
    public IFieldInfoRW doCreateInfo(IClassMeta topMeta, BasicListFieldMeta fieldMeta,
                                     String name, String friendlyName, Collection<Class> collectionTypeReferenced) {
        IFieldInfoRW result = null;
        boolean editable = fieldMeta.isEditable();
        final Class referencingCollectionEntryCls = fieldMeta.getPresentationClass();
        final String referencingCollectionEntryClsName = (referencingCollectionEntryCls != null) ? referencingCollectionEntryCls.getName() : "";

        result = new BasicCollectionFieldInfo(name, friendlyName, editable, referencingCollectionEntryClsName);
        return result;
    }

    @Override
    public Class<? extends IFieldMeta> targetMeta() {
        return BasicListFieldMeta.class;
    }
}
