package com.taoswork.tallybook.dynamic.dataservice.core.validate.field.result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/9/28.
 */
public class ValueValidationResult {

    private boolean valid = false;
    private final List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();

    public final static ValueValidationResult success = new ValueValidationResult(true);

    public ValueValidationResult(boolean valid) {
        this.valid = valid;
    }

    public ValueValidationResult(boolean valid, List<ErrorMessage> errorMessages) {
        this.valid = valid;
        this.errorMessages.addAll(errorMessages);
    }

    public ValueValidationResult(boolean valid, ErrorMessage... errorMessages) {
        this.valid = valid;
        for (ErrorMessage errorMessage : errorMessages){
            this.errorMessages.add(errorMessage);
        }
    }

    public ValueValidationResult(boolean valid, String... errorMessages) {
        this.valid = valid;
        for (String errorMessage : errorMessages){
            this.addErrorMessage(errorMessage);
        }
    }

    public ValueValidationResult(boolean valid, String errorCode, Object[] args) {
        this.valid = valid;
        this.addErrorMessage(errorCode, args);
    }

    public ValueValidationResult setValid(boolean valid) {
        this.valid = valid;
        return this;
    }

    public boolean isValid() {
        return valid;
    }

    public List<ErrorMessage> getErrorMessages() {
        return errorMessages;
    }

    public ValueValidationResult setErrorMessages(List<String> errorMessages) {
        for (String errorMessage : errorMessages){
            this.addErrorMessage(errorMessage);
        }
        return this;
    }

    public ValueValidationResult addErrorMessage(ErrorMessage errorMessage){
        this.errorMessages.add(errorMessage);
        return this;
    }

    public ValueValidationResult addErrorMessage(String messageCode, Object[] args){
        ErrorMessage message = new ErrorMessage(messageCode, args);
        return this.addErrorMessage(message);
    }

    public ValueValidationResult addErrorMessage(String messageCode){
        return this.addErrorMessage(messageCode, null);
    }

    public ValueValidationResult addResult(boolean valid, String errorMessage){
        return this.setValid(valid).addErrorMessage(errorMessage);
    }
}
