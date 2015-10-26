package com.taoswork.tallybook.dynamic.dataservice.core.dataio.in.translator;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed.ForeignEntityFieldMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.dataio.in.Entity;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;
import com.taoswork.tallybook.general.solution.threading.ThreadLocalHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyValue;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/9/23.
 */
public abstract class EntityInstanceTranslator {
    private static Logger LOGGER = LoggerFactory.getLogger(EntityInstanceTranslator.class);

    private ThreadLocal<ObjectMapper> objectMapper = ThreadLocalHelper.createThreadLocal(ObjectMapper.class);
    protected ThreadLocal<BeanWrapperImpl> beanWrapperThreadLocal = new ThreadLocal<BeanWrapperImpl>(){
        @Override
        protected BeanWrapperImpl initialValue() {
            return new BeanWrapperImpl();
        }
    };

    public EntityInstanceTranslator(){}

    protected abstract DynamicEntityMetadataAccess getDynamicEntityMetadataAccess();

    /**
     *
     * @param source
     * @param id, id value of the entity
     * @return
     */
    public Persistable convert(Entity source, String id) throws ServiceException {
        Persistable instance = null;
        DynamicEntityMetadataAccess entityMetadataAccess = this.getDynamicEntityMetadataAccess();
        try {
            Class entityClass = (source.getEntityType());
            final Persistable tempInstance = (Persistable) entityClass.newInstance();
            BeanWrapperImpl instanceBean = beanWrapperThreadLocal.get();
            instanceBean.setWrappedInstance(tempInstance);
            ClassMetadata classMetadata = entityMetadataAccess.getClassMetadata(entityClass, false);

            for (Map.Entry<String, String> entry : source.getEntity().entrySet()) {
                String fieldKey = entry.getKey();
                IFieldMetadata fieldMetadata = classMetadata.getFieldMetadata(fieldKey);
                if (fieldMetadata != null) {
                    if (fieldMetadata.isPrimitiveField()) {
                        PropertyValue pv = new PropertyValue(fieldKey, entry.getValue());
                        instanceBean.setPropertyValue(pv);
                    } else {
                        if (fieldMetadata instanceof ForeignEntityFieldMetadata) {
                            String valKey = entry.getKey();
                            String valStr = entry.getValue();
                            Persistable valObj = null;
                            if (StringUtils.isEmpty(valStr)) {
                                valObj = null;
                            } else {
                                ForeignEntityFieldMetadata foreignEntityFieldMetadata = (ForeignEntityFieldMetadata) fieldMetadata;
                                Class entityType = foreignEntityFieldMetadata.getEntityType();
                                valObj = (Persistable) objectMapper.get().readValue(valStr, entityType);
                            }
                            Field field = fieldMetadata.getField();
                            field.set(tempInstance, valObj);

                        } else {
                            LOGGER.error("Field with name '{}' not handled, please check", fieldKey);
                        }
                    }
                } else {
                    LOGGER.error("Field with name '{}' not handled, please check", fieldKey);
                }
            }

            if (StringUtils.isNotEmpty(id)) {
                String idFieldName = classMetadata.getIdField().getName();
                instanceBean.setPropertyValue(idFieldName, id);
            }

            instance = tempInstance;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
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
