package com.taoswork.tallybook.general.authority.core.verifier;

import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.authority.core.permission.IEntityPermission;
import com.taoswork.tallybook.general.authority.core.permission.IPermissionAuthority;
import com.taoswork.tallybook.general.authority.core.resource.AccessibleFitting;
import com.taoswork.tallybook.general.authority.core.resource.IResourceInstance;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public interface ISecurityVerifier {
    boolean canAccess(IPermissionAuthority auth, Access access, String resourceEntity);

    boolean canAccess(IPermissionAuthority auth, Access access, IResourceInstance... resources);

    AccessibleFitting calcAccessibleFitting(IPermissionAuthority auth, Access access, String resourceEntity);

    AccessibleFitting calcAccessibleFitting(IEntityPermission entityPermission, Access access);
}
