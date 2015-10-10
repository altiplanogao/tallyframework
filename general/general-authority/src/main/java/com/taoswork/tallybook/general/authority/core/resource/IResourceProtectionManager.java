package com.taoswork.tallybook.general.authority.core.resource;

/**
 * IResourceProtectionManager, a manager for all:
 *      IResourceProtection s
 *      Resource alias
 */
public interface IResourceProtectionManager {
    int getVersion();

    /**
     * Adjust the name of resourceEntity. (consider about using alias as resourceEntity)
     * @param resourceEntity
     * @return
     */
    String correctResourceEntity(String resourceEntity);

    /**
     * Add IResourceProtection object to the manager.
     * @param resourceProtection
     * @return
     */
    IResourceProtectionManager registerResourceProtection(IResourceProtection resourceProtection);

    /**
     * Add alias for a resourceEntity
     * @param resourceEntity
     * @param alias
     * @return
     */
    IResourceProtectionManager registerAlias(String resourceEntity, String alias);

    /**
     * Get resource protection mode for resourceEntity
     * @param resourceEntity
     * @return
     */
    IResourceProtection getResourceProtection(String resourceEntity);

    /**
     * Work out a resourceFitting object for the instance
     * As the 1st step of AccessVerifier.canAccess(...) check.
     * See AccessVerifier.canAccess(...)
     *
     * @param resourceEntity
     * @param instance
     * @return
     */
    ResourceFitting getResourceFitting(String resourceEntity, Object instance);

    /**
     * Work out a resourceFitting object for the instances
     * Batch mode of getResourceFitting(String resourceEntity, Object instance);
     *
     * As the 1st step of AccessVerifier.canAccess(...) check.
     * See AccessVerifier.canAccess(...)
     *
     * @param matchingPreferred
     * @param resourceEntity
     * @param instances
     * @return
     */
    ResourceFitting getResourceFitting(boolean matchingPreferred, String resourceEntity, Object... instances);
}
