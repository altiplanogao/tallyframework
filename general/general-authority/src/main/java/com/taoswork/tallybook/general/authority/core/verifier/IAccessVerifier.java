package com.taoswork.tallybook.general.authority.core.verifier;

import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.authority.core.permission.IEntityPermission;
import com.taoswork.tallybook.general.authority.core.permission.IPermissionAuthority;
import com.taoswork.tallybook.general.authority.core.resource.AccessibleAreas;

/**
 * IAccessVerifier is used to check if an authority object (IPermissionAuthority) have access to a specific resource
 */
public interface IAccessVerifier {
    /**
     * Get all the possible access for the specified resource.
     * @param auth
     * @param resourceType
     * @return
     */
    Access getAllPossibleAccess(IPermissionAuthority auth, String resourceType);

    /**
     * Check if auth can access the resource type
     */
    boolean canAccess(IPermissionAuthority auth, Access access, String resourceType);

    /**
     * check if auth can access the resource instance
     * steps:
     *      1. work out ResourceFitting
     *      2. work out the merged access according to ResourceFitting
     *      3. check if merged access qualified
     */
    boolean canAccess(IPermissionAuthority auth, Access access, String resourceType, Object... resources);

    /**
     * A shortcut for calcAccessibleAreas(IEntityPermission entityPermission, Access access);
     */
    AccessibleAreas calcAccessibleAreas(IPermissionAuthority auth, Access access, String resourceType);

    /**
     * If returns null, no resource can pass
     *
     * @param entityPermission
     * @param access
     * @return AccessibleAreas defining accessible records using filters definitions.
     */
    AccessibleAreas calcAccessibleAreas(IEntityPermission entityPermission, Access access);
}
