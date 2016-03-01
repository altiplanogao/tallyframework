package com.taoswork.tallybook.authority.solution.engine.filter;

/**
 * Created by Gao Yuan on 2015/6/5.
 * <p>
 * A runtime object representing DB object ResourceCriteria
 */
public interface IFilter {
    String getResource();

    void setResource(String resourceTypeName);

    String getFilterParameter();

    void setFilterParameter(String parameter);

    boolean isMatch(Object instance);
}
