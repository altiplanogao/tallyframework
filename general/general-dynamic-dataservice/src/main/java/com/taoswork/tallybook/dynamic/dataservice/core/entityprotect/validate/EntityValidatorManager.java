package com.taoswork.tallybook.dynamic.dataservice.core.entityprotect.validate;

import com.taoswork.tallybook.dynamic.datameta.metadata.IClassMetadata;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;
import com.taoswork.tallybook.general.datadomain.support.entity.validation.IEntityValidator;
import com.taoswork.tallybook.general.datadomain.support.entity.validation.error.EntityValidationErrors;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

public class EntityValidatorManager implements EntityValidator {
    private final static FakeValidator fakeValidator = new FakeValidator();
    private final ConcurrentMap<String, IEntityValidator> validatorCache = new ConcurrentHashMap<String, IEntityValidator>();

    private IEntityValidator getValidator(String validatorName) {
        IEntityValidator validator = validatorCache.computeIfAbsent(validatorName, new Function<String, IEntityValidator>() {
            @Override
            public IEntityValidator apply(String s) {
                try {
                    IEntityValidator validator = (IEntityValidator) Class.forName(s).newInstance();
                    return validator;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return fakeValidator;
            }
        });
        if (validator == fakeValidator)
            return null;
        return validator;
    }

    @Override
    public void validate(Persistable entity, IClassMetadata classMetadata, EntityValidationErrors entityValidationErrors) {
        Collection<String> validatorNames = classMetadata.getReadonlyValidators();
        for (String validatorName : validatorNames) {
            IEntityValidator validator = getValidator(validatorName);
            if (validator != null) {
                validator.validate(entity, entityValidationErrors);
            }
        }
    }

    private static class FakeValidator implements IEntityValidator {
        @Override
        public boolean validate(Persistable entity, EntityValidationErrors validationErrors) {
            return true;
        }
    }
}
