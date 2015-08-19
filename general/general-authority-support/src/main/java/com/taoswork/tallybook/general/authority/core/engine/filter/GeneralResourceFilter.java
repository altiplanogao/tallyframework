package com.taoswork.tallybook.general.authority.core.engine.filter;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
public class GeneralResourceFilter implements IResourceFilter {

    String resourceTypeName;
    String parameter;

    @Override
    public String getResourceTypeName() {
        return resourceTypeName;
    }

    @Override
    public void setResourceTypeName(String resourceTypeName) {
        this.resourceTypeName = resourceTypeName;
    }

    @Override
    public void setFilterParameter(String parameter) {
        this.parameter = parameter;
    }

    @Override
    public boolean isMatch(Object entity) {
        return true;
    }

    @Override
    public String unMatchQueryInterrupter() {
        return "";
    }
}
