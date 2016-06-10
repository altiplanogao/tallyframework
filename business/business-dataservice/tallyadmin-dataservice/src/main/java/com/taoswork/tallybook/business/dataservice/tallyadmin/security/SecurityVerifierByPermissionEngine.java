package com.taoswork.tallybook.business.dataservice.tallyadmin.security;

import com.taoswork.tallybook.authority.core.Access;
import com.taoswork.tallybook.authority.core.permission.IKAuthority;
import com.taoswork.tallybook.authority.core.permission.IKPermission;
import com.taoswork.tallybook.authority.core.resource.IKProtectionCenter;
import com.taoswork.tallybook.authority.core.resource.link.IKProtectionMapping;
import com.taoswork.tallybook.authority.core.verifier.impl.KAccessVerifier;
import com.taoswork.tallybook.authority.solution.engine.IPermissionEngine;
import com.taoswork.tallybook.dataservice.security.impl.BaseSecurityVerifier;

/**
 * Created by Gao Yuan on 2016/2/26.
 */
public abstract class SecurityVerifierByPermissionEngine
        extends BaseSecurityVerifier {
    private final IPermissionEngine permissionEngine;
    public final String protectionScope;
    public final String tenantId;

    protected abstract String getCurrentUserId();

    public SecurityVerifierByPermissionEngine(IPermissionEngine permissionEngine, String protectionScope, String tenantId) {
        this.permissionEngine = permissionEngine;
        this.protectionScope = protectionScope;
        this.tenantId = tenantId;
    }

    @Override
    public Access getAllPossibleAccess(String resourceEntity, Access mask) {
        String userId = getCurrentUserId();
        IKAuthority authority = permissionEngine.getAuthority(protectionScope, tenantId, userId);
        IKPermission permission = authority.getPermission(resourceEntity);
        if(permission != null)
            return permission.getQuickCheckAccess();
        return Access.None;
    }

    @Override
    public boolean canAccess(String resourceEntity, Access access) {
        String userId = getCurrentUserId();
        IKAuthority authority = permissionEngine.getAuthority(protectionScope, tenantId, userId);
        IKPermission permission = authority.getPermission(resourceEntity);
        if(permission != null){
            Access masterAccess = permission.getMasterAccess();
            if(masterAccess == null)
                return false;
            return masterAccess.hasAccess(access);
        }
        return false;
    }

    @Override
    public boolean canAccess(String resourceEntity, Access access, Object... instances) {
        String userId = getCurrentUserId();
        IKAuthority authority = permissionEngine.getAuthority(protectionScope, tenantId, userId);

        IKProtectionCenter center = permissionEngine.getProtectionCenter(protectionScope, tenantId);
        IKProtectionMapping mapping = permissionEngine.getProtectionMapping(protectionScope);
        KAccessVerifier accessVerifier = new KAccessVerifier(center, mapping);

        return accessVerifier.canAccess(authority, access, resourceEntity, instances);
    }

    @Override
    public boolean canAccessMappedResource(String mappedResource, Access access) {
        IKProtectionMapping mapping = permissionEngine.getProtectionMapping(protectionScope);
        mappedResource = mapping.correctResource(mappedResource);
        return this.canAccess(mappedResource, access);
    }
}
