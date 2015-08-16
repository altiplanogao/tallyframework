package com.taoswork.tallybook.dynamic.dataservice.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Gao Yuan on 2015/5/13.
 */
public abstract class EntityServiceBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityServiceBase.class);

    public EntityServiceBase(){
        LOGGER.debug("[ENTITY SERVICE] " + this.getClass().getSimpleName());
    }
}
