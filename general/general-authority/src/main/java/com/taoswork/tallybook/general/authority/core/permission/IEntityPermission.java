package com.taoswork.tallybook.general.authority.core.permission;

import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.authority.core.basic.ProtectionMode;

import java.util.Collection;

/**
 * EntityPermission, owned by user, directly or indirectly. (Grouped by resourceEntity)
 */
public interface IEntityPermission {
    public String getResourceEntity();

    Access getMasterAccess();

    void setMasterAccess(Access masterAccess);

    /**
     * Merge all the access in IEntityPermission,
     * Used for quick check.
     * @return
     */
    public Access getQuickCheckAccess();

    public Access getAccessByFilters(Collection<String> filterCodes, boolean masterControlled, ProtectionMode protectionMode);

    Access getAccessByFilter(String filterCode);

    IEntityPermission addEntries(IPermissionEntry... permEntries);
}
