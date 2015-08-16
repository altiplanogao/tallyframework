package com.taoswork.tallybook.dynamic.dataservice.server.io.request;

import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.PropertyFilterCriteria;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.PropertySortCriteria;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/6/1.
 */
public class EntityQueryRequest extends EntityRequest {

    public static final int DEFAULT_REQUEST_MAX_RESULT_COUNT = 50;

    private CriteriaTransferObject criteriaTransferObject = new CriteriaTransferObject();

    public long getStartIndex() {
        return criteriaTransferObject.getFirstResult();
    }

    public void setStartIndex(int startIndex) {
        criteriaTransferObject.setFirstResult(startIndex);
    }

    public int getPageSize() {
        return criteriaTransferObject.getPageSize();
    }

    public void setPageSize(int pageSize) {
        criteriaTransferObject.setPageSize(pageSize);
    }

    public EntityQueryRequest appendSortCriteria(PropertySortCriteria sortCriteria){
        criteriaTransferObject.addSortCriteria(sortCriteria);
        return this;
    }

    public EntityQueryRequest appendFilterCriteria(PropertyFilterCriteria filterCriteria){
        criteriaTransferObject.addFilterCriteria(filterCriteria);
        return this;
    }

    public Collection<PropertySortCriteria> getSortCriterias() {
        return criteriaTransferObject.getSortCriterias();
    }

    public Collection<PropertyFilterCriteria> getFilterCriterias() {
        return criteriaTransferObject.getFilterCriteriasCollection();
    }
}
