package com.taoswork.tallybook.datadomain.base.entity.validation;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.datadomain.base.entity.validation.error.EntityValidationErrors;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

/**
 * Created by Gao Yuan on 2016/2/18.
 */
public class EntityValidatorPool {
    private final static FakeValidator fakeValidator = new FakeValidator();
    private final ConcurrentMap<String, IEntityValidator> validatorCache = new ConcurrentHashMap<String, IEntityValidator>();

    protected IEntityValidator getValidator(String validatorName) {
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

    private static class FakeValidator implements IEntityValidator {
        @Override
        public boolean validate(Persistable entity, EntityValidationErrors validationErrors) {
            throw new IllegalAccessError();
        }
    }
}
