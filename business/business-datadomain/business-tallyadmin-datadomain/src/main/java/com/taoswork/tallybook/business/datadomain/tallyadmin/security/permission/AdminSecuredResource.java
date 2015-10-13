package com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission;

import com.taoswork.tallybook.general.authority.domain.resource.SecuredResource;
import com.taoswork.tallybook.general.datadomain.support.entity.PersistFriendly;

@PersistFriendly(nameOverride = "admin-secured-resource")
public interface AdminSecuredResource extends SecuredResource<AdminSecuredResourceFilter> {
}
