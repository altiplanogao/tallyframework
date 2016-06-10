package com.taoswork.tallybook.datadomain.base.presentation;

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
    PresentationClass.Tab[] tabs() default {@PresentationClass.Tab(name = Tab.DEFAULT_NAME, order = 1)};

    PresentationClass.Group[] groups() default {@PresentationClass.Group(name = Group.DEFAULT_NAME, order = 1)};

    @Retention(RetentionPolicy.RUNTIME)
    @Target({})
    public @interface Tab {
        public static final String DEFAULT_NAME = "General";
        public static final int DEFAULT_ORDER = 9999;

        String name() default DEFAULT_NAME;

        int order() default DEFAULT_ORDER;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({})
    public @interface Group {
        public static final String DEFAULT_NAME = "General";
        public static final int DEFAULT_ORDER = 9999;

        String name() default DEFAULT_NAME;

        int order() default DEFAULT_ORDER;

        boolean collapsed() default false;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({})
    @interface FieldOverride{
        String fieldName();
        PresentationField define();
    }

    FieldOverride[] fieldOverrides() default {};

}
