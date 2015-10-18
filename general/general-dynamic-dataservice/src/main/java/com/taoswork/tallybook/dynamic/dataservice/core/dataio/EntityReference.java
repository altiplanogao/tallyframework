package com.taoswork.tallybook.dynamic.dataservice.core.dataio;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class EntityReference {
    private final String entityType;
    private final Object entityId;

    public EntityReference(String entityType, Object entityId) {
        this.entityType = entityType;
        this.entityId = entityId;
    }

    public String getEntityType() {
        return entityType;
    }

    public Object getEntityId() {
        return entityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof EntityReference)) return false;

        EntityReference that = (EntityReference) o;

        return new EqualsBuilder()
            .append(entityType, that.entityType)
            .append(entityId, that.entityId)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(entityType)
            .append(entityId)
            .toHashCode();
    }

    @Override
    public String toString() {
        return entityType + ':' + entityId;
    }
}
