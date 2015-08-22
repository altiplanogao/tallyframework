package com.taoswork.tallybook.general.authority.core.resource;

/**
 * Created by Gao Yuan on 2015/8/22.
 */
public interface IResourceProtectionManager {
    int getVersion();

    String correctResourceEntity(String resourceEntity);

    IResourceProtectionManager registerResourceProtection(IResourceProtection resourceProtection);

    IResourceProtectionManager registerAlias(String resourceEntity, String alias);

    IResourceProtection getResourceProtection(String resourceEntity);

    ResourceFitting getResourceFitting(String resourceEntity, Object instance);

    ResourceFitting getResourceFitting(boolean matchingPreferred, String resourceEntity, Object... instances);
}
