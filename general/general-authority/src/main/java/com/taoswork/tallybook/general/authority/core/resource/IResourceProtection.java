package com.taoswork.tallybook.general.authority.core.resource;

import com.taoswork.tallybook.general.authority.core.basic.ProtectionMode;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public interface IResourceProtection {

    String getFriendlyName();

    void setFriendlyName(String friendlyName);

    String getResourceEntity();

    String getCategory();

    void setCategory(String category);

    boolean isMasterControlled();

    void setMasterControlled(boolean isMasterControlled);

    ProtectionMode getProtectionMode();

    void setProtectionMode(ProtectionMode protectionMode);

    Collection<IResourceFilter> getFilters();

    IResourceProtection addFilters(IResourceFilter... filters);

    IResourceProtection cleanFilters();
}
