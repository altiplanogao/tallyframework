package com.taoswork.tallybook.dynamic.dataservice.core.security;

import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Gao Yuan on 2015/9/27.
 */
public class NoPermissionException extends ServiceException {
    public final static String DEFAULT_ERROR_CODE = "noPermission";
    private String messageCode;
    private Object[] args;

    public NoPermissionException() {
    }

    public NoPermissionException(String messageCode, Object[] args) {
        this.messageCode = messageCode;
        this.args = args;
    }

    public NoPermissionException(String message) {
        super(message);
    }

    public NoPermissionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoPermissionException(Throwable cause) {
        super(cause);
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
