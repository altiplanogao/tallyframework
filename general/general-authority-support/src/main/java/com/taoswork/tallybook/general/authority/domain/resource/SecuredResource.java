package com.taoswork.tallybook.general.authority.domain.resource;

import com.taoswork.tallybook.general.authority.core.basic.ProtectionMode;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

import java.util.List;

/**
 * database layer object of IResourceProtection ({@link com.taoswork.tallybook.general.authority.core.resource.IResourceProtection})
 */
public interface SecuredResource<RF extends SecuredResourceFilter> extends Persistable {

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

    List<RF> getFilters();

    void setFilters(List<RF> criterias);

    int getVersion();

    boolean isMainLine();

    /*

    String getFilterNamespace();

    void setFilterNamespace(String filterNamespace);

     */
}
