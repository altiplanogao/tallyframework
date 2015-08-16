package com.taoswork.tallybook.general.authority.domain.authority.permission.impl;

import com.taoswork.tallybook.general.authority.domain.authority.permission.Permission;
import com.taoswork.tallybook.general.authority.domain.authority.permission.Role;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/4/19.
 */
@Entity
@Table(name = RoleImpl.TABLE_NAME)
public class RoleImpl implements Role {

    public static final String TABLE_NAME = "AUTH_ROLE";
    public static final String TABLE_NAME_JOIN_ROLE_PERM = "AUTH_ROLE_PERM_XREF";
    public static final String TABLE_NAME_JOIN_ROLE_PERM_ROLE_COL = "ROLE_ID";
    public static final String TABLE_NAME_JOIN_ROLE_PERM_PERM_COL = "PERMISSION_ID";

    @Id
    @Column(name = "ID")
    @PresentationField(order = 1)
    protected Long id;

    @Column(name = "SCREEN_NAME", nullable = false)
    @PresentationField(order = 2, nameField = true)
    protected String screenName;

    @Column(name = "INSIDE_NAME", nullable = false)
    @PresentationField(visibility = Visibility.HIDDEN_ALL)
    protected String insideName;

    @Column(name = "ORG_ID", nullable = false)
    @PresentationField(order = 3)
    protected Long organizationId;

    @ManyToMany(
            targetEntity = PermissionImpl.class,
            fetch = FetchType.LAZY)
    @JoinTable(name = TABLE_NAME_JOIN_ROLE_PERM,
            joinColumns = @JoinColumn(name = TABLE_NAME_JOIN_ROLE_PERM_ROLE_COL, referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = TABLE_NAME_JOIN_ROLE_PERM_PERM_COL, referencedColumnName = "ID"))
    protected Set<Permission> allPermissions = new HashSet<Permission>();
    public static final String OWN_M2M_ALL_PERMS = "allPermissions";

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


    @Override
    public Set<Permission> getAllPermissions() {
        return allPermissions;
    }

    @Override
    public void setAllPermissions(Set<Permission> allPermissions) {
        this.allPermissions = allPermissions;
    }
}
