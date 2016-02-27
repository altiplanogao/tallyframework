package com.taoswork.tallybook.datadomain.base.entity.validation.error;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by Gao Yuan on 2015/10/4.
 */
public class ValidationErrors {
    private Collection<ValidationError> errors;

    public ValidationErrors() {
        this(null);
    }

    public ValidationErrors(Collection<ValidationError> errors) {
        if (errors == null)
            errors = new ArrayList<ValidationError>();
        this.errors = errors;
    }

    public void appendError(String errorCode, Object[] args) {
        if (errorCode != null && !"".equals(errorCode)) {
            this.appendError(new ValidationError(errorCode, args));
        }
    }

    public void appendError(String errorCode) {
        this.appendError(errorCode, new Object[]{});
    }

    public void appendError(ValidationError error) {
        if (error != null) {
            this.errors.add(error);
        }
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    public Collection<ValidationError> getErrors() {
        return Collections.unmodifiableCollection(errors);
    }

    public void absorb(ValidationErrors another) {
        errors.addAll(another.errors);
    }
}
