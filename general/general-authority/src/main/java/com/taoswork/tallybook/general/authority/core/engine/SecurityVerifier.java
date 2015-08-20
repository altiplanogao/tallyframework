package com.taoswork.tallybook.general.authority.core.engine;

import com.taoswork.tallybook.general.authority.core.ISecurityVerifier;
import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.authority.core.basic.ProtectionMode;
import com.taoswork.tallybook.general.authority.core.permission.IEntityPermission;
import com.taoswork.tallybook.general.authority.core.permission.IPermissionOwner;
import com.taoswork.tallybook.general.authority.core.resource.IResourceInstance;
import com.taoswork.tallybook.general.authority.core.resource.IResourceProtection;
import com.taoswork.tallybook.general.authority.core.resource.ResourceFitting;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public class SecurityVerifier implements ISecurityVerifier{
    private SecuredResourceManager securedResourceManager;

    public SecurityVerifier(SecuredResourceManager securedResourceManager) {
        this.securedResourceManager = securedResourceManager;
    }

    @Override
    public boolean canAccess(IPermissionOwner perms, Access access, String resourceEntity) {
        IEntityPermission entityPermission = perms.getEntityPermission(resourceEntity);
        if(entityPermission == null){
            return false;
        }

        //We don't know the exact purpose, so just use the merged access;
        Access mergedAccess = entityPermission.mergedAccess();
        return mergedAccess.hasAccess(access);
    }

    @Override
    public boolean canAccess(IPermissionOwner perms, Access access, IResourceInstance ... resources) {
        if(resources.length == 0){
            throw new IllegalArgumentException();
        }
        String resourceEntity = resources[0].resourceEntity();
        IEntityPermission entityPermission = perms.getEntityPermission(resourceEntity);

        ResourceFitting fitting = null;
        if(resources.length == 1) {
            fitting = securedResourceManager.getResourceFitting(resources[0]);
        }else {
            fitting = securedResourceManager.getResourceFitting(true, resources);
        }
        Access mergedAccess = entityPermission.accessByFilters(fitting.matchingFilters,
            fitting.isMasterControlled, fitting.protectionMode);
        return mergedAccess.hasAccess(access);
    }
}
