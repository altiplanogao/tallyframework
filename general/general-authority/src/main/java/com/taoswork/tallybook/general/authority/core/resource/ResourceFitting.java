package com.taoswork.tallybook.general.authority.core.resource;

import com.taoswork.tallybook.general.authority.core.basic.ProtectionMode;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public final class ResourceFitting {

    public ResourceFitting(boolean isMasterControlled,
                           ProtectionMode protectionMode,
                           Collection<String> matchingFilters,
                           Collection<String> unmatchedFilters) {
        this.isMasterControlled = isMasterControlled;
        this.protectionMode = protectionMode;
        this.matchingFilters = matchingFilters;
        this.unmatchedFilters = unmatchedFilters;
    }

    public final boolean isMasterControlled;
    public final ProtectionMode protectionMode;
    public final Collection<String> matchingFilters;
    public final Collection<String> unmatchedFilters;
}
