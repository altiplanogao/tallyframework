package com.taoswork.tallybook.general.authority.domain.permission.impl;

import com.taoswork.tallybook.general.authority.domain.permission.Permission;
import com.taoswork.tallybook.general.authority.domain.permission.PermissionEntry;
import com.taoswork.tallybook.general.authority.domain.resource.SecuredResourceFilter;
import com.taoswork.tallybook.general.authority.domain.resource.impl.SecuredResourceFilterImpl;
import com.taoswork.tallybook.general.authority.domain.access.ResourceAccess;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;

import javax.persistence.*;

/**
 *  Solution A:
 *      +-----------------------+  1     .*    +-----------------------+  1     .*    +-----------------------+
 *      |   Permission          |  --------->  | Permission 4 Entity   |   ---------> |   Permission Entry    |
 *      | ( Permission Package) |              |                       |              |                       |
 *      | owned by user or group|              |                       |              |                       |
 *      | SimplePermissionAuthority.java       | EntityPermission.java |              | PermissionEntry.java  |
 *      |-----------------------|              |-----------------------|              |-----------------------|
 *      | id                    |              | id                    |              | id                    |
 *      |                       |              | entity name (resourceEntity)         | filter (SecuredResourceFilterImpl.java)
 *      |                       |              | master access         |              | access                |
 *      |                       |              |                       |              |                       |
 *      |                       |              |                       |              |                       |
 *      |                       |              |                       |              |                       |
 *      |                       |              |                       |              |                       |
 *      |                       |              |                       |              |                       |
 *      |                       |              |                       |              |                       |
 *      | version               |              |                       |              |                       |
 *      +-----------------------+              +-----------------------+              +-----------------------+
 *
 *  Solution B:
 *      +-----------------------+  1     .*    +-----------------------+
 *      |   Permission          |  --------->  |   Permission Entry    |
 *      | ( Permission Package) |              |                       |
 *      | owned by user or group|              |                       |
 *      | SimplePermissionAuthority.java       | PermissionEntry.java  |
 *      |-----------------------|              |-----------------------|
 *      | id                    |              | id                    |
 *      | name                  |              | entity name (resourceEntity)
 *      | owner (optional)      |              | is main or filtered   |
 *      |                       |              | filter (SecuredResourceFilterImpl.java)
 *      |                       |              | access (or master access)
 *      |                       |              |                       |
 *      |                       |              |                       |
 *      |  version              |              |                       |
 *      +-----------------------+              +-----------------------+
 *
 *
 */
@Entity
@Table(name =PermissionEntryImpl.TABLE_NAME)
public class PermissionEntryImpl
        implements PermissionEntry {

    public static final String TABLE_NAME = "AUTH_PERM_ENTRY";

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

    @Column(name = "RES_ENTITY", nullable = false)
    protected String resourceEntity;

    @ManyToOne(targetEntity = SecuredResourceFilterImpl.class)
    @JoinColumn(name = "RES_FILTER_ID")
    @PresentationField(order = 4)
    protected SecuredResourceFilter securedResourceFilter;

    @Embedded
    @Column(name = "ACCESS", nullable=false)
    @PresentationField(order = 5)
    protected ResourceAccess access;

    @ManyToOne(targetEntity = PermissionImpl.class)
    @JoinColumn(name = "PERM_ID")
    protected Permission permission;
    public static final String OWN_M2O_PERM = "permission";

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
    public SecuredResourceFilter getSecuredResourceFilter() {
        return securedResourceFilter;
    }

    @Override
    public PermissionEntry setSecuredResourceFilter(SecuredResourceFilter securedResourceFilter) {
        this.securedResourceFilter = securedResourceFilter;
        return this;
    }

    @Override
    public String getResourceEntity() {
        return resourceEntity;
    }

    @Override
    public void setResourceEntity(String resourceEntity) {
        this.resourceEntity = resourceEntity;
    }

    @Override
    public Permission getPermission() {
        return permission;
    }

    @Override
    public void setPermission(Permission permission) {
        this.permission = permission;
    }
}
