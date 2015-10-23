package com.taoswork.tallybook.dynamic.dataservice.server.io.request;

import com.taoswork.tallybook.dynamic.datameta.description.infos.EntityInfoType;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.parameter.EntityTypeParameter;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/8/4.
 */
public abstract class EntityRequest {
    private String resourceName;
    private Class<? extends Persistable> entityType;
    private String baseUri;
    private String entityUri;
    private String fullUrl;
    private final Set<EntityInfoType> entityInfoTypes = new HashSet<EntityInfoType>();

    public EntityRequest(){
        this("", "", "");
    }

    public EntityRequest(String resourceName,
                         Class<? extends Persistable> entityType,
                         String baseUri, String url){
        this.setResourceName(resourceName)
            .setEntityType(entityType)
            .setBaseUri(baseUri);
    }

    public EntityRequest(String resourceName,
                         String entityType,
                         String baseUri){
        this.setResourceName(resourceName)
            .withEntityType(entityType)
            .setBaseUri(baseUri);
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public EntityRequest setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
        return this;
    }

    public Class<? extends Persistable> getEntityType() {
        return entityType;
    }

    public EntityRequest setEntityType(Class<? extends Persistable> entityType){
        this.entityType = entityType;
        return this;
    }

    public EntityRequest withEntityType(String entityType){
        try {
            if(!StringUtils.isEmpty(entityType)){
                Class etype = Class.forName(entityType);
                if(Persistable.class.isAssignableFrom(etype)){
                    this.entityType = (Class<? extends Persistable>)etype;
                } else {
                    this.entityType = null;
                }
            }else {
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

    public EntityRequest setResourceName(String resourceName){
        this.resourceName = resourceName;
        return this;
    }

    public String getBaseUri() {
        return baseUri;
    }

    public EntityRequest setBaseUri(String baseUri) {
        this.baseUri = baseUri;
        return this;
    }

    public String getEntityUri() {
        return entityUri;
    }

    public EntityRequest setEntityUri(String entityUri) {
        this.entityUri = entityUri;
        return this;
    }

    public EntityRequest setEntityRequest(EntityTypeParameter entityTypeParam,
                         String baseUri, String fullUrl){
        this.setResourceName(entityTypeParam.getTypeName())
            .setEntityType(entityTypeParam.getType())
            .setBaseUri(baseUri)
            .setFullUrl(fullUrl);
        return this;
    }

    ////////////////////////////////////////

    public Collection<EntityInfoType> getEntityInfoTypes() {
        return Collections.unmodifiableCollection(entityInfoTypes);
    }

    public EntityRequest setEntityInfoType(EntityInfoType entityInfoType){
        entityInfoTypes.clear();
        entityInfoTypes.add(entityInfoType);
        return this;
    }

    public EntityRequest addEntityInfoType(EntityInfoType... entityInfoTypes){
        for (EntityInfoType entityInfoType : entityInfoTypes) {
            this.entityInfoTypes.add(entityInfoType);
        }
        return this;
    }

    public EntityRequest clearEntityInfoType() {
        entityInfoTypes.clear();
        return this;
    }

    public boolean hasEntityInfoType(EntityInfoType entityInfoType){
        return entityInfoTypes.contains(entityInfoType);
    }

}
