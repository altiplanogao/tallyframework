package com.taoswork.tallybook.dynamic.dataservice.entity.description.edo;

import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.base.OrderedName;
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
public class GroupEdo extends FriendlyEdo {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupEdo.class);

    private final Set<OrderedName> fields = new TreeSet<OrderedName>(new OrderedName.OrderedComparator());

    private OrderedName getFieldNameWithName(final String name){
        return CollectionUtility.find(fields, new TPredicate<OrderedName>() {
            @Override
            public boolean evaluate(OrderedName notNullObj) {
                return notNullObj.getName().equals(name);
            }
        });
    }

    public Set<OrderedName> getFieldNames(){
        return Collections.unmodifiableSet(fields);
    }

    GroupEdo addFieldName(OrderedName fieldName){
        OrderedName existingFieldOrder = getFieldNameWithName(fieldName.getName());
        if(existingFieldOrder != null){
            LOGGER.warn("FieldEdo '{}' under GroupEdo '{}' already exist.", fieldName.getName(), this.getName());
        }
        fields.add(fieldName);
        return this;
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
        for(OrderedName fieldEdo : groupEdo.fields){
            final String fieldName = fieldEdo.getName();
            OrderedName existionFieldEdo = CollectionUtility.find(fields, new TPredicate<OrderedName>() {
                @Override
                public boolean evaluate(OrderedName notNullObj) {
                    return notNullObj.getName().equals(fieldName);
                }
            });
            if(existionFieldEdo != null){
//                existionFieldEdo.merge(fieldEdo);
            }else {
                fields.add(fieldEdo);
            }
        }
    }

    @Override
    protected String toStringChildInfo() {
        return "fields.size=" + fields.size();
    }
}
