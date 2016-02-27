package com.taoswork.tallybook.dataservice.security.impl;

import com.taoswork.tallybook.authority.core.Access;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Gao Yuan on 2015/8/22.
 */
public class BlockAllSecurityVerifier extends BaseSecurityVerifier {
    private static final Logger LOGGER = LoggerFactory.getLogger(BlockAllSecurityVerifier.class);

    @Override
    public Access getAllPossibleAccess(String resourceEntity, Access mask) {
        return Access.None;
    }

    @Override
    public boolean canAccess(String resourceEntity, Access access) {
        LOGGER.trace("hardcoded to block: {0} {1}", resourceEntity, access);
        return false;
    }

    @Override
    public boolean canAccess(String resourceEntity, Access access, Object... instances) {
        if (instances.length > 0) {
            LOGGER.trace("hardcoded to block '{0}' instance: {1} ... {2}", instances.length, instances[0], access);
        } else {
            LOGGER.trace("hardcoded to block '{0}' instance: {1}", instances.length, access);
        }
        return false;
    }

    @Override
    public boolean canAccessMappedResource(String mappedResource, Access access) {
        LOGGER.trace("hardcoded to block: {0} {1}", mappedResource, access);
        return false;
    }
}
