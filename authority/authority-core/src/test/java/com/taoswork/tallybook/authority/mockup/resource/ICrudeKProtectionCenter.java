package com.taoswork.tallybook.authority.mockup.resource;

import com.taoswork.tallybook.authority.core.resource.IKProtection;
import com.taoswork.tallybook.authority.core.resource.IKProtectionCenter;

/**
 * ICrudeKProtectionCenter, a manager for all:
 *      IKProtection s
 *      Resource alias
 */
public interface ICrudeKProtectionCenter
        extends IKProtectionCenter {

    /**
     * Add IKProtection object to the manager.
     * @param protection
     * @return
     */
    ICrudeKProtectionCenter registerProtection(IKProtection protection);


}
