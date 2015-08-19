package com.taoswork.tallybook.general.authority.core.authority.user;

import com.taoswork.tallybook.general.authority.core.authority.permission.Permission;
import com.taoswork.tallybook.general.authority.core.authority.permission.Role;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/4/20.
 */
public interface IPermissionUser extends Serializable {

    Set<Permission> getPermissions();

    void setPermissions(Set<Permission> permissions);

    Set<Role> getRoles();

    void setRoles(Set<Role> roles);

//    Set<Permission> getAllPermissionExpanded();

//    boolean isAuthorizedToAccess(SecurityDescriptor object);

  //  boolean isAuthorizedToAccessEntity(ProtectedResource entity, AccessType accessType);

}
