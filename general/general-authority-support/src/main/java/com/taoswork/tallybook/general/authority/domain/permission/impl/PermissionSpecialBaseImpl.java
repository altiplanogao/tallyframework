package com.taoswork.tallybook.general.authority.domain.permission.impl;

import com.taoswork.tallybook.general.authority.domain.permission.Permission;
import com.taoswork.tallybook.general.authority.domain.permission.PermissionSpecial;
import com.taoswork.tallybook.general.authority.domain.resource.SecuredResourceSpecial;
import com.taoswork.tallybook.general.authority.domain.resource.impl.SecuredResourceSpecialBaseImpl;
import com.taoswork.tallybook.general.authority.domain.access.ResourceAccess;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationClass;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
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
 *      |                       |              | entity name (resourceEntity)         | filter (SecuredResourceSpecialBaseImpl.java)
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
 *      |                       |              | filter (SecuredResourceSpecialBaseImpl.java)
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

    @Id
    @Column(name = "ID")
    protected Long id;

    @Column(name = "NAME")
    @PresentationField(order = 2, fieldType = FieldType.NAME)
    protected String name;

    @Column(name = "RES_ENTITY", nullable = false)
    @PresentationField(order = 4)
    protected String resourceEntity;

    @FieldRelation(RelationType.OneWay_ManyToOne)
    @ManyToOne(targetEntity = SecuredResourceSpecialBaseImpl.class)
    @JoinColumn(name = "RES_SPEC_ID")
    @PresentationField(order = 5)
    protected SecuredResourceSpecial securedResourceSpecial;

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
    public SecuredResourceSpecial getSecuredResourceSpecial() {
        return securedResourceSpecial;
    }

    @Override
    public void setSecuredResourceSpecial(SecuredResourceSpecial securedResourceSpecial) {
        this.securedResourceSpecial = securedResourceSpecial;
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
