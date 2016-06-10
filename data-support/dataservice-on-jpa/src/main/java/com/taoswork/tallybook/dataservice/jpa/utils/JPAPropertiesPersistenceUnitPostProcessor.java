package com.taoswork.tallybook.dataservice.jpa.utils;

import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;

import java.util.Properties;

/**
 * Created by Gao Yuan on 2015/4/25.
 */
public class JPAPropertiesPersistenceUnitPostProcessor implements PersistenceUnitPostProcessor {

    private Properties persistenceProps;

    @Override
    public void postProcessPersistenceUnitInfo(MutablePersistenceUnitInfo pui) {
        if (persistenceProps != null) {
            String puName = pui.getPersistenceUnitName() + ".";
            Properties props = pui.getProperties();

            for (String key : persistenceProps.stringPropertyNames()) {
                if (key.startsWith(puName)) {
                    String value = persistenceProps.getProperty(key);
                    String newKey = key.substring(puName.length());
                    if ("null".equalsIgnoreCase(value)) {
                        props.remove(newKey);
                    } else if (value != null && !"".equals(value)) {
                        props.put(newKey, value);
                    }
                }
            }
            pui.setProperties(props);
        }
    }

    public Properties getPersistenceProps() {
        return persistenceProps;
    }

    public void setPersistenceProps(Properties persistenceProps) {
        this.persistenceProps = persistenceProps;
    }

}


