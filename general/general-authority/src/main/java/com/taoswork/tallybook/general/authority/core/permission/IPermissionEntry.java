package com.taoswork.tallybook.general.authority.core.permission;

import com.taoswork.tallybook.general.authority.core.basic.Access;

/**
 * IPermissionEntry, owned by user, directly or indirectly.
 * And always has a corresponding IResourceFilter in SecuredResourceManager (mapped by filter's code)
 */
public interface IPermissionEntry {

    void merge(IPermissionEntry permissionEntry);

    public String getFilterCode();

    public Access getAccess();
}
