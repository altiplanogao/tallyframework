package com.taoswork.tallybook.general.authority.core.resource;

import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.authority.core.basic.ProtectionMode;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public interface IVirtualResourceProtectionMapping {

    //trustor
    String getVirtualResource();

    Access getVirtualAccess();

    //trustee
    String getTrusteeResourceEntity();

    Access getTrusteeAccess();

    //
    ProtectionMode getProtectionMode();

}
