package com.taoswork.tallybook.descriptor.metadata.fieldmetadata.basic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BaseNonCollectionFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BasicFieldMetaObject;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.IFieldMetaSeed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Collection;

public final class ExternalForeignEntityFieldMeta
        extends BaseNonCollectionFieldMeta {
    private final static Logger LOGGER = LoggerFactory.getLogger(ExternalForeignEntityFieldMeta.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    private final Class declareType;
    private final Class targetType;

    private final String theDataFieldName;

    private final String idProperty;
    private final String displayProperty;

    private transient Field entityField;

    public ExternalForeignEntityFieldMeta(BasicFieldMetaObject bfmo,
                                          Class declareType, Class targetType,
                                          String theDataFieldName,
                                          String idProperty, String displayProperty) {
        super(bfmo);
        this.declareType = declareType;
        this.targetType = targetType;
        this.theDataFieldName = theDataFieldName;
        this.idProperty = idProperty;
        this.displayProperty = displayProperty;
    }

    @Override
    protected FieldType overrideUnknownFieldType() {
        return FieldType.EXTERNAL_FOREIGN_KEY;
    }

    public Class getDeclareType() {
        return declareType;
    }

    public Class getTargetType() {
        return targetType;
    }

    public String getTheDataFieldName() {
        return theDataFieldName;
    }
//
//    @Override
//    public boolean isPrimitiveField() {
//        return false;
//    }

    public Field getEntityField() {
        synchronized (this) {
            if (this.entityField == null) {
                try {
                    String declaringClassName = this.basicFieldMetaObject.getDeclaringClassName();
                    Class ownerClz = Class.forName(declaringClassName);
                    Field field = ownerClz.getDeclaredField(this.theDataFieldName);
                    field.setAccessible(true);
                    this.entityField = (field);
                } catch (ClassNotFoundException e) {
                    LOGGER.error("Field declaring class not found");
                    throw new RuntimeException(e);
                } catch (NoSuchFieldException e) {
                    LOGGER.error("Field not found");
                    throw new RuntimeException(e);
                }
            }
            return this.entityField;
        }
    }

    public String getIdProperty() {
        return idProperty;
    }

    public String getDisplayProperty() {
        return displayProperty;
    }

    @Override
    public int gatherReferencingTypes(Collection<Class> collection) {
        collection.add(getTargetType());
        return 1 + super.gatherReferencingTypes(collection);
    }

    public static class Seed implements IFieldMetaSeed {
        public final String theDataField;
        public final Class declaredType;
        public final Class targetType;
        public final String displayField;
        public final String idProperty;

        public Seed(String theDataField, Class declaredType, Class targetType, String idProperty, String displayField) {
            this.declaredType = declaredType;
            this.theDataField = theDataField;
            this.targetType = targetType;
            this.idProperty = idProperty;
            this.displayField = displayField;
        }

        @Override
        public IFieldMeta makeFieldMeta(BasicFieldMetaObject bfmo) {
            return new ExternalForeignEntityFieldMeta(bfmo,
                    declaredType, targetType, theDataField,
                    idProperty, displayField);
        }

    }
}
