package com.taoswork.tallybook.general.authority.core.permission.impl;

import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.authority.core.permission.IPermissionEntry;
import com.taoswork.tallybook.general.authority.core.permission.wirte.IPermissionEntryWrite;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * PermissionEntry, owned by user, directly or indirectly.
 * Corresponding to a permission setting in application/database
 */
public final class PermissionEntry implements IPermissionEntryWrite {
    private final String filterCode;
    private Access access;

    public PermissionEntry(String filterCode, Access access) {
        this.filterCode = filterCode;
        this.access = access.clone();
    }

    public PermissionEntry(IPermissionEntry that) {
        this.filterCode = that.getFilterCode();
        this.access = that.getAccess().clone();
    }

    @Override
    public String getFilterCode() {
        return filterCode;
    }

    @Override
    public Access getAccess() {
        return access;
    }

    @Override
    public IPermissionEntryWrite merge(IPermissionEntry that) {
        if (!this.filterCode.equals(that.getFilterCode())) {
            throw new IllegalArgumentException();
        }
        this.access = this.access.merge(that.getAccess());
        return this;
    }

    @Override
    public IPermissionEntry clone() {
        return new PermissionEntry(filterCode, access);
    }

    @Override
    public String toString() {
        return "{code:" + filterCode + " " + access + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof PermissionEntry)) return false;

        PermissionEntry that = (PermissionEntry) o;

        return new EqualsBuilder()
            .append(filterCode, that.filterCode)
            .append(access, that.access)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(filterCode)
            .append(access)
            .toHashCode();
    }
}
