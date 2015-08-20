package com.taoswork.tallybook.general.authority.core.engine;

import com.taoswork.tallybook.general.authority.core.ISecurityVerifier;
import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.authority.core.permission.IEntityPermission;
import com.taoswork.tallybook.general.authority.core.permission.IPermissionOwner;
import com.taoswork.tallybook.general.authority.core.resource.*;

import java.util.ArrayList;
import java.util.List;

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
        Access mergedAccess = entityPermission.getQuickCheckAccess();
        return mergedAccess.hasAccess(access);
    }

    @Override
    public boolean canAccess(IPermissionOwner perms, Access access, IResourceInstance ... resources) {
        if(resources.length == 0){
            throw new IllegalArgumentException();
        }
        String resourceEntity = resources[0].getResourceEntity();
        IEntityPermission entityPermission = perms.getEntityPermission(resourceEntity);

        ResourceFitting fitting = null;
        if(resources.length == 1) {
            fitting = securedResourceManager.getResourceFitting(resources[0]);
        }else {
            fitting = securedResourceManager.getResourceFitting(true, resources);
        }
        Access mergedAccess = entityPermission.getAccessByFilters(fitting.matchingFilters,
            fitting.isMasterControlled, fitting.protectionMode);
        return mergedAccess.hasAccess(access);
    }

    @Override
    public AccessibleFitting calcAccessibleFitting(IPermissionOwner perms, Access access, String resourceEntity) {
        IEntityPermission entityPermission = perms.getEntityPermission(resourceEntity);
        if(entityPermission == null){
            return null;
        }
        return calcAccessibleFitting(entityPermission, access);
    }
    /**
     * If returns null, no resource can pass
     *
     * @param entityPermission
     * @param access
     * @return
     */
    @Override
    public AccessibleFitting calcAccessibleFitting(IEntityPermission entityPermission, Access access){
        String resourceEntity = entityPermission.getResourceEntity();
        IResourceProtection protection = securedResourceManager.getResourceProtection(resourceEntity);

        boolean hasMasterAccess = entityPermission.getMasterAccess().hasAccess(access);
        List<String> matchingFilters = new ArrayList<String>();
        List<String> unmatchedFilters = new ArrayList<String>();
        for (IResourceFilter filter : protection.getFilters()){
            boolean hasAccess = entityPermission.getAccessByFilter(filter.getCode()).hasAccess(access);
            (hasAccess ? matchingFilters : unmatchedFilters).add(filter.getCode());
        }

        switch (protection.getProtectionMode()){
            case FitAll:
                if (protection.isMasterControlled()) {
                    if (hasMasterAccess) {
                        return new AccessibleFitting(null, unmatchedFilters);
                    }else {
                        return null;
                    }
                } else {
                    if (hasMasterAccess) {
                        return new AccessibleFitting(null, unmatchedFilters);
                    } else if (matchingFilters.isEmpty()) {
                        return null;
                    } else {
                        return new AccessibleFitting(matchingFilters, unmatchedFilters);
                    }
                }

            case FitAny:
                if (protection.isMasterControlled()) {
                    if (!hasMasterAccess) {
                        return null;
                    } else {
                        if (matchingFilters.isEmpty()) {
                            return new AccessibleFitting(null, unmatchedFilters);
                        } else if (unmatchedFilters.isEmpty()) {
                            return new AccessibleFitting(null, null);
                        } else {
                            return new AccessibleFitting(matchingFilters, unmatchedFilters, true);
                        }
                    }
                } else {
                    if (!hasMasterAccess) {
                        if(matchingFilters.isEmpty()){
                            return null;
                        }else {
                            return new AccessibleFitting(matchingFilters, null);
                        }
                    } else {
                        if (matchingFilters.isEmpty()) {
                            return new AccessibleFitting(null, unmatchedFilters);
                        } else if(unmatchedFilters.isEmpty()){
                            return new AccessibleFitting(null, null);
                        }else {
                            return new AccessibleFitting(matchingFilters, unmatchedFilters, true);
                        }
                    }
                }
            default:
                throw new IllegalStateException();
        }
    }
}
