package com.taoswork.tallybook.dataservice.jpa.core;

import com.taoswork.tallybook.dataservice.IDataServiceDefinition;
import com.taoswork.tallybook.dataservice.jpa.config.JpaDatasourceBeanConfiguration;
import com.taoswork.tallybook.dataservice.jpa.config.JpaDatasourceConfiguration;
import com.taoswork.tallybook.dataservice.jpa.config.db.IDbConfig;
import com.taoswork.tallybook.dataservice.service.impl.DataServiceBase;
import com.taoswork.tallybook.general.extension.collections.ListBuilder;

/**
 * Created by Gao Yuan on 2016/2/16.
 */
public abstract class JpaDataServiceBase<
        DSrcBeanConf extends JpaDatasourceBeanConfiguration,
        DSrcConf extends JpaDatasourceConfiguration,
        JpaDbConf extends IDbConfig>
        extends DataServiceBase {

    /**
     * @param dsDef           : defines the service's name, message path, properties
     * @param dSrcBeanConfClz : defines the datastore beans which are directly used by application
     * @param dSrcConfClz     : defines the connection parameter to the database.
     * @param dbConf          : defines the db type, and its data source creation method
     */
    public JpaDataServiceBase(IDataServiceDefinition dsDef,
                              Class<? extends DSrcBeanConf> dSrcBeanConfClz,
                              Class<? extends DSrcConf> dSrcConfClz,
                              Class<? extends JpaDbConf> dbConf) {
        super(dsDef, new ListBuilder<Class>()
                .append(dSrcBeanConfClz)
                .append(dSrcConfClz)
                .append(dbConf));
    }
}
