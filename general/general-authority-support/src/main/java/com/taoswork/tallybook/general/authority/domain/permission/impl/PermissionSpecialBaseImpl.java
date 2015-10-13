package com.taoswork.tallybook.general.authority.domain.permission.impl;

import com.taoswork.tallybook.general.authority.domain.permission.Permission;
import com.taoswork.tallybook.general.authority.domain.permission.PermissionSpecial;
import com.taoswork.tallybook.general.authority.domain.resource.SecuredResourceFilter;
import com.taoswork.tallybook.general.authority.domain.resource.impl.SecuredResourceFilterBaseImpl;
import com.taoswork.tallybook.general.authority.domain.access.ResourceAccess;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationClass;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;
import com.taoswork.tallybook.general.datadomain.support.presentation.relation.FieldRelation;
import com.taoswork.tallybook.general.datadomain.support.presentation.relation.RelationType;

import javax.persistence.*;

/**
 *  Solution A:
 *      +-----------------------+  1     .*    +-----------------------+  1     .*    +-----------------------+
 *      |   Permission          |  --------->  | Permission 4 Entity   |   ---------> |   Permission Entry    |
 *      | ( Permission Package) |              |                       |              |                       |
 *      | owned by user or group|              |                       |              |                       |
 *      | SimplePermissionAuthority.java       | EntityPermission.java |              | EntityPermissionSpecial.java  |
 *      |-----------------------|              |-----------------------|              |-----------------------|
 *      | id                    |              | id                    |              | id                    |
 *      |                       |              | entity name (resourceEntity)         | filter (SecuredResourceFilterBaseImpl.java)
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
 *      | SimplePermissionAuthority.java       | EntityPermissionSpecial.java  |
 *      |-----------------------|              |-----------------------|
 *      | id                    |              | id                    |
 *      | name                  |              | entity name (resourceEntity)
 *      | owner (optional)      |              | is main or filtered   |
 *      |                       |              | filter (SecuredResourceFilterBaseImpl.java)
 *      |                       |              | access (or master access)
 *      |                       |              |                       |
 *      |                       |              |                       |
 *      |  version              |              |                       |
 *      +-----------------------+              +-----------------------+
 *
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@PresentationClass(instantiable =false)
public abstract class PermissionSpecialBaseImpl<P extends Permission>
        implements PermissionSpecial<P> {

    public static final String TABLE_NAME = "AUTH_PERM_ENTRY";

    @Id
    @Column(name = "ID")
    @PresentationField(order = 1, visibility = Visibility.HIDDEN_ALL)
    protected Long id;

    @Column(name = "NAME")
    @PresentationField(order = 2, nameField = true)
    protected String name;

    @Column(name = "ORG_ID", nullable = false)
    @PresentationField(order = 3)
    protected Long organizationId;

    @Column(name = "RES_ENTITY", nullable = false)
    @PresentationField(order = 4)
    protected String resourceEntity;

    @FieldRelation(RelationType.OneWay_ManyToOne)
    @ManyToOne(targetEntity = SecuredResourceFilterBaseImpl.class)
    @JoinColumn(name = "RES_FILTER_ID")
    @PresentationField(order = 5)
    protected SecuredResourceFilter securedResourceFilter;

    @Embedded
    @Column(name = "ACCESS", nullable=false)
    @PresentationField(order = 6)
    protected ResourceAccess access;

//    @FieldRelation(RelationType.TwoWay_ManyToOneOwner)
//    @ManyToOne(targetEntity = PermissionBaseImpl.class)
//    @JoinColumn(name = "PERM_ID")
//    @PresentationField(order = 7)
//    protected P permission;
//    public static final String OWN_M2O_PERM = "permission";

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
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public ResourceAccess getAccess() {
        return access;
    }

    @Override
    public void setAccess(ResourceAccess access) {
        this.access = access;
    }

    @Override
    public Long getOrganizationId() {
        return organizationId;
    }

    @Override
    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    @Override
    public SecuredResourceFilter getSecuredResourceFilter() {
        return securedResourceFilter;
    }

    @Override
    public void setSecuredResourceFilter(SecuredResourceFilter securedResourceFilter) {
        this.securedResourceFilter = securedResourceFilter;
    }

    @Override
    public String getResourceEntity() {
        return resourceEntity;
    }

    @Override
    public void setResourceEntity(String resourceEntity) {
        this.resourceEntity = resourceEntity;
    }

//    @Override
//    public P getPermission() {
//        return permission;
//    }
//
//    @Override
//    public void setPermission(P permission) {
//        this.permission = permission;
//    }
}
