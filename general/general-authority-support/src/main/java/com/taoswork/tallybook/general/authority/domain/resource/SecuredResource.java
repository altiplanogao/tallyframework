package com.taoswork.tallybook.general.authority.domain.resource;

import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

import java.util.List;

/**
 * database layer object of IResourceProtection ({@link com.taoswork.tallybook.general.authority.core.resource.IResourceProtection})
 */
public interface SecuredResource<RF extends SecuredResourceSpecial> extends Persistable {

    Long getId();

    void setId(Long id);

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    String getResourceEntity();

    void setResourceEntity(String type);

    String getCategory();

    void setCategory(String category);

    boolean isMasterControlled();

    void setMasterControlled(Boolean masterControlled);

    ResourceProtectionMode getProtectionMode();

    void setProtectionMode(ResourceProtectionMode protectionMode);

    List<RF> getFilters();

    void setFilters(List<RF> criterias);

    int getVersion();

    /*

    String getFilterNamespace();

    void setFilterNamespace(String filterNamespace);

     */
}
