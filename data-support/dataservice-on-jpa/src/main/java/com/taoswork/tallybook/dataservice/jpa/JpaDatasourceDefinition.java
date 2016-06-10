package com.taoswork.tallybook.dataservice.jpa;

import com.taoswork.tallybook.dataservice.DatasourceDefinition;

/**
 * Created by Gao Yuan on 2015/5/10.
 */
public interface JpaDatasourceDefinition extends DatasourceDefinition {
    public static final String DATA_SERVICE_DEFINITION_BEAN_NAME = "JpaDatasourceDefinition";

    public static final String PERSISTENCE_XML_PREFIX = "classpath:/META-INF/persistence/";

    String getDbName();

    String getJndiDbName();

    String getDataSourceName();

    String getPersistenceXml();

    String getPersistenceUnit();

    String getEntityManagerName();

    String getTransactionManagerName();

    /* CODE TEMPLATE AS FOLLOWING */

    /* END OF TEMPLATE*/
}
