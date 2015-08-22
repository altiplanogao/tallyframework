package com.taoswork.tallybook.general.authority.core.authority.permission;

import com.taoswork.tallybook.general.authority.core.authority.resource.SecuredResourceFilter;
import com.taoswork.tallybook.general.authority.domain.access.ResourceAccess;

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

    SecuredResourceFilter getSecuredResourceFilter();

    PermissionEntry setSecuredResourceFilter(SecuredResourceFilter resourceCriteria);

    ResourceAccess getAccess();

    PermissionEntry setAccess(ResourceAccess access);

    Long getOrganizationId();

    PermissionEntry setOrganizationId(Long organizationId);

    String getResourceEntity();

    void setResourceEntity(String resourceEntity);

    Permission getPermission();

    void setPermission(Permission permission);
}