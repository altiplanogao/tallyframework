package com.taoswork.tallybook.dynamic.datadomain.presentation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Gao Yuan on 2015/5/21.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface PresentationClass {
    PresentationClass.Tab[] tabs() default { @PresentationClass.Tab(name = Tab.DEFAULT_NAME) };

    PresentationClass.Group[] groups() default { @PresentationClass.Group(name = Group.DEFAULT_NAME) };

    boolean excludeFromPolymorphism() default false;

    @Retention(RetentionPolicy.RUNTIME)
    @Target({})
    public @interface Tab {
        public static final String DEFAULT_NAME = "General";

        String name() default DEFAULT_NAME;
        int order() default 9999;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({})
    public @interface Group {
        public static final String DEFAULT_NAME = "General";

        String name() default DEFAULT_NAME;
        int order() default 9999;
        boolean collapsed() default false;
    }
}
