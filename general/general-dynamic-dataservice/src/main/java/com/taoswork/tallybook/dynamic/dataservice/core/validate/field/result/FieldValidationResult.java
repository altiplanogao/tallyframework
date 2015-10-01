package com.taoswork.tallybook.dynamic.dataservice.core.validate.field.result;

import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/9/29.
 */
public class FieldValidationResult {
    private final String fieldName;
    private boolean valid = true;
    private final List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();

    public FieldValidationResult(String fieldName) {
        this.fieldName = fieldName;
    }

    public FieldValidationResult appendValueValidationResult(ValueValidationResult valueValidationResult){
        if(valueValidationResult != null) {
            if (!valueValidationResult.isValid()) {
                valid &= valueValidationResult.isValid();
                errorMessages.addAll(valueValidationResult.getErrorMessages());
            }
        }
        return this;
    }

    public String getFieldName() {
        return fieldName;
    }

    public boolean isValid() {
        return valid;
    }

    public List<ErrorMessage> getErrorMessages() {
        return errorMessages;
    }
}
