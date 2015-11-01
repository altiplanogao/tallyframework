package com.taoswork.tallybook.general.authority.domain.permission.impl;

import com.taoswork.tallybook.general.authority.GeneralAuthoritySupportRoot;
import com.taoswork.tallybook.general.authority.domain.permission.Permission;
import com.taoswork.tallybook.general.authority.domain.permission.PermissionEntry;
import com.taoswork.tallybook.general.datadomain.support.entity.PersistField;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationClass;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@PresentationClass(instantiable =false, groups = {
    @PresentationClass.Group(name = PermissionBaseImpl.Presentation.Group.Authority, order = 2)
})
public abstract class PermissionBaseImpl<PS extends PermissionEntry>
        implements Permission<PS> {

    public static final String TABLE_NAME = "AUTH_PERM";
    public static final String TABLE_NAME_JOIN_PERM_PERM = "AUTH_PERM_PERM_XREF";
    public static final String TABLE_NAME_JOIN_PERM_PERM_PARENT_COL = "PERM_ID";
    public static final String TABLE_NAME_JOIN_PERM_PERM_CHILD_COL = "SON_PERM_ID";

    public static final String TABLE_NAME_JOIN_PERM_PERMENTRY = "AUTH_PERM_PERM_XREF";
    public static final String TABLE_NAME_JOIN_PERM_PERMENTRY_P_COL = "PERM_ID";
    public static final String TABLE_NAME_JOIN_PERM_PERMENTRY_PE_COL = "PERMENTRY_ID";

    protected static final String ID_GENERATOR_NAME = "PermissionBaseImpl_IdGen";

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = ID_GENERATOR_NAME)
    @TableGenerator(
        name = ID_GENERATOR_NAME,
        table = GeneralAuthoritySupportRoot.ID_GENERATOR_TABLE_NAME,
        initialValue = 0)
    @Column(name = "ID")
    protected Long id;

    @Column(name = "NAME", nullable = false, length = 100)
    @PersistField(fieldType = FieldType.NAME)
    @PresentationField(order = 2)
    protected String name;

    @Column(name = "DESCRIPTION")
    @PersistField(fieldType = FieldType.STRING)
    @PresentationField(order = 3, visibility = Visibility.GRID_HIDE)
    protected String description;

//    @FieldRelation(RelationType.TwoWay_ManyToOneBelonging)
//    @OneToMany(
//            targetEntity = PermissionEntryBaseImpl.class,
//            mappedBy = PermissionEntryBaseImpl.OWN_M2O_PERM,
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
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
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
public static class Presentation{
    public static class Tab{
    }
    public static class Group{
        public static final String General = "General";
        public static final String Authority = "Authority";
    }
}

}
