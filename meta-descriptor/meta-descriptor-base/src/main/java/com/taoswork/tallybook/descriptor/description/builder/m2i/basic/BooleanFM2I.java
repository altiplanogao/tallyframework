package com.taoswork.tallybook.descriptor.description.builder.m2i.basic;

import com.taoswork.tallybook.descriptor.description.builder.m2i.FM2I;
import com.taoswork.tallybook.descriptor.description.descriptor.field.base.IFieldInfoRW;
import com.taoswork.tallybook.descriptor.description.descriptor.field.typed.BooleanFieldInfo;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.basic.BooleanFieldMeta;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/2/22.
 */
public class BooleanFM2I extends FM2I<BooleanFieldMeta> {
    @Override
    public Class<? extends IFieldMeta> targetMeta() {
        return BooleanFieldMeta.class;
    }

    @Override
    public IFieldInfoRW doCreateInfo(IClassMeta topMeta,
                                     BooleanFieldMeta fieldMeta,
                                     String name, String friendlyName,
                                     Collection<Class> collectionTypeReferenced) {
        boolean editable = fieldMeta.isEditable();
        BooleanFieldInfo booleanFieldInfo = new BooleanFieldInfo(name, friendlyName, editable, ((BooleanFieldMeta) fieldMeta).getMode());
        return booleanFieldInfo;
    }
}
