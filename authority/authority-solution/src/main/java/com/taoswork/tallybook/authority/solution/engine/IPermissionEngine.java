package com.taoswork.tallybook.authority.solution.engine;

import com.taoswork.tallybook.authority.core.permission.IKAuthority;
import com.taoswork.tallybook.authority.core.resource.IKProtectionCenter;
import com.taoswork.tallybook.authority.core.resource.link.IKProtectionMapping;
import com.taoswork.tallybook.authority.core.verifier.IKAccessVerifier;

/**
 * Created by Gao Yuan on 2016/2/24.
 */
public interface IPermissionEngine {
    IKAuthority getAuthority(String protectionSpace, String tenantId, String userId);

    IKProtectionCenter getProtectionCenter(String protectionSpace, String tenantId);

    IKProtectionMapping getProtectionMapping(String protectionSpace);

    IKAccessVerifier getAccessVerifier(String protectionSpace, String tenantId);
}
