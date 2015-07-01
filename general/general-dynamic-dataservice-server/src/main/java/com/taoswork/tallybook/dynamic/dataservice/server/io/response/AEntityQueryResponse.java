package com.taoswork.tallybook.dynamic.dataservice.server.io.response;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    private Map<String, IEntityInfo> entityInfos;

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

    public AEntityQueryResponse addEntityInfo(String infoType, IEntityInfo entityInfo){
        if(this.entityInfos == null){
            this.entityInfos = new HashMap<String, IEntityInfo>();
        }
        this.entityInfos.put(infoType, entityInfo);
        return this;
    }

    public AEntityQueryResponse addEntityInfos(Map<String, IEntityInfo> entityInfoMap){
        if(this.entityInfos == null){
            this.entityInfos = new HashMap<String, IEntityInfo>();
        }
        this.entityInfos.putAll(entityInfoMap);
        return this;
    }

    public <T extends IEntityInfo> T getEntityInfo(String infoType){
        return (T) entityInfos.getOrDefault(infoType, null);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Map<String, IEntityInfo> getEntityInfos(){
        if (null == entityInfos){
            return null;
        }
        return Collections.unmodifiableMap(entityInfos);
    }
}
