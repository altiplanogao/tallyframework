package com.taoswork.tallybook.general.datadomain.support.entity.validation.error;

/**
 * Created by Gao Yuan on 2015/10/4.
 */
public class FieldValidationErrors extends ValidationErrors {
    private final String fieldName;

    public FieldValidationErrors(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
