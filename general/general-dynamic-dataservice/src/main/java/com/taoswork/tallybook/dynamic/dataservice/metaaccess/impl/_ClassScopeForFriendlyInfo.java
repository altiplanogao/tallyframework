package com.taoswork.tallybook.dynamic.dataservice.metaaccess.impl;

/**
 * Created by Gao Yuan on 2015/7/6.
 */
class _ClassScopeForFriendlyInfo extends ClassScopeExtension<_FriendlyEntityInfoType> {
    public _ClassScopeForFriendlyInfo(ClassScope classScope, _FriendlyEntityInfoType note) {
        super(classScope, note);
    }

    public _ClassScopeForFriendlyInfo(String clazzName, boolean withSuper, boolean withHierarchy, _FriendlyEntityInfoType note) {
        super(clazzName, withSuper, withHierarchy, note);
    }

    public _ClassScopeForFriendlyInfo(Class clazz, boolean withSuper, boolean withHierarchy, _FriendlyEntityInfoType note) {
        super(clazz, withSuper, withHierarchy, note);
    }
}