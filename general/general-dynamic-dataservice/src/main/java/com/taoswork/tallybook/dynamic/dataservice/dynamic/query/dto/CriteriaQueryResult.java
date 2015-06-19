package com.taoswork.tallybook.dynamic.dataservice.dynamic.query.dto;

import com.taoswork.tallybook.general.extension.collections.CollectionUtility;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/17.
 */
public class CriteriaQueryResult<T> {
    private List<T> entityCollection;
    private Long totalCount;
    private long startIndex;

    public List<T> getEntityCollection() {
        return entityCollection;
    }

    public CriteriaQueryResult setEntityCollection(List<T> entityCollection) {
        this.entityCollection = entityCollection;
        return this;
    }

    public int fetchedCount(){
        if(CollectionUtility.isEmpty(entityCollection)){
            return 0;
        }
        return entityCollection.size();
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public CriteriaQueryResult setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public long getStartIndex() {
        return startIndex;
    }

    public CriteriaQueryResult setStartIndex(long startIndex) {
        this.startIndex = startIndex;
        return this;
    }
}
