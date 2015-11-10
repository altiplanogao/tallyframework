package com.taoswork.tallybook.dynamic.dataservice.server.io.response.result;

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
