package com.taoswork.tallybook.datadomain.base.entity;

import com.taoswork.tallybook.datadomain.base.entity.validation.IEntityValidator;
import com.taoswork.tallybook.datadomain.base.entity.valuecopier.IEntityCopier;
import com.taoswork.tallybook.datadomain.base.entity.valuegate.IEntityGate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Gao Yuan on 2015/10/2.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface PersistEntity {

    @Retention(RetentionPolicy.RUNTIME)
    @Target({})
    @interface FieldOverride{
        String fieldName();
        PersistField define();
    }

    FieldOverride[] fieldOverrides() default {};

    String value() default "";//name override

    boolean instantiable() default true;

    boolean asDefaultPermissionGuardian() default false;

    /**
     * Explicit guardian setting
     *
     * @return
     */
    Class permissionGuardian() default void.class;

    Class<? extends IEntityValidator>[] validators() default {};

    Class<? extends IEntityGate>[] valueGates() default {};

    Class<? extends IEntityCopier> copier() default IEntityCopier.class;
}
