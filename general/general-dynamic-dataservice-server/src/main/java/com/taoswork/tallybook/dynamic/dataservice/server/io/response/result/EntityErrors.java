package com.taoswork.tallybook.dynamic.dataservice.server.io.response.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/10/1.
 */
public class EntityErrors {
    private boolean authorized = true;

    private List<String> global = new ArrayList<String>();

    private MultiValueMap<String, String> fields = new LinkedMultiValueMap();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private transient Exception unhandledException;

    public void addGlobalErrorMessage(String message){
        this.global.add(message);
    }

    public void addFieldErrorMessage(String fieldName, String message){
        this.fields.add(fieldName, message);
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    public List<String> getGlobal() {
        return global;
    }

    public MultiValueMap<String, String> getFields() {
        return fields;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Exception getUnhandledException() {
        return unhandledException;
    }

    public void setUnhandledException(Exception unhandledException) {
        this.unhandledException = unhandledException;
    }

    public boolean containsError() {
        if (!global.isEmpty()) {
            return true;
        }
        if (!fields.isEmpty()) {
            return true;
        }
        return unhandledException != null;
    }
}
