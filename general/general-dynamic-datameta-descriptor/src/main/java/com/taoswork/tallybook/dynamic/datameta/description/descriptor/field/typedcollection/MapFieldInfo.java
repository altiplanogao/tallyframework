package com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.typedcollection;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.base.CollectionFieldInfoBase;
import com.taoswork.tallybook.dynamic.datameta.metadata.EntryTypeUnion;

/**
 * Created by Gao Yuan on 2015/10/24.
 */
public class MapFieldInfo extends CollectionFieldInfoBase {
    public MapFieldInfo(String name, String friendlyName, boolean editable, EntryTypeUnion keyType, EntryTypeUnion valueType) {
        super(name, friendlyName, editable);
    }
}
