package com.taoswork.tallybook.dataservice.security.impl;

import com.taoswork.tallybook.authority.core.Access;
import com.taoswork.tallybook.dataservice.security.ISecurityVerifier;
import com.taoswork.tallybook.dataservice.security.NoPermissionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Some arbitrary code added for easy debugging.
 */
public class PassAllSecurityVerifier extends BaseSecurityVerifier {
    private static final Logger LOGGER = LoggerFactory.getLogger(PassAllSecurityVerifier.class);
    private final boolean fixedCanAccess = true;

    @Override
    public Access getAllPossibleAccess(String resourceEntity, Access mask) {
        return new Access(mask);
    }

    @Override
    public boolean canAccess(String resourceEntity, Access access) {
        LOGGER.trace("hardcoded to pass: {0} {1}", resourceEntity, access);
        boolean _canAccess = fixedCanAccess;
        return _canAccess;
    }

    @Override
    public boolean canAccess(String resourceEntity, Access access, Object... instances) {
        if (instances.length > 0) {
            LOGGER.trace("hardcoded to pass '{0}' instance: {1} ... {2}", instances.length, instances[0], access);
        } else {
            LOGGER.trace("hardcoded to pass '{0}' instance: {1}", instances.length, access);
        }
        boolean _canAccess = fixedCanAccess;
        return _canAccess;
    }

    @Override
    public boolean canAccessMappedResource(String mappedResource, Access access) {
        LOGGER.trace("hardcoded to pass: {0} {1}", mappedResource, access);
        boolean _canAccess = fixedCanAccess;
        return _canAccess;
    }
}
