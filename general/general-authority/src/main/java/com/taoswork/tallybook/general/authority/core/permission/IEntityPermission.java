package com.taoswork.tallybook.general.authority.core.permission;

import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.authority.core.basic.ProtectionMode;

import java.util.Collection;

/**
 * EntityPermission, user owned, directly or indirectly.
 *
 * Describes entity permission for a particular type of resourceEntity.
 * Defines:
 *      The resource entity
 *      The master access
 *      The filters' access (defined by IEntityPermissionSpecial)
 * Note:
 *      ProtectionMode is on the resource side, NOT here
 */
public interface IEntityPermission {
    String getResourceEntity();

    /**
     * @return the master access
     */
    Access getMasterAccess();

    /**
     * set the master access
     */
    void setMasterAccess(Access masterAccess);

    /**
     * Merge all the access in IEntityPermission (also in IEntityPermissionSpecial s),
     * Used for quick check.
     *
     * @return the merged access
     */
    Access getQuickCheckAccess();


    /**
     * An instance can match a list of filters. Different filter has different access restriction.
     * This method works out the finial Access value.
     *
     * @param filterCodes, the filters selected to be checked.
     * @param masterControlled, if the resource is master controlled, see IResourceProtection
     * @param protectionMode, the resource's protection mode
     * @return
     */
    Access getAccessByFilters(Collection<String> filterCodes, boolean masterControlled, ProtectionMode protectionMode);

    /**
     * @return the access method for the filter of this resource entity
     */
    Access getAccessByFilter(String filterCode);

    /**
     * Add an IEntityPermissionSpecial, in another words: add new filter access method
     * @return
     */
    IEntityPermission addSpecials(IEntityPermissionSpecial... permSpecials);

    IEntityPermission clone();
}
