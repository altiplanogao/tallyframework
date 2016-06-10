package com.taoswork.tallybook.dataservice.server.io.request;

/**
 * Created by Gao Yuan on 2016/2/4.
 */
public interface Pageable {

    long getStartIndex();

    int getPageSize();
}
