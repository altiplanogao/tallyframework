package com.taoswork.tallybook.dynamic.dataservice.entity.description.builder;

import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.base.OrderedName;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.clazz.EntityInfo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.field.FieldInfo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.easy.grid.EntityGridInfo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.easy.grid.impl.EntityGridInfoImpl;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public class EntityGridInfoBuilder {
    public static EntityGridInfo build(EntityInfo classInfo){
        EntityGridInfoImpl classGridInfo = new EntityGridInfoImpl();
        classGridInfo.copyNamedInfo(classInfo);

        classGridInfo.setPrimarySearchField(classInfo.getPrimarySearchField());

        Map<OrderedName, FieldInfo> gridNameOrdered = new TreeMap<OrderedName, FieldInfo>(new OrderedName.OrderedComparator());
        for (String fieldName : classInfo.getGridFields()){
            FieldInfo fieldInfo = classInfo.getField(fieldName);
            if(fieldInfo != null){
                gridNameOrdered.put(new OrderedName(fieldName, fieldInfo.getOrder()), fieldInfo);
            }
        }

        for (Map.Entry<OrderedName, FieldInfo> fieldInfoEntry : gridNameOrdered.entrySet()) {
            FieldInfo fieldInfo = fieldInfoEntry.getValue();
            classGridInfo.addField(fieldInfo);
        }
        return classGridInfo;
    }
}
