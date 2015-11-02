package com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.base;

/**
 * Created by Gao Yuan on 2015/10/24.
 */
public abstract class CollectionFieldInfoBase extends FieldInfoBase implements ICollectionFieldInfoRW {

    public CollectionFieldInfoBase(String name, String friendlyName, boolean editable) {
        super(name, friendlyName, editable);
    }

    @Override
    public boolean isCollection() {
        return true;
    }
}
