package com.taoswork.tallybook.descriptor.metadata.utils;

import com.taoswork.tallybook.datadomain.base.entity.IdField;
import com.taoswork.tallybook.datadomain.base.entity.TransientField;
import com.taoswork.tallybook.general.solution.reflect.AnnotationUtility;
import com.taoswork.tallybook.general.solution.reflect.FieldScanMethod;
import com.taoswork.tallybook.general.solution.reflect.FieldScanner;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Gao Yuan on 2016/2/15.
 */
public class GeneralFieldScanMethod extends FieldScanMethod {
    public static FieldScanMethod scanAllPersistentNoSuper = new GeneralFieldScanMethod()
            .setScanSuper(false).setIncludeId(true).setIncludeStatic(false).setIncludeTransient(false);

    @Override
    public boolean isIdField(Field field) {
        if (field.isAnnotationPresent(IdField.class)) {
            return true;
        }
        if (AnnotationUtility.isFieldAnnotated(field, "id")) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isTransientField(Field field) {
        if (field.isAnnotationPresent(TransientField.class)) {
            return true;
        }
        if (AnnotationUtility.isFieldAnnotated(field, "transient")) {
            return true;
        }
        return false;
    }

    public static List<Field> getFields(Class<?> clz) {
        return FieldScanner.getFields(clz, new GeneralFieldScanMethod());
    }

}
