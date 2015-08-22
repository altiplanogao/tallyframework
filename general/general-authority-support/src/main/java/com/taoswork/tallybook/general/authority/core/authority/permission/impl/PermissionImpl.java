package com.taoswork.tallybook.general.authority.core.authority.permission.impl;

import com.taoswork.tallybook.general.authority.core.authority.permission.Permission;
import com.taoswork.tallybook.general.authority.core.authority.permission.PermissionEntry;
import com.taoswork.tallybook.general.authority.core.authority.permission.Role;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;

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

    @Column(name = "ORG_ID", nullable = false)
    @PresentationField(order = 3)
    protected Long organizationId;

    @OneToMany(
            targetEntity = PermissionEntryImpl.class,
            mappedBy = PermissionEntryImpl.OWN_M2O_PERM,
            fetch = FetchType.LAZY)
    protected List<PermissionEntry> allEntries = new ArrayList<PermissionEntry>();
    public static final String OWN_M2M_ALL_ENTRIES = "allEntries";

    @Version
    protected Integer version;

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
    public Long getOrganizationId() {
        return organizationId;
    }

    @Override
    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    @Override
    public List<PermissionEntry> getAllEntries() {
        return allEntries;
    }

    @Override
    public void setAllEntries(List<PermissionEntry> allEntries) {
        this.allEntries = allEntries;
    }

}
