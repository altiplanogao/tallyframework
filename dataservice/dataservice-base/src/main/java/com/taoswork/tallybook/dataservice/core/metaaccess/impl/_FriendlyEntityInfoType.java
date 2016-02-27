package com.taoswork.tallybook.dataservice.core.metaaccess.impl;

import com.taoswork.tallybook.descriptor.description.infos.EntityInfoType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Locale;

/**
 * Created by Gao Yuan on 2015/7/6.
 */
class _FriendlyEntityInfoType {
    public final EntityInfoType entityInfoType;
    public final Locale locale;

    public _FriendlyEntityInfoType(EntityInfoType entityInfoType, Locale locale) {
        this.entityInfoType = entityInfoType;
        this.locale = locale;
    }

    public EntityInfoType getEntityInfoType() {
        return entityInfoType;
    }

    public Locale getLocale() {
        return locale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof _FriendlyEntityInfoType)) return false;

        _FriendlyEntityInfoType that = (_FriendlyEntityInfoType) o;

        return new EqualsBuilder()
                .append(entityInfoType, that.entityInfoType)
                .append(locale, that.locale)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(entityInfoType)
                .append(locale)
                .toHashCode();
    }
}
