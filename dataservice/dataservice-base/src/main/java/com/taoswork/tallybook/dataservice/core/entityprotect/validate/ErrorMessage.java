package com.taoswork.tallybook.dataservice.core.entityprotect.validate;

/**
 * Created by Gao Yuan on 2015/9/28.
 */
public class ErrorMessage {
    private String code;
    private Object[] args;

    public ErrorMessage(String code, Object[] args) {
        this.code = code;
        this.args = args;
    }

    public ErrorMessage(String code) {
        this(code, null);
    }

    public String getCode() {
        return code;
    }

    public ErrorMessage setCode(String code) {
        this.code = code;
        return this;
    }

    public Object[] getArgs() {
        return args;
    }

    public ErrorMessage setArgs(Object[] args) {
        this.args = args;
        return this;
    }
}
