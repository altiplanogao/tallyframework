package com.taoswork.tallybook.general.datadomain.support.presentation;

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
    PresentationClass.Tab[] tabs() default { @PresentationClass.Tab(name = Tab.DEFAULT_NAME, order = 1) };

    PresentationClass.Group[] groups() default { @PresentationClass.Group(name = Group.DEFAULT_NAME, order = 1) };

    boolean instantiable() default true;

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
