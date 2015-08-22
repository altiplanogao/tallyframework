package com.taoswork.tallybook.general.authority.core.authority.resource;

import java.io.Serializable;

/**
 * Entity used to describe a kind of resource need to be protected.
 *
 * Created by Gao Yuan on 2015/4/28.
 */

public interface SecuredResourceFilter extends Serializable{

    Long getId();

    void setId(Long id);

    String getName();

    SecuredResourceFilter setName(String name);

    SecuredResource getSecuredResource();

    SecuredResourceFilter setResourceType(SecuredResource securedResource);

    String getFilter();

    SecuredResourceFilter setFilter(String filter);

    String getFilterParameter();

    SecuredResourceFilter setFilterParameter(String filterParameter);

}
