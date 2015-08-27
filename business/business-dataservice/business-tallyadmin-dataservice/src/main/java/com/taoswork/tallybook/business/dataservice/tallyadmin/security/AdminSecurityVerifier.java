package com.taoswork.tallybook.business.dataservice.tallyadmin.security;

import com.taoswork.tallybook.dynamic.dataservice.core.security.ISecurityVerifier;
import com.taoswork.tallybook.general.authority.core.basic.Access;

/**
 * Created by Gao Yuan on 2015/8/23.
 */
public class AdminSecurityVerifier implements ISecurityVerifier {
    @Override
    public boolean canAccess(String resourceEntity, Access access) {
        return false;
    }

    @Override
    public boolean canAccess(String resourceEntity, Access access, Object... instances) {
        return false;
    }

    @Override
    public void checkAccess(String resourceEntity, Access access) throws SecurityException {

    }

    @Override
    public void checkAccess(String resourceEntity, Access access, Object... instances) throws SecurityException {

    }

    @Override
    public boolean canAccessMappedResource(String mappedResource, Access access) {
        return false;
    }
}
