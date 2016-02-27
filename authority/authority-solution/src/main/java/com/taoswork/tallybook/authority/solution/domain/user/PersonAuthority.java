package com.taoswork.tallybook.authority.solution.domain.user;

import com.taoswork.tallybook.datadomain.base.entity.CollectionField;
import com.taoswork.tallybook.datadomain.base.entity.CollectionMode;
import com.taoswork.tallybook.datadomain.onmongo.presentation.ObjectIdEntry;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2016/2/10.
 */
@Entity
public class PersonAuthority extends BaseAuthority {
    private String name;

    @Reference
    @CollectionField(mode = CollectionMode.Lookup)
    List<GroupAuthority> groups = new ArrayList<GroupAuthority>();

    public PersonAuthority() {
    }

    public PersonAuthority(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GroupAuthority> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupAuthority> groups) {
        this.groups = groups;
    }
}
