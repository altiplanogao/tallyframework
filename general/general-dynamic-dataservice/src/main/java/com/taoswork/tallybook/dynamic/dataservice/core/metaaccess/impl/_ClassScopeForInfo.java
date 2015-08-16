package com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.impl;

import com.taoswork.tallybook.dynamic.datameta.description.infos.EntityInfoType;

/**
 * Created by Gao Yuan on 2015/7/6.
 */
class _ClassScopeForInfo extends ClassScopeExtension<EntityInfoType> {
    public _ClassScopeForInfo(ClassScope classScope, EntityInfoType note) {
        super(classScope, note);
    }

    public _ClassScopeForInfo(String clazzName, boolean withSuper, boolean withHierarchy, EntityInfoType note) {
        super(clazzName, withSuper, withHierarchy, note);
    }

    public _ClassScopeForInfo(Class clazz, boolean withSuper, boolean withHierarchy, EntityInfoType note) {
        super(clazz, withSuper, withHierarchy, note);
    }
}