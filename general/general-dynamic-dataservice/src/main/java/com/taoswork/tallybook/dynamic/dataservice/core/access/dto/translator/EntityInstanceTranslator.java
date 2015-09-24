package com.taoswork.tallybook.dynamic.dataservice.core.access.dto.translator;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.Entity;
import com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.DynamicEntityMetadataAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyValue;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/9/23.
 */
public abstract class EntityInstanceTranslator {
    private static Logger LOGGER = LoggerFactory.getLogger(EntityInstanceTranslator.class);

    protected ThreadLocal<BeanWrapperImpl> beanWrapperThreadLocal = new ThreadLocal<BeanWrapperImpl>(){
        @Override
        protected BeanWrapperImpl initialValue() {
            return new BeanWrapperImpl();
        }
    };

    public EntityInstanceTranslator(){}

    protected abstract DynamicEntityMetadataAccess getDynamicEntityMetadataAccess();

    public Serializable convert(Entity source) {
        Serializable instance = null;
        DynamicEntityMetadataAccess entityMetadataAccess = this.getDynamicEntityMetadataAccess();
        try {
            Class entityClass = Class.forName(source.getEntityType());
            Serializable tempInstance = (Serializable)entityClass.newInstance();
            BeanWrapperImpl instanceBean = beanWrapperThreadLocal.get();
            instanceBean.setWrappedInstance(tempInstance);
            ClassMetadata classMetadata = entityMetadataAccess.getClassMetadata(entityClass, false);

            for(Map.Entry<String, String> entry : source.getEntity().entrySet()){
                String fieldKey = entry.getKey();
                if(classMetadata.hasField(fieldKey)){
                    PropertyValue pv = new PropertyValue(fieldKey, entry.getValue());
                    instanceBean.setPropertyValue(pv);
                }else {
                    LOGGER.error("Field with name '{}' not handled, please check", fieldKey);
                }
            }

            instance = tempInstance;
        } catch (ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        } catch (InstantiationException e) {
            LOGGER.error(e.getMessage());
        } catch (IllegalAccessException e) {
            LOGGER.error(e.getMessage());
        }
        return instance;
    }

//    public Entity instanceToEntity(String ceilingType, Serializable instance){
//        DynamicEntityMetadataAccess entityMetadataAccess = this.getDynamicEntityMetadataAccess();
//        Entity entity =null;
//        try{
//            Entity tempEntity = new Entity();
//            Class entityClass = instance.getClass();
//            tempEntity.setEntityType(entityClass);
//            if(ceilingType != null){
//                Class ceilingClz = Class.forName(ceilingType);
//                if(ceilingClz.isAssignableFrom(entityClass)){
//                    tempEntity.setEntityCeilingType(ceilingType);
//                }
//            }
//            ClassMetadata classMetadata = entityMetadataAccess.getClassMetadata(entityClass, false);
//            for(classMetadata.getReadonlyFieldMetadataMap())
//            entity = tempEntity;
//        }catch (Exception e){
//
//        }
//        return entity;
//    }

}
