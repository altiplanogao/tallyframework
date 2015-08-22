package com.taoswork.tallybook.general.authority.core.verifier.impl;

import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.authority.core.permission.IEntityPermission;
import com.taoswork.tallybook.general.authority.core.permission.IPermissionAuthority;
import com.taoswork.tallybook.general.authority.core.resource.*;
import com.taoswork.tallybook.general.authority.core.resource.impl.VirtualResourceProtectionMapping;
import com.taoswork.tallybook.general.authority.core.verifier.IVirtualResourceSecurityVerifier;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public class VirtualResourceSecurityVerifier implements IVirtualResourceSecurityVerifier {
    private static class _SimulatedResourceAccess {
        private final String resourceEntity;

        private final Access access;

        public _SimulatedResourceAccess(String resourceEntity, Access access) {
            this.resourceEntity = resourceEntity;
            this.access = access;
        }

        public _SimulatedResourceAccess(IVirtualResourceProtectionMapping protection){
            this(protection.getVirtualResource(), protection.getVirtualAccess());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (!(o instanceof _SimulatedResourceAccess)) return false;

            _SimulatedResourceAccess that = (_SimulatedResourceAccess) o;

            return new EqualsBuilder()
                .append(resourceEntity, that.resourceEntity)
                .append(access, that.access)
                .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                .append(resourceEntity)
                .append(access)
                .toHashCode();
        }
    }
    private final ResourceProtectionManager securedResourceManager;
    private final Map<_SimulatedResourceAccess, IVirtualResourceProtectionMapping> simulatedResourceProtections
        = new ConcurrentHashMap<_SimulatedResourceAccess, IVirtualResourceProtectionMapping>();

    public VirtualResourceSecurityVerifier(ResourceProtectionManager securedResourceManager) {
        this.securedResourceManager = securedResourceManager;
    }

    public VirtualResourceSecurityVerifier register(VirtualResourceProtectionMapping protection){
        simulatedResourceProtections.put(new _SimulatedResourceAccess(protection), protection);
        return this;
    }

    @Override
    public boolean canAccess(IPermissionAuthority auth, Access access, String virtualResource) {
        _SimulatedResourceAccess simulatedResourceAccess = new _SimulatedResourceAccess(virtualResource, access);
        IVirtualResourceProtectionMapping protection = this.simulatedResourceProtections.getOrDefault(simulatedResourceAccess, null);
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
