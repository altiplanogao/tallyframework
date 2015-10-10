package com.taoswork.tallybook.general.authority.core.permission.wirte;

import com.taoswork.tallybook.general.authority.core.permission.IEntityPermissionSpecial;

/**
 * Created by Gao Yuan on 2015/8/22.
 */
public interface IEntityPermissionSpecialWrite extends IEntityPermissionSpecial {

    IEntityPermissionSpecialWrite merge(IEntityPermissionSpecial that);
}
