package com.taoswork.tallybook.datadomain.base.presentation.typed;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface PresentationExternalForeignKey {
    Class targetType() default void.class;

    String dataField();

    String displayField() default "name";

    String idField() default "id";
}
