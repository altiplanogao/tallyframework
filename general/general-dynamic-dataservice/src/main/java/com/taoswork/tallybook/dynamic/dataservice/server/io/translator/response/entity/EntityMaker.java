package com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response.entity;

import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.field.FieldInfo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.easy.grid.EntityGridInfo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.edo.ClassEdo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.edo.FieldEdo;
import com.taoswork.tallybook.dynamic.dataservice.entity.description.descriptor.base.OrderedName;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.utils.NativeFieldHelper;
import com.taoswork.tallybook.dynamic.dataservice.server.dto.entity.Entity;
import com.taoswork.tallybook.dynamic.dataservice.server.dto.entity.Property;
import com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response.entity.field.FieldValueProcessorManager;
import com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response.entity.field.IFieldValueProcessor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/1.
 */
public class EntityMaker {
    public static <T> Entity makeGridEntity(T entityObj, EntityGridInfo entityGridInfo) {
        Entity entity = new Entity();
        Class entityClz = entityObj.getClass();
        entity.setType(entityClz.getName());
        //     List<Property> properties = entity.getRWProperties();
        try {
            for (FieldInfo fieldEdo : entityGridInfo.getFields()) {
                String fieldName = fieldEdo.getName();
                Property property = new Property();
                property.setName(fieldName);
                Field nativeField = NativeFieldHelper.getSingleField(entityClz, fieldName);
                nativeField.setAccessible(true);
                IFieldValueProcessor fieldValueProcessor = FieldValueProcessorManager.instance().getProperProcessor(fieldEdo);
                Object value = nativeField.get(entityObj);
                String valueInStr = fieldValueProcessor.getStringValue(value);
                property.setValue(valueInStr);

                entity.putProperty(property);

                if(fieldEdo.isNameField()){
                    entity.setId(valueInStr);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return entity;
    }

    public static List<Entity> makeGridEntityList(List<?> entityObjArray, EntityGridInfo entityGridInfo) {
        List<Entity> entities = new ArrayList<Entity>();
        for (Object entityObj : entityObjArray) {
            Entity entity = makeGridEntity(entityObj, entityGridInfo);
            entities.add(entity);
        }
        return entities;
    }
}