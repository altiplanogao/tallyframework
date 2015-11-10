package com.taoswork.tallybook.dynamic.dataservice.core.dataio.in.translator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taoswork.tallybook.dynamic.datameta.metadata.IClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.embedded.EmbeddedFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed.DateFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed.ForeignEntityFieldMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.dataio.in.Entity;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.DateModel;
import com.taoswork.tallybook.general.solution.threading.ThreadLocalHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyValue;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by Gao Yuan on 2015/9/23.
 */
public abstract class EntityInstanceTranslator {
    private static Logger LOGGER = LoggerFactory.getLogger(EntityInstanceTranslator.class);

    private ThreadLocal<ObjectMapper> objectMapper = ThreadLocalHelper.createThreadLocal(ObjectMapper.class);
//    protected ThreadLocal<BeanWrapperImpl> beanWrapperThreadLocal = new ThreadLocal<BeanWrapperImpl>(){
//        @Override
//        protected BeanWrapperImpl initialValue() {
//            return new BeanWrapperImpl();
//        }
//    };

    public EntityInstanceTranslator(){}

    protected abstract DynamicEntityMetadataAccess getDynamicEntityMetadataAccess();

    protected Map<String, Object> buildEntityPropertyTree(Map<String, String> entityProperties){
        Map<String, Object> result = new HashMap<String, Object>();
        for (Map.Entry<String, String> entry : entityProperties.entrySet()){
            String propertyName = entry.getKey();
            String propertyValue = entry.getValue();
            pushProperty(result, propertyName, propertyValue);
        }
        return result;
    }

    protected void pushProperty(Map<String, Object> target, String propertyKey, String propertyValue) {
        int dpos = propertyKey.indexOf(".");
        if (dpos <= 0) {
            target.put(propertyKey, propertyValue);
            return;
        }
        String currentPiece = propertyKey.substring(0, dpos);
        String remainPiece = propertyKey.substring(dpos + 1);
        Map<String, Object> subMap = (Map<String, Object>) target.computeIfAbsent(currentPiece, new Function<String, Map<String, Object>>() {
            @Override
            public Map<String, Object> apply(String s) {
                return new HashMap<String, Object>();
            }
        });
        pushProperty(subMap, remainPiece, propertyValue);
    }

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
            IClassMetadata classMetadata = entityMetadataAccess.getClassMetadata(entityClass, false);

            Map<String, String> entityAsMap = source.getEntity();
            Map<String, Object> entityAsTree = buildEntityPropertyTree(entityAsMap);
            fillEntity(tempInstance, classMetadata, entityAsTree);

            if (StringUtils.isNotEmpty(id)) {
                String idFieldName = classMetadata.getIdField().getName();
                BeanWrapperImpl beanWrapper = new BeanWrapperImpl();
                beanWrapper.setWrappedInstance(tempInstance);
                beanWrapper.setPropertyValue(idFieldName, id);
            }

            instance = tempInstance;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }
        return instance;
    }

    private void fillEntity(Object instance, IClassMetadata classMetadata, Map<String, Object> entityAsTree) throws IOException, IllegalAccessException, InstantiationException {
        BeanWrapperImpl instanceBean = new BeanWrapperImpl();
        instanceBean.setWrappedInstance(instance);
        for (Map.Entry<String, Object> entry : entityAsTree.entrySet()) {
            String fieldKey = entry.getKey();
            IFieldMetadata fieldMetadata = classMetadata.getFieldMetadata(fieldKey);
            if (fieldMetadata != null) {
                if (fieldMetadata.isPrimitiveField()) {
                    if (fieldMetadata instanceof DateFieldMetadata) {
                        Field field = fieldMetadata.getField();
                        String fieldValue = (String) entry.getValue();
                        if(StringUtils.isEmpty(fieldValue)){
                            field.set(instance, null);
                        } else {
                            DateFieldMetadata dateFieldMetadata = (DateFieldMetadata) fieldMetadata;
                            DateModel model = dateFieldMetadata.getModel();
                            boolean useJavaDate = dateFieldMetadata.isUseJavaDate();
                            Long ms = Long.parseLong(fieldValue);
                            Object val = ms;
                            if (useJavaDate) {
                                val = new Date(ms);
                            }
                            field.set(instance, val);
                        }
                    } else {
                        PropertyValue pv = new PropertyValue(fieldKey, entry.getValue());
                        instanceBean.setPropertyValue(pv);
                    }
                } else {
                    String valKey = entry.getKey();
                    if (fieldMetadata instanceof ForeignEntityFieldMetadata) {
                        String valStr = (String) entry.getValue();
                        Persistable valObj = null;
                        if (StringUtils.isEmpty(valStr)) {
                            valObj = null;
                        } else {
                            ForeignEntityFieldMetadata foreignEntityFieldMetadata = (ForeignEntityFieldMetadata) fieldMetadata;
                            Class entityType = foreignEntityFieldMetadata.getEntityType();
                            valObj = (Persistable) objectMapper.get().readValue(valStr, entityType);
                        }
                        Field field = fieldMetadata.getField();
                        field.set(instance, valObj);
                    }else if(fieldMetadata instanceof EmbeddedFieldMetadata){
                        EmbeddedFieldMetadata embeddedFieldMetadata = (EmbeddedFieldMetadata) fieldMetadata;
                        Field field = fieldMetadata.getField();
                        Object embeddObj = field.get(instance);
                        if(embeddObj == null){
                            embeddObj = embeddedFieldMetadata.getFieldClass().newInstance();
                            field.set(instance, embeddObj);
                        }
                        fillEntity(embeddObj, embeddedFieldMetadata.getClassMetadata(), (Map<String, Object>) entry.getValue());
                    } else {
                        LOGGER.error("Field with name '{}' not handled, please check", fieldKey);
                    }
                }
            } else {
                LOGGER.error("Field with name '{}' not handled, please check", fieldKey);
            }
        }
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
//            IClassMetadata classMetadata = entityMetadataAccess.getMutableClassMetadata(entityClass, false);
//            for(classMetadata.getReadonlyFieldMetadataMap())
//            entity = tempEntity;
//        }catch (Exception e){
//
//        }
//        return entity;
//    }

}
