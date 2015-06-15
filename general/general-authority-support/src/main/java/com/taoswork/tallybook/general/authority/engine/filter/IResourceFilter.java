package com.taoswork.tallybook.general.authority.engine.filter;

/**
 * Created by Gao Yuan on 2015/6/5.
 *
 * A runtime object representing DB object ResourceCriteria
 */
public interface IResourceFilter {
    String getResourceTypeName();

    void setResourceTypeName(String resourceTypeName);

    void setFilterParameter(String parameter);

    boolean isMatch(Object entity);

    String unMatchQueryInterrupter();
}
