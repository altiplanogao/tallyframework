package com.taoswork.tallybook.dynamic.dataservice.server.io.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.base.IEntityInfo;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.range.QueryResultRange;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/6/24.
 */
public abstract class AEntityQueryResponse {
    private Long startIndex;
    private int pageSize;
    private Long totalCount;

    public Long getStartIndex() {
        return startIndex;
    }

    public AEntityQueryResponse setStartIndex(Long startIndex) {
        this.startIndex = startIndex;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public AEntityQueryResponse setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public AEntityQueryResponse setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public QueryResultRange makeRange(){
        return new QueryResultRange(startIndex,
                pageSize, totalCount);
    }
}
