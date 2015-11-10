package com.taoswork.tallybook.general.authority.domain.access;

import com.taoswork.tallybook.dynamic.datadomain.converters.BooleanToStringConverter;
import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.datadomain.support.presentation.PresentationField;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.Visibility;
import com.taoswork.tallybook.general.datadomain.support.presentation.typed.PresentationBoolean;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
@Embeddable
public class ResourceAccess {
    public final static int NONE_EXTENDED = 0x00;

    @Column(name = "CAN_CREATE", nullable = false, length = 2)
    @Convert(converter = BooleanToStringConverter.class)
    @PresentationField
    @PresentationBoolean
    private Boolean canCreate;

    @Column(name = "CAN_READ", nullable = false, length = 2)
    @Convert(converter = BooleanToStringConverter.class)
    @PresentationField
    @PresentationBoolean
    private Boolean canRead;

    @Column(name = "CAN_UPDATE", nullable = false, length = 2)
    @Convert(converter = BooleanToStringConverter.class)
    @PresentationField
    @PresentationBoolean
    private Boolean canUpdate;

    @Column(name = "CAN_DELETE", nullable = false, length = 2)
    @Convert(converter = BooleanToStringConverter.class)
    @PresentationField
    @PresentationBoolean
    private Boolean canDelete;

    @Column(name = "CAN_QUERY", nullable = false, length = 2)
    @Convert(converter = BooleanToStringConverter.class)
    @PresentationField
    @PresentationBoolean
    private Boolean canQuery;

    @PresentationField(visibility = Visibility.HIDDEN_ALL)
    private int extended = NONE_EXTENDED;

    public ResourceAccess() {
        this(Access.None);
    }

    public ResourceAccess(Access access) {
        setCanCreate(access.hasAccess(Access.Create));
        setCanRead(access.hasAccess(Access.Read));
        setCanUpdate(access.hasAccess(Access.Update));
        setCanDelete(access.hasAccess(Access.Delete));
        setCanQuery(access.hasAccess(Access.Query));
        this.extended = access.getExtended();
    }

    public final static ResourceAccess createByAccess(Access access) {
        return new ResourceAccess(access);
    }

    public final boolean canCreate() {
        return isCanCreate();
    }

    public final boolean canRead() {
        return isCanRead();
    }

    public final boolean canUpdate() {
        return isCanUpdate();
    }

    public final boolean canDelete() {
        return isCanDelete();
    }

    public final boolean canQuery() {
        return isCanQuery();
    }

    public final boolean isCanCreate() {
        return canCreate;
    }

    public final ResourceAccess setCanCreate(boolean canCreate) {
        this.canCreate = canCreate;
        return this;
    }

    public final boolean isCanRead() {
        return canRead;
    }

    public final ResourceAccess setCanRead(boolean canRead) {
        this.canRead = canRead;
        return this;
    }

    public final boolean isCanUpdate() {
        return canUpdate;
    }

    public final ResourceAccess setCanUpdate(boolean update) {
        this.canUpdate = update;
        return this;
    }

    public final boolean isCanDelete() {
        return canDelete;
    }

    public final ResourceAccess setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
        return this;
    }

    public final boolean isCanQuery() {
        return canQuery;
    }

    public final ResourceAccess setCanQuery(boolean canQuery) {
        this.canQuery = canQuery;
        return this;
    }

    public final ResourceAccess setCanOperate(int mask, boolean can) {
        this.extended = Access.bitSet(this.extended, mask, can);
        return this;
    }

    public final boolean getCanOperate(int mask) {
        return Access.bitCoversAll(this.extended, mask);
    }

    public final ResourceAccess setCrudAll(boolean canAccess) {
        setCanCreate(canAccess);
        setCanRead(canAccess);
        setCanUpdate(canAccess);
        setCanDelete(canAccess);
        setCanQuery(canAccess);
        return this;
    }

    public final ResourceAccess setByAccess(Access access) {
        setCanCreate(access.hasAccess(Access.Create));
        setCanRead(access.hasAccess(Access.Read));
        setCanUpdate(access.hasAccess(Access.Update));
        setCanDelete(access.hasAccess(Access.Delete));
        setCanQuery(access.hasAccess(Access.Query));
        this.extended = access.getExtended();
        return this;
    }

    public final Access getAsAccess() {
        Access access = Access.makeExtendedAccess(this.extended);
        if (canCreate) {
            access = access.or(Access.Create);
        }
        if (canRead) {
            access = access.or(Access.Read);
        }
        if (canUpdate) {
            access = access.or(Access.Update);
        }
        if (canDelete) {
            access = access.or(Access.Delete);
        }
        if (canQuery) {
            access = access.or(Access.Query);
        }
        return access;
    }

    public final int getExtended() {
        return extended;
    }

    public final ResourceAccess setExtended(int extended) {
        this.extended = extended;
        return this;
    }

    public final ResourceAccess setExtendedAccess(int accessMask) {
        return setExtendedAccess(accessMask, true);
    }

    public final ResourceAccess setExtendedAccess(int accessMask, boolean value) {
        if (value) {
            extended |= accessMask;
        } else {
            extended &= (~accessMask);
        }
        return this;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ResourceAccess)) return false;

        ResourceAccess that = (ResourceAccess) o;

        return new org.apache.commons.lang3.builder.EqualsBuilder()
            .append(canCreate, that.canCreate)
            .append(canRead, that.canRead)
            .append(canUpdate, that.canUpdate)
            .append(canDelete, that.canDelete)
            .append(canQuery, that.canQuery)
            .append(extended, that.extended)
            .isEquals();
    }

    @Override
    public final int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(canCreate)
            .append(canRead)
            .append(canUpdate)
            .append(canDelete)
            .append(canQuery)
            .append(extended)
            .toHashCode();
    }
}
