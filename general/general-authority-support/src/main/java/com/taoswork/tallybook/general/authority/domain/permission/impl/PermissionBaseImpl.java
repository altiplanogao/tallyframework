package com.taoswork.tallybook.general.authority.domain.permission.impl;

import com.taoswork.tallybook.general.authority.domain.permission.Permission;
import com.taoswork.tallybook.general.authority.domain.permission.PermissionSpecial;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationClass;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@PresentationClass(instantiable =false)
public abstract class PermissionBaseImpl<PS extends PermissionSpecial>
        implements Permission<PS> {

    public static final String TABLE_NAME = "AUTH_PERM";
    public static final String TABLE_NAME_JOIN_PERM_PERM = "AUTH_PERM_PERM_XREF";
    public static final String TABLE_NAME_JOIN_PERM_PERM_PARENT_COL = "PERM_ID";
    public static final String TABLE_NAME_JOIN_PERM_PERM_CHILD_COL = "SON_PERM_ID";

    public static final String TABLE_NAME_JOIN_PERM_PERMENTRY = "AUTH_PERM_PERM_XREF";
    public static final String TABLE_NAME_JOIN_PERM_PERMENTRY_P_COL = "PERM_ID";
    public static final String TABLE_NAME_JOIN_PERM_PERMENTRY_PE_COL = "PERMENTRY_ID";

    @Id
    @Column(name = "ID")
    protected Long id;

    @Column(name = "SCREEN_NAME", nullable = false)
    @PresentationField(order = 2, nameField = true, fieldType = FieldType.NAME)
    protected String screenName;

//    @FieldRelation(RelationType.TwoWay_ManyToOneBelonging)
//    @OneToMany(
//            targetEntity = PermissionSpecialBaseImpl.class,
//            mappedBy = PermissionSpecialBaseImpl.OWN_M2O_PERM,
//            fetch = FetchType.LAZY)
//    protected List<PS> allEntries = new ArrayList<PS>();
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
//
//    @Override
//    public List<PS> getAllEntries() {
//        return allEntries;
//    }
//
//    @Override
//    public void setAllEntries(List<PS> allEntries) {
//        this.allEntries = allEntries;
//    }

}
