package com.taoswork.tallybook.general.authority.core.resource;

/**
 * Created by Gao Yuan on 2015/6/5.
 *
 * A runtime object representing DB object ResourceCriteria
 */
public interface IResourceFilter {

    /**
     * an unique code for the resourceEntity;
     * @return
     */
    String getCode();

    String getFriendlyName();

    boolean isMatch(IResourceInstance instance);

    String unMatchQueryInterrupter();
}
