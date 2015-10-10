package com.taoswork.tallybook.general.authority.core.permission;

import com.taoswork.tallybook.general.authority.core.basic.Access;

/**
 * IEntityPermissionSpecial, user owned, directly or indirectly.
 *
 * And always has a corresponding IResourceFilter in ResourceProtectionManager (mapped by filter's code)
 */
public interface IEntityPermissionSpecial {

    String getFilterCode();

    Access getAccess();

    IEntityPermissionSpecial clone();
}
