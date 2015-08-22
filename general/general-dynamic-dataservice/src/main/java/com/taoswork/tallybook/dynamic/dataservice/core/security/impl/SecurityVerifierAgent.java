package com.taoswork.tallybook.dynamic.dataservice.core.security.impl;

import com.taoswork.tallybook.dynamic.dataservice.core.security.ISecurityVerifier;
import com.taoswork.tallybook.general.authority.core.basic.Access;

/**
 * Created by Gao Yuan on 2015/8/22.
 */
public class SecurityVerifierAgent implements ISecurityVerifier {
    public static final String COMPONENT_NAME = "SecurityVerifierAgent";

    private ISecurityVerifier verifier = null;

    public SecurityVerifierAgent(){
        this(null);
    }

    public SecurityVerifierAgent(ISecurityVerifier verifier){
        setVerifier(verifier);
    }

    public ISecurityVerifier getVerifier() {
        return verifier;
    }

    public void setVerifier(ISecurityVerifier verifier) {
        if (verifier == null){
            verifier = new PassAllSecurityVerifier();
        }
        this.verifier = verifier;
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
    public void checkAccess(String resourceEntity, Access access) throws SecurityException {
        verifier.checkAccess(resourceEntity, access);
    }

    @Override
    public void checkAccess(String resourceEntity, Access access, Object... instances) throws SecurityException {
        verifier.canAccess(resourceEntity, access, instances);
    }

    @Override
    public boolean canAccessMappedResource(String mappedResource, Access access) {
        return verifier.canAccessMappedResource(mappedResource, access);
    }
}