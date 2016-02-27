package com.taoswork.tallybook.datadomain.base.entity.validation.error;

/**
 * Created by Gao Yuan on 2015/10/4.
 */
public class ValidationError {
    private String code;
    private Object[] args;

    public ValidationError(String code, Object[] args) {
        this.code = code;
        this.args = args;
    }

    public ValidationError(String code) {
        this(code, null);
    }

    public String getCode() {
        return code;
    }

    public ValidationError setCode(String code) {
        this.code = code;
        return this;
    }

    public Object[] getArgs() {
        return args;
    }

    public ValidationError setArgs(Object[] args) {
        this.args = args;
        return this;
    }
}
