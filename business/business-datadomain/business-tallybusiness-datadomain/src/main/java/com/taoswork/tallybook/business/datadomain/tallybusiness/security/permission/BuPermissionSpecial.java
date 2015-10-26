package com.taoswork.tallybook.business.datadomain.tallybusiness.security.permission;

import com.taoswork.tallybook.general.authority.domain.permission.PermissionSpecial;
import com.taoswork.tallybook.general.datadomain.support.entity.PersistFriendly;

/**
 * Created by Gao Yuan on 2015/8/27.
 */
@PersistFriendly(nameOverride = "bu-permission-special")
public interface BuPermissionSpecial extends PermissionSpecial<BuPermission> {

    Long getBuId();

    void setBuId(Long organizationId);

}
