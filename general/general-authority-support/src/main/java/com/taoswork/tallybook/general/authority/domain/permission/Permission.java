package com.taoswork.tallybook.general.authority.domain.permission;

import com.taoswork.tallybook.general.authority.domain.resource.SecuredResourceFilter;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

import java.util.List;

/**
 * Permission is used to control resource (Resource is described by type ProtectedResource)
 * access from proper user.
 * User owns permission, and resource requires permission.
 *
 * Permission controls Resource access:
 * ResourceCriteria {@link SecuredResourceFilter}
 * defines a kind of resource need to be secured.
 *
 * {@link PermissionSpecial} defines the access permission for a specified kind of resource (specified by ResourceCriteria).
 * SUMMARY:
 * {@link PermissionSpecial} DEFINES ACCESS TO RESOURCE.
 *
 * {@link Permission} contains children '{@link PermissionSpecial}'.
 * Permission is designed as a tree, a permission node could be contained
 * in another permission node, thus the parent node has all the permission defined in the child node.
 * SUMMARY:
 * {@link Permission} DEFINES A BUNCH OF {@link PermissionSpecial}
 * {@link PermissionSpecial} DEFINES ACCESS TO RESOURCE.
 *
 * {@link Role} contains a bunch of {@link Permission}.
 * SUMMARY:
 * {@link Role} DEFINES A BUNCH OF {@link PermissionSpecial}
 * {@link PermissionSpecial} DEFINES ACCESS TO RESOURCE.
 *
 * IPermissionUser {@link com.taoswork.tallybook.general.authority.core.authority.user.IPermissionUser}
 * represents for a permission consumer, having values of {@link Permission} and {@link Role} assigned.
 * SUMMARY:
 * {@link com.taoswork.tallybook.general.authority.core.authority.user.IPermissionUser} DEFINES A BUNCH OF {@link PermissionSpecial}
 * {@link PermissionSpecial} DEFINES ACCESS TO RESOURCE.
 *
 *
 *
 * USER OWNS PERMISSIONS {@link PermissionSpecial}
 * If a tallyuser has permission containing access to a type of object,
 * then it's ok for him to access such type of objects.
 *
 * RESOURCE REQUIRES PERMISSION
 * If the resource requires multi-type permissions,
 * in order to access such resource, the tallyuser must have all these permissions.
 *
 * Created by Gao Yuan on 2015/4/15.
 */
public interface Permission<PS extends PermissionSpecial> extends Persistable {

    Long getId();

    void setId(Long id);

    String getScreenName();

    void setScreenName(String screenName);

    List<PS> getAllEntries();

    void setAllEntries(List<PS> allEntries);
}
