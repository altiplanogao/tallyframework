package com.taoswork.tallybook.general.authority.domain.authority.user.impl;

import com.taoswork.tallybook.general.authority.domain.authority.access.AccessType;
import com.taoswork.tallybook.general.authority.domain.authority.permission.Permission;
import com.taoswork.tallybook.general.authority.domain.authority.user.IPermissionUser;
import com.taoswork.tallybook.general.authority.engine.utils.PermissionUtility;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/4/28.
 */
public abstract class APermissionUser implements IPermissionUser {
    public void clearCachedPermissions() {
        allPermissionExpanded = new WeakReference<Set<Permission>>(null);
        accessMap = new WeakReference<Map<String, AccessType>>(null);
    }

    transient
    private WeakReference<Set<Permission>> allPermissionExpanded = new WeakReference<Set<Permission>>(null);
    transient
    private WeakReference<Map<String, AccessType>> accessMap = new WeakReference<Map<String, AccessType>>(null);

 //   @Override
    public Set<Permission> getAllPermissionExpanded() {
        Set<Permission> permissions = this.allPermissionExpanded.get();
        if (null == permissions) {

            Set<Permission> rawPermissions = null;
            rawPermissions = PermissionUtility.instance().appendAllPermissions(rawPermissions, this.getPermissions());
            rawPermissions = PermissionUtility.instance().appendAllRoles(rawPermissions, this.getRoles());

            permissions = PermissionUtility.instance().expandPermissions(rawPermissions, null, true, true);
            this.allPermissionExpanded = new WeakReference<Set<Permission>>(permissions);
        }
        return permissions;
    }
/*

        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(name = "ABSTRACT_TB_PERMISSIONUSER_PERMISSION_XREF",
                joinColumns = @JoinColumn(name = "PERMISSIONUSER_ID", referencedColumnName = "ID"),
                inverseJoinColumns = @JoinColumn(name = "PERMISSION_ID", referencedColumnName = "ID"))
        protected Set<Permission> permissions;

        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(name = "ABSTRACT_TB_PERMISSIONUSER_ROLE_EREF",
                joinColumns = @JoinColumn(name = "PERMISSIONUSER_ID", referencedColumnName = "ID"),
                inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID"))
        protected Set<Role> roles;

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
                return roles;
        }

        @Override
        public void setRoles(Set<Role> roles) {
                this.roles = roles;
        }


 */

/*
    @Override
    public boolean isAuthorizedToAccess(ISecuredObject<Permission> object) {
        Set<Permission> requiredPermissions = object.getPermissions();
        if (null == requiredPermissions || requiredPermissions.isEmpty()) {
            return true;
        }
        Set<Permission> havingPermissions = getAllPermissionExpanded();
        SecureMode secureMode = object.getSecureMode();
        switch (secureMode) {
            case FitAll:
                return havingPermissions.containsAll(requiredPermissions);
            case FitNone:
                return !SetUtility.containsAny(havingPermissions, requiredPermissions);
            case FitAny:
                return SetUtility.containsAny(havingPermissions, requiredPermissions);
            default:
                return false;
        }
    }

    @Override
    public boolean isAuthorizedToAccessEntity(String entityType, AccessType accessType) {
        Map<String, AccessType> accesses = accessMap.get();
        if (accesses == null) {
            Set<Permission> rawPermissions = null;
            rawPermissions = PermissionUtility.instance().appendAllPermissions(rawPermissions, this.getPermissions());
            rawPermissions = PermissionUtility.instance().appendAllRoles(rawPermissions, this.getRoles());

            Set<Permission> permissions = PermissionUtility.instance().expandPermissions(rawPermissions, null, true, false);
            accesses = PermissionUtility.instance().getAccessMap(permissions, null);
            this.accessMap = new WeakReference<Map<String, AccessType>>(accesses);
        }
        AccessType allowedAccess = accesses.getOrDefault(entityType, AccessType.instance(AccessType.NONE));
        return allowedAccess.containsAll(accessType);
    }

    */
}
