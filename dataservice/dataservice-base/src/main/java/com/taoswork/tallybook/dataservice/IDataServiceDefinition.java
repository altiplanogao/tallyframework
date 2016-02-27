package com.taoswork.tallybook.dataservice;

/**
 * Created by Gao Yuan on 2016/2/13.
 */
public interface IDataServiceDefinition {
    public static final String DATA_SERVICE_DEFINITION_BEAN_NAME = "IDataServiceDefinition";
    public static final String RUNTIME_PROPERTIES_FILE_PREFIX = "/runtime-properties/";
    public static final String ENTITY_MESSAGES_FILE_PREFIX = "/entity-messages/";
    public static final String ERROR_MESSAGES_FILE_PREFIX = "/error-messages/";

    public static final String FILE_DELIMTER = ";";

    String getDataServiceName();

    String getEntityMessageDirectory();

    String getErrorMessageDirectory();

    String getPropertiesResourceDirectory();

    Class[] getExtraConfig();

}
