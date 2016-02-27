package com.taoswork.tallybook.authority.solution.engine.filter;

import com.taoswork.tallybook.authority.solution.domain.resource.FilterType;
import com.taoswork.tallybook.authority.solution.engine.filter.utils.FilterHelper;
import org.apache.commons.collections4.map.LRUMap;

import java.util.Map;

/**
 * Created by Gao Yuan on 2016/2/10.
 */
public class FilterManager {
    private static final Map<String, IFilter> filterMap = new LRUMap<String, IFilter>(1000);

    public static IFilter getFilter(String resourceTypeName, FilterType ft, String parameter) {
        String fm = FilterHelper.calcFingerMark(resourceTypeName, ft, parameter);
        IFilter filter = filterMap.get(fm);
        if (filter == null) {
            filter = FilterHelper.createFilter(resourceTypeName, ft, parameter);
            filterMap.put(fm, filter);
        }
        return filter;
    }
}