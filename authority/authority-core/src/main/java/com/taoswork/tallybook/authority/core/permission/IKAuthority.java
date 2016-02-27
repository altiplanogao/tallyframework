package com.taoswork.tallybook.authority.core.permission;

/**
 * IKAuthority is owner of .IKPermission (of different resource entities)
 * We may treat it as a user, owning permissions
 */
public interface IKAuthority {

    IKPermission getPermission(String resource);
}
