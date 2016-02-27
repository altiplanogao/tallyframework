package com.taoswork.tallybook.authority.core.resource.link;

import com.taoswork.tallybook.authority.core.Access;
import com.taoswork.tallybook.authority.core.ProtectionMode;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public interface IKProtectionLink {

    //trustor
    String getVirtualResource();

    Access getVirtualAccess();

    //trustee
    String getActualResource();

    Access getActualAccess();

    //
    ProtectionMode getProtectionMode();

}
