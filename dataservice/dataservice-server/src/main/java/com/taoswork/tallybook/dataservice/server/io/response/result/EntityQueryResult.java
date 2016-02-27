package com.taoswork.tallybook.dataservice.server.io.response.result;

import com.taoswork.tallybook.dataservice.server.io.response.range.QueryResultRange;

import java.util.List;

/**
 * Created by Gao Yuan on 2015/8/5.
 */
public class EntityQueryResult {
    private Long startIndex;
    private int pageSize;
    private Long totalCount;

    private List<?> beans;

    public Long getStartIndex() {
        return startIndex;
    }

    public EntityQueryResult setStartIndex(Long startIndex) {
        this.startIndex = startIndex;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public EntityQueryResult setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public EntityQueryResult setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public QueryResultRange makeRange() {
        return new QueryResultRange(startIndex,
                pageSize, totalCount);
    }

    public List<?> getBeans() {
        return beans;
    }

    public EntityQueryResult setBeans(List<?> beans) {
        this.beans = beans;
        return this;
    }
}
