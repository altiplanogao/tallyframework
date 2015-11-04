package com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.typedcollection;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.base.CollectionFieldInfoBase;
import com.taoswork.tallybook.dynamic.datameta.metadata.EntryTypeUnion;

/**
 * Created by Gao Yuan on 2015/10/24.
 */
public class CollectionFieldInfo extends CollectionFieldInfoBase {
    public CollectionFieldInfo(String name, String friendlyName, boolean editable, EntryTypeUnion elementType) {
        super(name, friendlyName, editable);
    }
}
