package com.taoswork.tallybook.general.authority.domain.permission.impl;

import com.taoswork.tallybook.general.authority.domain.permission.Permission;
import com.taoswork.tallybook.general.authority.domain.permission.PermissionEntry;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationClass;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;

import javax.persistence.*;

/**
 * Created by Gao Yuan on 2015/4/19.
 */

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@PresentationClass(instantiable =false)
public abstract class PermissionBaseImpl<PE extends PermissionEntry>
        implements Permission<PE> {

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

//    @FieldRelation(RelationType.TwoWay_ManyToOneBelonging)
//    @OneToMany(
//            targetEntity = PermissionEntryBaseImpl.class,
//            mappedBy = PermissionEntryBaseImpl.OWN_M2O_PERM,
//            fetch = FetchType.LAZY)
//    protected List<PE> allEntries = new ArrayList<PE>();
//    public static final String OWN_M2M_ALL_ENTRIES = "allEntries";

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
//
//    @Override
//    public List<PE> getAllEntries() {
//        return allEntries;
//    }
//
//    @Override
//    public void setAllEntries(List<PE> allEntries) {
//        this.allEntries = allEntries;
//    }

}
