package com.taoswork.tallybook.dynamic.dataservice.server.io.request.translator;

import com.taoswork.tallybook.dynamic.datameta.description.infos.EntityInfoType;
import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.Entity;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.PropertyFilterCriteria;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.PropertySortCriteria;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.SortDirection;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by Gao Yuan on 2015/6/2.
 */
public class Parameter2RequestTranslator {
    private static final Logger LOGGER = LoggerFactory.getLogger(Parameter2RequestTranslator.class);
    private static final Set<String> indexParaNames = new HashSet<String>();

    static {
        indexParaNames.add(GeneralRequestParameter.REQUEST_START_INDEX);
        indexParaNames.add(GeneralRequestParameter.REQUEST_MAX_INDEX);
        indexParaNames.add(GeneralRequestParameter.REQUEST_PAGE_SIZE);
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

    private static boolean _queryRequestHandleIndexParameter(String propertyName, List<String> values, Map<String, Integer> integerValues) {
        if (indexParaNames.contains(propertyName)) {
            Integer value = getIntegerValue(values);
            integerValues.put(propertyName, value);
            return true;
        }
        return false;
    }

    protected static void _queryRequestSetPropertyCriterias(EntityQueryRequest request, Map<String, List<String>> requestParams) {
        Map<String, Integer> integerValues = new HashMap<String, Integer>();

        if (requestParams == null || requestParams.isEmpty()) {
            ;
        } else {
            List<PropertySortCriteria> result = new ArrayList<PropertySortCriteria>();
            for (Map.Entry<String, List<String>> entry : requestParams.entrySet()) {
                String key = entry.getKey();
                List<String> value = entry.getValue();
                String propertyName = key;
                if (_queryRequestHandleIndexParameter(propertyName, value, integerValues)) {
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
        Integer pageSize = integerValues.getOrDefault(GeneralRequestParameter.
                REQUEST_PAGE_SIZE, null);

        request.setStartIndex(startIndex);
        if (pageSize == null && maxIndex == null) {
            request.setPageSize(EntityQueryRequest.DEFAULT_REQUEST_MAX_RESULT_COUNT);
            return;
        } else if (pageSize != null) {
            request.setPageSize(pageSize);
        } else if (maxIndex != null) {
            pageSize = maxIndex - startIndex;
            if (pageSize > 0) {
                request.setPageSize(pageSize);
            } else {
                request.setPageSize(EntityQueryRequest.DEFAULT_REQUEST_MAX_RESULT_COUNT);
            }
        }
    }

    public static EntityRequest makeInfoRequest(String entityResName, String entityType,
                                                String entityUri, String fullUrl,
                                                MultiValueMap<String, String> requestParams, Set<String> infoFilter) {
        EntityRequest request = new EntityRequest();
        request.setEntityRequest(entityResName, entityType, entityUri, fullUrl);
        _fillInfoCriterias(request, requestParams, infoFilter);

        return request;
    }

    public static EntityQueryRequest makeQueryRequest(String entityResName, String entityType,
                                                      String entityUri, String fullUrl,
                                                      MultiValueMap<String, String> requestParams, Set<String> infoFilter) {
        EntityQueryRequest request = new EntityQueryRequest();
        request.setEntityRequest(entityResName, entityType, entityUri, fullUrl);
        _queryRequestSetPropertyCriterias(request, requestParams);
        _fillInfoCriterias(request, requestParams, infoFilter);

        return request;
    }

    public static EntityAddGetRequest makeAddGetRequest(String entityResName, String entityType, String entityUri, String fullUrl) {
        EntityAddGetRequest request = new EntityAddGetRequest();
        request.setEntityRequest(entityResName, entityType, entityUri, fullUrl);
        request.addEntityInfoType(EntityInfoType.Form);
        return request;
    }

    public static EntityAddPostRequest makeAddPostRequest(String entityTypeName, String entityType,
                                                          String entityUri, String fullUrl, Entity entity) {
        EntityAddPostRequest request = new EntityAddPostRequest(entity);
        request.setEntityRequest(entityTypeName, entityType, entityUri, fullUrl);
        request.clearEntityInfoType();
        return request;
    }

    public static EntityReadRequest makeReadRequest(String entityResName, String entityType,
                                                    String entityUri, String fullUrl, String id) {
        EntityReadRequest request = new EntityReadRequest();
        request.setEntityRequest(entityResName, entityType, entityUri, fullUrl);
        request.setId(id);
        request.addEntityInfoType(EntityInfoType.Form);

        return request;
    }

    public static EntityUpdatePostRequest makeUpdatePostRequest(String entityTypeName, String entityType,
                                                                String entityUri, String fullUrl, Entity entity) {
        EntityUpdatePostRequest request = new EntityUpdatePostRequest(entity);
        request.setEntityRequest(entityTypeName, entityType, entityUri, fullUrl);
        request.clearEntityInfoType();
        return request;
    }

    public static EntityDeletePostRequest makeDeletePostRequest(String entityTypeName, String entityType,
                                                                String entityUri, String fullUrl, String id, Entity entity) {
        EntityDeletePostRequest request = new EntityDeletePostRequest(entity);
        request.setEntityRequest(entityTypeName, entityType, entityUri, fullUrl);
        request.setId(id);
        if(StringUtils.isEmpty(entity.getEntityType())){
            entity.setEntityType(entityType);
        }
        if(StringUtils.isEmpty(entity.getEntityCeilingType())){
            entity.setEntityCeilingType(entityType);
        }
        request.clearEntityInfoType();
        return request;
    }

    protected static void _fillInfoCriterias(EntityRequest request,
                                             Map<String, List<String>> requestParams,
                                             Set<String> infoFilter) {
        List<String> infoTypes = requestParams.get(GeneralRequestParameter.ENTITY_INFO_TYPE);
        if(infoTypes != null){
            for (String infoTypeString : infoTypes){
                if(infoFilter.contains(infoTypeString)){
                    request.addEntityInfoType(EntityInfoType.instance(infoTypeString));
                }
            }
        }
    }
}
