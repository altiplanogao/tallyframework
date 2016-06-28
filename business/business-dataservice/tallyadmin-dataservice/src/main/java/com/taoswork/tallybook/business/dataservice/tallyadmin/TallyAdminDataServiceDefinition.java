package com.taoswork.tallybook.business.dataservice.tallyadmin;

import com.taoswork.tallycheck.dataservice.IDataServiceDefinition;

/**
 * Created by Gao Yuan on 2015/5/10.
 */
public class TallyAdminDataServiceDefinition implements IDataServiceDefinition {
    //DataServiceName
    public final static String DATA_SERVICE_NAME = "TallyAdminDataService";

    //TADMIN_DSD : Data Source Definition
    public final static String TADMIN_DSD = "tallyadmin";

    public static final String TADMIN_ENTITY_MESSAGES =
            ENTITY_MESSAGES_FILE_PREFIX + "tallyadmin/" + FILE_DELIMTER +
                    ENTITY_MESSAGES_FILE_PREFIX + "permission/";

    public static final String TADMIN_ERROR_MESSAGES =
            ERROR_MESSAGES_FILE_PREFIX + "tallyadmin/" + FILE_DELIMTER +
                    ERROR_MESSAGES_FILE_PREFIX + "permission/";

    public static final String TADMIN_RUNTIME_PROPERTIES =
            RUNTIME_PROPERTIES_FILE_PREFIX + "tallyadmin/";

    @Override
    public String getDataServiceName() {
        return DATA_SERVICE_NAME;
    }

    @Override
    public String getEntityMessageDirectory() {
        return TADMIN_ENTITY_MESSAGES;
    }

    @Override
    public String getErrorMessageDirectory() {
        return TADMIN_ERROR_MESSAGES;
    }

    @Override
    public String getPropertiesResourceDirectory() {
        return TADMIN_RUNTIME_PROPERTIES;
    }

    @Override
    public Class[] getExtraConfig() {
        return new Class[]{};
    }
}