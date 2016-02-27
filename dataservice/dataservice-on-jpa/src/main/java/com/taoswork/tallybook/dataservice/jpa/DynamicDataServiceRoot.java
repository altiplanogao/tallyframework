package com.taoswork.tallybook.dataservice.jpa;

import com.taoswork.tallybook.dataservice.jpa.core.dao.EntityDao;
import com.taoswork.tallybook.dataservice.jpa.core.entityservice.JpaEntityService;
import com.taoswork.tallybook.dataservice.service.EntityMetaAccess;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public final class DynamicDataServiceRoot {
    /**
     *     JpaEntityService
     *     ({@link JpaEntityService})
     *          a. CRUD support for entities (Service level)
     *          b. depends on EntityMetaAccess & EntityDao
     *
     *
     *
     *
     *     EntityMetaAccess
     *     ({@link EntityMetaAccess})
     *          a. List classes of a super class (or interface)
     *          b. depends on EntityManager
     *
     *     EntityDao
     *     ({@link EntityDao})
     *          a. CRUD support for entities (DAO level)
     *          b. depends on EntityManager
     *
     *
     */

}
