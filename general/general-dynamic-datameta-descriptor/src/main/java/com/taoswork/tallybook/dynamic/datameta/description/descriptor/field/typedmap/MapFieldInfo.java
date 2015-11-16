package com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.typedmap;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.base.CollectionFieldInfoBase;
import com.taoswork.tallybook.dynamic.datameta.metadata.EntryTypeUnion;

/**
 * Created by Gao Yuan on 2015/10/24.
 */
public class MapFieldInfo extends CollectionFieldInfoBase {
    private final String keyEntryType;
    private final String valueEntryType;
    public MapFieldInfo(String name, String friendlyName, boolean editable, EntryTypeUnion keyType, EntryTypeUnion valueType) {
        super(name, friendlyName, editable);
        Class keyPresentationTyp = keyType.getPresentationClass();
        Class valPresentationTyp = valueType.getPresentationClass();
        keyEntryType = (keyPresentationTyp != null) ? keyPresentationTyp.getName() : "";
        valueEntryType = (valPresentationTyp != null) ? valPresentationTyp.getName() : "";
    }

    public String getValueEntryType() {
        return valueEntryType;
    }

    public String getKeyEntryType() {
        return keyEntryType;
    }
}
