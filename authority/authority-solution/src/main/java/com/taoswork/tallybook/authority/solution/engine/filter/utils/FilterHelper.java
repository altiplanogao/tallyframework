package com.taoswork.tallybook.authority.solution.engine.filter.utils;

import com.taoswork.tallybook.authority.solution.domain.resource.FilterType;
import com.taoswork.tallybook.authority.solution.engine.filter.FilterByClassifications;
import com.taoswork.tallybook.authority.solution.engine.filter.IFilter;

/**
 * Created by Gao Yuan on 2016/2/10.
 */
public class FilterHelper {

    private static String getFilterClassNameBy(FilterType ft) {
        switch (ft) {
            case Classified:
                return FilterByClassifications.class.getName();
            default:
                throw new IllegalArgumentException();
        }
    }

    public static IFilter createFilter(String resourceTypeName, FilterType ft, String parameter) {
        String filterClsName = getFilterClassNameBy(ft);
        return createFilter(resourceTypeName, filterClsName, parameter);
    }

    private static IFilter createFilter(String resourceTypeName, String filterClsName, String filterParameter) {
        try {
            Class filterClz = Class.forName(filterClsName);

            if (IFilter.class.isAssignableFrom(filterClz)) {
                IFilter filter = (IFilter) filterClz.newInstance();
                filter.setResource(resourceTypeName);
                filter.setFilterParameter(filterParameter);
                return filter;
            } else {
                return null;
            }
        } catch (ClassNotFoundException exp) {
            return null;
        } catch (InstantiationException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    public static String calcFingerMark(String resourceTypeName,
                                        FilterType ft,
                                        String filterParameter) {
        return calcFingerMark(resourceTypeName, getFilterClassNameBy(ft), filterParameter);
    }

    public static String calcFingerMark(String resourceTypeName,
                                        String filterClsName,
                                        String filterParameter) {
        StringBuilder sb = new StringBuilder(filterClsName).append(":");
        sb.append(resourceTypeName).append(":")
                .append(filterParameter).append(":");

        return sb.toString();

    }

    public static String calcFingerMark(IFilter filter) {
        StringBuilder sb = new StringBuilder(filter.getClass().getName()).append(":");
        sb.append(filter.getResource()).append(":")
                .append(filter.getFilterParameter()).append(":");

        return sb.toString();
    }

}
