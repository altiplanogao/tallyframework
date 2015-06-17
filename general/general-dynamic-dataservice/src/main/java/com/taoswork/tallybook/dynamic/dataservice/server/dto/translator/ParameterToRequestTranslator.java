package com.taoswork.tallybook.dynamic.dataservice.server.dto.translator;

import com.taoswork.tallybook.dynamic.dataservice.query.dto.PropertyFilterCriteria;
import com.taoswork.tallybook.dynamic.dataservice.query.dto.PropertySortCriteria;
import com.taoswork.tallybook.dynamic.dataservice.query.dto.SortDirection;
import com.taoswork.tallybook.dynamic.dataservice.server.dto.request.EntityQueryRequest;
import com.taoswork.tallybook.dynamic.dataservice.server.dto.request.GeneralRequestParameter;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;

import java.util.*;

/**
 * Created by Gao Yuan on 2015/6/2.
 */
public class ParameterToRequestTranslator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParameterToRequestTranslator.class);
    private static final Set<String> staticParaNames = new HashSet<String>();

    static {
        staticParaNames.add(GeneralRequestParameter.REQUEST_START_INDEX);
        staticParaNames.add(GeneralRequestParameter.REQUEST_MAX_INDEX);
        staticParaNames.add(GeneralRequestParameter.REQUEST_MAX_RESULT_COUNT);
    }

    private static Integer getIntegerValue(List<String> values) {
        if (CollectionUtils.isEmpty(values)) {
            return null;
        } else {
            try {
                int startIndex = Integer.parseInt(values.get(0));
                return startIndex;
            } catch (NumberFormatException exp) {
                return null;
            }
        }
    }

    public static EntityQueryRequest makeQueryRequest(
            String entityClz,
            MultiValueMap<String, String> requestParams) {
        EntityQueryRequest request = new EntityQueryRequest();
        request.withEntityType(entityClz);
        setPropertyCriterias(request, requestParams);
        return request;
    }

    private static boolean handleIndexParameter(String propertyName, List<String> values, Map<String, Integer> integerValues) {
        if (staticParaNames.contains(propertyName)) {
            Integer value = getIntegerValue(values);
            integerValues.put(propertyName, value);
            return true;
        }
        return false;
    }

    protected static void setPropertyCriterias(EntityQueryRequest request, Map<String, List<String>> requestParams) {

        Map<String, Integer> integerValues = new HashMap<String, Integer>();

        if (requestParams == null || requestParams.isEmpty()) {
            ;
        } else {
            List<PropertySortCriteria> result = new ArrayList<PropertySortCriteria>();
            for (Map.Entry<String, List<String>> entry : requestParams.entrySet()) {
                String key = entry.getKey();
                List<String> value = entry.getValue();
                String propertyName = key;
                if (handleIndexParameter(propertyName, value, integerValues)) {
                    continue;
                } else if (key.startsWith(GeneralRequestParameter.SORT_PARAMETER)) {
                    propertyName = key.substring(GeneralRequestParameter.SORT_PARAMETER.length());
                    PropertySortCriteria sortCriteria = new PropertySortCriteria(propertyName);
                    int valueSize = value.size();
                    if (valueSize > 1) {
                        LOGGER.warn("Sort Criteria for {} count > 1, ignoring the fronts ... ", propertyName);
                    }
                    String orderStr = value.get(valueSize - 1);
                    SortDirection sortDirection = GeneralRequestParameter.getSortDirection(orderStr);
                    if(null != sortDirection) {
                        sortCriteria.setSortDirection(sortDirection);
                        request.appendSortCriteria(sortCriteria);
                    }
                } else {
                    PropertyFilterCriteria filterCriteria = new PropertyFilterCriteria(propertyName);
                    filterCriteria.addFilterValues(value);
                    request.appendFilterCriteria(filterCriteria);
                }
            }
        }

        int startIndex = integerValues.getOrDefault(GeneralRequestParameter.REQUEST_START_INDEX, 0);
        Integer maxIndex = integerValues.getOrDefault(GeneralRequestParameter.REQUEST_MAX_INDEX, null);
        Integer maxResultCount = integerValues.getOrDefault(GeneralRequestParameter.REQUEST_MAX_RESULT_COUNT, null);

        request.setStartIndex(startIndex);
        if (maxResultCount == null && maxIndex == null) {
            request.setMaxResultCount(EntityQueryRequest.DEFAULT_REQUEST_MAX_RESULT_COUNT);
            return;
        } else if (maxResultCount != null) {
            request.setMaxResultCount(maxResultCount);
        } else if (maxIndex != null) {
            maxResultCount = maxIndex - startIndex;
            if (maxResultCount > 0) {
                request.setMaxResultCount(maxResultCount);
            } else {
                request.setMaxResultCount(EntityQueryRequest.DEFAULT_REQUEST_MAX_RESULT_COUNT);
            }
        }
    }
}
