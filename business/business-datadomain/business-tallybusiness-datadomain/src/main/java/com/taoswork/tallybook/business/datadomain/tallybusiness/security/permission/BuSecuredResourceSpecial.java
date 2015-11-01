package com.taoswork.tallybook.business.datadomain.tallybusiness.security.permission;

import com.taoswork.tallybook.general.authority.domain.resource.SecuredResourceSpecial;
import com.taoswork.tallybook.general.datadomain.support.entity.PersistEntity;

@PersistEntity(nameOverride = "bu-secured-resource-filter")
public interface BuSecuredResourceSpecial extends SecuredResourceSpecial<BuSecuredResource> {
}
