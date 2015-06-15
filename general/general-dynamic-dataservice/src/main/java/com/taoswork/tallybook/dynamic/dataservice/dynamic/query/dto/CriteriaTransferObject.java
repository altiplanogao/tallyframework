package com.taoswork.tallybook.dynamic.dataservice.dynamic.query.dto;

/**
 * Created by Gao Yuan on 2015/5/22.
 */
public class CriteriaTransferObject {
    private Integer firstResult;
    private Integer maxResults;

    public Integer getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(Integer firstResult) {
        this.firstResult = firstResult;
    }

    public Integer getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }
}
