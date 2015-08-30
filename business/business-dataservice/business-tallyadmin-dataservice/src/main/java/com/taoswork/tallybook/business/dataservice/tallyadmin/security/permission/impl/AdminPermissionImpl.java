package com.taoswork.tallybook.business.dataservice.tallyadmin.security.permission.impl;

import com.taoswork.tallybook.business.dataservice.tallyadmin.security.permission.AdminPermission;
import com.taoswork.tallybook.business.dataservice.tallyadmin.security.permission.AdminPermissionEntry;
import com.taoswork.tallybook.general.authority.domain.permission.impl.PermissionBaseImpl;
import com.taoswork.tallybook.general.datadomain.support.presentation.relation.FieldRelation;
import com.taoswork.tallybook.general.datadomain.support.presentation.relation.RelationType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/8/27.
 */
@Entity
@Table(name = "ADMIN_PERM")
public class AdminPermissionImpl
    extends PermissionBaseImpl<AdminPermissionEntry>
    implements AdminPermission {

    @FieldRelation(RelationType.TwoWay_ManyToOneBelonging)
    @OneToMany(
            targetEntity = AdminPermissionEntryImpl.class,
            mappedBy = AdminPermissionEntryImpl.OWN_M2O_PERM,
            fetch = FetchType.LAZY)
    protected List<AdminPermissionEntry> allEntries = new ArrayList<AdminPermissionEntry>();
    public static final String OWN_M2M_ALL_ENTRIES = "allEntries";

    @Override
    public List<AdminPermissionEntry> getAllEntries() {
        return allEntries;
    }

    @Override
    public void setAllEntries(List<AdminPermissionEntry> allEntries) {
        this.allEntries = allEntries;
    }
}
