package com.taoswork.tallybook.authority.mockup.resource;

import com.taoswork.tallybook.authority.core.resource.IKProtection;
import com.taoswork.tallybook.authority.core.resource.impl.BaseKProtectionCenter;
import com.taoswork.tallybook.authority.core.resource.link.IKProtectionMapping;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class CrudeKProtectionCenter
        extends BaseKProtectionCenter
        implements ICrudeKProtectionCenter {
    /**
     * All the IKProtection managed,
     * the key is the resource entity type string (IKProtection.getResource())
     */
    private final Map<String, IKProtection> resourceProtections = new ConcurrentHashMap<String, IKProtection>();
    private final IKProtectionMapping mapping;

    public CrudeKProtectionCenter(IKProtectionMapping mapping) {
        super();
        this.mapping = mapping;
    }

    @Override
    protected IKProtectionMapping getMapping() {
        return mapping;
    }

    @Override
    public ICrudeKProtectionCenter registerProtection(IKProtection protection) {
        resourceProtections.put(protection.getResource(), protection);
        return this;
    }

    @Override
    protected IKProtection getDirectResourceProtection(String resource) {
        return resourceProtections.get(resource);
    }
}
