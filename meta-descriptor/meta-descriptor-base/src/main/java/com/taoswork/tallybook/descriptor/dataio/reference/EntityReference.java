package com.taoswork.tallybook.descriptor.dataio.reference;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Single Entity reference by entityType and id
 */
public class EntityReference {
    private final String entityType;
    private final String entityId;

    public EntityReference(String entityType, String entityId) {
        this.entityType = entityType;
        this.entityId = entityId;
    }

    public String getEntityType() {
        return entityType;
    }

    public String getEntityId() {
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
