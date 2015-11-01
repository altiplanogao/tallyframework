package com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission;

import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminEmployee;
import com.taoswork.tallybook.general.authority.domain.permission.Role;
import com.taoswork.tallybook.general.datadomain.support.entity.PersistEntity;

import java.util.Set;

/**
 * Created by Gao Yuan on 2015/8/27.
 */
@PersistEntity(nameOverride = "admin-role")
public interface AdminRole
    extends Role<AdminPermission> {

    @Override
    Set<AdminPermission> getAllPermissions();

    @Override
    void setAllPermissions(Set<AdminPermission> allPermissions);

    Set<AdminEmployee> getAllEmployees();

    void setAllEmployees(Set<AdminEmployee> allEmployees);
}
