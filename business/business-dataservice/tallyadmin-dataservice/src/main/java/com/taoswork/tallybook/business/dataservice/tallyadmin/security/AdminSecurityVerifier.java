package com.taoswork.tallybook.business.dataservice.tallyadmin.security;

import com.taoswork.tallycheck.authority.core.Access;
import com.taoswork.tallycheck.authority.core.verifier.IKAccessVerifier;
import com.taoswork.tallycheck.dataservice.security.ISecurityVerifier;
import com.taoswork.tallycheck.dataservice.security.NoPermissionException;

/**
 * Created by Gao Yuan on 2015/8/23.
 */
public class AdminSecurityVerifier implements ISecurityVerifier {
    private IKAccessVerifier accessVerifier = null;

    public AdminSecurityVerifier(){

    }

    @Override
    public Access getAllPossibleAccess(String resourceEntity, Access mask) {
        return mask;
    }

    @Override
    public boolean canAccess(String resourceEntity, Access access) {
        return false;
    }

    @Override
    public boolean canAccess(String resourceEntity, Access access, Object... instances) {
        return false;
    }

    @Override
    public void checkAccess(String resourceEntity, Access access) throws NoPermissionException {
//        accessVerifier.canAccess()
    }

    @Override
    public void checkAccess(String resourceEntity, Access access, Object... instances) throws NoPermissionException {

    }

    @Override
    public boolean canAccessMappedResource(String mappedResource, Access access) {
        return false;
    }
}
