package com.taoswork.tallybook.descriptor.description.builder.m2i.basic;

import com.taoswork.tallybook.descriptor.description.builder.m2i.FM2I;
import com.taoswork.tallybook.descriptor.description.descriptor.field.base.IFieldInfoRW;
import com.taoswork.tallybook.descriptor.description.descriptor.field.typed.ForeignKeyFieldInfo;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.basic.ForeignEntityFieldMeta;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/2/22.
 */
public class ForeignEntityFM2I extends FM2I<ForeignEntityFieldMeta> {
    @Override
    public Class<? extends IFieldMeta> targetMeta() {
        return ForeignEntityFieldMeta.class;
    }

    @Override
    public IFieldInfoRW doCreateInfo(IClassMeta topMeta,
                                     ForeignEntityFieldMeta fieldMeta, String name, String friendlyName,
                                     Collection<Class> collectionTypeReferenced) {
        boolean editable = fieldMeta.isEditable();
        ForeignEntityFieldMeta feFm = (ForeignEntityFieldMeta) fieldMeta;
        ForeignKeyFieldInfo fkFieldInfo = new ForeignKeyFieldInfo(name, friendlyName, editable,
                feFm.getDeclareType(), feFm.getTargetType(),
                feFm.getIdField(), feFm.getDisplayField());
        return fkFieldInfo;
    }
}
