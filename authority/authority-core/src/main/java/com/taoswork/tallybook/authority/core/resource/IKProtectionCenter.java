package com.taoswork.tallybook.authority.core.resource;

/**
 * Created by Gao Yuan on 2016/2/8.
 */
public interface IKProtectionCenter {

    /**
     * Get resource protection mode for resource
     * @param resource
     * @return
     */
    IKProtection getProtection(String resource);

    /**
     * Work out a resourceFitting object for the instance
     * As the 1st step of KAccessVerifier.canAccess(...) check.
     * See KAccessVerifier.canAccess(...)
     *
     * @param resource
     * @param instance
     * @return
     */
    KCaseFitting getFitting(String resource, Object instance);

    /**
     * Work out a resourceFitting object for the instances
     * Batch mode of getFitting(String resourceEntity, Object instance);
     *
     * As the 1st step of KAccessVerifier.canAccess(...) check.
     * See KAccessVerifier.canAccess(...)
     *
     * @param matchingPreferred
     * @param resource
     * @param instances
     * @return
     */
    KCaseFitting getFitting(boolean matchingPreferred, String resource, Object... instances);
}
