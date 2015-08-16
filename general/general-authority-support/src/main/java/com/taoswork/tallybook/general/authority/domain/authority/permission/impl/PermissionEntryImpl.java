package com.taoswork.tallybook.general.authority.domain.authority.permission.impl;

import com.taoswork.tallybook.general.authority.domain.authority.access.ResourceAccess;
import com.taoswork.tallybook.general.authority.domain.authority.permission.Permission;
import com.taoswork.tallybook.general.authority.domain.authority.permission.PermissionEntry;
import com.taoswork.tallybook.general.authority.domain.authority.resource.ResourceCriteria;
import com.taoswork.tallybook.general.authority.domain.authority.resource.impl.ResourceCriteriaImpl;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/4/19.
 */
@Entity
@Table(name =PermissionEntryImpl.TABLE_NAME)
public class PermissionEntryImpl
        implements PermissionEntry {

    public static final String TABLE_NAME = "AUTH_PERM_ENTITY";
    public static final String COL_NAME_4_PERMISSION = "PERM_ID";
    public static final String COL_NAME_4_RES_CRITERIA = "RES_CRIT_ID";

    @Id
    @Column(name = "ID")
    @PresentationField(order = 1)
    protected Long id;

    @Column(name = "NAME")
    @PresentationField(order = 2, nameField = true)
    protected String name;

    @Column(name = "ORG_ID", nullable = false)
    @PresentationField(order = 3)
    protected Long organizationId;

    @ManyToOne(targetEntity = ResourceCriteriaImpl.class)
    @JoinColumn(name = COL_NAME_4_RES_CRITERIA)
    @PresentationField(order = 4)
    protected ResourceCriteria resourceCriteria;

    @Embedded
    @Column(name = "ACCESS", nullable=false)
    @PresentationField(order = 5)
    protected ResourceAccess access;

    @ManyToMany(
            targetEntity = PermissionImpl.class,
            mappedBy = PermissionImpl.OWN_M2M_ALL_ENTRIES,
            fetch = FetchType.LAZY)
    protected List<Permission> permissionsUsingThis = new ArrayList<Permission>();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public PermissionEntry setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public ResourceAccess getAccess() {
        return access;
    }

    @Override
    public PermissionEntry setAccess(ResourceAccess access) {
        this.access = access;
        return this;
    }

    @Override
    public Long getOrganizationId() {
        return organizationId;
    }

    @Override
    public PermissionEntry setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
        return this;
    }

    @Override
    public ResourceCriteria getResourceCriteria() {
        return resourceCriteria;
    }

    @Override
    public PermissionEntry setResourceCriteria(ResourceCriteria resourceCriteria) {
        this.resourceCriteria = resourceCriteria;
        return this;
    }

    @Override
    public List<Permission> getPermissionsUsingThis() {
        return permissionsUsingThis;
    }

    @Override
    public PermissionEntry setPermissionsUsingThis(List<Permission> permissionsUsingThis) {
        this.permissionsUsingThis = permissionsUsingThis;
        return this;
    }
}
