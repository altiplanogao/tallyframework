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
    private Map<String, IEntityInfo> infos;

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

    public AEntityQueryResponse addInfo(EntityInfoType infoType, IEntityInfo entityInfo){
        if(this.infos == null){
            this.infos = new HashMap<String, IEntityInfo>();
        }
        this.infos.put(infoType.getName(), entityInfo);
        return this;
    }

    public AEntityQueryResponse addInfos(Map<String, IEntityInfo> entityInfoMap){
        if(this.infos == null){
            this.infos = new HashMap<String, IEntityInfo>();
        }
        this.infos.putAll(entityInfoMap);
        return this;
    }

    public <T extends IEntityInfo> T getInfo(String infoType){
        return (T) infos.getOrDefault(infoType, null);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Map<String, IEntityInfo> getInfos(){
        if (null == infos){
            return null;
        }
        return Collections.unmodifiableMap(infos);
    }
}
