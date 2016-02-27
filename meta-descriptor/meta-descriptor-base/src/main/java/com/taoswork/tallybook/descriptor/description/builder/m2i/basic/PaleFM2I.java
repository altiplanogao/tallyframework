package com.taoswork.tallybook.descriptor.description.builder.m2i.basic;

import com.taoswork.tallybook.descriptor.description.builder.m2i.FM2I;
import com.taoswork.tallybook.descriptor.description.descriptor.field.base.IFieldInfoRW;
import com.taoswork.tallybook.descriptor.description.descriptor.field.typed.PaleFieldInfo;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.basic.PaleFieldMeta;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/2/22.
 */
public class PaleFM2I extends FM2I<PaleFieldMeta> {
    @Override
    public Class<? extends IFieldMeta> targetMeta() {
        return PaleFieldMeta.class;
    }

    public IFieldInfoRW doCreateInfo(IClassMeta topMeta,
                                     PaleFieldMeta fieldMeta,
                                     String name, String friendlyName,
                                     Collection<Class> collectionTypeReferenced) {
        boolean editable = fieldMeta.isEditable();
        PaleFieldInfo stringFieldInfo = new PaleFieldInfo(name, friendlyName, editable);
        return stringFieldInfo;
    }
}
