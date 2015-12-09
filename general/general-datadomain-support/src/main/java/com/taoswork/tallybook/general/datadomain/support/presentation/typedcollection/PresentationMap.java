package com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection;

import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.entry.IPrimitiveEntry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * see comments in com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.CollectionMode
 * ({@link com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.CollectionMode})
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface PresentationMap {
    EntryType keyEntryType () default EntryType.Unknown;

    Class<? extends IPrimitiveEntry> simpleKeyEntryDelegate() default IPrimitiveEntry.class;

    EntryType valueEntryType () default EntryType.Unknown;

    Class<? extends IPrimitiveEntry> simpleValueEntryDelegate() default IPrimitiveEntry.class;

}
