package com.taoswork.tallybook.general.authority.core.authority.permission;


import com.taoswork.tallybook.general.authority.core.authority.access.ResourceAccess;
import com.taoswork.tallybook.general.authority.core.authority.resource.ResourceCriteria;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/4/19.
 */
public interface PermissionEntry
         extends Serializable {
    Long getId();

    void setId(Long id);

    String getName();

    PermissionEntry setName(String name);

    ResourceCriteria getResourceCriteria();

    PermissionEntry setResourceCriteria(ResourceCriteria resourceCriteria);

    ResourceAccess getAccess();

    PermissionEntry setAccess(ResourceAccess access);

    Long getOrganizationId();

    PermissionEntry setOrganizationId(Long organizationId);

    List<Permission> getPermissionsUsingThis();

    PermissionEntry setPermissionsUsingThis(List<Permission> permissionsUsingThis);
}