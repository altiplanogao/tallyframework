package com.taoswork.tallybook.dataservice.security.impl;

import com.taoswork.tallybook.authority.core.Access;
import com.taoswork.tallybook.dataservice.security.ISecurityVerifier;
import com.taoswork.tallybook.dataservice.security.NoPermissionException;

/**
 * Created by Gao Yuan on 2016/2/26.
 */
public abstract class BaseSecurityVerifier implements ISecurityVerifier {

    @Override
    public void checkAccess(String resourceEntity, Access access) throws NoPermissionException {
        boolean canAccess = this.canAccess(resourceEntity, access);
        if(!canAccess){
            throw new SecurityException("Not allowed to access " + resourceEntity);
        }
    }

    @Override
    public void checkAccess(String resourceEntity, Access access, Object... instances) throws NoPermissionException {
        boolean canAccess = this.canAccess(resourceEntity, access, instances);
        if(!canAccess){
            throw new SecurityException("Not allowed to access ");
        }
    }

}
