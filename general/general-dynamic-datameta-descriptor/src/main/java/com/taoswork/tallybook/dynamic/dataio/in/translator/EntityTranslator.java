package com.taoswork.tallybook.dynamic.dataio.in.translator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taoswork.tallybook.dynamic.dataio.in.ForeignEntityRef;
import com.taoswork.tallybook.dynamic.datameta.metadata.IClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.IClassMetadataAccess;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.embedded.EmbeddedFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed.DateFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed.ExternalForeignEntityFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed.ForeignEntityFieldMetadata;
import com.taoswork.tallybook.dynamic.dataio.in.Entity;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.DateMode;
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
public abstract class EntityTranslator {
    private static Logger LOGGER = LoggerFactory.getLogger(EntityTranslator.class);

    private ThreadLocal<ObjectMapper> objectMapper = ThreadLocalHelper.createThreadLocal(ObjectMapper.class);
//    protected ThreadLocal<BeanWrapperImpl> beanWrapperThreadLocal = new ThreadLocal<BeanWrapperImpl>(){
//        @Override
//        protected BeanWrapperImpl initialValue() {
//            return new BeanWrapperImpl();
//        }
//    };

    public EntityTranslator(){}

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

    protected abstract IClassMetadataAccess getClassMetadataAccess();

    /**
     *
     * @param source
     * @param id, id value of the entity
     * @return
     */
    public Persistable translate(Entity source, String id) throws TranslateException {
        Persistable instance = null;
        final IClassMetadataAccess classMetadataAccess = getClassMetadataAccess();
        try {
            final Class entityClass = (source.getEntityType());
            final Persistable tempInstance = (Persistable) entityClass.newInstance();
            final IClassMetadata classMetadata = classMetadataAccess.getClassMetadata(entityClass, false);

            final Map<String, String> propsAsMap = source.getProps();
            final Map<String, Object> propsAsTree = buildEntityPropertyTree(propsAsMap);
            fillEntity(tempInstance, classMetadata, propsAsTree);

            if (StringUtils.isNotEmpty(id)) {
                String idFieldName = classMetadata.getIdField().getName();
                BeanWrapperImpl beanWrapper = new BeanWrapperImpl();
                beanWrapper.setWrappedInstance(tempInstance);
                beanWrapper.setPropertyValue(idFieldName, id);
            }

            instance = tempInstance;
        } catch (InstantiationException e) {
            throw new TranslateException("InstantiationException for entity creation: " + source.getEntityType());
        } catch (IllegalAccessException e) {
            throw new TranslateException("IllegalAccessException for entity creation: " + source.getEntityType());
        }
        return instance;
    }

    private void fillEntity(Object instance, final IClassMetadata classMetadata, Map<String, Object> entityAsTree) throws TranslateException {
        BeanWrapperImpl instanceBean = new BeanWrapperImpl();
        instanceBean.setWrappedInstance(instance);
        String translatingField = "";
        try {
            for (Map.Entry<String, Object> entry : entityAsTree.entrySet()) {
                String fieldKey = entry.getKey();
                translatingField = fieldKey;
                IFieldMetadata fieldMetadata = classMetadata.getFieldMetadata(fieldKey);
                if (fieldMetadata != null) {
                    if (fieldMetadata.isPrimitiveField()) {
                        if (fieldMetadata instanceof DateFieldMetadata) {
                            Field field = fieldMetadata.getField();
                            String fieldValue = (String) entry.getValue();
                            if (StringUtils.isEmpty(fieldValue)) {
                                field.set(instance, null);
                            } else {
                                DateFieldMetadata dateFieldMetadata = (DateFieldMetadata) fieldMetadata;
                                DateMode model = dateFieldMetadata.getMode();
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
                                ForeignEntityRef ref = objectMapper.get().readValue(valStr, ForeignEntityRef.class);
                                valObj = (Persistable)entityType.newInstance();

                                IClassMetadata foreignClassMetadata = classMetadata.getReferencingClassMetadata(entityType);
                                BeanWrapperImpl fkBean = new BeanWrapperImpl(valObj);
                                fkBean.setPropertyValue(new PropertyValue(foreignClassMetadata.getIdFieldName(), ref.id));
                            }
                            Field field = fieldMetadata.getField();
                            field.set(instance, valObj);
                        } else if (fieldMetadata instanceof ExternalForeignEntityFieldMetadata) {
                            String valStr = (String) entry.getValue();
                            Object valObj = null;
                            if (StringUtils.isEmpty(valStr)) {
                                valObj = null;
                            } else {
                                ExternalForeignEntityFieldMetadata effm = (ExternalForeignEntityFieldMetadata)fieldMetadata;
                                ForeignEntityRef ref = objectMapper.get().readValue(valStr, ForeignEntityRef.class);
                                valObj = ref.id;
                                instanceBean.setPropertyValue(new PropertyValue(fieldKey, valObj));
                            }
                        } else if (fieldMetadata instanceof EmbeddedFieldMetadata) {
                            EmbeddedFieldMetadata embeddedFieldMetadata = (EmbeddedFieldMetadata) fieldMetadata;
                            Field field = fieldMetadata.getField();
                            Object embeddObj = field.get(instance);
                            if (embeddObj == null) {
                                embeddObj = embeddedFieldMetadata.getFieldClass().newInstance();
                                field.set(instance, embeddObj);
                            }
                            fillEntity(embeddObj, embeddedFieldMetadata.getClassMetadata(), (Map<String, Object>) entry.getValue());
                        } else {
                            throw new TranslateException("Field not handled with name: " + fieldKey);
                        }
                    }
                } else {
                    throw new TranslateException("Field not handled with name: " + fieldKey);
                }
            }
            translatingField = "";
        } catch (IllegalAccessException e) {
            throw new TranslateException("Illegal Access to field: " + translatingField, e);
        } catch (IOException e) {
            throw new TranslateException("IOException to field: " + translatingField, e);
        } catch (InstantiationException e) {
            throw new TranslateException("InstantiationException to field: " + translatingField, e);
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
