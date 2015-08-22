package com.taoswork.tallybook.general.authority.core.permission;

import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.authority.core.basic.ProtectionMode;

import java.util.Collection;

/**
 * EntityPermission, owned by user, directly or indirectly. (Grouped by resourceEntity)
 */
public interface IEntityPermission {
    String getResourceEntity();

    Access getMasterAccess();

    void setMasterAccess(Access masterAccess);

    /**
     * Merge all the access in IEntityPermission,
     * Used for quick check.
     *
     * @return
     */
    Access getQuickCheckAccess();

    Access getAccessByFilters(Collection<String> filterCodes, boolean masterControlled, ProtectionMode protectionMode);

    Access getAccessByFilter(String filterCode);

    IEntityPermission addEntries(IPermissionEntry... permEntries);

    IEntityPermission clone();
}
