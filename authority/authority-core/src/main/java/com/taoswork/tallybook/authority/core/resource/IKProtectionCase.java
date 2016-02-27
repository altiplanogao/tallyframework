package com.taoswork.tallybook.authority.core.resource;

/**
 * A sub-collection of a particular type of
 * IKProtection ({@link IKProtection})
 */
public interface IKProtectionCase {

    /**
     * an unique code for the a particular type of IKProtection;
     *
     * @return
     */
    String getCode();

    /**
     * Check if the resource instance belongs to the "case"
     * @param instance
     * @return true if belongs
     */
    boolean isMatch(Object instance);

}
