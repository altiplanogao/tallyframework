package com.taoswork.tallybook.general.authority.domain.authority.resource;

import com.taoswork.tallybook.general.authority.domain.authority.access.AccessType;
import com.taoswork.tallybook.general.authority.domain.authority.permission.Permission;
import com.taoswork.tallybook.general.authority.service.consumer.ISecurityVerifier;
import com.taoswork.tallybook.general.authority.service.consumer.ResourceSecurityException;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/4.
 */
public class TFileAccessor {
    ISecurityVerifier securityVerifier;
    private List<Permission> permissions;

    public ISecurityVerifier getSecurityVerifier() {
        return securityVerifier;
    }

    public void setSecurityVerifier(ISecurityVerifier securityVerifier) {
        this.securityVerifier = securityVerifier;
    }

    public boolean create(TFile file) {
        try {
            securityVerifier.securityCheck(file, AccessType.Create);
        } catch (ResourceSecurityException e) {
            return false;
        }
        return true;
    }

    public boolean read(TFile file) {
        try {
            securityVerifier.securityCheck(file, AccessType.Read);
        } catch (ResourceSecurityException e) {
            return false;
        }
        return true;
    }

    public boolean update(TFile file) {
        try {
            securityVerifier.securityCheck(file, AccessType.Update);
        } catch (ResourceSecurityException e) {
            return false;
        }
        return true;
    }

    public boolean delete(TFile file) {
        try {
            securityVerifier.securityCheck(file, AccessType.Delete);
        } catch (ResourceSecurityException e) {
            return false;
        }
        return true;
    }

    public boolean query() {
        try {
            securityVerifier.securityCheckQuery(TFile.RESOURCE_TYPE_NAME, AccessType.Query);
        } catch (ResourceSecurityException e) {
            return false;
        }
        return true;
    }

    public boolean execute(TFile file) {
        try {
            securityVerifier.securityCheck(file, AccessType.extended(TFile.ACCESS_EXECUTE));
        } catch (ResourceSecurityException e) {
            return false;
        }
        return true;
    }

}