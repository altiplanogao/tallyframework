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

    public ResourceProtection(String resourceEntity) {
        this.resourceEntity = resourceEntity;
    }

    @Override
    public String getFriendlyName() {
        return friendlyName;
    }

    @Override
    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
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
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean isMasterControlled() {
        return masterControlled;
    }

    @Override
    public void setMasterControlled(boolean isMasterControlled) {
        this.masterControlled = isMasterControlled;
    }

    @Override
    public ProtectionMode getProtectionMode() {
        return protectionMode;
    }

    @Override
    public void setProtectionMode(ProtectionMode protectionMode) {
        this.protectionMode = protectionMode;
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
