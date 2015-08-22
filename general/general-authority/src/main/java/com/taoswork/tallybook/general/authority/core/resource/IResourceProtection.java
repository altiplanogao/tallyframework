package com.taoswork.tallybook.general.authority.core.resource;

import com.taoswork.tallybook.general.authority.core.basic.ProtectionMode;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public interface IResourceProtection {

    String getFriendlyName();

    IResourceProtection setFriendlyName(String friendlyName);

    String getResourceEntity();

    String getCategory();

    String getFilterNamespace();

    void setFilterNamespace(String filterNamespace);

    IResourceProtection setCategory(String category);

    boolean isMasterControlled();

    IResourceProtection setMasterControlled(boolean isMasterControlled);

    ProtectionMode getProtectionMode();

    IResourceProtection setProtectionMode(ProtectionMode protectionMode);

    int version();

    Collection<IResourceFilter> getFilters();

    IResourceProtection addFilters(IResourceFilter... filters);

    IResourceProtection cleanFilters();
}
