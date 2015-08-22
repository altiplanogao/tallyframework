package com.taoswork.tallybook.general.authority.core.verifier;

import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.authority.core.permission.IEntityPermission;
import com.taoswork.tallybook.general.authority.core.permission.IPermissionAuthority;
import com.taoswork.tallybook.general.authority.core.resource.AccessibleFitting;
import com.taoswork.tallybook.general.authority.core.resource.IResourceInstance;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public interface IVirtualResourceSecurityVerifier {
    boolean canAccess(IPermissionAuthority auth, Access access, String virtualResource);
}
