package com.taoswork.tallybook.general.datadomain.support.entity.validation.error;

import com.taoswork.tallybook.general.extension.collections.StringChain;

import java.util.*;

/**
 * Created by Gao Yuan on 2015/10/4.
 */
public class EntityValidationErrors extends ValidationErrors {
    private final Map<String, FieldValidationErrors> fieldErrorsMap = new HashMap<String, FieldValidationErrors>();

    public void addFieldErrors(FieldValidationErrors fieldErrors){
        FieldValidationErrors existing = fieldErrorsMap.get(fieldErrors.getFieldName());
        if(existing == null){
            fieldErrorsMap.put(fieldErrors.getFieldName(), fieldErrors);
        }else {
            existing.absorb(fieldErrors);
        }
    }
    public void appendFieldError(String fieldName, String errorCode) {
        appendFieldError(fieldName, errorCode, null);
    }

    public void appendFieldError(String fieldName, String errorCode, Object[] args) {
        FieldValidationErrors fieldErrors = new FieldValidationErrors(fieldName);
        fieldErrors.appendError(errorCode, args);
        this.addFieldErrors(fieldErrors);
    }

    public Collection<FieldValidationErrors> getFieldErrors(){
        return Collections.unmodifiableCollection(fieldErrorsMap.values());
    }

    @Override
    public boolean isValid() {
        for(FieldValidationErrors fieldErrors : fieldErrorsMap.values()){
           if(!fieldErrors.isValid())
               return false;
        }
        return super.isValid();
    }

    public void appendErrorFieldsNames(Collection<String> fieldFriendlyNames) {
        if(fieldFriendlyNames == null || fieldFriendlyNames.size() == 0)
            return;
        StringChain sc = new StringChain();
        sc.setFixes("", ", ", "");
        sc.addAll(fieldFriendlyNames);
        this.appendError(new ValidationError("validation.error.global.error.fields", new Object[]{}));
    }
}
