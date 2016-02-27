package com.taoswork.tallybook.authority.core.resource.link;

import com.taoswork.tallybook.authority.core.resource.KResourceAccess;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Gao Yuan on 2016/2/24.
 */
public final class KProtectionMapping implements IKProtectionMappingRegister {
    /**
     * alias to resource-entity-type mapping
     */
    private final Map<String, String> resourceAlias = new HashMap<String, String>();

    private final Map<KResourceAccess, IKProtectionLink> mappedResourceProtections
            = new ConcurrentHashMap<KResourceAccess, IKProtectionLink>();

    @Override
    public IKProtectionMappingRegister registerLink(IKProtectionLink protection) {
        mappedResourceProtections.put(new KResourceAccess(protection), protection);
        return this;
    }

    @Override
    public IKProtectionMappingRegister registerAlias(String resource, String alias) {
        resourceAlias.put(alias, resource);
        return this;
    }

    @Override
    public IKProtectionMappingRegister registerAlias(String resource, String... aliases) {
        for(String a : aliases) {
            resourceAlias.put(a, resource);
        }
        return this;
    }

    @Override
    public IKProtectionMappingRegister registerAlias(String resource, Collection<String> aliases) {
        for(String a : aliases) {
            resourceAlias.put(a, resource);
        }
        return this;
    }

    @Override
    public IKProtectionLink getLink(KResourceAccess rcAccess) {
        IKProtectionLink protection = this.mappedResourceProtections.get(rcAccess);
        return protection;
    }

    @Override
    public String correctResource(String resource) {
        String v = resourceAlias.get(resource);
        if (v == null)
            return resource;
        return v;
    }
}
