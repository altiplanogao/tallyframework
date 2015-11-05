package com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Gao Yuan on 2015/11/5.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface SimpleEntry {
    String name();
}
