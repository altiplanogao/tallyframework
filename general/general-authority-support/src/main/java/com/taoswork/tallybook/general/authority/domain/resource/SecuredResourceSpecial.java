package com.taoswork.tallybook.general.authority.domain.resource;

import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

/**
 * Entity used to describe a kind of resource need to be protected.
 *
 * Created by Gao Yuan on 2015/4/28.
 */

//@PersistFriendly(nameOverride = "admin-resource-criteria")
public interface SecuredResourceSpecial<R extends SecuredResource> extends Persistable {

    Long getId();

    void setId(Long id);

    String getName();

    SecuredResourceSpecial setName(String name);

    R getSecuredResource();

    SecuredResourceSpecial setResourceType(R securedResource);

    /**
     * Filter types:
     * Name contains
     * Has attribute
     * Created by
     * @return
     */
    FilterType getFilter();

    SecuredResourceSpecial setFilter(FilterType filter);

    String getFilterParameter();

    SecuredResourceSpecial setFilterParameter(String filterParameter);

    boolean isMainLine();
}
