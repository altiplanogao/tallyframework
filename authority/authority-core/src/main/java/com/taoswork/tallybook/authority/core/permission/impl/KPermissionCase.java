package com.taoswork.tallybook.authority.core.permission.impl;

import com.taoswork.tallybook.authority.core.Access;
import com.taoswork.tallybook.authority.core.permission.IKPermissionCase;
import com.taoswork.tallybook.authority.core.permission.wirte.IKPermissionCaseW;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * KPermissionCase, owned by user, directly or indirectly.
 * Corresponding to a permission setting in application/database
 */
public final class KPermissionCase implements IKPermissionCaseW {
    private final String code;
    private Access access;

    public KPermissionCase(String code, Access access) {
        this.code = code;
        this.access = access.clone();
    }

    public KPermissionCase(IKPermissionCase that) {
        this.code = that.getCode();
        this.access = that.getAccess().clone();
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public Access getAccess() {
        return access;
    }

    @Override
    public IKPermissionCaseW merge(IKPermissionCase that) {
        if (!this.code.equals(that.getCode())) {
            throw new IllegalArgumentException();
        }
        this.access = this.access.merge(that.getAccess());
        return this;
    }

    @Override
    public IKPermissionCase clone() {
        return new KPermissionCase(code, access);
    }

    @Override
    public String toString() {
        return "{code:" + code + " " + access + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof KPermissionCase)) return false;

        KPermissionCase that = (KPermissionCase) o;

        return new EqualsBuilder()
                .append(code, that.code)
                .append(access, that.access)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(code)
                .append(access)
                .toHashCode();
    }
}
