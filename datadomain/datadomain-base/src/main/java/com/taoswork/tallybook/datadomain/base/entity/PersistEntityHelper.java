package com.taoswork.tallybook.datadomain.base.entity;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Gao Yuan on 2015/12/19.
 */
public class PersistEntityHelper {
    public static final String getEntityName(Class<?> entityInterface) {
        String typeName = entityInterface.getSimpleName().toLowerCase();
        PersistEntity persistEntity = entityInterface.getDeclaredAnnotation(PersistEntity.class);
        if (persistEntity != null) {
            String nameOverride = persistEntity.value();
            if (StringUtils.isNotEmpty(nameOverride))
                typeName = nameOverride;
        }
        return typeName;
    }
}
