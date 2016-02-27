package com.taoswork.tallybook.dataservice.server.io.response.result;

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

    public void addGlobalErrorMessage(String message) {
        this.global.add(message);
    }

    public void addFieldErrorMessage(String fieldName, String message) {
        this.fields.add(fieldName, message);
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<String> getGlobal() {
        if (global.isEmpty())
            return null;
        return global;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public MultiValueMap<String, String> getFields() {
        if (fields.isEmpty())
            return null;
        return fields;
    }

    public boolean containsError() {
        if (!authorized)
            return true;
        if (!global.isEmpty()) {
            return true;
        }
        if (!fields.isEmpty()) {
            return true;
        }
        return false;
    }
}
