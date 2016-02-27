package com.taoswork.tallybook.authority.core.resource.link;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/2/24.
 */
public interface IKProtectionMappingRegister extends IKProtectionMapping {

    IKProtectionMappingRegister registerLink(IKProtectionLink protection);
    /**
     * Add alias for a resourceEntity
     *
     * @param resource
     * @param alias
     * @return
     */
    IKProtectionMappingRegister registerAlias(String resource, String alias);

    IKProtectionMappingRegister registerAlias(String resource, String... aliases);

    IKProtectionMappingRegister registerAlias(String resource, Collection<String> aliases);

}
