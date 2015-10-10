package com.taoswork.tallybook.general.authority.core.resource;

/**
 * A sub-collection of a particular type of
 * IResourceProtection ({@link com.taoswork.tallybook.general.authority.core.resource.IResourceProtection})
 */
public interface IResourceFilter {

    /**
     * an unique code for the a particular type of IResourceProtection;
     *
     * @return
     */
    String getCode();

    /**
     * Check if the resource instance belongs to the filter
     * @param instance
     * @return true if belongs
     */
    boolean isMatch(Object instance);

    /**
     * TODO: UNUSED
     * @return
     */
    String unMatchQueryInterrupter();
}
