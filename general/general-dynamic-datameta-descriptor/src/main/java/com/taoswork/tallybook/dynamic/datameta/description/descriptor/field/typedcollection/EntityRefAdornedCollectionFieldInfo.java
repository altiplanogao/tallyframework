package com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.typedcollection;

/**
 * Created by Gao Yuan on 2015/11/15.
 */
public class EntityRefAdornedCollectionFieldInfo extends _CollectionFieldInfo {
    public EntityRefAdornedCollectionFieldInfo(String name, String friendlyName, boolean editable, String instanceType) {
        super(name, friendlyName, editable, instanceType);
    }

    @Override
    public String getEntryType() {
        return "entity-ref-adorned";
    }
}
