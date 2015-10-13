package com.taoswork.tallybook.general.authority.domain.permission;

import com.taoswork.tallybook.general.authority.domain.resource.SecuredResourceFilter;
import com.taoswork.tallybook.general.authority.domain.access.ResourceAccess;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

/**
 * Created by Gao Yuan on 2015/4/19.
 */
public interface PermissionSpecial<P extends Permission>
         extends Persistable {
    Long getId();

    void setId(Long id);

    String getName();

    void setName(String name);

    SecuredResourceFilter getSecuredResourceFilter();

    void setSecuredResourceFilter(SecuredResourceFilter resourceCriteria);

    ResourceAccess getAccess();

    void setAccess(ResourceAccess access);

    Long getOrganizationId();

    void setOrganizationId(Long organizationId);

    String getResourceEntity();

    void setResourceEntity(String resourceEntity);

    P getPermission();

    void setPermission(P permission);
}