package com.taoswork.tallybook.module.elevator.dataservice;

import com.taoswork.tallybook.dataservice.IDataServiceDefinition;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
public class ElevatorModuleDataServiceDefinition implements IDataServiceDefinition {
    //DataServiceName
    public final static String DATA_SERVICE_NAME = "ElevatorModuleDataService";

    //MELEVATOR_DSD : Data Source Definition
    public final static String MELEVATOR_DSD = "elevatormodule";


    public static final String MELEVATOR_ENTITY_MESSAGES = ENTITY_MESSAGES_FILE_PREFIX +
            "elevatormodule/";

    public static final String MELEVATOR_ERROR_MESSAGES = ERROR_MESSAGES_FILE_PREFIX +
            "elevatormodule/";

    public static final String MELEVATOR_RUNTIME_PROPERTIES = RUNTIME_PROPERTIES_FILE_PREFIX +
            "elevatormodule/";

    @Override
    public String getDataServiceName() {
        return DATA_SERVICE_NAME;
    }

    @Override
    public String getEntityMessageDirectory() {
        return MELEVATOR_ENTITY_MESSAGES;
    }

    @Override
    public String getErrorMessageDirectory() {
        return MELEVATOR_ERROR_MESSAGES;
    }

    @Override
    public String getPropertiesResourceDirectory() {
        return MELEVATOR_RUNTIME_PROPERTIES;
    }

    @Override
    public Class[] getExtraConfig() {
        return new Class[0];
    }
}
