package com.taoswork.tallybook.general.authority.core;

import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.authority.core.permission.IPermissionOwner;
import com.taoswork.tallybook.general.authority.core.resource.IResourceInstance;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public interface ISecurityVerifier {
    boolean canAccess (IPermissionOwner perms, Access access, String resourceEntity);
    boolean canAccess (IPermissionOwner perms, Access access, IResourceInstance... resources);
}
