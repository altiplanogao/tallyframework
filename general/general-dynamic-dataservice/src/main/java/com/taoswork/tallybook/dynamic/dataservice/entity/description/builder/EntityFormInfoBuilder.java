package com.taoswork.tallybook.dynamic.dataservice.entity.description.builder;

import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.clazz.EntityInfo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.field.FieldInfo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.group.GroupInfo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.tab.TabInfo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.easy.form.EntityFormInfo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.easy.form.impl.*;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public class EntityFormInfoBuilder {

    private static class FormInfoCreator{
        static EntityFormInfoRW createClassFormInfo(EntityInfo classInfo){
            EntityFormInfoRW classFormInfo = new EntityFormInfoImpl();
            classFormInfo.copyNamedInfo(classInfo);
            return classFormInfo;
        }
        static TabFormInfoRW createTabFormInfo(TabInfo tabInfo){
            TabFormInfoRW tabFormInfo = new TabFormInfoImpl();
            tabFormInfo.copyNamedInfo(tabInfo);
            return tabFormInfo;
        }
        static GroupFormInfoRW createGroupFormInfo(GroupInfo groupInfo){
            GroupFormInfoRW groupFormInfo = new GroupFormInfoImpl();
            groupFormInfo.copyNamedInfo(groupInfo);
            return groupFormInfo;
        }
    }
    
    public static EntityFormInfo build(EntityInfo classInfo){
        EntityFormInfoRW formInfoRW = FormInfoCreator.createClassFormInfo(classInfo);
        for(TabInfo tabInfo : classInfo.getTabs()){
            TabFormInfoRW tabFormInfo = FormInfoCreator.createTabFormInfo(tabInfo);
            formInfoRW.addTab(tabFormInfo);
            for(GroupInfo groupInfo : tabInfo.getGroups()){
                GroupFormInfoRW groupFormInfo = FormInfoCreator.createGroupFormInfo(groupInfo);
                tabFormInfo.addGroup(groupFormInfo);
                for(FieldInfo fieldInfo : groupInfo.getFields()){
                    groupFormInfo.addField(fieldInfo);
                }
            }
        }

        for(FieldInfo fieldInfo : classInfo.getFields()){
            formInfoRW.addField(fieldInfo);
        }

        return formInfoRW;
    }
}
