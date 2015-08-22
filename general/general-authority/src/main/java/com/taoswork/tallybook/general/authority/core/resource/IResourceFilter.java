package com.taoswork.tallybook.general.authority.core.resource;

/**
 * Created by Gao Yuan on 2015/6/5.
 * <p>
 * A runtime object representing DB object ResourceCriteria
 */
public interface IResourceFilter {

    /**
     * an unique code for the resourceEntity;
     *
     * @return
     */
    String getCode();

    boolean isMatch(IResourceInstance instance);

    String unMatchQueryInterrupter();
}
