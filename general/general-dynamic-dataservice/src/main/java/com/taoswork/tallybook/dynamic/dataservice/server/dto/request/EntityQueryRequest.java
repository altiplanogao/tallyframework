package com.taoswork.tallybook.dynamic.dataservice.server.dto.request;

import com.taoswork.tallybook.dynamic.dataservice.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dynamic.dataservice.query.dto.PropertyFilterCriteria;
import com.taoswork.tallybook.dynamic.dataservice.query.dto.PropertySortCriteria;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/6/1.
 */
public class EntityQueryRequest {

    public static final String REQUEST_START_INDEX = "startIndex";
    public static final String REQUEST_MAX_INDEX = "maxIndex";
    public static final String REQUEST_MAX_RESULT_COUNT = "maxResult";
    public static final String SORT_PARAMETER = "sort_";

    public static final int DEFAULT_REQUEST_MAX_RESULT_COUNT = 50;

    public EntityQueryRequest(){

    }

    private Class<?> entityType;
    private CriteriaTransferObject criteriaTransferObject = new CriteriaTransferObject();

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

    public void setMaxResultCount(int maxResultCount) {
        criteriaTransferObject.setMaxResultCount(maxResultCount);
    }

    public Collection<PropertySortCriteria> getSortCriterias() {
        return criteriaTransferObject.getSortCriterias();
    }

    public Collection<PropertyFilterCriteria> getFilterCriterias() {
        return criteriaTransferObject.getFilterCriteriasCollection();
    }

    public int getFirstResult() {
        return criteriaTransferObject.getFirstResult();
    }

    public int getMaxResultCount() {
        return criteriaTransferObject.getMaxResultCount();
    }
}
