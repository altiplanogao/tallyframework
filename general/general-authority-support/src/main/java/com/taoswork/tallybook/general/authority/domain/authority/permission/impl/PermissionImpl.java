package com.taoswork.tallybook.general.authority.domain.authority.permission.impl;

import com.taoswork.tallybook.dynamic.datadomain.presentation.PresentationField;
import com.taoswork.tallybook.dynamic.datadomain.presentation.client.Visibility;
import com.taoswork.tallybook.general.authority.domain.authority.permission.Permission;
import com.taoswork.tallybook.general.authority.domain.authority.permission.PermissionEntry;
import com.taoswork.tallybook.general.authority.domain.authority.permission.Role;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/4/19.
 */
@Entity
@Table(name = PermissionImpl.TABLE_NAME)
public class PermissionImpl
        implements Permission {

    public static final String TABLE_NAME = "AUTH_PERM";
    public static final String TABLE_NAME_JOIN_PERM_PERM = "AUTH_PERM_PERM_XREF";
    public static final String TABLE_NAME_JOIN_PERM_PERM_PARENT_COL = "PERM_ID";
    public static final String TABLE_NAME_JOIN_PERM_PERM_CHILD_COL = "SON_PERM_ID";

    public static final String TABLE_NAME_JOIN_PERM_PERMENTRY = "AUTH_PERM_PERM_XREF";
    public static final String TABLE_NAME_JOIN_PERM_PERMENTRY_P_COL = "PERM_ID";
    public static final String TABLE_NAME_JOIN_PERM_PERMENTRY_PE_COL = "PERMENTRY_ID";

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

//    //The following access symbol is only used for screen display,
//    //the real access control is defined in IPE.
//    @Column(name = "ACCESS", nullable = false)
//    protected int accessSymbol;

    @Column(name = "FRIENDLY", nullable = false)
    protected boolean userFriendly;

    @ManyToMany(
            targetEntity = PermissionEntryImpl.class,
            fetch = FetchType.LAZY)
    @JoinTable(name = TABLE_NAME_JOIN_PERM_PERMENTRY,
            joinColumns = @JoinColumn(name = TABLE_NAME_JOIN_PERM_PERMENTRY_P_COL, referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = TABLE_NAME_JOIN_PERM_PERMENTRY_PE_COL, referencedColumnName = "ID")
    )
    protected List<PermissionEntry> allEntries = new ArrayList<PermissionEntry>();
    public static final String OWN_M2M_ALL_ENTRIES = "allEntries";

    @ManyToMany(fetch = FetchType.LAZY,
            targetEntity = PermissionImpl.class)
    @JoinTable(name = TABLE_NAME_JOIN_PERM_PERM,
            joinColumns = @JoinColumn(name = TABLE_NAME_JOIN_PERM_PERM_PARENT_COL, referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = TABLE_NAME_JOIN_PERM_PERM_CHILD_COL, referencedColumnName = "ID"))
    protected List<Permission> childrenPermission = new ArrayList<Permission>();
    public static final String OWN_M2M_CHILD_PERMS = "childrenPermission";

    @ManyToMany(
            targetEntity = PermissionImpl.class,
            mappedBy = PermissionImpl.OWN_M2M_CHILD_PERMS,
            fetch = FetchType.LAZY)
    protected List<Permission> parentPermission = new ArrayList<Permission>();

    @ManyToMany(
            targetEntity = RoleImpl.class,
            mappedBy = RoleImpl.OWN_M2M_ALL_PERMS,
            fetch = FetchType.LAZY)
    protected Set<Role> allRoles = new HashSet<Role>();


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
//
//    @Override
//    public int getAccessSymbol() {
//        return accessSymbol;
//    }
//
//    @Override
//    public void setAccessSymbol(int accessSymbol) {
//        this.accessSymbol = accessSymbol;
//    }

    @Override
    public boolean isUserFriendly() {
        return userFriendly;
    }

    @Override
    public void setUserFriendly(boolean userFriendly) {
        this.userFriendly = userFriendly;
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
    public List<Permission> getChildrenPermission() {
        return childrenPermission;
    }

    @Override
    public void setChildrenPermission(List<Permission> childrenPermission) {
        this.childrenPermission = childrenPermission;
    }

    @Override
    public List<PermissionEntry> getAllEntries() {
        return allEntries;
    }

    @Override
    public void setAllEntries(List<PermissionEntry> allEntries) {
        this.allEntries = allEntries;
    }

    @Override
    public List<Permission> getParentPermission() {
        return parentPermission;
    }

    @Override
    public void setParentPermission(List<Permission> parentPermission) {
        this.parentPermission = parentPermission;
    }

    @Override
    public Set<Role> getAllRoles() {
        return allRoles;
    }

    @Override
    public void setAllRoles(Set<Role> allRoles) {
        this.allRoles = allRoles;
    }
}
