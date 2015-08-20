package com.taoswork.tallybook.general.authority.core.resource;

import com.taoswork.tallybook.general.authority.core.basic.ProtectionMode;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public interface IResourceProtection {

    public String getResourceEntity();

    void setCategory(String category);

    public String getCategory();

    void setProtectionMode(ProtectionMode protectionMode);

    ProtectionMode getProtectionMode();

    public void setFriendlyName(String friendlyName);

    public String getFriendlyName();

    void setMasterControlled(boolean isMasterControlled);

    public boolean isMasterControlled();

    Collection<IResourceFilter> getFilters();

    IResourceProtection addFilters(IResourceFilter... filters);

    IResourceProtection cleanFilters();
}
