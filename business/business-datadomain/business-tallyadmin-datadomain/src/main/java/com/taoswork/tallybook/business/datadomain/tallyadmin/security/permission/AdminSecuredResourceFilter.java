package com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission;

import com.taoswork.tallybook.general.authority.domain.resource.SecuredResourceFilter;
import com.taoswork.tallybook.general.datadomain.support.entity.PersistFriendly;

@PersistFriendly(nameOverride = "admin-secured-resource-filter")
public interface AdminSecuredResourceFilter extends SecuredResourceFilter<AdminSecuredResource> {
}
