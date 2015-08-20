package com.taoswork.tallybook.general.authority.core.permission.impl;

import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.authority.core.permission.IPermissionEntry;

/**
 * PermissionEntry, owned by user, directly or indirectly.
 * Corresponding to a permission setting in application/database
 */
public final class PermissionEntry implements IPermissionEntry {
    private final String filterCode;
    private Access access;

    public PermissionEntry(String filterCode, Access access){
        this.filterCode = filterCode;
        this.access = access.clone();
    }

    @Override
    public void merge(IPermissionEntry permissionEntry){
        if(!this.filterCode.equals(permissionEntry.getFilterCode())){
            throw new IllegalArgumentException();
        }
        this.access = this.access.merge(permissionEntry.getAccess());
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
    public String toString() {
        return "{code:" + filterCode + " " + access + '}';
    }
}
