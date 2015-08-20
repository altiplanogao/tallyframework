package com.taoswork.tallybook.general.authority.core.resource;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/8/20.
 */
public final class AccessibleFitting {
    public AccessibleFitting(
        Collection<String> passFilters,
        Collection<String> blockFilters) {
        this(passFilters, blockFilters, false);
    }
    public AccessibleFitting(
        Collection<String> passFilters,
        Collection<String> blockFilters, boolean inAnyMode) {
        this.passFilters = passFilters;
        this.blockFilters = blockFilters;
        this.inAnyMode = inAnyMode;
    }

    public final boolean inAnyMode;
    public final Collection<String> passFilters;
    public final Collection<String> blockFilters;
}
