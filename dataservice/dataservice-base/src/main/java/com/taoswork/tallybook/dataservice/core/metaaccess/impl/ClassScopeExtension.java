package com.taoswork.tallybook.dataservice.core.metaaccess.impl;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by Gao Yuan on 2015/7/3.
 */
class ClassScopeExtension<T> extends ClassScope {
    private final T note;

    public ClassScopeExtension(ClassScope classScope, T note) {
        super(classScope);
        this.note = note;
    }

    public ClassScopeExtension(String clazzName, boolean withSuper, boolean withHierarchy, T note) {
        super(clazzName, withSuper, withHierarchy);
        this.note = note;
    }

    public ClassScopeExtension(Class clazz, boolean withSuper, boolean withHierarchy, T note) {
        super(clazz, withSuper, withHierarchy);
        this.note = note;
    }

    public T getNote() {
        return note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ClassScopeExtension)) return false;

        ClassScopeExtension that = (ClassScopeExtension) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(note, that.note)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(note)
                .toHashCode();
    }
}
