package com.taoswork.tallybook.general.authority.domain.permission.impl;

import com.taoswork.tallybook.general.authority.GeneralAuthoritySupportRoot;
import com.taoswork.tallybook.general.authority.domain.access.ResourceAccess;
import com.taoswork.tallybook.general.authority.domain.permission.Permission;
import com.taoswork.tallybook.general.authority.domain.permission.PermissionEntry;
import com.taoswork.tallybook.general.datadomain.support.entity.PersistField;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationClass;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;

import javax.persistence.*;

/**
 *  Solution A:
 *      +-----------------------+  1     .*    +-----------------------+  1     .*    +-----------------------+
 *      |   Permission          |  --------->  | Permission 4 Entity   |   ---------> |   Permission Entry    |
 *      | ( Permission Package) |              |                       |              |                       |
 *      | owned by user or group|              |                       |              |                       |
 *      | SimplePermissionAuthority.java       | EntityPermission.java |              | EntityPermissionEntry.java  |
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
 *      | SimplePermissionAuthority.java       | EntityPermissionEntry.java  |
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
@PresentationClass(instantiable =false,
    groups = {
        @PresentationClass.Group(name = PermissionEntryBaseImpl.Presentation.Group.General, order = 1),
        @PresentationClass.Group(name = PermissionEntryBaseImpl.Presentation.Group.Access, order = 2)}
)
public abstract class PermissionEntryBaseImpl<P extends Permission>
        implements PermissionEntry<P> {

    protected static final String ID_GENERATOR_NAME = "PermissionEntryBaseImpl_IdGen";

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = ID_GENERATOR_NAME)
    @TableGenerator(
        name = ID_GENERATOR_NAME,
        table = GeneralAuthoritySupportRoot.ID_GENERATOR_TABLE_NAME,
        initialValue = 0)
    @Column(name = "ID")
    protected Long id;

    @Column(name = "NAME")
    @PersistField(fieldType = FieldType.NAME)
    @PresentationField(order = 2)
    protected String name;

    @Embedded
    @Column(name = "ACCESS", nullable=false)
    @PresentationField(order = 6, group = "Access")
    protected ResourceAccess access = new ResourceAccess();

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
//    @Override
//    public P getPermission() {
//        return permission;
//    }
//
//    @Override
//    public void setPermission(P permission) {
//        this.permission = permission;
//    }

    public static class Presentation{
        public static class Tab{
        }
        public static class Group{
            public static final String General = "General";
            public static final String Access = "Access";
        }
    }
}
