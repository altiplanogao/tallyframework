package com.taoswork.tallybook.dynamic.dataservice.core.security.impl;

import com.taoswork.tallybook.dynamic.dataservice.core.security.ISecurityVerifier;
import com.taoswork.tallybook.general.authority.core.basic.Access;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Gao Yuan on 2015/8/22.
 */
public class PassAllSecurityVerifier implements ISecurityVerifier {
    private static final Logger LOGGER = LoggerFactory.getLogger(PassAllSecurityVerifier.class);

    @Override
    public Access getAllPossibleAccess(String resourceEntity, Access mask) {
        return new Access(mask);
    }

    @Override
    public boolean canAccess(String resourceEntity, Access access) {
        LOGGER.trace("hardcoded to pass: {0} {1}", resourceEntity, access);
        return true;
    }

    @Override
    public boolean canAccess(String resourceEntity, Access access, Object... instances) {
        if (instances.length > 0) {
            LOGGER.trace("hardcoded to pass '{0}' instance: {1} ... {2}", instances.length, instances[0], access);
        } else {
            LOGGER.trace("hardcoded to pass '{0}' instance: {1}", instances.length, access);
        }
        return true;
    }

    @Override
    public void checkAccess(String resourceEntity, Access access) throws SecurityException {
        LOGGER.trace("hardcoded to pass: {0} {1}", resourceEntity, access);
    }

    @Override
    public void checkAccess(String resourceEntity, Access access, Object... instances) throws SecurityException {
        if (instances.length > 0) {
            LOGGER.trace("hardcoded to pass '{0}' instance: {1} ... {2}", instances.length, instances[0], access);
        } else {
            LOGGER.trace("hardcoded to pass '{0}' instance: {1}", instances.length, access);
        }
    }

    @Override
    public boolean canAccessMappedResource(String mappedResource, Access access) {
        LOGGER.trace("hardcoded to pass: {0} {1}", mappedResource, access);
        return true;
    }
}
