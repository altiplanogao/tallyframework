package com.taoswork.tallybook.dynamic.dataservice.entity.description.edo;

import com.taoswork.tallybook.dynamic.datadomain.presentation.client.SupportedFieldType;
import com.taoswork.tallybook.dynamic.datadomain.presentation.client.Visibility;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.edo.inf.FriendlyEdo;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class FieldEdo extends FriendlyEdo {
    public int visibility = Visibility.DEFAULT;
    private boolean isCollection;
    private SupportedFieldType fieldType = SupportedFieldType.UNKNOWN;

    public boolean nameField = false;

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public boolean isGridVisible() {
        return Visibility.gridVisible(visibility);
    }

    public boolean isCollection() {
        return isCollection;
    }

    public boolean isNameField() {
        return nameField;
    }

    public void setNameField(boolean nameField) {
        this.nameField = nameField;
    }

    public SupportedFieldType getFieldType() {
        if (fieldType == null) {
            return SupportedFieldType.UNKNOWN;
        }
        return fieldType;
    }

    public void setFieldType(SupportedFieldType fieldType) {
        this.fieldType = fieldType;
    }

    public void merge(FieldEdo fieldEdo) {
        if (this.equals(fieldEdo)) {
            return;
        }
        //TODO
    }


}
