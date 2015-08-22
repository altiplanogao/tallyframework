package com.taoswork.tallybook.general.authority.core.permission.authorities;

import com.taoswork.tallybook.general.authority.core.permission.IEntityPermission;
import com.taoswork.tallybook.general.authority.core.permission.IPermissionAuthority;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public interface ISimplePermissionAuthority extends IPermissionAuthority {

    ISimplePermissionAuthority addEntityPermission(IEntityPermission... entityPermissions);

    ISimplePermissionAuthority merge(ISimplePermissionAuthority that);

    ISimplePermissionAuthority clone();
}
