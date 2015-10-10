package com.taoswork.tallybook.business.datadomain.tallybusiness.security.permission;

import com.taoswork.tallybook.business.datadomain.tallybusiness.Employee;
import com.taoswork.tallybook.general.authority.domain.permission.Role;
import com.taoswork.tallybook.general.datadomain.support.entity.PersistFriendly;

import java.util.Set;

/**
 * Created by Gao Yuan on 2015/8/27.
 */
@PersistFriendly(nameOverride = "bu-role")
public interface BuRole
    extends Role<BuPermission> {

    @Override
    Set<BuPermission> getAllPermissions();

    @Override
    void setAllPermissions(Set<BuPermission> allPermissions);

    Set<Employee> getAllEmployees();

    void setAllEmployees(Set<Employee> allEmployees);
}
