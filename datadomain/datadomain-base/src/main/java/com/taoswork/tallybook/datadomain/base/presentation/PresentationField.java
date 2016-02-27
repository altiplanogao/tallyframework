package com.taoswork.tallybook.datadomain.base.presentation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface PresentationField {
    public final static int ORDER_NOT_DEFINED = 99999;
    public final static int DEFAULT_ORDER_BIAS = 100000;

    /**
     * Optional - only required if you want to order the appearance of this field in the UI
     * <p>
     * The order in which this field will appear in a GUI relative to other fields from the same class
     *
     * @return the display order
     */
    int order() default ORDER_NOT_DEFINED;

    String tab() default PresentationClass.Tab.DEFAULT_NAME;

    String group() default PresentationClass.Group.DEFAULT_NAME;

    /**
     * Optional - only required if you want to restrict the visibility of this field in the admin tool
     * <p>
     * Describes how the field is shown in admin GUI.
     *
     * @return whether or not to hide the form field.
     */
    int visibility() default Visibility.VISIBLE_ALL;

    /**
     * Ignore the field to noticed by user,
     * which means the field won't be sent to the front-end
     *
     * @return
     */
    boolean ignore() default false;
}
