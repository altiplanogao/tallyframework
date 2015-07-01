package com.taoswork.tallybook.dynamic.dataservice;

import com.taoswork.tallybook.dynamic.dataservice.dao.DynamicEntityDao;
import com.taoswork.tallybook.dynamic.dataservice.metaaccess.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.dynamic.dataservice.service.DynamicEntityService;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public final class DynamicDataServiceRoot {
    /**
     *     DynamicEntityService
     *     ({@link DynamicEntityService})
     *          a. CRUD support for entities (Service level)
     *          b. depends on DynamicEntityMetadataAccess & DynamicEntityDao
     *
     *
     *
     *
     *     DynamicEntityMetadataAccess
     *     ({@link DynamicEntityMetadataAccess})
     *          a. List classes of a super class (or interface)
     *          b. depends on EntityManager
     *
     *     DynamicEntityDao
     *     ({@link DynamicEntityDao})
     *          a. CRUD support for entities (DAO level)
     *          b. depends on EntityManager
     *
     *
     */

}
