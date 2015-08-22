package com.taoswork.tallybook.general.authority.core.resource.impl;

import com.taoswork.tallybook.general.authority.core.basic.ProtectionMode;
import com.taoswork.tallybook.general.authority.core.resource.IResourceFilter;
import com.taoswork.tallybook.general.authority.core.resource.IResourceProtection;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public final class ResourceProtection implements IResourceProtection {
    private String friendlyName;
    private final String resourceEntity;
    private String category;

    /**
     * Restrict filter-types could be used for this resourceEntity
     */
    private String filterNamespace;
    /**
     * masterControlled, see EntityPermission.masterAccess
     * <p>
     * If user want to access Resource filtered by 'FilterA'
     * The user must owns a PermissionEntry (with proper access) referring to the filter.
     * <p>
     * When masterControlled enabled here. The user should also have proper access on the EntityPermission
     */
    private boolean masterControlled = true;
    private ProtectionMode protectionMode = ProtectionMode.FitAll;

    private ConcurrentHashMap<String, IResourceFilter> filterMap = new ConcurrentHashMap<String, IResourceFilter>();

    private final int version;

    public ResourceProtection(String resourceEntity) {
        this(resourceEntity, 0);
    }

    public ResourceProtection(String resourceEntity, int version) {
        this.resourceEntity = resourceEntity;
        this.version = version;
    }

    @Override
    public String getFriendlyName() {
        return friendlyName;
    }

    @Override
    public IResourceProtection setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
        return this;
    }

    @Override
    public String getResourceEntity() {
        return resourceEntity;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public String getFilterNamespace() {
        return filterNamespace;
    }

    @Override
    public void setFilterNamespace(String filterNamespace) {
        this.filterNamespace = filterNamespace;
    }

    @Override
    public IResourceProtection setCategory(String category) {
        this.category = category;
        return this;
    }

    @Override
    public boolean isMasterControlled() {
        return masterControlled;
    }

    @Override
    public IResourceProtection setMasterControlled(boolean isMasterControlled) {
        this.masterControlled = isMasterControlled;
        return this;
    }

    @Override
    public ProtectionMode getProtectionMode() {
        return protectionMode;
    }

    @Override
    public IResourceProtection setProtectionMode(ProtectionMode protectionMode) {
        this.protectionMode = protectionMode;
        return this;
    }

    @Override
    public int version() {
        return version;
    }

    @Override
    public Collection<IResourceFilter> getFilters() {
        return filterMap.values();
    }

    @Override
    public IResourceProtection addFilters(IResourceFilter... filters) {
        for (IResourceFilter filter : filters) {
            filterMap.put(filter.getCode(), filter);
        }
        return this;
    }

    @Override
    public IResourceProtection cleanFilters() {
        filterMap.clear();
        return this;
    }

}