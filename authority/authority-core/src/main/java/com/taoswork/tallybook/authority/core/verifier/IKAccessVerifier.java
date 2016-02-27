package com.taoswork.tallybook.authority.core.verifier;

import com.taoswork.tallybook.authority.core.Access;
import com.taoswork.tallybook.authority.core.permission.IKAuthority;
import com.taoswork.tallybook.authority.core.permission.IKPermission;
import com.taoswork.tallybook.authority.core.resource.KAccessibleScope;

/**
 * IKAccessVerifier is used to check if an authority object (IKAuthority) have access to a specific resource
 */
public interface IKAccessVerifier {
    /**
     * Get all the possible access for the specified resource.
     * @param auth
     * @param resource
     * @return
     */
    Access getAllPossibleAccess(IKAuthority auth, String resource);

    /**
     * Check if auth can access the resource type
     */
    boolean canAccess(IKAuthority auth, Access access, String resource);

    /**
     * check if auth can access the resource instance
     * steps:
     *      1. work out KCaseFitting
     *      2. work out the merged access according to KCaseFitting
     *      3. check if merged access qualified
     */
    boolean canAccess(IKAuthority auth, Access access, String resource, Object... instances);

    /**
     * A shortcut for calcAccessibleAreas(IKPermission entityPermission, Access access);
     */
    KAccessibleScope calcAccessibleAreas(IKAuthority auth, Access access, String resource);

    /**
     * If returns null, no resource can pass
     *
     * @param permission: the holding access
     * @param access: the desired access
     * @return KAccessibleScope defining accessible records using filters definitions.
     */
    KAccessibleScope calcAccessibleAreas(IKPermission permission, Access access);

    boolean canAccessMappedResource(IKAuthority auth, Access access, String virtualResource);

}
