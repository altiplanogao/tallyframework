package com.taoswork.tallybook.general.authority.core.permission;

import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.authority.core.basic.ProtectionMode;

import java.util.Collection;

/**
 * EntityPermission, owned by user, directly or indirectly. (Grouped by resourceEntity)
 */
public interface IEntityPermission {
    public String resourceEntity();

    Access getMasterAccess();

    void setMasterAccess(Access masterAccess);

    /**
     * Merge all the access in AuthorityOnFilter,
     * Used for quick check.
     * @return
     */
    public Access mergedAccess();

    public Access accessByFilters(Collection<String> filterCodes, boolean masterControlled, ProtectionMode protectionMode);

    IEntityPermission addEntries(IPermissionEntry... permEntries);
}
