package com.taoswork.tallybook.general.datadomain.support.entity;

import com.taoswork.tallybook.general.datadomain.support.entity.validation.IEntityValidator;
import com.taoswork.tallybook.general.datadomain.support.entity.valuecopier.IEntityValueCopier;
import com.taoswork.tallybook.general.datadomain.support.entity.valuegate.IEntityValueGate;

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
    String nameOverride() default "";

    boolean asDefaultPermissionGuardian () default false;

    Class permissionGuardian() default void.class;

    Class<? extends IEntityValidator>[] validators() default {};

    Class<? extends IEntityValueGate>[] valueGates() default {};

    Class<? extends IEntityValueCopier> copier() default IEntityValueCopier.class;
}
