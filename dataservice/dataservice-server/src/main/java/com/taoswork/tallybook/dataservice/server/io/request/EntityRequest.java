package com.taoswork.tallybook.dataservice.server.io.request;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.dataservice.server.io.request.parameter.EntityTypeParameter;
import com.taoswork.tallybook.descriptor.description.infos.EntityInfoType;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/8/4.
 */
public abstract class EntityRequest {
    private final String resourceName;
    private Class<? extends Persistable> entityType;
    //The uri of this request
    private final String uri;
    //The uri of this request, with parameter
    private final URI fullUri;
    private final String entityUri;

    private final Set<EntityInfoType> entityInfoTypes = new HashSet<EntityInfoType>();

    public EntityRequest(EntityTypeParameter entityTypeParam,
                         URI fullUri) {
        this.resourceName = entityTypeParam.getTypeName();
        this.setEntityType(entityTypeParam.getType());
        // this.entityType = entityTypeParam.getType();
        this.fullUri = fullUri;
        this.uri = fullUri.getPath();
        this.entityUri = entityTypeParam.getEntityUri();
    }

    public String getFullUri() {
        return fullUri.toString();
    }

    public Class<? extends Persistable> getEntityType() {
        return entityType;
    }

    public EntityRequest setEntityType(Class<? extends Persistable> entityType) {
        this.entityType = entityType;
        return this;
    }

    public EntityRequest withEntityType(String entityType) {
        try {
            if (!StringUtils.isEmpty(entityType)) {
                Class etype = Class.forName(entityType);
                if (Persistable.class.isAssignableFrom(etype)) {
                    this.entityType = (Class<? extends Persistable>) etype;
                } else {
                    this.entityType = null;
                }
            } else {
                this.entityType = null;
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getUri() {
        return uri;
    }

    public String getEntityUri() {
        return entityUri;
    }

    ////////////////////////////////////////

    public Collection<EntityInfoType> getEntityInfoTypes() {
        return Collections.unmodifiableCollection(entityInfoTypes);
    }

    public EntityRequest setEntityInfoType(EntityInfoType entityInfoType) {
        entityInfoTypes.clear();
        entityInfoTypes.add(entityInfoType);
        return this;
    }

    public EntityRequest addEntityInfoType(EntityInfoType... entityInfoTypes) {
        for (EntityInfoType entityInfoType : entityInfoTypes) {
            this.entityInfoTypes.add(entityInfoType);
        }
        return this;
    }

    public EntityRequest clearEntityInfoType() {
        entityInfoTypes.clear();
        return this;
    }

    public boolean hasEntityInfoType(EntityInfoType entityInfoType) {
        return entityInfoTypes.contains(entityInfoType);
    }
}
