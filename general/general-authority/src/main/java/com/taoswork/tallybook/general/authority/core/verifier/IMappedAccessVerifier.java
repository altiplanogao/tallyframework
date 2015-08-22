package com.taoswork.tallybook.general.authority.core.verifier;

import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.authority.core.permission.IPermissionAuthority;
import com.taoswork.tallybook.general.authority.core.resource.impl.ResourceProtectionMapping;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public interface IMappedAccessVerifier {
    IMappedAccessVerifier registerResourceMapping(ResourceProtectionMapping protection);

    boolean canAccessMappedResource(IPermissionAuthority auth, Access access, String virtualResource);
}
