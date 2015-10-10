package com.taoswork.tallybook.general.datadomain.support.presentation;

import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;

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
     *
     * The order in which this field will appear in a GUI relative to other fields from the same class
     *
     * @return the display order
     */
    int order() default ORDER_NOT_DEFINED;

    String tab() default PresentationClass.Tab.DEFAULT_NAME;

    String group() default PresentationClass.Group.DEFAULT_NAME;

    boolean nameField() default false;

    boolean required() default false;

    /**
     * Optional - only required if you want to restrict the visibility of this field in the admin tool
     *
     * Describes how the field is shown in admin GUI.
     *
     * @return whether or not to hide the form field.
     */
    int visibility() default Visibility.VISIBLE_ALL;

    /**
     * Optional - only required if you want to explicitly specify the field type. This
     * value is normally inferred by the system based on the field type in the entity class.
     *
     * Explicity specify the type the GUI should consider this field
     * Specifying UNKNOWN will cause the system to make its best guess
     *
     * @return the field type
     */
    FieldType fieldType() default FieldType.UNKNOWN;
}
