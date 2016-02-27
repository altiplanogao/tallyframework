package com.taoswork.tallybook.datadomain.base.entity;

import com.taoswork.tallybook.datadomain.base.presentation.typedcollection.mapentry.IMapEntryDelegate;
import com.taoswork.tallybook.datadomain.base.presentation.typedcollection.mapentry.StringStringEntry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Map;

/**
 * Created by Gao Yuan on 2016/2/21.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface MapField {
    MapMode mode();

    Class targetClass() default void.class;

    Class mapImplementationClass() default void.class;

    //Only works for MapMode.Basic, and mandatory
    Class<? extends IMapEntryDelegate> entryDelegate() default IMapEntryDelegate.class;

    //Only works for MapMode.Entity & MapMode.Lookup
    String keyFieldOnValue() default "";

    //Only works for MapMode.AdornedLookup
    Class joinEntity() default void.class;
}

//class MapFieldExamples {
//    static class Embed {
//    }
//
//    static class Entity {
//        String name;
//    }
//
//    static class AdornedEntity {
//    }
//
//    //key & value saved in entryDelegate
//    @MapField(mode = MapMode.Basic, entryDelegate = StringStringEntry.class)
//    Map<String, String> a;
//    //Map<Embed, String> a;
//    //Map<Entity, String> a;
//    //Map<String, Embed> a;
//    //Map<Embed, Embed> a;
//    //Map<Entity, Embed> a;
//
//    //key saved in entity
//    @MapField(mode = MapMode.Entity, keyFieldOnValue = "name")
//    Map<String, Entity> b;
//    //Map<Embed, Entity> b;
//    //Map<Entity, Entity> b;
//
//    @MapField(mode = MapMode.Lookup, keyFieldOnValue = "name")
//    Map<String, Entity> c;
//
//    @MapField(mode = MapMode.AdornedLookup, joinEntity = AdornedEntity.class)
//    Map<String, Entity> d;
//}
