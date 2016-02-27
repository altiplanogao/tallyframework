package com.taoswork.tallybook.authority.solution.domain.resource;

import com.taoswork.tallybook.authority.core.Access;
import com.taoswork.tallybook.authority.core.ProtectionMode;
import com.taoswork.tallybook.authority.core.resource.link.IKProtectionLink;
import com.taoswork.tallybook.authority.core.resource.link.KProtectionLink;

/**
 * Created by Gao Yuan on 2016/2/24.
 */
public class ProtectionLink {
    private String virtualResource;

    private Access virtualAccess;

    //trustee
    public String actualResource;

    private Access actualAccess;

    private ProtectionMode protectionMode;

    public ProtectionLink() {
    }

    public ProtectionLink(String virtualResource, Access virtualAccess, String actualResource, Access actualAccess, ProtectionMode protectionMode) {
        this.virtualResource = virtualResource;
        this.virtualAccess = virtualAccess;
        this.actualResource = actualResource;
        this.actualAccess = actualAccess;
        this.protectionMode = protectionMode;
    }

    public String getVirtualResource() {
        return virtualResource;
    }

    public void setVirtualResource(String virtualResource) {
        this.virtualResource = virtualResource;
    }

    public Access getVirtualAccess() {
        return virtualAccess;
    }

    public void setVirtualAccess(Access virtualAccess) {
        this.virtualAccess = virtualAccess;
    }

    public String getActualResource() {
        return actualResource;
    }

    public void setActualResource(String actualResource) {
        this.actualResource = actualResource;
    }

    public Access getActualAccess() {
        return actualAccess;
    }

    public void setActualAccess(Access actualAccess) {
        this.actualAccess = actualAccess;
    }

    public ProtectionMode getProtectionMode() {
        return protectionMode;
    }

    public void setProtectionMode(ProtectionMode protectionMode) {
        this.protectionMode = protectionMode;
    }

    public IKProtectionLink convert(){
        return new KProtectionLink(virtualResource, virtualAccess, actualResource, actualAccess, protectionMode);
    }
}
