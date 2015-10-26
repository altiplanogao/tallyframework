package com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission;

import com.taoswork.tallybook.general.authority.domain.resource.SecuredResourceSpecial;
import com.taoswork.tallybook.general.datadomain.support.entity.PersistFriendly;

@PersistFriendly(nameOverride = "admin-secured-resource-special")
public interface AdminSecuredResourceSpecial extends SecuredResourceSpecial<AdminSecuredResource> {
}
