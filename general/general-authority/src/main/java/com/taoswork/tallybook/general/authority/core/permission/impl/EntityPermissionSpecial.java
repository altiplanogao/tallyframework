package com.taoswork.tallybook.general.authority.core.permission.impl;

import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.authority.core.permission.IEntityPermissionSpecial;
import com.taoswork.tallybook.general.authority.core.permission.wirte.IEntityPermissionSpecialWrite;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * EntityPermissionSpecial, owned by user, directly or indirectly.
 * Corresponding to a permission setting in application/database
 */
public final class EntityPermissionSpecial implements IEntityPermissionSpecialWrite {
    private final String filterCode;
    private Access access;

    public EntityPermissionSpecial(String filterCode, Access access) {
        this.filterCode = filterCode;
        this.access = access.clone();
    }

    public EntityPermissionSpecial(IEntityPermissionSpecial that) {
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
    public IEntityPermissionSpecialWrite merge(IEntityPermissionSpecial that) {
        if (!this.filterCode.equals(that.getFilterCode())) {
            throw new IllegalArgumentException();
        }
        this.access = this.access.merge(that.getAccess());
        return this;
    }

    @Override
    public IEntityPermissionSpecial clone() {
        return new EntityPermissionSpecial(filterCode, access);
    }

    @Override
    public String toString() {
        return "{code:" + filterCode + " " + access + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof EntityPermissionSpecial)) return false;

        EntityPermissionSpecial that = (EntityPermissionSpecial) o;

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
