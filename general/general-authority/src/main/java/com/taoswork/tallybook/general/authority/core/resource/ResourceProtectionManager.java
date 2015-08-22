package com.taoswork.tallybook.general.authority.core.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public final class ResourceProtectionManager {
    private Map<String, IResourceProtection> resourceEntityRegistry = new ConcurrentHashMap<String, IResourceProtection>();

    public ResourceProtectionManager() {
    }

    public ResourceProtectionManager registerResourceProtection(IResourceProtection resourceProtection) {
        resourceEntityRegistry.put(resourceProtection.getResourceEntity(), resourceProtection);
        return this;
    }

    public IResourceProtection getResourceProtection(String resourceEntity) {
        return resourceEntityRegistry.getOrDefault(resourceEntity, null);
    }

    public ResourceFitting getResourceFitting(IResourceInstance resource) {
        String resourceEntity = resource.getResourceEntity();
        IResourceProtection protection = getResourceProtection(resourceEntity);

        List<String> matchingFilter = new ArrayList<String>();
        List<String> unmatchedFilter = new ArrayList<String>();
        for (IResourceFilter filter : protection.getFilters()) {
            if (filter.isMatch(resource)) {
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

    public ResourceFitting getResourceFitting(boolean matchingPreferred, IResourceInstance... resources) {
        if (resources.length == 0) {
            throw new IllegalArgumentException();
        }
        String resourceEntity = resources[0].getResourceEntity();
        IResourceProtection protection = getResourceProtection(resourceEntity);

        List<String> matchingFilter = new ArrayList<String>();
        List<String> unmatchedFilter = new ArrayList<String>();
        String masterFilter = "";
        for (IResourceFilter filter : protection.getFilters()) {
            boolean matching = false;
            boolean unmatched = false;
            for (IResourceInstance resource : resources) {
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
