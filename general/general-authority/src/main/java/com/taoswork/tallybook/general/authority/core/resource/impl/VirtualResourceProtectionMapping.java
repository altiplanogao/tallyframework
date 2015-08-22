package com.taoswork.tallybook.general.authority.core.resource.impl;

import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.authority.core.basic.ProtectionMode;
import com.taoswork.tallybook.general.authority.core.resource.IVirtualResourceProtectionMapping;

/**
 * Created by Gao Yuan on 2015/8/22.
 */
public class VirtualResourceProtectionMapping implements IVirtualResourceProtectionMapping {
    //trustor
    /**
     * virtualResourceEntity: usually be a fake resource
     */
    private final String virtualResourceEntity;

    private final Access virtualAccess;

    //trustee
    public final String trusteeResourceEntity;

    private final Access trusteeAccess;

    //
    private final ProtectionMode protectionMode;

    public VirtualResourceProtectionMapping(String virtualResourceEntity, Access virtualAccess,
                                            String trusteeResourceEntity, Access trusteeAccess) {
        this(virtualResourceEntity, virtualAccess,
            trusteeResourceEntity, trusteeAccess, ProtectionMode.FitAll);
    }

    public VirtualResourceProtectionMapping(String virtualResourceEntity, Access virtualAccess,
                                            String trusteeResourceEntity, Access trusteeAccess,
                                            ProtectionMode protectionMode) {
        this.virtualResourceEntity = virtualResourceEntity;
        this.virtualAccess = virtualAccess;
        this.trusteeResourceEntity = trusteeResourceEntity;
        this.trusteeAccess = trusteeAccess;
        this.protectionMode = protectionMode;
    }

    @Override
    public String getVirtualResource() {
        return virtualResourceEntity;
    }

    @Override
    public Access getVirtualAccess() {
        return virtualAccess;
    }

    @Override
    public String getTrusteeResourceEntity() {
        return trusteeResourceEntity;
    }

    @Override
    public Access getTrusteeAccess() {
        return trusteeAccess;
    }

    @Override
    public ProtectionMode getProtectionMode() {
        return protectionMode;
    }
}
