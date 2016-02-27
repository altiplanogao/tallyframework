package com.taoswork.tallybook.dataservice.mongo.config;

import org.springframework.context.annotation.Bean;

/**
 * Created by Gao Yuan on 2016/2/16.
 */
public abstract class MongoPersistableConfiguration {
    public static final String PERSISTABLE_ENTITIES_BEAN_NAME = "persistableEntities";

    protected abstract Class<?>[] createPersistableEntities();

    @Bean(name = PERSISTABLE_ENTITIES_BEAN_NAME)
    public Class<?>[] persistableEntities() {
        return createPersistableEntities();
    }
}
