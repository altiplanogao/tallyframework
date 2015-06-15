package com.taoswork.tallybook.business.datadomain.tallybusiness;

import com.taoswork.tallybook.general.authority.domain.authority.user.IPermissionUser;

import java.io.Serializable;

/**
 * Created by Gao Yuan on 2015/4/14.
 */
public interface Employee extends IPermissionUser, Serializable {
    Long getId();

    Employee setId(Long id);

    Long getUserId();

    Employee setUserId(Long userId);

    Organization getOrganization();

    Employee setOrganization(Organization organization);

    EmployeeStatus getActiveStatus();

    Employee setActiveStatus(EmployeeStatus activeStatus);

    String getName();

    Employee setName(String name);

    String getTitle();

    Employee setTitle(String title);

    EmployeeOwnedSetting getSetting();

    Employee setSetting(EmployeeOwnedSetting setting);
}
