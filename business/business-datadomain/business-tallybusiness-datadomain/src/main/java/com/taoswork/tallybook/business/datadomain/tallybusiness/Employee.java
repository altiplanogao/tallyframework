package com.taoswork.tallybook.business.datadomain.tallybusiness;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;

/**
 * Created by Gao Yuan on 2015/4/14.
 */
public interface Employee extends
        //IPermissionUser,
        Persistable {
    Long getId();

    Employee setId(Long id);

    Long getPersonId();

    Employee setUserId(Long userId);

    BusinessUnit getBusinessUnit();

    Employee setOrganization(BusinessUnit businessUnit);

    EmployeeStatus getActiveStatus();

    Employee setActiveStatus(EmployeeStatus activeStatus);

    String getName();

    Employee setName(String name);

    String getTitle();

    Employee setTitle(String title);

    EmployeeOwnedSetting getSetting();

    Employee setSetting(EmployeeOwnedSetting setting);
}
