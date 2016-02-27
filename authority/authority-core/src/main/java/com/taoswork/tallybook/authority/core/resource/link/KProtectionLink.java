package com.taoswork.tallybook.authority.core.resource.link;

import com.taoswork.tallybook.authority.core.Access;
import com.taoswork.tallybook.authority.core.ProtectionMode;

/**
 * Created by Gao Yuan on 2015/8/22.
 */
public class KProtectionLink implements IKProtectionLink {
    //trustor
    /**
     * virtualResource: usually be a fake resource
     */
    private final String virtualResource;

    private final Access virtualAccess;

    //trustee
    public final String actualResource;

    private final Access actualAccess;

    //
    private final ProtectionMode protectionMode;

    public KProtectionLink(String virtualResource, Access virtualAccess,
                           String actualResource, Access actualAccess) {
        this(virtualResource, virtualAccess,
                actualResource, actualAccess, ProtectionMode.FitAll);
    }

    public KProtectionLink(String virtualResource, Access virtualAccess,
                           String actualResource, Access actualAccess,
                           ProtectionMode protectionMode) {
        this.virtualResource = virtualResource;
        this.virtualAccess = virtualAccess;
        this.actualResource = actualResource;
        this.actualAccess = actualAccess;
        this.protectionMode = protectionMode;
    }

    @Override
    public String getVirtualResource() {
        return virtualResource;
    }

    @Override
    public Access getVirtualAccess() {
        return virtualAccess;
    }

    @Override
    public String getActualResource() {
        return actualResource;
    }

    @Override
    public Access getActualAccess() {
        return actualAccess;
    }

    @Override
    public ProtectionMode getProtectionMode() {
        return protectionMode;
    }
}
