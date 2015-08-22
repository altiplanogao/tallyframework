package com.taoswork.tallybook.general.authority.core.resource.impl;

import com.taoswork.tallybook.general.authority.core.resource.IResourceFilter;
import com.taoswork.tallybook.general.authority.core.resource.IResourceProtection;
import com.taoswork.tallybook.general.authority.core.resource.IResourceProtectionManager;
import com.taoswork.tallybook.general.authority.core.resource.ResourceFitting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public final class ResourceProtectionManager implements IResourceProtectionManager {
    private final Map<String, IResourceProtection> resourceEntityRegistry = new ConcurrentHashMap<String, IResourceProtection>();
    private final Map<String, String> resourceEntityAlias = new HashMap<String, String>();
    private int version;

    public ResourceProtectionManager() {
    }

    @Override
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String correctResourceEntity(String resourceEntity) {
        return resourceEntityAlias.getOrDefault(resourceEntity, resourceEntity);
    }

    @Override
    public IResourceProtectionManager registerResourceProtection(IResourceProtection resourceProtection) {
        resourceEntityRegistry.put(resourceProtection.getResourceEntity(), resourceProtection);
        return this;
    }

    @Override
    public IResourceProtectionManager registerAlias(String resourceEntity, String alias){
        resourceEntityAlias.put(alias, resourceEntity);
        return this;
    }

    @Override
    public IResourceProtection getResourceProtection(String resourceEntity) {
        resourceEntity = resourceEntityAlias.getOrDefault(resourceEntity, resourceEntity);
        return resourceEntityRegistry.getOrDefault(resourceEntity, null);
    }

    @Override
    public ResourceFitting getResourceFitting(String resourceEntity, Object instance) {
        IResourceProtection protection = getResourceProtection(resourceEntity);

        List<String> matchingFilter = new ArrayList<String>();
        List<String> unmatchedFilter = new ArrayList<String>();
        for (IResourceFilter filter : protection.getFilters()) {
            if (filter.isMatch(instance)) {
                matchingFilter.add(filter.getCode());
            } else {
                unmatchedFilter.add(filter.getCode());
            }
        }
        return new ResourceFitting(
            protection.isMasterControlled(),
            protection.getProtectionMode(),
            matchingFilter, unmatchedFilter);
    }

    @Override
    public ResourceFitting getResourceFitting(boolean matchingPreferred, String resourceEntity, Object... instances) {
        if (instances.length == 0) {
            throw new IllegalArgumentException();
        }
        IResourceProtection protection = getResourceProtection(resourceEntity);

        List<String> matchingFilter = new ArrayList<String>();
        List<String> unmatchedFilter = new ArrayList<String>();
        String masterFilter = "";
        for (IResourceFilter filter : protection.getFilters()) {
            boolean matching = false;
            boolean unmatched = false;
            for (Object resource : instances) {
                if (filter.isMatch(resource)) {
                    matching = true;
                } else {
                    unmatched = true;
                }
            }
            if ((matchingPreferred && matching) || (!(matchingPreferred || unmatched))) {
                matchingFilter.add(filter.getCode());
            } else {
                unmatchedFilter.add(filter.getCode());
            }
        }
        return new ResourceFitting(
            protection.isMasterControlled(),
            protection.getProtectionMode(),
            matchingFilter, unmatchedFilter);
    }

}
