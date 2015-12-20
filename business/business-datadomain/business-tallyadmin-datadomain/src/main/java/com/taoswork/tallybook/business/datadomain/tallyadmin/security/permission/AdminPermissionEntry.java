package com.taoswork.tallybook.business.datadomain.tallyadmin.security.permission;

import com.taoswork.tallybook.general.authority.domain.permission.PermissionEntry;
import com.taoswork.tallybook.general.datadomain.support.entity.PersistEntity;

/**
 * Created by Gao Yuan on 2015/8/27.
 */
@PersistEntity(nameOverride = "admin-permission-entry")
public interface AdminPermissionEntry
    extends PermissionEntry<
        AdminPermission,
        AdminSecuredResource,
        AdminSecuredResourceSpecial> {
}
