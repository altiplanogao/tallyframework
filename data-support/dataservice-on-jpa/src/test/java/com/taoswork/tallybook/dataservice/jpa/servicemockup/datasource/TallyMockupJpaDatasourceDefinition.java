package com.taoswork.tallybook.dataservice.jpa.servicemockup.datasource;

import com.taoswork.tallybook.dataservice.jpa.JpaDatasourceDefinition;

/**
 * Created by Gao Yuan on 2015/5/8.
 */
public final class TallyMockupJpaDatasourceDefinition implements JpaDatasourceDefinition {

    //TMOCKUP_DSD : Data Source Definition
    public final static String TMOCKUP_DSD = "tallymockup";

    public final static String TMOCKUP_DB_NAME = "tallymockup";
    //Prediction: must have JNDI enabled with name: jdbc/mockupdb
    public static final String TMOCKUP_JNDI_NAME = "jdbc/tallymockupJndiDb";

    public static final String TMOCKUP_DATASOURCE_NAME = "tallymockupDataSource";

    public static final String TMOCKUP_PERSISTENCE_XML = PERSISTENCE_XML_PREFIX + "persistence-test.xml";
    //should be same as in persistence.xml
    public final static String TMOCKUP_PU_NAME = "tallymockupPU";

    public static final String TMOCKUP_ENTITY_MANAGER_FACTORY_NAME = "tallymockupEntityManagerFactory";

    public static final String TMOCKUP_TRANSACTION_MANAGER_NAME = "tallymockupTransactionManager";

    @Override
    public String getDbName() {
        return TMOCKUP_DB_NAME;
    }

    @Override
    public String getJndiDbName() {
        return TMOCKUP_JNDI_NAME;
    }

    @Override
    public String getDataSourceName() {
        return TMOCKUP_DATASOURCE_NAME;
    }

    @Override
    public String getPersistenceXml() {
        return TMOCKUP_PERSISTENCE_XML;
    }

    @Override
    public String getPersistenceUnit() {
        return TMOCKUP_PU_NAME;
    }

    @Override
    public String getEntityManagerName() {
        return TMOCKUP_ENTITY_MANAGER_FACTORY_NAME;
    }

    @Override
    public String getTransactionManagerName() {
        return TMOCKUP_TRANSACTION_MANAGER_NAME;
    }

}
