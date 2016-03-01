package com.taoswork.tallybook.authority.core.verifier.impl;

import com.taoswork.tallybook.authority.core.Access;
import com.taoswork.tallybook.authority.core.IllegalCodePathException;
import com.taoswork.tallybook.authority.core.permission.IKAuthority;
import com.taoswork.tallybook.authority.core.permission.IKPermission;
import com.taoswork.tallybook.authority.core.resource.*;
import com.taoswork.tallybook.authority.core.resource.link.IKProtectionLink;
import com.taoswork.tallybook.authority.core.resource.link.IKProtectionMapping;
import com.taoswork.tallybook.authority.core.verifier.IKAccessVerifier;

import java.util.ArrayList;
import java.util.List;

public class KAccessVerifier implements IKAccessVerifier {
    private final IKProtectionCenter center;
    private final IKProtectionMapping mapping;

    public KAccessVerifier(IKProtectionCenter center,
                           IKProtectionMapping mapping) {

        this.center = center;
        this.mapping = mapping;
    }

    @Override
    public Access getAllPossibleAccess(IKAuthority auth, String resource) {
        if(mapping != null){
            resource = mapping.correctResource(resource);
        }
        IKPermission permission = auth.getPermission(resource);
        if (permission == null) {
            return Access.None;
        }

        //We don't know the exact purpose, so just use the merged access;
        Access mergedAccess = permission.getQuickCheckAccess();
        return mergedAccess;
    }

    @Override
    public boolean canAccess(IKAuthority auth, Access access, String resource) {
        if(mapping != null){
            resource = mapping.correctResource(resource);
        }
        IKPermission permission = auth.getPermission(resource);
        if (permission == null) {
            return false;
        }

        //We don't know the exact purpose, so just use the merged access;
        Access mergedAccess = permission.getQuickCheckAccess();
        return mergedAccess.hasAccess(access);
    }

    @Override
    public boolean canAccess(IKAuthority auth, Access access, String resource, Object... instances) {
        if (instances.length == 0) {
            throw new IllegalArgumentException();
        }

        if(mapping != null){
            resource = mapping.correctResource(resource);
        }
        IKPermission permission = auth.getPermission(resource);

        KCaseFitting fitting = null;
        if (instances.length == 1) {
            fitting = center.getFitting(resource, instances[0]);
        } else {
            fitting = center.getFitting(true, resource, instances);
        }
        Access mergedAccess = permission.getAccessByCases(fitting.matching,
                fitting.isMasterControlled, fitting.protectionMode);
        return mergedAccess.hasAccess(access);
    }

    @Override
    public KAccessibleScope calcAccessibleAreas(IKAuthority auth, Access access, String resource) {
        if(mapping != null){
            resource = mapping.correctResource(resource);
        }
        IKPermission permission = auth.getPermission(resource);
        if (permission == null) {
            return null;
        }
        return calcAccessibleAreas(permission, access);
    }

    @Override
    public KAccessibleScope calcAccessibleAreas(IKPermission permission, Access access) {
        final String resource = permission.getResource();
        final IKProtection protection = center.getProtection(resource);

        final boolean hasMasterAccess = permission.getMasterAccess().hasAccess(access);
        final List<String> matchingCases = new ArrayList<String>();
        final List<String> unmatchedCases = new ArrayList<String>();
        for (IKProtectionCase _case : protection.getCases()) {
            boolean hasAccess = permission.getCaseAccess(_case.getCode()).hasAccess(access);
            (hasAccess ? matchingCases : unmatchedCases).add(_case.getCode());
        }

        switch (protection.getProtectionMode()) {
            case FitAll:
                if (protection.isMasterControlled()) {
                    // C11
                    if (hasMasterAccess) {
                        //i04, i05, i06
                        return new KAccessibleScope(null, unmatchedCases);
                    } else {
                        //i01, i02, i03
                        return null;
                    }
                } else {
                    // C01
                    if (hasMasterAccess) {
                        //i04, i05, i06
                        return new KAccessibleScope(null, unmatchedCases);
                    } else if (matchingCases.isEmpty()) {
                        //i01
                        return null;
                    } else {
                        //i02, i03
                        return new KAccessibleScope(matchingCases, unmatchedCases);
                    }
                }

            case FitAny:
                if (protection.isMasterControlled()) {
                    // C10
                    if (hasMasterAccess) {
                        if (matchingCases.isEmpty()) {
                            //i04
                            return new KAccessibleScope(null, unmatchedCases);
                        } else if (unmatchedCases.isEmpty()) {
                            //i06
                            return new KAccessibleScope(null, null);
                        } else {
                            //i05
                            return new KAccessibleScope(matchingCases, unmatchedCases, true);
                        }
                    } else {
                        //i01, i02, i03
                        return null;
                    }
                } else {
                    // C00
                    if (hasMasterAccess) {
                        if (matchingCases.isEmpty()) {
                            //i04
                            return new KAccessibleScope(null, unmatchedCases); //can also handle i06
                        } else if (unmatchedCases.isEmpty()) {
                            //i06
                            return new KAccessibleScope(null, null);
                        } else {
                            //i05
                            return new KAccessibleScope(matchingCases, unmatchedCases, true);
                        }
                    } else {
                        if (matchingCases.isEmpty()) {
                            //i01
                            return null;
                        } else {
                            //i02, i03
                            return new KAccessibleScope(matchingCases, null);
                        }
                    }
                }
            default:
                throw new IllegalCodePathException("Everything should be handled, should never be here.");
        }
    }

    @Override
    public boolean canAccessMappedResource(IKAuthority auth, Access access, String virtualResource) {
        if(mapping == null)
            return false;
        final KResourceAccess rcAccess = new KResourceAccess(virtualResource, access);
        IKProtectionLink protection = mapping.getLink(rcAccess);
        if (protection == null)
            return false;

        IKPermission entityPermission = auth.getPermission(protection.getActualResource());
        if (entityPermission == null) {
            return false;
        }

        //We don't know the exact purpose, so just use the merged access;
        Access mergedAccess = entityPermission.getQuickCheckAccess();
        switch (protection.getProtectionMode()) {
            case FitAll:
                return mergedAccess.hasAccess(protection.getActualAccess());
            case FitAny:
                return mergedAccess.hasAnyAccess(protection.getActualAccess());
            default:
                throw new IllegalCodePathException("Access Verifier error.");
        }
    }
}
