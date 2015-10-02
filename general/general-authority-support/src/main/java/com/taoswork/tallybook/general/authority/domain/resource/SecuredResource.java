package com.taoswork.tallybook.general.authority.domain.resource;

import com.taoswork.tallybook.general.authority.core.basic.ProtectionMode;
import com.taoswork.tallybook.general.datadomain.support.entity.PersistFriendly;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/5.
 */

@PersistFriendly(nameOverride = "admin-resource-type")
public interface SecuredResource extends Persistable {

    Long getId();

    void setId(Long id);

    Long getOrganization();

    void setOrganization(Long organization);

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

    int getVersion();

    boolean isMainLine();
}
