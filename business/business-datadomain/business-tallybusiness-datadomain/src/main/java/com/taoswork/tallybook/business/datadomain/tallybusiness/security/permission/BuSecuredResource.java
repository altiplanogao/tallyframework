package com.taoswork.tallybook.business.datadomain.tallybusiness.security.permission;

import com.taoswork.tallybook.general.authority.domain.resource.SecuredResource;
import com.taoswork.tallybook.general.datadomain.support.entity.PersistFriendly;

@PersistFriendly(nameOverride = "bu-secured-resource")
public interface BuSecuredResource extends SecuredResource<BuSecuredResourceSpecial> {

    Long getOrganization();

    void setOrganization(Long organization);
}
