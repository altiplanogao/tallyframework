package com.taoswork.tallybook.descriptor.jpa.metadata.utils;

import com.taoswork.tallybook.datadomain.base.entity.TransientField;
import com.taoswork.tallybook.general.solution.reflect.FieldScanMethod;
import com.taoswork.tallybook.general.solution.reflect.FieldScanner;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Gao Yuan on 2016/2/15.
 */
public class JpaFieldScanMethod extends FieldScanMethod {
    public static FieldScanMethod scanAllPersistentNoSuper = new JpaFieldScanMethod()
            .setScanSuper(false).setIncludeId(true).setIncludeStatic(false).setIncludeTransient(false);

    @Override
    public boolean isIdField(Field field) {
        return field.isAnnotationPresent(Id.class);
    }

    @Override
    public boolean isTransientField(Field field) {
        if (field.isAnnotationPresent(TransientField.class)) {
            return true;
        }
//        field.getAnnotations()
        return field.isAnnotationPresent(Transient.class);
    }

    public static List<Field> getFields(Class<?> clz) {
        return FieldScanner.getFields(clz, new JpaFieldScanMethod());
    }

}
