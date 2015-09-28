package com.taoswork.tallybook.dynamic.dataservice.core.security;

import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;

/**
 * Created by Gao Yuan on 2015/9/27.
 */
public class NoPermissionException extends ServiceException {
    public NoPermissionException() {
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
}
