package com.taoswork.tallybook.dataservice.security.impl;

import com.taoswork.tallybook.authority.core.Access;
import com.taoswork.tallybook.dataservice.security.ISecurityVerifier;
import com.taoswork.tallybook.dataservice.security.NoPermissionException;

/**
 * Created by Gao Yuan on 2015/8/22.
 */
public class SecurityVerifierAgent implements ISecurityVerifier {
    public static final String COMPONENT_NAME = "SecurityVerifierAgent";

    private ISecurityVerifier verifier = null;

    public SecurityVerifierAgent() {
        this(null);
    }

    public SecurityVerifierAgent(ISecurityVerifier verifier) {
        setVerifier(verifier);
    }

    public ISecurityVerifier getVerifier() {
        return verifier;
    }

    public void setVerifier(ISecurityVerifier verifier) {
        if (verifier == null) {
            verifier = new PassAllSecurityVerifier();
        }
        this.verifier = verifier;
    }

    @Override
    public Access getAllPossibleAccess(String resourceEntity, Access mask) {
        return verifier.getAllPossibleAccess(resourceEntity, mask);
    }

    @Override
    public boolean canAccess(String resourceEntity, Access access) {
        return verifier.canAccess(resourceEntity, access);
    }

    @Override
    public boolean canAccess(String resourceEntity, Access access, Object... instances) {
        return verifier.canAccess(resourceEntity, access, instances);
    }

    @Override
    public void checkAccess(String resourceEntity, Access access) throws NoPermissionException {
        verifier.checkAccess(resourceEntity, access);
    }

    @Override
    public void checkAccess(String resourceEntity, Access access, Object... instances) throws NoPermissionException {
        if (!verifier.canAccess(resourceEntity, access, instances)) {
            throw new NoPermissionException();
        }
    }

    @Override
    public boolean canAccessMappedResource(String mappedResource, Access access) {
        return verifier.canAccessMappedResource(mappedResource, access);
    }
}
