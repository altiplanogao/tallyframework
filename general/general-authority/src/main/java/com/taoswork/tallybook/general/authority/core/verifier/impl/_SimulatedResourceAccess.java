package com.taoswork.tallybook.general.authority.core.verifier.impl;

import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.authority.core.resource.IResourceProtectionMapping;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by Gao Yuan on 2015/8/22.
 */
class _SimulatedResourceAccess {
    private final String resourceEntity;

    private final Access access;

    public _SimulatedResourceAccess(String resourceEntity, Access access) {
        this.resourceEntity = resourceEntity;
        this.access = access;
    }

    public _SimulatedResourceAccess(IResourceProtectionMapping protection){
        this(protection.getVirtualResource(), protection.getVirtualAccess());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof _SimulatedResourceAccess)) return false;

        _SimulatedResourceAccess that = (_SimulatedResourceAccess) o;

        return new EqualsBuilder()
            .append(resourceEntity, that.resourceEntity)
            .append(access, that.access)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(resourceEntity)
            .append(access)
            .toHashCode();
    }
}
