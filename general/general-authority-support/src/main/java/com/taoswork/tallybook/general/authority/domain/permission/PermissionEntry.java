package com.taoswork.tallybook.general.authority.domain.permission;

import com.taoswork.tallybook.general.authority.domain.access.ResourceAccess;
import com.taoswork.tallybook.general.authority.domain.permission.validation.PermissionEntryValidator;
import com.taoswork.tallybook.general.authority.domain.resource.SecuredResource;
import com.taoswork.tallybook.general.authority.domain.resource.SecuredResourceSpecial;
import com.taoswork.tallybook.general.datadomain.support.entity.PersistEntity;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

/**
 * Created by Gao Yuan on 2015/4/19.
 */
@PersistEntity(
    validators = {PermissionEntryValidator.class}
)
public interface PermissionEntry<P extends Permission,
    SR extends SecuredResource,
    SRS extends SecuredResourceSpecial>
         extends Persistable {
    Long getId();

    void setId(Long id);

    String getName();

    void setName(String name);

    SR getSecuredResource();

    void setSecuredResource(SR securedResource);

    SRS getSecuredResourceSpecial();

    void setSecuredResourceSpecial(SRS resourceCriteria);

    ResourceAccess getAccess();

    void setAccess(ResourceAccess access);

    P getPermission();

    void setPermission(P permission);
}