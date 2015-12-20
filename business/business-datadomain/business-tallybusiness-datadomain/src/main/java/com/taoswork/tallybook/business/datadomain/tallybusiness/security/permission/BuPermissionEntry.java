package com.taoswork.tallybook.business.datadomain.tallybusiness.security.permission;

import com.taoswork.tallybook.general.authority.domain.permission.PermissionEntry;
import com.taoswork.tallybook.general.datadomain.support.entity.PersistEntity;

/**
 * Created by Gao Yuan on 2015/8/27.
 */
@PersistEntity(nameOverride = "bu-permission-entry")
public interface BuPermissionEntry extends PermissionEntry<BuPermission, BuSecuredResource, BuSecuredResourceSpecial> {

    Long getBuId();

    void setBuId(Long organizationId);

}
