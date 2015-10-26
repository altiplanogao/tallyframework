package com.taoswork.tallybook.general.authority.domain.permission;

import com.taoswork.tallybook.general.authority.domain.resource.SecuredResourceSpecial;
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

    SecuredResourceSpecial getSecuredResourceSpecial();

    void setSecuredResourceSpecial(SecuredResourceSpecial resourceCriteria);

    ResourceAccess getAccess();

    void setAccess(ResourceAccess access);

    String getResourceEntity();

    void setResourceEntity(String resourceEntity);

    P getPermission();

    void setPermission(P permission);
}