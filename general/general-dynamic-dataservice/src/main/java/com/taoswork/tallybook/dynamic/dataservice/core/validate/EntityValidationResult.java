package com.taoswork.tallybook.dynamic.dataservice.core.validate;

import com.taoswork.tallybook.dynamic.dataservice.core.validate.field.result.ErrorMessage;
import com.taoswork.tallybook.dynamic.dataservice.core.validate.field.result.FieldValidationResult;

import java.util.*;

/**
 * Created by Gao Yuan on 2015/9/30.
 */
public class EntityValidationResult {
    private boolean valid = true;
    private Map<String, List<ErrorMessage>> fieldErrors = new HashMap<String, List<ErrorMessage>>();

    public void appendFieldValidationResult(FieldValidationResult fieldValidationResult){
        if(fieldValidationResult != null){
            if(!fieldValidationResult.isValid()) {
                this.valid &= fieldValidationResult.isValid();
                String fieldName = fieldValidationResult.getFieldName();
                List<ErrorMessage> fieldErrors = this.fieldErrors.getOrDefault(fieldName, null);
                if (fieldErrors == null) {
                    fieldErrors = new ArrayList<ErrorMessage>();
                    this.fieldErrors.put(fieldName, fieldErrors);
                }
                fieldErrors.addAll(fieldValidationResult.getErrorMessages());
            }
        }
        return;
    }

    public boolean isValid() {
        return valid;
    }

    public Map<String, List<ErrorMessage>> getFieldErrors(){
        return Collections.unmodifiableMap(fieldErrors);
    }
}
