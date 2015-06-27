package com.taoswork.tallybook.dynamic.dataservice.entity.description.edo;

import com.taoswork.tallybook.dynamic.dataservice.entity.description.edo.inf.FriendlyEdo;
import com.taoswork.tallybook.general.extension.collections.CollectionUtility;
import com.taoswork.tallybook.general.extension.utils.CompareUtility;
import com.taoswork.tallybook.general.extension.utils.TPredicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public class TabEdo extends FriendlyEdo {
    private static final Logger LOGGER = LoggerFactory.getLogger(TabEdo.class);

    private final Set<GroupEdo> groups = new TreeSet<GroupEdo>(new FriendlyEdo.OrderedComparator());

    public GroupEdo getGroupWithName(final String name){
        return CollectionUtility.find(groups, new TPredicate<GroupEdo>() {
            @Override
            public boolean evaluate(GroupEdo notNullObj) {
                return notNullObj.getName().equals(name);
            }
        });
    }

    public Set<GroupEdo> getGroups(){
        return Collections.unmodifiableSet(groups);
    }

    TabEdo addGroup(GroupEdo groupDto){
        GroupEdo existingGroupDto = getGroupWithName(groupDto.getName());
        if(existingGroupDto != null){
            LOGGER.warn("GroupEdo '{}' under TabEdo '{}' already exist.", groupDto.getName(), this.getName());
        }
        groups.add(groupDto);
        return this;
    }

    public void merge(TabEdo tabEdo){
        if(this.equals(tabEdo)){
            return;
        }
        if(!CompareUtility.isSame(getName(), tabEdo.getName())){
            LOGGER.warn("Merge TabEdo with different name: '{}' vs '{}'", getName(), tabEdo.getName());
        }
//        if(!CompareUtility.isSame(getFriendlyName(), tabEdo.getFriendlyName())){
//            LOGGER.warn("Merge TabEdo with different friendly name: '{}' vs '{}'", getFriendlyName(), tabEdo.getFriendlyName());
//        }
        for(GroupEdo groupEdo : tabEdo.getGroups()){
            final String groupName = groupEdo.getName();
            GroupEdo existionGroupEdo = CollectionUtility.find(groups, new TPredicate<GroupEdo>() {
                @Override
                public boolean evaluate(GroupEdo notNullObj) {
                    return notNullObj.getName().equals(groupName);
                }
            });
            if(existionGroupEdo != null){
                existionGroupEdo.merge(groupEdo);
            }else {
                groups.add(groupEdo);
            }
        }
    }

    @Override
    public String toStringChildInfo() {
        return "groups.size=" + groups.size();
    }
}
