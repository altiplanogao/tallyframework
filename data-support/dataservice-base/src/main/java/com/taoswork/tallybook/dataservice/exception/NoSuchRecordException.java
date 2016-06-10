package com.taoswork.tallybook.dataservice.exception;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Gao Yuan on 2015/10/3.
 */
public class NoSuchRecordException extends ServiceException {
    public final static String DEFAULT_ERROR_CODE = "service.error.no.such.record";
    private String messageCode;
    private Object[] args;

    private Class<?> entityType;
    private Object key;

    public <T extends Persistable> NoSuchRecordException(Class<T> entityType, Object key) {
        this.entityType = entityType;
        this.key = key;
    }

    public Class<?> getEntityType() {
        return entityType;
    }

    public Object getKey() {
        return key;
    }
    public String getMessageCode() {
        if(StringUtils.isEmpty(messageCode))
            return DEFAULT_ERROR_CODE;
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public Object[] getArgs() {
        if(args == null){
            return new Object[]{};
        }
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
