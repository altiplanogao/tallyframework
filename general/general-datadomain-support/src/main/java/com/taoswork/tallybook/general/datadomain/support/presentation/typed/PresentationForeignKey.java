package com.taoswork.tallybook.general.datadomain.support.presentation.typed;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface PresentationForeignKey {
    String idField() default "id";
    String displayField() default "name";
}
