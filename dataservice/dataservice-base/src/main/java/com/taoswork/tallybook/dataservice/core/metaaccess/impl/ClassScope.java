package com.taoswork.tallybook.dataservice.core.metaaccess.impl;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * Created by Gao Yuan on 2015/7/3.
 */
class ClassScope implements Cloneable, Serializable {
    private final String clazzName;
    private final boolean withSuper;
    private final boolean withHierarchy;

    public ClassScope(String clazzName, boolean withSuper, boolean withHierarchy) {
        this.clazzName = clazzName;
        this.withSuper = withSuper;
        this.withHierarchy = withHierarchy;
    }

    public ClassScope(Class clazz, boolean withSuper, boolean withHierarchy) {
        this(clazz.getName(), withSuper, withHierarchy);
    }

    public ClassScope(ClassScope classScope) {
        this(classScope.clazzName, classScope.withSuper, classScope.withHierarchy);
    }

    public String getClazzName() {
        return clazzName;
    }

    public boolean isWithSuper() {
        return withSuper;
    }

    public boolean isWithHierarchy() {
        return withHierarchy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ClassScope)) return false;

        ClassScope that = (ClassScope) o;

        return new EqualsBuilder()
                .append(withSuper, that.withSuper)
                .append(withHierarchy, that.withHierarchy)
                .append(clazzName, that.clazzName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(clazzName)
                .append(withSuper)
                .append(withHierarchy)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "ClassScope{" +
                clazzName + '\'' +
                ", super:" + withSuper +
                ", hierarchy:" + withHierarchy +
                '}';
    }
}
