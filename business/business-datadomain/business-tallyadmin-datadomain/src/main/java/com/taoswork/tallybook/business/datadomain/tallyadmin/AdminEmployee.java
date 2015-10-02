package com.taoswork.tallybook.business.datadomain.tallyadmin;

import com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission.AdminPermission;
import com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission.AdminRole;
import com.taoswork.tallybook.general.authority.core.authentication.user.AccountStatus;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

import java.util.Set;

/**
 * Created by Gao Yuan on 2015/5/10.
 */
public interface AdminEmployee extends Persistable {
    Long getId();

    void setId(Long id);

    Long getPersonId();

    void setPersonId(Long personId);

    String getTitle();

    void setTitle(String title);

    AccountStatus getStatus();

    void setStatus(AccountStatus status);

    Set<AdminRole> getAllRoles();

    void setAllRoles(Set<AdminRole> allRoles);

    Set<AdminPermission> getAllPermissions();

    void setAllPermissions(Set<AdminPermission> allPermissions);
}
