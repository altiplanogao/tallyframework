package com.taoswork.tallybook.general.authority.domain.resource;

import com.taoswork.tallybook.general.authority.core.basic.ProtectionMode;

/**
 * Created by Gao Yuan on 2015/8/18.
 */
public class ResourceProtection {
//    ResourceCategory category;
    String resourceEntity;
    String friendlyName;
    ProtectionMode protectionMode;
    boolean hasMainControl;
}
