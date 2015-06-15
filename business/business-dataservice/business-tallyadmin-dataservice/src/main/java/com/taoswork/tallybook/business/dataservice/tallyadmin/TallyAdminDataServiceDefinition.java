package com.taoswork.tallybook.business.dataservice.tallyadmin;

import com.taoswork.tallybook.general.dataservice.support.IDataServiceDefinition;

/**
 * Created by Gao Yuan on 2015/5/10.
 */
public class TallyAdminDataServiceDefinition implements IDataServiceDefinition {
    //TADMIN_DSD : Data Source Definition
    public final static String TADMIN_DSD = "tallyadmin";

    public final static String TADMIN_DB_NAME = "tallyadmin";
    //Prediction: must have JNDI enabled with name: jdbc/userdb
    public static final String TADMIN_JNDI_NAME = "jdbc/tallyadminJndiDb";

    public static final String TADMIN_DATASOURCE_NAME = "tallyadminDataSource";

    public static final String TADMIN_PERSISTENCE_XML = PERSISTENCE_XML_PREFIX + "persistence-tallyadmin.xml";
    //should be same as in persistence.xml
    public final static String TADMIN_PU_NAME = "tallyadminPU";

    public static final String TADMIN_ENTITY_MANAGER_FACTORY_NAME = "tallyadminEntityManagerFactory";

    public static final String TADMIN_TRANSACTION_MANAGER_NAME = "tallyadminTransactionManager";

    public static final String TADMIN_RUNTIME_PROPERTIES = RUNTIME_PROPERTIES_FILE_PREFIX +
            "tallyadmin-ds/";

    @Override
    public String getDbName() {
        return TADMIN_DB_NAME;
    }

    @Override
    public String getJndiDbName() {
        return TADMIN_JNDI_NAME;
    }

    @Override
    public String getDataSourceName() {
        return TADMIN_DATASOURCE_NAME;
    }

    @Override
    public String getPersistenceXml() {
        return TADMIN_PERSISTENCE_XML;
    }

    @Override
    public String getPersistenceUnit() {
        return TADMIN_PU_NAME;
    }

    @Override
    public String getEntityManagerName() {
        return TADMIN_ENTITY_MANAGER_FACTORY_NAME;
    }

    @Override
    public String getTransactionManagerName() {
        return TADMIN_TRANSACTION_MANAGER_NAME;
    }

    @Override
    public String getPropertiesResourceDirectory() {
        return TADMIN_RUNTIME_PROPERTIES;
    }
}