package com.taoswork.tallybook.authority.core.permission;

import com.taoswork.tallybook.authority.core.Access;
import com.taoswork.tallybook.authority.core.ProtectionMode;

import java.util.Collection;

/**
 * KPermission, user owned (IKAuthority), directly or indirectly.
 * <p>
 * Describes entity permission for a particular type of resourceEntity.
 * Defines:
 * The resource entity
 * The master access
 * The cases' access (defined by IKPermissionCase)
 * Note:
 * ProtectionMode is on the resource side, NOT here
 */
public interface IKPermission {
    /**
     * The resource this IKPermission protects
     * @return
     */
    String getResource();

    /**
     * @return the master access
     */
    Access getMasterAccess();

    /**
     * set the master access
     */
    void setMasterAccess(Access masterAccess);

    /**
     * IKPermission contains cases (IKPermissionCase)
     * Merge all the cases' access in IKPermission (also in IKPermissionCase s),
     * Used for quick check.
     *
     * @return the merged access
     */
    Access getQuickCheckAccess();


    /**
     * An instance can match a list of cases. Different 'case' has different access restriction.
     * This method works out the finial Access value.
     *
     * @param caseCodes,        the cases selected to be checked.
     * @param masterControlled, if the resource is master controlled, see IKProtection
     * @param protectionMode,   the resource's protection mode
     * @return
     */
    Access getAccessByCases(Collection<String> caseCodes, boolean masterControlled, ProtectionMode protectionMode);

    /**
     * @return the access method for the case of this resource entity
     */
    Access getCaseAccess(String caseCode);

    IKPermission clone();
}
