package com.taoswork.tallybook.authority.core.resource;

import com.taoswork.tallybook.authority.core.ProtectionMode;

import java.util.Collection;

/**
 * Describes a kind of resource.
 * Define:
 *      If the resource master controlled
 *      its protection mode: fit all / fit any.
 *      Contains sub-collections, defined by "case"s
 */
public interface IKProtection {
    String getResource();

    /**
     * @return if the resource master controlled
     */
    boolean isMasterControlled();

    IKProtection setMasterControlled(boolean isMasterControlled);

    /**
     * @return the resource's protection mode, any or all?
     */
    ProtectionMode getProtectionMode();

    IKProtection setProtectionMode(ProtectionMode protectionMode);

    /**
     * @return The filters defining sub-collections
     */
    Collection<IKProtectionCase> getCases();

    IKProtection addCase(IKProtectionCase _case);

    IKProtection addCases(IKProtectionCase... cases);

    IKProtection cleanCases();

    long version();
}
