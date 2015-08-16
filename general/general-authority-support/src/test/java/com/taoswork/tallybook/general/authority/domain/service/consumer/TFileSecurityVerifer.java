package com.taoswork.tallybook.general.authority.domain.service.consumer;

import com.taoswork.tallybook.general.authority.domain.authority.access.AccessType;
import com.taoswork.tallybook.general.authority.domain.authority.user.IPermissionUser;
import com.taoswork.tallybook.general.authority.engine.resource.ResourceSecured;
import com.taoswork.tallybook.general.authority.service.consumer.ISecurityVerifier;
import com.taoswork.tallybook.general.authority.service.consumer.ResourceSecurityException;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
public class TFileSecurityVerifer implements ISecurityVerifier {

    private IPermissionUser permissionUser;
    @Override
    public IPermissionUser getPermissionUser() {
        return permissionUser;
    }

    public void setPermissionUser(IPermissionUser permissionUser) {
        this.permissionUser = permissionUser;
    }

    @Override
    public void securityCheck(ResourceSecured resourceSecured, AccessType accessType) throws ResourceSecurityException {

    }

    @Override
    public void securityCheck(Object resourceEntity, AccessType accessType) throws ResourceSecurityException {

    }

    @Override
    public void securityCheckQuery(String resourceType, AccessType accessType) throws ResourceSecurityException {

    }
}
