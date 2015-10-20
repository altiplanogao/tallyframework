package com.taoswork.tallybook.general.authority.domain.permission.impl;

import com.taoswork.tallybook.general.authority.domain.permission.Permission;
import com.taoswork.tallybook.general.authority.domain.permission.Role;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationClass;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;

import javax.persistence.*;

/**
 * Created by Gao Yuan on 2015/4/19.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@PresentationClass(instantiable =false)
public abstract class RoleBaseImpl<P extends Permission> implements Role<P> {

    @Id
    @Column(name = "ID")
    protected Long id;

    @Column(name = "SCREEN_NAME", nullable = false)
    @PresentationField(order = 2, fieldType = FieldType.NAME)
    protected String screenName;

    @Column(name = "INSIDE_NAME", nullable = false)
    @PresentationField(visibility = Visibility.HIDDEN_ALL)
    protected String insideName;

    @Column(name = "ORG_ID", nullable = false)
    @PresentationField(order = 3)
    protected Long organizationId;

//    @FieldRelation(RelationType.OneWay_ManyToMany)
//    @ManyToMany(
//            targetEntity = P.class,
//            fetch = FetchType.LAZY)
//    @JoinTable(name = TABLE_NAME_JOIN_ROLE_PERM,
//            joinColumns = @JoinColumn(name = TABLE_NAME_JOIN_ROLE_PERM_ROLE_COL, referencedColumnName = "ID"),
//            inverseJoinColumns = @JoinColumn(name = TABLE_NAME_JOIN_ROLE_PERM_PERM_COL, referencedColumnName = "ID"))
//    protected Set<Permission> allPermissions = new HashSet<Permission>();
//    public static final String OWN_M2M_ALL_PERMS = "allPermissions";

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getScreenName() {
        return screenName;
    }

    @Override
    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    @Override
    public String getInsideName() {
        return insideName;
    }

    @Override
    public void setInsideName(String insideName) {
        this.insideName = insideName;
    }

    @Override
    public Long getOrganizationId() {
        return organizationId;
    }

    @Override
    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

//
//    @Override
//    public Set<P> getAllPermissions() {
//        return allPermissions;
//    }
//
//    @Override
//    public void setAllPermissions(Set<P> allPermissions) {
//        this.allPermissions = allPermissions;
//    }
}
