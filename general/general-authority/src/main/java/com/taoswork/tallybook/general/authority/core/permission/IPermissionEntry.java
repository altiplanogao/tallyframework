package com.taoswork.tallybook.general.authority.core.permission;

import com.taoswork.tallybook.general.authority.core.basic.Access;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public interface IPermissionEntry {

    void merge(IPermissionEntry permissionEntry);

    public String getFilterCode();

    public Access getAccess();
}
