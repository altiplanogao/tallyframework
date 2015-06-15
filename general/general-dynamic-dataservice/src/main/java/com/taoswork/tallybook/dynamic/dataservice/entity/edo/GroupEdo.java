package com.taoswork.tallybook.dynamic.dataservice.entity.edo;

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
public class GroupEdo extends FriendlyEdo {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupEdo.class);

    private final Set<FieldEdo> fields = new TreeSet<FieldEdo>(new FriendlyEdo.OrderedComparator());

    public FieldEdo getFieldWithName(final String name){
        return CollectionUtility.find(fields, new TPredicate<FieldEdo>() {
            @Override
            public boolean evaluate(FieldEdo notNullObj) {
                return notNullObj.getName().equals(name);
            }
        });
    }

    GroupEdo addField(FieldEdo fieldEdo){
        FieldEdo existingFieldEdo = getFieldWithName(fieldEdo.getName());
        if(existingFieldEdo != null){
            LOGGER.warn("FieldEdo '{}' under GroupEdo '{}' already exist.", fieldEdo.getName(), this.getName());
        }
        fields.add(fieldEdo);
        return this;
    }

    public Set<FieldEdo> getFields(){
        return Collections.unmodifiableSet(fields);
    }

    @Override
    protected String toStringChildInfo() {
        return "fields.size=" + fields.size();
    }

    public void merge(GroupEdo groupEdo){
        if(this.equals(groupEdo)){
            return;
        }
        if(!CompareUtility.isSame(getName(), groupEdo.getName())){
            LOGGER.warn("Merge GroupEdo with different name: '{}' vs '{}'", getName(), groupEdo.getName());
        }
        if(!CompareUtility.isSame(getFriendlyName(), groupEdo.getFriendlyName())){
            LOGGER.warn("Merge GroupEdo with different friendly name: '{}' vs '{}'", getFriendlyName(), groupEdo.getFriendlyName());
        }
        for(FieldEdo fieldEdo : groupEdo.fields){
            final String fieldName = fieldEdo.getName();
            FieldEdo existionFieldEdo = CollectionUtility.find(fields, new TPredicate<FieldEdo>() {
                @Override
                public boolean evaluate(FieldEdo notNullObj) {
                    return notNullObj.getName().equals(fieldName);
                }
            });
            if(existionFieldEdo != null){
                existionFieldEdo.merge(fieldEdo);
            }else {
                fields.add(fieldEdo);
            }
        }
    }
}
