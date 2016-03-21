package com.taoswork.tallybook.descriptor.metadata.fieldmetadata.basic;

import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.datadomain.base.presentation.typed.PresentationForeignKey;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BaseNonCollectionFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BasicFieldMetaObject;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.IFieldMetaSeed;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.IFacet;

import java.lang.reflect.Field;
import java.util.Collection;

import static com.taoswork.tallybook.datadomain.base.presentation.FieldType.FOREIGN_KEY;

public final class ForeignEntityFieldMeta
        extends BaseNonCollectionFieldMeta {
    private final Class declareType;
    private final Class targetType;
    private final String idField;
    private final String displayField;

    public ForeignEntityFieldMeta(BasicFieldMetaObject bfmo,
                                  Class declareType, Class targetType,
                                  String idField, String displayField) {
        super(bfmo);
        this.declareType = declareType;
        this.targetType = targetType;
        this.idField = idField;
        this.displayField = displayField;
    }

    public Class getDeclareType() {
        return declareType;
    }

    public Class getTargetType() {
        return targetType;
    }

    public String getIdField() {
        return idField;
    }

    public String getDisplayField() {
        return displayField;
    }

//    @Override
//    public boolean isPrimitiveField() {
//        return false;
//    }

    @Override
    protected FieldType overrideUnknownFieldType() {
        return FOREIGN_KEY;
    }

    @Override
    public int gatherReferencingTypes(Collection<Class> collection) {
        collection.add(getTargetType());
        return 1 + super.gatherReferencingTypes(collection);
    }

    public static class Seed extends Facet implements IFieldMetaSeed {
        public final Class declaredType;

        public Seed(Class declaredType, Facet facet) {
            super(facet);
            this.declaredType = declaredType;
        }

        @Override
        public IFieldMeta makeFieldMeta(BasicFieldMetaObject bfmo) {
            return new ForeignEntityFieldMeta(bfmo, declaredType, this.targetEntity, idField, displayField);
        }
    }

    /**
     * Created by Gao Yuan on 2016/2/19.
     */
    public static class Facet implements IFacet {
        protected Class targetEntity;
        protected String idField;
        protected String displayField;

        public Facet(Facet facet){
            this.targetEntity = facet.targetEntity;
            this.idField = facet.idField;
            this.displayField = facet.displayField;
        }

        public Facet(Class targetEntity) {
            this.targetEntity = targetEntity;
            this.displayField = "name";
            this.idField = "id";
        }

        public Facet(Class targetEntity, String idField, String displayField) {
            this.targetEntity = targetEntity;
            this.idField = idField;
            this.displayField = displayField;
        }

        public Class getTargetEntity() {
            return targetEntity;
        }

        public void setTargetEntity(Class targetEntity) {
            this.targetEntity = targetEntity;
        }

        public String getIdField() {
            return idField;
        }

        public void setIdField(String idField) {
            this.idField = idField;
        }

        public String getDisplayField() {
            return displayField;
        }

        public void setDisplayField(String displayField) {
            this.displayField = displayField;
        }

        public static Facet makeFacetByAnnotation(Field field){
            PresentationForeignKey pfk = field.getDeclaredAnnotation(PresentationForeignKey.class);
            if(pfk == null)
                return null;
            final String nameField = pfk.displayField();
            final String idField = pfk.idField();
            Class targetType = pfk.targetEntity();
            if (targetType.equals(void.class)) {
                targetType = field.getType();
            }
            return new Facet(targetType, idField, nameField);
        }

    }
}
