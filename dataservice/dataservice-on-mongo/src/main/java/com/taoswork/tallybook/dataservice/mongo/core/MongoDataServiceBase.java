package com.taoswork.tallybook.dataservice.mongo.core;

import com.taoswork.tallybook.dataservice.IDataServiceDefinition;
import com.taoswork.tallybook.dataservice.mongo.config.MongoDatasourceBeanConfiguration;
import com.taoswork.tallybook.dataservice.mongo.config.MongoDatasourceConfiguration;
import com.taoswork.tallybook.dataservice.mongo.config.MongoPersistableConfiguration;
import com.taoswork.tallybook.dataservice.service.impl.DataServiceBase;
import com.taoswork.tallybook.general.extension.collections.ListBuilder;


/**
 * Created by Gao Yuan on 2016/2/16.
 */

public abstract class MongoDataServiceBase<
        EntityTypeConf extends MongoPersistableConfiguration,
        DSrcConf extends MongoDatasourceConfiguration>
        extends DataServiceBase {

    /**
     * @param dSrvDef        : defines the service's name, message path, properties
     * @param entityTypeConf : defines the entity types
     * @param dSrcConf       : defines the connection parameter to the database.
     */
    public MongoDataServiceBase(IDataServiceDefinition dSrvDef,
                                Class<? extends EntityTypeConf> entityTypeConf,
                                Class<? extends DSrcConf> dSrcConf, Class ... confs) {
        super(dSrvDef, new ListBuilder<Class>()
                .append(MongoDatasourceBeanConfiguration.class)
                .append(entityTypeConf).append(dSrcConf).append(confs));
    }
}
