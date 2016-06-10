package com.taoswork.tallybook.datadomain.base.entity;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Gao Yuan on 2016/2/15.
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface TransientField {
}
