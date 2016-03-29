package com.taoswork.tallybook.dataservice.mongo.core;

import com.taoswork.tallybook.dataservice.IDataService;
import com.taoswork.tallybook.dataservice.IDataServiceDefinition;
import com.taoswork.tallybook.dataservice.mongo.config.MongoDatasourceBeanConfiguration;
import com.taoswork.tallybook.dataservice.mongo.config.MongoDatasourceConfiguration;
import com.taoswork.tallybook.dataservice.mongo.config.MongoPersistableConfiguration;
import com.taoswork.tallybook.dataservice.mongo.core.entityservice.MongoEntityService;
import com.taoswork.tallybook.dataservice.mongo.core.script.DataScript;
import com.taoswork.tallybook.dataservice.mongo.core.script.MongoDataImporter;
import com.taoswork.tallybook.dataservice.service.impl.DataServiceBase;
import com.taoswork.tallybook.general.extension.collections.ListBuilder;
import com.taoswork.tallybook.general.solution.property.RuntimeEnvironmentPropertyPlaceholderConfigurer;
import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.AdvancedDatastore;

import java.util.List;
import java.util.Properties;


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

    @Override
    protected void postConstruct() {
        super.postConstruct();
        MongoEntityService entityService = this.getService(MongoEntityService.COMPONENT_NAME);
        AdvancedDatastore datastore = entityService.getAdvancedDatastore();

        RuntimeEnvironmentPropertyPlaceholderConfigurer runtimeEnvironmentPropertyPlaceholderConfigurer = this.getService(IDataService.PROPERTY_CONFIGURER);
        Properties props = runtimeEnvironmentPropertyPlaceholderConfigurer.getSubProperties("tallydataservice");
        String importFiles = props.getProperty("tallydataservice.import");
        if(StringUtils.isNotEmpty(importFiles)){
            MongoDataImporter importer = new MongoDataImporter();
            List<DataScript> dss = DataScript.parseDataScripts(importFiles);
            for (DataScript ds : dss){
                importer.load(datastore, ds.getTask(), ds.getFile());
            }
        }
    }
}
