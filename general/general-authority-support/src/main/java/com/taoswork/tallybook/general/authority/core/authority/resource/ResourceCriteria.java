package com.taoswork.tallybook.general.authority.core.authority.resource;

import java.io.Serializable;

/**
 * Entity used to describe a kind of resource need to be protected.
 *
 * Created by Gao Yuan on 2015/4/28.
 */

public interface ResourceCriteria extends Serializable{

    Long getId();

    void setId(Long id);

    String getName();

    ResourceCriteria setName(String name);

    ResourceType getResourceType();

    ResourceCriteria setResourceType(ResourceType resourceType);

    String getFilter();

    ResourceCriteria setFilter(String filter);

    String getFilterParameter();

    ResourceCriteria setFilterParameter(String filterParameter);

    boolean isRootTypeCriteria();
}
