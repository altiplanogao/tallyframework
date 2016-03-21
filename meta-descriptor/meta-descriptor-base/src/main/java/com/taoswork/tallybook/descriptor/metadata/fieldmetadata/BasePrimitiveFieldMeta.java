package com.taoswork.tallybook.descriptor.metadata.fieldmetadata;

import com.taoswork.tallybook.datadomain.base.presentation.Visibility;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/3/21.
 */
public abstract class BasePrimitiveFieldMeta extends BaseFieldMeta {

    public BasePrimitiveFieldMeta(BasicFieldMetaObject bfmo) {
        super(bfmo);
    }

//    @Override
//    public final boolean isPrimitiveField() {
//        return true;
//    }

    @Override
    public final boolean isCollectionField() {
        return false;
    }

    @Override
    public boolean isId() {
        return basicFieldMetaObject.isId();
    }

    @Override
    public boolean isNameField() {
        return basicFieldMetaObject.isNameField();
    }

    @Override
    public void setNameField(boolean b) {
        basicFieldMetaObject.setNameField(b);
    }

    @Override
    public boolean showOnGrid() {
        return Visibility.gridVisible(basicFieldMetaObject.getVisibility());
    }

    @Override
    public int gatherReferencingTypes(Collection<Class> collection) {
        return 0;
    }

    public abstract Object getValueFromString(String valStr);
}