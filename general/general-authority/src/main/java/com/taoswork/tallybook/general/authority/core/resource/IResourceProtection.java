package com.taoswork.tallybook.general.authority.core.resource;

import com.taoswork.tallybook.general.authority.core.basic.ProtectionMode;

import java.util.Collection;

/**
 * Describes a kind of resource.
 * Define:
 *      If the resource master controlled
 *      its protection mode: fit all / fit any.
 *      Contains sub-collections, defined by filters
 */
public interface IResourceProtection {
    String getResourceEntity();

    /**
     * @return if the resource master controlled
     */
    boolean isMasterControlled();

    IResourceProtection setMasterControlled(boolean isMasterControlled);

    /**
     * @return the resource's protection mode, any or all?
     */
    ProtectionMode getProtectionMode();

    IResourceProtection setProtectionMode(ProtectionMode protectionMode);

    int version();

    /**
     * @return The filters defining sub-collections
     */
    Collection<IResourceFilter> getFilters();

    IResourceProtection addFilters(IResourceFilter... filters);

    IResourceProtection cleanFilters();
}
