package com.taoswork.tallybook.general.authority.core.resource;

/**
 * A runtime object representing DB object ResourceCriteria
 */
public interface IResourceFilter {

    /**
     * an unique code for the resourceEntity;
     *
     * @return
     */
    String getCode();

    boolean isMatch(Object instance);

    String unMatchQueryInterrupter();
}
