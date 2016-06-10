package com.taoswork.tallybook.dataservice.server.io.request;

import com.taoswork.tallybook.dataservice.core.dao.query.dto.SortDirection;
import org.springframework.util.StringUtils;

/**
 * Created by Gao Yuan on 2015/6/17.
 */
public class GeneralRequestParameter {

    public static final String REQUEST_START_INDEX = "startIndex";
    public static final String REQUEST_MAX_INDEX = "maxIndex";
    public static final String REQUEST_PAGE_SIZE = "pageSize";
    public static final String SORT_PARAMETER = "sort_";
    public static final String SORT_ASCENDING = "asc";
    public static final String SORT_DESCENDING = "desc";

    public static final String ENTITY_INFO_TYPE = "einfo";

    public static SortDirection getSortDirection(String directionStr) {
        if (StringUtils.isEmpty(directionStr)) {
            return null;
        } else {
            directionStr = directionStr.toLowerCase();
            if (SORT_ASCENDING.equals(directionStr)) {
                return SortDirection.ASCENDING;
            } else if (SORT_DESCENDING.equals(directionStr)) {
                return SortDirection.DESCENDING;
            } else {
                return null;
            }
        }
    }
}
