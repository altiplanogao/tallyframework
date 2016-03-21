package com.taoswork.tallybook.descriptor.dataio.in.translator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.descriptor.dataio.in.Entity;
import com.taoswork.tallybook.descriptor.dataio.in.ForeignEntityRef;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.IClassMetaAccess;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.classmetadata.ClassMetaUtils;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BaseNonCollectionFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BasePrimitiveFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.basic.ExternalForeignEntityFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.basic.ForeignEntityFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.embedded.EmbeddedFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.list.ListFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.map.MapFieldMeta;
import org.apache.commons.beanutils.ConvertUtilsBean2;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Translate String map into Persistable object
 */
public class EntityTranslator implements IEntityTranslator{
    private static Logger LOGGER = LoggerFactory.getLogger(EntityTranslator.class);

    private static ObjectMapper objectMapper = new ObjectMapper();
    private final ConvertUtilsBean2 convertUtils;

    public EntityTranslator() {
        convertUtils = makeConvertUtils();
    }

    protected ConvertUtilsBean2 makeConvertUtils(){
        return new ConvertUtilsBean2();
    }

    protected Map<String, Object> buildPropertyTree(Map<String, String> entityProperties) {
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

    /**
     * @param source
     * @param id,    id value of the entity
     * @return
     */
    public Persistable translate(final IClassMetaAccess cmAccess, Entity source, String id) throws TranslateException {
        Persistable instance = null;
        try {
            final Class entityClass = (source.getType());
            final Persistable tempInstance = (Persistable) entityClass.newInstance();
            final IClassMeta classMeta = cmAccess.getClassMeta(entityClass, false);

            final Map<String, String> propsAsMap = source.getProps();
            final Map<String, Object> propsAsTree = buildPropertyTree(propsAsMap);
            fillEntity(tempInstance, classMeta, propsAsTree);

            if (StringUtils.isNotEmpty(id)) {
                String idFieldName = classMeta.getIdField().getName();
                ClassMetaUtils.setPrimitiveField(classMeta, idFieldName, tempInstance, id);
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
        String translatingField = "";
        try {
            for (Map.Entry<String, Object> entry : entityAsTree.entrySet()) {
                String fieldKey = entry.getKey();
                translatingField = fieldKey;
                IFieldMeta fieldMeta = classMeta.getFieldMeta(fieldKey);
                if (fieldMeta != null) {
                    if (fieldMeta instanceof BasePrimitiveFieldMeta) {
                        String fieldValue = (String) entry.getValue();
                        BasePrimitiveFieldMeta bpfm = (BasePrimitiveFieldMeta)fieldMeta;
                        Field field = fieldMeta.getField();
                        final Object val = StringUtils.isEmpty(fieldValue) ? null : bpfm.getValueFromString(fieldValue);
                        field.set(instance, val);
                    } else if (fieldMeta instanceof BaseNonCollectionFieldMeta){
                        if (fieldMeta instanceof ForeignEntityFieldMeta) {
                            String valStr = (String) entry.getValue();
                            final Persistable valObj = workOutForeignEntity(classMeta, (ForeignEntityFieldMeta) fieldMeta, valStr);
                            Field field = fieldMeta.getField();
                            field.set(instance, valObj);
                        } else if (fieldMeta instanceof ExternalForeignEntityFieldMeta) {
                            String valStr = (String) entry.getValue();
                            final Object valObj;
                            if (StringUtils.isEmpty(valStr)) {
                                valObj = null;
                            } else {
                                ExternalForeignEntityFieldMeta effm = (ExternalForeignEntityFieldMeta) fieldMeta;
                                ForeignEntityRef ref = objectMapper.readValue(valStr, ForeignEntityRef.class);
                                valObj = convertUtils.convert(ref.id, fieldMeta.getFieldClass());
                            }
                            Field field = fieldMeta.getField();
                            field.set(instance, valObj);
                        } else if (fieldMeta instanceof EmbeddedFieldMeta) {
                            EmbeddedFieldMeta embeddedFieldMeta = (EmbeddedFieldMeta) fieldMeta;
                            Field field = fieldMeta.getField();
                            Object embeddObj = field.get(instance);
                            if (embeddObj == null) {
                                embeddObj = embeddedFieldMeta.getFieldClass().newInstance();
                                field.set(instance, embeddObj);
                            }
                            fillEntity(embeddObj, embeddedFieldMeta.getClassMetadata(), (Map<String, Object>) entry.getValue());
                        } else if(fieldMeta instanceof ListFieldMeta) {
                            throw new TranslateException("Field not handled with name: " + fieldKey);

                        } else if(fieldMeta instanceof MapFieldMeta) {
                            throw new TranslateException("Field not handled with name: " + fieldKey);

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

    private Persistable workOutForeignEntity(IClassMeta classMeta, ForeignEntityFieldMeta fieldMeta, String valStr) throws IOException, InstantiationException, IllegalAccessException {
        final Persistable valObj;
        if (StringUtils.isEmpty(valStr)) {
            valObj = null;
        } else {
            ForeignEntityFieldMeta foreignEntityFieldMeta = fieldMeta;
            Class entityType = foreignEntityFieldMeta.getTargetType();
            ForeignEntityRef ref = objectMapper.readValue(valStr, ForeignEntityRef.class);
            valObj = (Persistable) entityType.newInstance();

            IClassMeta fcm = classMeta.getReferencingClassMeta(entityType);
            ClassMetaUtils.setPrimitiveField(fcm, fcm.getIdFieldName(), valObj, ref.id);
        }
        return valObj;
    }

}
