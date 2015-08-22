package com.taoswork.tallybook.general.authority.core.authority.resource;

import com.taoswork.tallybook.general.authority.core.basic.ProtectionMode;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/5.
 */

public interface SecuredResource extends Serializable{

    Long getId();

    void setId(Long id);

    String getFriendlyName();

    void setFriendlyName(String friendlyName);

    String getResourceEntity();

    void setResourceEntity(String type);

    String getCategory();

    void setCategory(String category);

    boolean isMasterControlled();

    void setMasterControlled(boolean masterControlled);

    ProtectionMode getProtectionMode();

    void setProtectionMode(ProtectionMode protectionMode);

    List<SecuredResourceFilter> getFilters();

    void setFilters(List<SecuredResourceFilter> criterias);
}
