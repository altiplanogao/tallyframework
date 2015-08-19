package com.taoswork.tallybook.general.authority.core.authority.user;

import com.taoswork.tallybook.general.authority.core.authority.permission.Permission;
import com.taoswork.tallybook.general.authority.core.authority.permission.Role;
import com.taoswork.tallybook.general.authority.core.authority.user.impl.APermissionUser;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
public class EasyPermissionUser4Test extends APermissionUser {
    private Set<Permission> permissions = new HashSet<Permission>();

    @Override
    public Set<Permission> getPermissions() {
        return permissions;
    }

    @Override
    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public Set<Role> getRoles() {
        return null;
    }

    @Override
    public void setRoles(Set<Role> roles) {

    }
}