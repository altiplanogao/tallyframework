package com.taoswork.tallybook.general.authority.service.consumer;

import com.taoswork.tallybook.general.authority.domain.authority.access.AccessType;
import com.taoswork.tallybook.general.authority.domain.authority.user.IPermissionUser;
import com.taoswork.tallybook.general.authority.engine.resource.ResourceSecured;

/**
 * Created by Gao Yuan on 2015/6/4.
 */
public interface ISecurityVerifier {
    IPermissionUser getPermissionUser();

    void securityCheck(ResourceSecured resourceSecured, AccessType accessType) throws ResourceSecurityException;
    void securityCheck(Object resourceEntity, AccessType accessType) throws ResourceSecurityException;
    void securityCheckQuery(String resourceType, AccessType accessType) throws ResourceSecurityException;
}
