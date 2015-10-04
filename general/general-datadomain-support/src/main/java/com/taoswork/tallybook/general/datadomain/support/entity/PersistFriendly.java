package com.taoswork.tallybook.general.datadomain.support.entity;

import com.taoswork.tallybook.general.datadomain.support.entity.validation.IEntityValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Gao Yuan on 2015/10/2.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface PersistFriendly {
    String nameOverride() default "";

    boolean asDefaultPermissionGuardian () default false;

    Class permissionGuardian() default Object.class;

    Class<? extends IEntityValidator>[] validators() default {};
}
