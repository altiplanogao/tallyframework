package com.taoswork.tallybook.authority.core.resource.impl;

import com.taoswork.tallybook.authority.core.ProtectionMode;
import com.taoswork.tallybook.authority.core.resource.IKProtection;
import com.taoswork.tallybook.authority.core.resource.IKProtectionCase;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public final class KProtection implements IKProtection {
    private final String resource;


    /**
     * masterControlled, see KPermission.masterAccess
     * <p>
     * If user want to access Resource filtered by 'FilterA'
     * The user must owns a KPermissionCase (with proper access) referring to the filter.
     * <p>
     * When masterControlled enabled here. The user should also have proper access on the KPermission
     */
    private boolean masterControlled = true;

    private ProtectionMode protectionMode = ProtectionMode.FitAll;

    private ConcurrentHashMap<String, IKProtectionCase> casesMap = new ConcurrentHashMap<String, IKProtectionCase>();

    private final long version;

    public KProtection(String resource) {
        this(resource, 0);
    }

    public KProtection(String resource, long version) {
        this.resource = resource;
        this.version = version;
    }

    @Override
    public String getResource() {
        return resource;
    }

    @Override
    public boolean isMasterControlled() {
        return masterControlled;
    }

    @Override
    public IKProtection setMasterControlled(boolean isMasterControlled) {
        this.masterControlled = isMasterControlled;
        return this;
    }

    @Override
    public ProtectionMode getProtectionMode() {
        return protectionMode;
    }

    @Override
    public IKProtection setProtectionMode(ProtectionMode protectionMode) {
        this.protectionMode = protectionMode;
        return this;
    }

    @Override
    public long version() {
        return version;
    }

    @Override
    public Collection<IKProtectionCase> getCases() {
        return casesMap.values();
    }

    @Override
    public IKProtection addCase(IKProtectionCase _case) {
        casesMap.put(_case.getCode(), _case);
        return this;
    }

    @Override
    public IKProtection addCases(IKProtectionCase... cases) {
        for (IKProtectionCase _case : cases) {
            casesMap.put(_case.getCode(), _case);
        }
        return this;
    }

    @Override
    public IKProtection cleanCases() {
        casesMap.clear();
        return this;
    }

}
