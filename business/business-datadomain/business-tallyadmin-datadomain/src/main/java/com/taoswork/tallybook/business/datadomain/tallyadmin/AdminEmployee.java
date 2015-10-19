package com.taoswork.tallybook.business.datadomain.tallyadmin;

import com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission.AdminPermission;
import com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission.AdminRole;
import com.taoswork.tallybook.business.datadomain.tallyadmin.valueprotect.AdminEmployeeGate;
import com.taoswork.tallybook.business.datadomain.tallyuser.Person;
import com.taoswork.tallybook.general.authority.core.authentication.user.AccountStatus;
import com.taoswork.tallybook.general.datadomain.support.entity.PersistFriendly;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

import java.util.Set;

/**
 * Created by Gao Yuan on 2015/5/10.
 */
@PersistFriendly(nameOverride = "admin",
    valueGates = {AdminEmployeeGate.class}
)
public interface AdminEmployee extends Persistable {
    Long getId();

    void setId(Long id);

    String getName();

    void setName(String name);

    Long getPersonId();

    void setPersonId(Long personId);

    Person getPerson();

    void setPerson(Person person);

    String getTitle();

    void setTitle(String title);

    AccountStatus getStatus();

    void setStatus(AccountStatus status);

    Set<AdminRole> getAllRoles();

    void setAllRoles(Set<AdminRole> allRoles);

    Set<AdminPermission> getAllPermissions();

    void setAllPermissions(Set<AdminPermission> allPermissions);
}
