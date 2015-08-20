package com.taoswork.tallybook.general.authority.core.permission.impl;

import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.authority.core.basic.ProtectionMode;
import com.taoswork.tallybook.general.authority.core.permission.IEntityPermission;
import com.taoswork.tallybook.general.authority.core.permission.IPermissionEntry;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public final class EntityPermission implements IEntityPermission {
    private final String resourceEntity;
    private Access masterAccess = Access.None;
    private final ConcurrentHashMap<String, IPermissionEntry> permissionEntries = new ConcurrentHashMap<String, IPermissionEntry>();

    private Object lock = new Object();
    private transient volatile boolean dirty = false;
    private transient volatile Access mergedAccess = Access.None;

    public EntityPermission(String resourceEntity){
        this.resourceEntity = resourceEntity;
    }

    @Override
    public String resourceEntity() {
        return resourceEntity;
    }

    @Override
    public Access getMasterAccess() {
        synchronized (lock) {
            return masterAccess;
        }
    }

    @Override
    public void setMasterAccess(Access masterAccess) {
        synchronized (lock) {
            this.masterAccess = masterAccess;
            dirty = true;
        }
    }

    @Override
    public Access mergedAccess() {
        synchronized (lock){
            if(dirty){
                Access a = masterAccess;
                for (IPermissionEntry permissionEntry : permissionEntries.values()){
                    a = a.merge(permissionEntry.getAccess());
                }
                mergedAccess = a;
                dirty = false;
            }
            return mergedAccess;
        }
    }

    @Override
    public Access accessByFilters(Collection<String> filterCodes,
                                  boolean masterControlled,  ProtectionMode protectionMode) {
        switch (protectionMode){
            case FitAll:
                return this.andAccessByFilters(masterControlled, filterCodes);
            case FitAny:
                return this.orAccessByFilters(masterControlled, filterCodes);
            default:
                throw new IllegalStateException();
        }
    }

    private Access andAccessByFilters(boolean masterControlled, Collection<String> filterCodes) {
        Map<String, IPermissionEntry> map = new HashMap<String, IPermissionEntry>();
        synchronized (lock) {
            for(String code : filterCodes){
                IPermissionEntry entry = permissionEntries.get(code);
                if(entry != null){
                    map.put(code, entry);
                }else {
                    return Access.None;
                }
            }
        }

        if(masterControlled) {
            Access access = this.masterAccess;
            for (IPermissionEntry pe : map.values()) {
                access = access.and(pe.getAccess());
            }
            return access;
        } else {
            if(filterCodes.isEmpty()){
                return this.masterAccess;
            }
            Access access = null;
            for (IPermissionEntry pe : map.values()) {
                if (access == null) {
                    access = pe.getAccess();
                } else {
                    access = access.and(pe.getAccess());
                }
            }
            return access;
        }
    }

    private Access orAccessByFilters(boolean masterControlled, Collection<String> filterCodes) {
        Map<String, IPermissionEntry> map = new HashMap<String, IPermissionEntry>();
        synchronized (lock) {
            for(String code : filterCodes){
                IPermissionEntry entry = permissionEntries.get(code);
                if(entry != null){
                    map.put(code, entry);
                }
            }
        }

        Access access = masterControlled ? masterAccess :
            (filterCodes.isEmpty() ? masterAccess : Access.None);
        for (IPermissionEntry pe : map.values()) {
            access = access.or(pe.getAccess());
        }

        return access;
    }

    @Override
    public IEntityPermission addEntries(IPermissionEntry... permEntries) {
        synchronized (lock) {
            for (IPermissionEntry entry : permEntries) {
                if(entry != null){
                    permissionEntries.put(entry.getFilterCode(), entry);
                }
            }
            dirty = true;
            return this;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("EntityPermission{'" + resourceEntity + "'");
        sb.append(", master=" + masterAccess)
            .append(", merged=" + mergedAccess())
            .append(", [");
        for (IPermissionEntry entry : permissionEntries.values()){
            sb.append("\n\t" + entry + "");
        }
        sb.append("]}");
        return sb.toString();
    }
}
