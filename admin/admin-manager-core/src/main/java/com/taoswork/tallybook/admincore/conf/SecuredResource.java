package com.taoswork.tallybook.admincore.conf;

import com.taoswork.tallybook.authority.core.ProtectionMode;

/**
 * Created by Gao Yuan on 2015/10/23.
 */
public class SecuredResource {
    private String name;
    private String entity;
    private String category;
    private ProtectionMode protectionMode;
    private boolean masterControlled;

    public SecuredResource(String name, String entity, String category, ProtectionMode protectionMode, boolean masterControlled) {
        this.name = name;
        this.entity = entity;
        this.category = category;
        this.protectionMode = protectionMode;
        this.masterControlled = masterControlled;
    }
    public SecuredResource(Class entity, String category, ProtectionMode protectionMode, boolean masterControlled) {
        this(entity.getSimpleName(), entity.getName(), category, protectionMode, masterControlled);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ProtectionMode getProtectionMode() {
        return protectionMode;
    }

    public void setProtectionMode(ProtectionMode protectionMode) {
        this.protectionMode = protectionMode;
    }

    public boolean isMasterControlled() {
        return masterControlled;
    }

    public void setMasterControlled(boolean masterControlled) {
        this.masterControlled = masterControlled;
    }
}
