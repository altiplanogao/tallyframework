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
public @interface PresentationCollection {

    CollectionMode collectionMode() default CollectionMode.Unknown;

    /**
     * Optional, and only useful on CollectionMode.Lookup or CollectionMode.AdornedLookup
     * @return
     */
    Class joinEntity() default void.class;

    Class<? extends IPrimitiveEntry> primitiveDelegate() default IPrimitiveEntry.class;
}
