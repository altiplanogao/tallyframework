package com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection;

import com.taoswork.tallybook.general.datadomain.support.presentation.typedcollection.entry.ISimpleEntryDelegate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface PresentationCollection {
    EntryType entryType () default EntryType.Unknown;

    Class<? extends ISimpleEntryDelegate> simpleEntryDelegate() default ISimpleEntryDelegate.class;
}
