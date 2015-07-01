package com.taoswork.tallybook.dynamic.dataservice.server.io.request;

import com.taoswork.tallybook.dynamic.dataservice.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dynamic.dataservice.query.dto.PropertyFilterCriteria;
import com.taoswork.tallybook.dynamic.dataservice.query.dto.PropertySortCriteria;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/6/1.
 */
public class EntityQueryRequest {

    public static final int DEFAULT_REQUEST_MAX_RESULT_COUNT = 50;

    public EntityQueryRequest(){

    }

    private Class<?> entityType;
    private CriteriaTransferObject criteriaTransferObject = new CriteriaTransferObject();
    private final Set<String> entityInfoNames = new HashSet<String>();

    public EntityQueryRequest withEntityType(String entityType){
        try {
            this.entityType = Class.forName(entityType);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public EntityQueryRequest withEntityType(Class<?> entityType){
        this.entityType = entityType;
        return this;
    }

    public Class<?> getEntityType() {
        return entityType;
    }

    public EntityQueryRequest appendSortCriteria(PropertySortCriteria sortCriteria){
        criteriaTransferObject.addSortCriteria(sortCriteria);
        return this;
    }

    public EntityQueryRequest appendFilterCriteria(PropertyFilterCriteria filterCriteria){
        criteriaTransferObject.addFilterCriteria(filterCriteria);
        return this;
    }

    public void setStartIndex(int startIndex) {
        criteriaTransferObject.setFirstResult(startIndex);
    }

    public void setPageSize(int pageSize) {
        criteriaTransferObject.setPageSize(pageSize);
    }

    public Collection<PropertySortCriteria> getSortCriterias() {
        return criteriaTransferObject.getSortCriterias();
    }

    public Collection<PropertyFilterCriteria> getFilterCriterias() {
        return criteriaTransferObject.getFilterCriteriasCollection();
    }

    public long getFirstResult() {
        return criteriaTransferObject.getFirstResult();
    }

    public int getPageSize() {
        return criteriaTransferObject.getPageSize();
    }

    public Collection<String> getEntityInfoNames() {
        return Collections.unmodifiableCollection(entityInfoNames);
    }

    public EntityQueryRequest setEntityInfoName(String entityInfoName){
        entityInfoNames.clear();
        entityInfoNames.add(entityInfoName);
        return this;
    }

    public EntityQueryRequest addEntityInfoNames(String... entityInfoNames){
        for (String entityInfoName : entityInfoNames) {
            this.entityInfoNames.add(entityInfoName);
        }
        return this;
    }

    public boolean hasEntityInfoName(String entityInfoName){
        return entityInfoNames.contains(entityInfoName);
    }
}
