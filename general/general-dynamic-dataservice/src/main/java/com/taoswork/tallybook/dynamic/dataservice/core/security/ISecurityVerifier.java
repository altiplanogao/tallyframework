package com.taoswork.tallybook.dynamic.dataservice.core.security;

import com.taoswork.tallybook.general.authority.core.basic.Access;

/**
 * Created by Gao Yuan on 2015/8/22.
 */
public interface ISecurityVerifier {
    Access getAllPossibleAccess(String resourceEntity, Access mask);

    boolean canAccess(String resourceEntity, Access access);

    boolean canAccess(String resourceEntity, Access access, Object... instances);

    void checkAccess(String resourceEntity, Access access) throws NoPermissionException;

    void checkAccess(String resourceEntity, Access access, Object... instances) throws NoPermissionException;

    boolean canAccessMappedResource(String mappedResource, Access access);
}
