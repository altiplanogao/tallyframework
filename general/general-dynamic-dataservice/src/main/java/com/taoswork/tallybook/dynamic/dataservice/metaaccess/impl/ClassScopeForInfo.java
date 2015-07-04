package com.taoswork.tallybook.dynamic.dataservice.metaaccess.impl;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.EntityInfoType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by Gao Yuan on 2015/7/3.
 */
class ClassScopeForInfo extends ClassScope {
    private final EntityInfoType infoType;

    public ClassScopeForInfo(ClassScope classScope, EntityInfoType infoType) {
        super(classScope);
        this.infoType = infoType;
    }

    public ClassScopeForInfo(String clazzName, boolean withSuper, boolean withHierarchy, EntityInfoType infoType) {
        super(clazzName, withSuper, withHierarchy);
        this.infoType = infoType;
    }

    public ClassScopeForInfo(Class clazz, boolean withSuper, boolean withHierarchy, EntityInfoType infoType) {
        super(clazz, withSuper, withHierarchy);
        this.infoType = infoType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ClassScopeForInfo)) return false;

        ClassScopeForInfo that = (ClassScopeForInfo) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(infoType, that.infoType)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(infoType)
                .toHashCode();
    }
}
