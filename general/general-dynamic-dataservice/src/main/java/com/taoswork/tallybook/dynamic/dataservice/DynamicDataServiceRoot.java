package com.taoswork.tallybook.dynamic.dataservice;

import com.taoswork.tallybook.dynamic.dataservice.dynamic.dao.DynamicEntityDao;
import com.taoswork.tallybook.dynamic.dataservice.dynamic.entitymanager.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.dynamic.dataservice.dynamic.service.DynamicEntityService;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.service.EntityDescriptionService;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.service.EntityMetadataService;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public final class DynamicDataServiceRoot {
    /**
     *     Kinds of service
     *
     *     EntityMetadataService
     *     ({@link EntityMetadataService})
     *          a. returns metadata of POJO
     *
     *     EntityDescriptionService
     *     ({@link EntityDescriptionService})
     *          a. Translate Metadata to EntityDescriptionObject
     *
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
