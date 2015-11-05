package com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface PresentationMap {
    EntryType keyEntryType () default EntryType.Unknown;

    SimpleEntry keyEntry() default @SimpleEntry(name = "Key");

    EntryType valueEntryType () default EntryType.Unknown;

    SimpleEntry valueEntry() default @SimpleEntry(name = "Value");
}
