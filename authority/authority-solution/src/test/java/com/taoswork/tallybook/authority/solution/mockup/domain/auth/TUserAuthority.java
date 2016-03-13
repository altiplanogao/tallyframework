package com.taoswork.tallybook.authority.solution.mockup.domain.auth;

import com.taoswork.tallybook.authority.solution.domain.user.GroupAuthority;
import com.taoswork.tallybook.authority.solution.domain.user.UserAuthority;
import com.taoswork.tallybook.datadomain.base.entity.CollectionField;
import com.taoswork.tallybook.datadomain.base.entity.CollectionMode;
import com.taoswork.tallybook.datadomain.base.entity.PersistEntity;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Gao Yuan on 2016/2/27.
 */
@Entity("testuser")
@PersistEntity(asDefaultPermissionGuardian = true)
public class TUserAuthority extends UserAuthority<TGroupAuthority> {

    private String name;

    @Reference
    @CollectionField(mode = CollectionMode.Lookup)
    List<TGroupAuthority> groups = new ArrayList<TGroupAuthority>();

    public TUserAuthority() {
    }

    public TUserAuthority(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TGroupAuthority> getGroups() {
        return groups;
    }

    public void setGroups(List<TGroupAuthority> groups) {
        this.groups = groups;
    }

    @Override
    public Collection<? extends GroupAuthority> theGroups() {
        return groups;
    }
}
