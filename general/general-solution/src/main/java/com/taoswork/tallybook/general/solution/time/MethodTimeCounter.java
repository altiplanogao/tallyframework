package com.taoswork.tallybook.general.solution.time;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;

import java.util.Date;

/**
 * Created by Gao Yuan on 2015/11/10.
 */
public class MethodTimeCounter extends TimeCounter{
    private final Logger logger;
    public MethodTimeCounter(Logger logger){
        super();
        this.logger = logger;
    }

    public void noticeOnExit(){
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        StackTraceElement e = stacktrace[2];
        String methodName = e.getMethodName();
        logger.info("{} cost {} seconds.", methodName, getPassedInSeconds());
    }
}
