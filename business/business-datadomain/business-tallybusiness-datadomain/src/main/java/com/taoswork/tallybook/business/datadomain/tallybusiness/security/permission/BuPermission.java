package com.taoswork.tallybook.business.datadomain.tallybusiness.security.permission;

import com.taoswork.tallybook.business.datadomain.tallybusiness.Employee;
import com.taoswork.tallybook.general.authority.domain.permission.Permission;
import com.taoswork.tallybook.general.datadomain.support.entity.PersistFriendly;

import java.util.Set;

/**
 * Created by Gao Yuan on 2015/8/27.
 */
@PersistFriendly(nameOverride = "bu-permission")
public interface BuPermission extends Permission<BuPermissionSpecial> {

    Long getOrganizationId();

    void setOrganizationId(Long organizationId);

    Set<Employee> getAllEmployees();

    void setAllEmployees(Set<Employee> allEmployees);
}
