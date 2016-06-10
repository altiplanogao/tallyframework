package com.taoswork.tallybook.datadomain.base.entity;

import com.taoswork.tallybook.datadomain.base.presentation.typedcollection.entry.IPrimitiveEntry;
import com.taoswork.tallybook.datadomain.base.presentation.typedcollection.entry.StringEntry;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

/**
 * Created by Gao Yuan on 2016/2/20.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface CollectionField {
    CollectionMode mode();

    Class targetClass() default void.class;

    Class collectionImplementationClass() default void.class;

    //Only works for CollectionMode.Primitive
    Class<? extends IPrimitiveEntry> primitiveDelegate() default IPrimitiveEntry.class;

    //Only works for CollectionMode.Entity
    String mappedBy() default "";

    //Only works for CollectionMode.AdornedLookup
    Class joinEntity() default void.class;

}

//class CollectionFieldExamples {
//    static class Embed {
//    }
//
//    static class Entity {
//    }
//
//    static class AdornedEntity {
//    }
//
//    @CollectionField(mode = CollectionMode.Primitive, primitiveDelegate = StringEntry.class)
//    List<String> a;
//
//    @CollectionField(mode = CollectionMode.Basic)
//    List<Embed> b;
//
//    @CollectionField(mode = CollectionMode.Entity)
//    List<Entity> c;
//
//    @CollectionField(mode = CollectionMode.Lookup)
//    List<Entity> d;
//
//    @CollectionField(mode = CollectionMode.AdornedLookup, joinEntity = AdornedEntity.class)
//    List<Entity> e;
//}
