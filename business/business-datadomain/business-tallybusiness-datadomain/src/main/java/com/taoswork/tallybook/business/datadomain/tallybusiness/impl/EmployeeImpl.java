package com.taoswork.tallybook.business.datadomain.tallybusiness.impl;

import com.taoswork.tallybook.business.datadomain.tallybusiness.Employee;
import com.taoswork.tallybook.business.datadomain.tallybusiness.EmployeeOwnedSetting;
import com.taoswork.tallybook.business.datadomain.tallybusiness.EmployeeStatus;
import com.taoswork.tallybook.business.datadomain.tallybusiness.Organization;
import com.taoswork.tallybook.general.authority.domain.permission.Permission;
import com.taoswork.tallybook.general.authority.domain.permission.Role;

import java.util.Set;

/**
 * Created by Gao Yuan on 2015/4/16.
 */
public class EmployeeImpl implements Employee {
    protected Long id;

    protected Long userId;
    protected Organization organization;

    protected EmployeeStatus activeStatus;

    protected String name;
    protected String title;
    protected EmployeeOwnedSetting setting;

    protected Set<Role> roles;
    protected Set<Permission> permissions;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Employee setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public Employee setUserId(Long userId) {
        this.userId = this.userId;
        return this;
    }

    @Override
    public Organization getOrganization() {
        return organization;
    }

    @Override
    public Employee setOrganization(Organization organization) {
        this.organization = organization;
        return this;
    }

    @Override
    public EmployeeStatus getActiveStatus() {
        return activeStatus;
    }

    @Override
    public EmployeeImpl setActiveStatus(EmployeeStatus activeStatus) {
        this.activeStatus = activeStatus;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Employee setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Employee setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public EmployeeOwnedSetting getSetting() {
        return setting;
    }

    @Override
    public Employee setSetting(EmployeeOwnedSetting setting) {
        this.setting = setting;
        return this;
    }
//
//    @Override
//    public Set<Role> getRoles() {
//        return roles;
//    }
//
//    @Override
//    public void setRoles(Set<Role> roles) {
//        this.roles = roles;
//    }
//
//    @Override
//    public Set<Permission> getPermissions() {
//        return permissions;
//    }
//
//    @Override
//    public void setPermissions(Set<Permission> permissions) {
//        this.permissions = permissions;
//    }
}
