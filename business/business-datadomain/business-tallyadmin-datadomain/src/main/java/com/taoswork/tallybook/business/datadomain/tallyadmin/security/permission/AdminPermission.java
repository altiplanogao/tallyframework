package com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission;

import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminEmployee;
import com.taoswork.tallybook.general.authority.domain.permission.Permission;
import com.taoswork.tallybook.general.datadomain.support.entity.PersistFriendly;

import java.util.Set;

/**
 * Created by Gao Yuan on 2015/8/27.
 */
@PersistFriendly(nameOverride = "admin-permission")
public interface AdminPermission extends Permission<AdminPermissionEntry> {
    Set<AdminEmployee> getAllEmployees();

    void setAllEmployees(Set<AdminEmployee> allEmployees);
}
