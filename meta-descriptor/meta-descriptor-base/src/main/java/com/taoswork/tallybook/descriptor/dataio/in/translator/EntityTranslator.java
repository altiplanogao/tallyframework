package com.taoswork.tallybook.descriptor.dataio.in.translator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.datadomain.base.presentation.typed.DateMode;
import com.taoswork.tallybook.descriptor.dataio.in.Entity;
import com.taoswork.tallybook.descriptor.dataio.in.ForeignEntityRef;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.IClassMetaAccess;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.basic.DateFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.basic.ExternalForeignEntityFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.basic.ForeignEntityFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.embedded.EmbeddedFieldMeta;
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

    public EntityTranslator() {
    }

    protected Map<String, Object> buildEntityPropertyTree(Map<String, String> entityProperties) {
        Map<String, Object> result = new HashMap<String, Object>();
        for (Map.Entry<String, String> entry : entityProperties.entrySet()) {
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

    protected abstract IClassMetaAccess getClassMetaAccess();

    /**
     * @param source
     * @param id,    id value of the entity
     * @return
     */
    public Persistable translate(Entity source, String id) throws TranslateException {
        Persistable instance = null;
        final IClassMetaAccess classMetaAccess = getClassMetaAccess();
        try {
            final Class entityClass = (source.getType());
            final Persistable tempInstance = (Persistable) entityClass.newInstance();
            final IClassMeta classMeta = classMetaAccess.getClassMeta(entityClass, false);

            final Map<String, String> propsAsMap = source.getProps();
            final Map<String, Object> propsAsTree = buildEntityPropertyTree(propsAsMap);
            fillEntity(tempInstance, classMeta, propsAsTree);

            if (StringUtils.isNotEmpty(id)) {
                String idFieldName = classMeta.getIdField().getName();
                BeanWrapperImpl beanWrapper = new BeanWrapperImpl();
                beanWrapper.setWrappedInstance(tempInstance);
                beanWrapper.setPropertyValue(idFieldName, id);
            }

            instance = tempInstance;
        } catch (InstantiationException e) {
            throw new TranslateException("InstantiationException for entity creation: " + source.getType());
        } catch (IllegalAccessException e) {
            throw new TranslateException("IllegalAccessException for entity creation: " + source.getType());
        }
        return instance;
    }

    private void fillEntity(Object instance, final IClassMeta classMeta, Map<String, Object> entityAsTree) throws TranslateException {
        BeanWrapperImpl instanceBean = new BeanWrapperImpl();
        instanceBean.setWrappedInstance(instance);
        String translatingField = "";
        try {
            for (Map.Entry<String, Object> entry : entityAsTree.entrySet()) {
                String fieldKey = entry.getKey();
                translatingField = fieldKey;
                IFieldMeta fieldMeta = classMeta.getFieldMeta(fieldKey);
                if (fieldMeta != null) {
                    if (fieldMeta.isPrimitiveField()) {
                        if (fieldMeta instanceof DateFieldMeta) {
                            Field field = fieldMeta.getField();
                            String fieldValue = (String) entry.getValue();
                            if (StringUtils.isEmpty(fieldValue)) {
                                field.set(instance, null);
                            } else {
                                DateFieldMeta dateFieldMeta = (DateFieldMeta) fieldMeta;
                                DateMode model = dateFieldMeta.getMode();
                                boolean useJavaDate = dateFieldMeta.isUseJavaDate();
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
                        if (fieldMeta instanceof ForeignEntityFieldMeta) {
                            String valStr = (String) entry.getValue();
                            Persistable valObj = null;
                            if (StringUtils.isEmpty(valStr)) {
                                valObj = null;
                            } else {
                                ForeignEntityFieldMeta foreignEntityFieldMeta = (ForeignEntityFieldMeta) fieldMeta;
                                Class entityType = foreignEntityFieldMeta.getTargetType();
                                ForeignEntityRef ref = objectMapper.get().readValue(valStr, ForeignEntityRef.class);
                                valObj = (Persistable) entityType.newInstance();

                                IClassMeta foreignClassMetadata = classMeta.getReferencingClassMeta(entityType);
                                BeanWrapperImpl fkBean = new BeanWrapperImpl(valObj);
                                fkBean.setPropertyValue(new PropertyValue(foreignClassMetadata.getIdFieldName(), ref.id));
                            }
                            Field field = fieldMeta.getField();
                            field.set(instance, valObj);
                        } else if (fieldMeta instanceof ExternalForeignEntityFieldMeta) {
                            String valStr = (String) entry.getValue();
                            Object valObj = null;
                            if (StringUtils.isEmpty(valStr)) {
                                valObj = null;
                            } else {
                                ExternalForeignEntityFieldMeta effm = (ExternalForeignEntityFieldMeta) fieldMeta;
                                ForeignEntityRef ref = objectMapper.get().readValue(valStr, ForeignEntityRef.class);
                                valObj = ref.id;
                                instanceBean.setPropertyValue(new PropertyValue(fieldKey, valObj));
                            }
                        } else if (fieldMeta instanceof EmbeddedFieldMeta) {
                            EmbeddedFieldMeta embeddedFieldMeta = (EmbeddedFieldMeta) fieldMeta;
                            Field field = fieldMeta.getField();
                            Object embeddObj = field.get(instance);
                            if (embeddObj == null) {
                                embeddObj = embeddedFieldMeta.getFieldClass().newInstance();
                                field.set(instance, embeddObj);
                            }
                            fillEntity(embeddObj, embeddedFieldMeta.getClassMetadata(), (Map<String, Object>) entry.getValue());
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
//        DynamicEntityMetadataAccess entityMetaAccess = this.getDynamicEntityMetadataAccess();
//        Entity entity =null;
//        try{
//            Entity tempEntity = new Entity();
//            Class entityClass = instance.getClass();
//            tempEntity.setType(entityClass);
//            if(ceilingType != null){
//                Class ceilingClz = Class.forName(ceilingType);
//                if(ceilingClz.isAssignableFrom(entityClass)){
//                    tempEntity.setCeilingType(ceilingType);
//                }
//            }
//            IClassMeta classMeta = entityMetaAccess.getMutableClassMetadata(entityClass, false);
//            for(classMeta.getReadonlyFieldMetaMap())
//            entity = tempEntity;
//        }catch (Exception e){
//
//        }
//        return entity;
//    }

}
