package com.taoswork.tallybook.dynamic.dataservice.entity.edo;

import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.*;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.classtree.EntityClass;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.classtree.EntityClassTree;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.service.EntityMetadataService;
import com.taoswork.tallybook.general.solution.autotree.AutoTreeException;
import com.taoswork.tallybook.general.solution.quickinterface.ICallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by Gao Yuan on 2015/5/25.
 */
public final class EdoBuilder {
    private static Logger LOGGER = LoggerFactory.getLogger(EdoBuilder.class);

    public static ClassEdo buildClassEdo(Class<?> clz, EntityClassTree polymorphicEntityClzTree, final EntityMetadataService entityMetadataService) {
        return buildClassEdo(entityMetadataService.getClassMetadata(clz.getName()), polymorphicEntityClzTree, entityMetadataService);
    }

    public static ClassEdo buildClassEdo(ClassMetadata classMetadata, EntityClassTree polymorphicEntityClzTree, final EntityMetadataService entityMetadataService) {
        final ClassEdo classEdo = createClassEdo(classMetadata);
        classEdoAppendMetadata(classEdo, classMetadata);
        if (polymorphicEntityClzTree != null) {
            polymorphicEntityClzTree.traverse(true, new ICallback<Void, EntityClass, AutoTreeException>() {
                @Override
                public Void callback(EntityClass parameter) throws AutoTreeException {
                    ClassMetadata childClassMetadata = entityMetadataService.getClassMetadata(parameter.clz.getName());
                    classEdoAppendMetadata(classEdo, childClassMetadata);
                    return null;
                }
            }, true);
        }
        //polymorphicEntityClzTree.
        return classEdo;
    }


    private static void classEdoAppendMetadata(ClassEdo classEdo, ClassMetadata classMetadata) {
        Map<String, TabMetadata> tabMetadataMap = classMetadata.getReadonlyTabMetadataMap();
        for (Map.Entry<String, TabMetadata> tabMetadataEntry : tabMetadataMap.entrySet()) {
            TabMetadata tabMetadata = tabMetadataEntry.getValue();
            TabEdo tabDto = createTabEdo(tabMetadata);
            classEdo.addTab(tabDto);
        }

//        Map<String, GroupMetadata> groupMetadataMap = classMetadata.getGroupMetadataMap();
//        for (Map.Entry<String, GroupMetadata> entry : groupMetadataMap.entrySet()){
//            if(intermediate.groupMetadataMapMerged.containsKey(entry.getKey())){
//                LOGGER.warn("Group with name '{}' already exist.", entry.getKey());
//            }
//            intermediate.groupMetadataMapMerged.put(entry.getKey(), entry.getValue());
//        }

        Map<String, FieldMetadata> fieldMetadataMap = classMetadata.getFieldMetadataMap();
        for (Map.Entry<String, FieldMetadata> entry : fieldMetadataMap.entrySet()) {
            FieldMetadata fieldMetadata = entry.getValue();
            String tabName = fieldMetadata.getTabName();
            String groupName = fieldMetadata.getGroupName();

            FieldEdo fieldEdo = createFieldEdo(fieldMetadata);

            TabEdo tabEdo = classEdo.getTab(tabName);
            GroupEdo groupEdo = tabEdo.getGroupWithName(groupName);
            if (groupEdo == null) {
                groupEdo = createGroupEdo(classMetadata.getGroupMetadataMap().get(groupName));
                tabEdo.addGroup(groupEdo);
            }
            groupEdo.addField(fieldEdo);
            if (fieldMetadata.showOnGrid() || fieldMetadata.isId()) {
                classEdo.addGridField(fieldEdo);
            }

            if(fieldMetadata.isId()){
                classEdo.setIdField(fieldEdo);
            }
        }
    }
 /*
    public static void makeGridFields(ClassEdo classEdo){
        for (TabEdo tabEdo : classEdo.getTabs()){
            for(GroupEdo groupEdo : tabEdo.getGroups()){
                for(FieldEdo fieldEdo : groupEdo.getFields()){
                    fieldEdo.
                }
            }
        }
    }*/

    private static ClassEdo createClassEdo(ClassMetadata classMetadata) {
        ClassEdo classEdo = new ClassEdo();
        copyNamesAndOrder(classMetadata, classEdo);
        return classEdo;
    }

    private static TabEdo createTabEdo(TabMetadata tabMetadata) {
        TabEdo tabEdo = new TabEdo();
        copyNamesAndOrder(tabMetadata, tabEdo);
        return tabEdo;
    }

    public static GroupEdo createGroupEdo(GroupMetadata groupMetadata) {
        GroupEdo groupEdo = new GroupEdo();
        copyNamesAndOrder(groupMetadata, groupEdo);
        return groupEdo;
    }

    public static FieldEdo createFieldEdo(FieldMetadata fieldMetadata) {
        FieldEdo fieldEdo = new FieldEdo();
        copyNamesAndOrder(fieldMetadata, fieldEdo);
        fieldEdo.setVisibility(fieldMetadata.getVisibility());
        fieldEdo.setNameField(fieldMetadata.isNameField());
        fieldEdo.setFieldType(fieldMetadata.getFieldType());
        return fieldEdo;
    }

    private static void copyNamesAndOrder(FriendlyMetadata friendlyMetadataFrom, FriendlyEdo friendlyEdoTo) {
        friendlyEdoTo.setFriendlyName(friendlyMetadataFrom.getFriendlyName())
                .setName(friendlyMetadataFrom.getName())
                .setOrder(friendlyMetadataFrom.getOrder());
    }
}
