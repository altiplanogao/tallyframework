package com.taoswork.tallybook.general.authority.core.verifier.impl;

import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.authority.core.permission.IEntityPermission;
import com.taoswork.tallybook.general.authority.core.permission.IPermissionAuthority;
import com.taoswork.tallybook.general.authority.core.resource.*;
import com.taoswork.tallybook.general.authority.core.resource.impl.ResourceProtectionManager;
import com.taoswork.tallybook.general.authority.core.resource.impl.ResourceProtectionMapping;
import com.taoswork.tallybook.general.authority.core.verifier.IAccessVerifier;
import com.taoswork.tallybook.general.authority.core.verifier.IMappedAccessVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public class AccessVerifier implements IAccessVerifier, IMappedAccessVerifier {
    private ResourceProtectionManager securedResourceManager;
    private final Map<_SimulatedResourceAccess, IResourceProtectionMapping> simulatedResourceProtections
        = new ConcurrentHashMap<_SimulatedResourceAccess, IResourceProtectionMapping>();

    public AccessVerifier(ResourceProtectionManager securedResourceManager) {
        this.securedResourceManager = securedResourceManager;
    }

    @Override
    public IMappedAccessVerifier registerResourceMapping(ResourceProtectionMapping protection){
        simulatedResourceProtections.put(new _SimulatedResourceAccess(protection), protection);
        return this;
    }

    @Override
    public Access getAllPossibleAccess(IPermissionAuthority auth, String resourceType) {
        resourceType = securedResourceManager.correctResourceEntity(resourceType);
        IEntityPermission entityPermission = auth.getEntityPermission(resourceType);
        if (entityPermission == null) {
            return Access.None;
        }

        //We don't know the exact purpose, so just use the merged access;
        Access mergedAccess = entityPermission.getQuickCheckAccess();
        return mergedAccess;
    }

    @Override
    public boolean canAccess(IPermissionAuthority auth, Access access, String resourceType) {
        resourceType = securedResourceManager.correctResourceEntity(resourceType);
        IEntityPermission entityPermission = auth.getEntityPermission(resourceType);
        if (entityPermission == null) {
            return false;
        }

        //We don't know the exact purpose, so just use the merged access;
        Access mergedAccess = entityPermission.getQuickCheckAccess();
        return mergedAccess.hasAccess(access);
    }

    @Override
    public boolean canAccess(IPermissionAuthority auth, Access access, String resourceType, Object... instances) {
        if (instances.length == 0) {
            throw new IllegalArgumentException();
        }

        resourceType = securedResourceManager.correctResourceEntity(resourceType);
        IEntityPermission entityPermission = auth.getEntityPermission(resourceType);

        ResourceFitting fitting = null;
        if (instances.length == 1) {
            fitting = securedResourceManager.getResourceFitting(resourceType, instances[0]);
        } else {
            fitting = securedResourceManager.getResourceFitting(true, resourceType, instances);
        }
        Access mergedAccess = entityPermission.getAccessByFilters(fitting.matchingFilters,
            fitting.isMasterControlled, fitting.protectionMode);
        return mergedAccess.hasAccess(access);
    }

    @Override
    public AccessibleFitting calcAccessibleFitting(IPermissionAuthority auth, Access access, String resourceType) {
        resourceType = securedResourceManager.correctResourceEntity(resourceType);
        IEntityPermission entityPermission = auth.getEntityPermission(resourceType);
        if (entityPermission == null) {
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
    public AccessibleFitting calcAccessibleFitting(IEntityPermission entityPermission, Access access) {
        String resourceType = entityPermission.getResourceEntity();
        IResourceProtection protection = securedResourceManager.getResourceProtection(resourceType);

        boolean hasMasterAccess = entityPermission.getMasterAccess().hasAccess(access);
        List<String> matchingFilters = new ArrayList<String>();
        List<String> unmatchedFilters = new ArrayList<String>();
        for (IResourceFilter filter : protection.getFilters()) {
            boolean hasAccess = entityPermission.getAccessByFilter(filter.getCode()).hasAccess(access);
            (hasAccess ? matchingFilters : unmatchedFilters).add(filter.getCode());
        }

        switch (protection.getProtectionMode()) {
            case FitAll:
                if (protection.isMasterControlled()) {
                    if (hasMasterAccess) {
                        return new AccessibleFitting(null, unmatchedFilters);
                    } else {
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
                        if (matchingFilters.isEmpty()) {
                            return null;
                        } else {
                            return new AccessibleFitting(matchingFilters, null);
                        }
                    } else {
                        if (matchingFilters.isEmpty()) {
                            return new AccessibleFitting(null, unmatchedFilters);
                        } else if (unmatchedFilters.isEmpty()) {
                            return new AccessibleFitting(null, null);
                        } else {
                            return new AccessibleFitting(matchingFilters, unmatchedFilters, true);
                        }
                    }
                }
            default:
                throw new IllegalStateException();
        }
    }


    @Override
    public boolean canAccessMappedResource(IPermissionAuthority auth, Access access, String virtualResource) {
        _SimulatedResourceAccess simulatedResourceAccess = new _SimulatedResourceAccess(virtualResource, access);
        IResourceProtectionMapping protection = this.simulatedResourceProtections.getOrDefault(simulatedResourceAccess, null);
        if (protection == null)
            return false;

        IEntityPermission entityPermission = auth.getEntityPermission(protection.getTrusteeResourceEntity());
        if (entityPermission == null) {
            return false;
        }

        //We don't know the exact purpose, so just use the merged access;
        Access mergedAccess = entityPermission.getQuickCheckAccess();
        switch (protection.getProtectionMode()){
            case FitAll:
                return mergedAccess.hasAccess(protection.getTrusteeAccess());
            case FitAny:
                return mergedAccess.hasAnyAccess(protection.getTrusteeAccess());
            default:
                throw new IllegalStateException();
        }
    }
}
