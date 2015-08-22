package com.taoswork.tallybook.general.authority.core.permission.wirte;

import com.taoswork.tallybook.general.authority.core.permission.IEntityPermission;

/**
 * Created by Gao Yuan on 2015/8/22.
 */
public interface IEntityPermissionWrite extends IEntityPermission {

    IEntityPermissionWrite merge(IEntityPermission that);
}
