package com.taoswork.tallybook.dynamic.dataservice.core.validate.field;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.FieldMetadata;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;
import com.taoswork.tallybook.general.datadomain.support.entity.validation.error.EntityValidationErrors;
import com.taoswork.tallybook.general.datadomain.support.entity.validation.error.FieldValidationErrors;
import com.taoswork.tallybook.general.datadomain.support.entity.validation.error.ValidationError;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by Gao Yuan on 2015/9/29.
 */
public class FieldValidatorManager {

    private static class FieldTypeType{
        public final FieldType type;
        public final String clz;

        public FieldTypeType(FieldType type){
            this.type = type;
            this.clz = null;
        }

        public FieldTypeType(Class clz) {
            this.type = null;
            this.clz = clz.getName();
        }

        public FieldTypeType(String clz) {
            this.type = null;
            this.clz = clz;
        }

        public FieldTypeType(FieldType type, Class clz) {
            this.type = type;
            this.clz = clz.getName();
        }

        public FieldTypeType(FieldType type, String clz) {
            this.type = type;
            this.clz = clz;
        }

        public FieldTypeType(IFieldValidator validator){
            this(validator.supportedFieldType(), validator.supportedFieldClass());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (!(o instanceof FieldTypeType)) return false;

            FieldTypeType that = (FieldTypeType) o;

            return new EqualsBuilder()
                .append(type, that.type)
                .append(clz, that.clz)
                .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                .append(type)
                .append(clz)
                .toHashCode();
        }

        @Override
        public String toString() {
            return "" + clz + ":" + type;
        }
    }

    //
    private MultiValueMap<FieldTypeType, IFieldValidator> typedFieldValidators = new LinkedMultiValueMap<FieldTypeType, IFieldValidator>();

    //E
    private Map<FieldTypeType, Set<IFieldValidator>> cachedFieldValidators = new HashMap<FieldTypeType, Set<IFieldValidator>>();

    public FieldValidatorManager addValidators(IFieldValidator... fieldValidators){
        for (IFieldValidator validator : fieldValidators){
            this.addValidator(validator);
        }
        return this;
    }

    public FieldValidatorManager addValidator(IFieldValidator fieldValidator) {
        if (fieldValidator != null) {
            FieldTypeType typeType = new FieldTypeType(fieldValidator);
            typedFieldValidators.add(typeType, fieldValidator);
        }
        return this;
    }

    public Collection<IFieldValidator> getValidators(FieldMetadata fieldMetadata) {
        return this.getValidators(fieldMetadata.getFieldType(), fieldMetadata.getFieldClass());
    }

    public Collection<IFieldValidator> getValidators(FieldType type, Class clz){
        FieldTypeType typeType = new FieldTypeType(type, clz);
        Set<IFieldValidator> cache = cachedFieldValidators.getOrDefault(typeType, null);
        if(cache == null){
            cache = new HashSet<IFieldValidator>();
            FieldTypeType typeOnlyType = new FieldTypeType(type);
            FieldTypeType typeOnlyClz = new FieldTypeType(clz);

            List<IFieldValidator> typed = typedFieldValidators.getOrDefault(typeType, null);
            List<IFieldValidator> typedT = typedFieldValidators.getOrDefault(typeOnlyType, null);
            List<IFieldValidator> typedC = typedFieldValidators.getOrDefault(typeOnlyClz, null);

            if(typed != null)
                cache.addAll(typed);
            if(typedT != null)
                cache.addAll(typedT);
            if(typedC != null)
                cache.addAll(typedC);

            cachedFieldValidators.put(typeType, cache);
        }
        return cache;
    }

    public void validate(Persistable entity, ClassMetadata classMetadata, EntityValidationErrors entityErrors) throws IllegalAccessException {
        List<String> fieldFriendlyNames = new ArrayList<String>();
        for (Map.Entry<String, FieldMetadata> fieldMetadataEntry : classMetadata.getReadonlyFieldMetadataMap().entrySet()){
            String fieldName = fieldMetadataEntry.getKey();
            FieldMetadata fieldMetadata = fieldMetadataEntry.getValue();
            Field field = fieldMetadata.getField();
            Object fieldValue = field.get(entity);
            FieldValidationErrors fieldError = this.validate(fieldMetadata, fieldValue);
            if(!fieldError.isValid()){
                fieldFriendlyNames.add(fieldMetadata.getFriendlyName());
                entityErrors.addFieldErrors(fieldError);
            }
        }
        entityErrors.appendErrorFieldsNames(fieldFriendlyNames);
    }

    public FieldValidationErrors validate(FieldMetadata fieldMetadata, Object fieldValue) {
        String fieldName = fieldMetadata.getName();
        FieldValidationErrors fieldValidationErrors = new FieldValidationErrors(fieldName);
        Collection<IFieldValidator> validators = this.getValidators(fieldMetadata);
        for(IFieldValidator validator : validators){
            ValidationError validateError = validator.validate(fieldMetadata, fieldValue);
            fieldValidationErrors.appendError(validateError);
        }

        return fieldValidationErrors;
    }

}
