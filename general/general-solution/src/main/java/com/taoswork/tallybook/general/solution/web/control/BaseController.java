package com.taoswork.tallybook.general.solution.web.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Gao Yuan on 2015/5/6.
 */
public abstract class BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    public BaseController(){
        LOGGER.info("[CONTROLLER: " +
                this.getClass().getSimpleName() + "] Constructor" );
    }


}
