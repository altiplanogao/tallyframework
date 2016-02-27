package com.taoswork.tallybook.dataservice.server.io.response.range;

/**
 * Created by Gao Yuan on 2015/6/24.
 */
public class QueryResultRange {
    private long start;
    private int pageSize;
    private long totalCount;

    private QueryResultRange() {
    }

    public QueryResultRange(long start, int pageSize, long totalCount) {
        this.start = start;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
    }

    public long getStart() {
        return start;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public boolean isValid() {
        return (start >= 0) && (start < totalCount);
    }

    public QueryResultRange pre() {
        return new QueryResultRange(start - pageSize, pageSize, totalCount);
    }

    public QueryResultRange next() {
        return new QueryResultRange(start + pageSize, pageSize, totalCount);
    }
}
