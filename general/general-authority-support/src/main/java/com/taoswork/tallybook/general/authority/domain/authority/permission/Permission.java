package com.taoswork.tallybook.general.authority.domain.authority.permission;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Permission is used to control resource (Resource is described by type ProtectedResource)
 * access from proper user.
 * User owns permission, and resource requires permission.
 *
 * Permission controls Resource access:
 * ResourceCriteria {@link com.taoswork.tallybook.general.authority.domain.authority.resource.ResourceCriteria}
 * defines a kind of resource need to be secured.
 *
 * {@link PermissionEntry} defines the access permission for a specified kind of resource (specified by ResourceCriteria).
 * SUMMARY:
 * {@link PermissionEntry} DEFINES ACCESS TO RESOURCE.
 *
 * {@link Permission} contains children '{@link PermissionEntry}'.
 * Permission is designed as a tree, a permission node could be contained
 * in another permission node, thus the parent node has all the permission defined in the child node.
 * SUMMARY:
 * {@link Permission} DEFINES A BUNCH OF {@link PermissionEntry}
 * {@link PermissionEntry} DEFINES ACCESS TO RESOURCE.
 *
 * {@link Role} contains a bunch of {@link Permission}.
 * SUMMARY:
 * {@link Role} DEFINES A BUNCH OF {@link PermissionEntry}
 * {@link PermissionEntry} DEFINES ACCESS TO RESOURCE.
 *
 * IPermissionUser {@link com.taoswork.tallybook.general.authority.domain.authority.user.IPermissionUser}
 * represents for a permission consumer, having values of {@link Permission} and {@link Role} assigned.
 * SUMMARY:
 * {@link com.taoswork.tallybook.general.authority.domain.authority.user.IPermissionUser} DEFINES A BUNCH OF {@link PermissionEntry}
 * {@link PermissionEntry} DEFINES ACCESS TO RESOURCE.
 *
 *
 *
 * USER OWNS PERMISSIONS {@link PermissionEntry}
 * If a tallyuser has permission containing access to a type of object,
 * then it's ok for him to access such type of objects.
 *
 * RESOURCE REQUIRES PERMISSION
 * If the resource requires multi-type permissions,
 * in order to access such resource, the tallyuser must have all these permissions.
 *
 * Created by Gao Yuan on 2015/4/15.
 */
public interface Permission extends Serializable {

    Long getId();

    void setId(Long id);

    String getScreenName();

    void setScreenName(String screenName);

    String getInsideName();

    void setInsideName(String insideName);

//    int getAccessSymbol();
//
//    void setAccessSymbol(int accessSymbol);

    boolean isUserFriendly();

    void setUserFriendly(boolean userFriendly);

    Long getOrganizationId();

    void setOrganizationId(Long organizationId);

    List<Permission> getChildrenPermission();

    void setChildrenPermission(List<Permission> childrenPermission);

    List<PermissionEntry> getAllEntries();

    void setAllEntries(List<PermissionEntry> allEntries);

    List<Permission> getParentPermission();

    void setParentPermission(List<Permission> parentPermission);

    Set<Role> getAllRoles();

    void setAllRoles(Set<Role> allRoles);
}
