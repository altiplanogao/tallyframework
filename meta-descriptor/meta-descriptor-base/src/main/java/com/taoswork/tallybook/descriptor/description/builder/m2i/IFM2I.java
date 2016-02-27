package com.taoswork.tallybook.descriptor.description.builder.m2i;

import com.taoswork.tallybook.descriptor.description.descriptor.field.IFieldInfo;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/2/22.
 */
public interface IFM2I {
    Class<? extends IFieldMeta> targetMeta();

    IFieldInfo createInfo(IClassMeta topMeta, String prefix, IFieldMeta fieldMeta, Collection<Class> collectionTypeReferenced);
}
