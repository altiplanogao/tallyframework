package com.taoswork.tallybook.general.authority.core.permission;

/**
 * IPermissionAuthority is owner of .IEntityPermission (of different resource entities)
 * We may treat it as a user, owning permissions
 */
public interface IPermissionAuthority {

    IEntityPermission getEntityPermission(String resourceEntity);

}
