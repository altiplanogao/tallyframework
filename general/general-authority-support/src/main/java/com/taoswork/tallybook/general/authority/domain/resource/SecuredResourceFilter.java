package com.taoswork.tallybook.general.authority.domain.resource;

import com.taoswork.tallybook.general.datadomain.support.entity.PersistFriendly;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;

/**
 * Entity used to describe a kind of resource need to be protected.
 *
 * Created by Gao Yuan on 2015/4/28.
 */

//@PersistFriendly(nameOverride = "admin-resource-criteria")
public interface SecuredResourceFilter<R extends SecuredResource> extends Persistable {

    Long getId();

    void setId(Long id);

    String getName();

    SecuredResourceFilter setName(String name);

    R getSecuredResource();

    SecuredResourceFilter setResourceType(R securedResource);

    String getFilter();

    SecuredResourceFilter setFilter(String filter);

    String getFilterParameter();

    SecuredResourceFilter setFilterParameter(String filterParameter);

}
