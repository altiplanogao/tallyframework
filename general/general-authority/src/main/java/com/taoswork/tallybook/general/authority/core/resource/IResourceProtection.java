package com.taoswork.tallybook.general.authority.core.resource;

import com.taoswork.tallybook.general.authority.core.basic.ProtectionMode;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public interface IResourceProtection {

    String getResourceEntity();

    void setCategory(String category);

    String getCategory();

    void setProtectionMode(ProtectionMode protectionMode);

    ProtectionMode getProtectionMode();

    void setFriendlyName(String friendlyName);

    String getFriendlyName();

    void setMasterControlled(boolean isMasterControlled);

    boolean isMasterControlled();

    Collection<IResourceFilter> getFilters();

    IResourceProtection addFilters(IResourceFilter... filters);

    IResourceProtection cleanFilters();
}
