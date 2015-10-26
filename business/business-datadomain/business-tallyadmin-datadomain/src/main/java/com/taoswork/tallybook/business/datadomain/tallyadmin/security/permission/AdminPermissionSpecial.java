package com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission;

import com.taoswork.tallybook.general.authority.domain.permission.PermissionSpecial;
import com.taoswork.tallybook.general.datadomain.support.entity.PersistFriendly;

/**
 * Created by Gao Yuan on 2015/8/27.
 */
@PersistFriendly(nameOverride = "admin-permission-special")
public interface AdminPermissionSpecial extends PermissionSpecial<AdminPermission> {
}
