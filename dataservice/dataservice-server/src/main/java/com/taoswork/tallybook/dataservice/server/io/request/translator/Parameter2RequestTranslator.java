package com.taoswork.tallybook.dataservice.server.io.request.translator;

import com.taoswork.tallybook.dataservice.core.dao.query.dto.PropertyFilterCriteria;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.PropertySortCriteria;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.SortDirection;
import com.taoswork.tallybook.dataservice.server.io.request.*;
import com.taoswork.tallybook.dataservice.server.io.request.parameter.CollectionEntryTypeParameter;
import com.taoswork.tallybook.dataservice.server.io.request.parameter.EntityTypeParameter;
import com.taoswork.tallybook.descriptor.dataio.in.Entity;
import com.taoswork.tallybook.descriptor.description.infos.EntityInfoType;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;

import java.net.URI;
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
                    if (null != sortDirection) {
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

        int startIndex = MapUtils.getIntValue(integerValues, GeneralRequestParameter.REQUEST_START_INDEX, 0);
        Integer maxIndex = integerValues.get(GeneralRequestParameter.REQUEST_MAX_INDEX);
        Integer pageSize = integerValues.get(GeneralRequestParameter.REQUEST_PAGE_SIZE);

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

    public static EntityInfoRequest makeInfoRequest(EntityTypeParameter entityTypeParam,
                                                    URI fullUri,
                                                    MultiValueMap<String, String> requestParams, Set<String> infoFilter) {
        EntityInfoRequest request = new EntityInfoRequest(entityTypeParam, fullUri);
        _fillInfoCriterias(request, requestParams, infoFilter);

        return request;
    }

    public static EntityQueryRequest makeQueryRequest(EntityTypeParameter entityTypeParam,
                                                      URI fullUri,
                                                      MultiValueMap<String, String> requestParams, Set<String> infoFilter) {
        EntityQueryRequest request = new EntityQueryRequest(entityTypeParam, fullUri);
        _queryRequestSetPropertyCriterias(request, requestParams);
        _fillInfoCriterias(request, requestParams, infoFilter);

        return request;
    }

    public static EntityCreateFreshRequest makeCreateFreshRequest(EntityTypeParameter entityTypeParam,
                                                                  URI fullUri) {
        EntityCreateFreshRequest request = new EntityCreateFreshRequest(entityTypeParam, fullUri);
        request.addEntityInfoType(EntityInfoType.Form);
        return request;
    }

    public static EntityCreateRequest makeCreateRequest(EntityTypeParameter entityTypeParam,
                                                        URI fullUri, Entity entity) {
        EntityCreateRequest request = new EntityCreateRequest(entityTypeParam, fullUri, entity);
        request.clearEntityInfoType();
        request.addEntityInfoType(EntityInfoType.Form);
        return request;
    }

    public static EntityReadRequest makeReadRequest(EntityTypeParameter entityTypeParam,
                                                    URI fullUri, String id) {
        EntityReadRequest request = new EntityReadRequest(entityTypeParam, fullUri);
        request.setId(id);
        request.addEntityInfoType(EntityInfoType.Form);

        return request;
    }

    public static EntityUpdateRequest makeUpdateRequest(EntityTypeParameter entityTypeParam,
                                                        URI fullUri, Entity entity) {
        EntityUpdateRequest request = new EntityUpdateRequest(entityTypeParam, fullUri, entity);
        request.clearEntityInfoType();
        request.addEntityInfoType(EntityInfoType.Form);
        return request;
    }

    public static EntityDeleteRequest makeDeleteRequest(EntityTypeParameter entityTypeParam,
                                                        URI fullUri, String id, Entity entity) {
        EntityDeleteRequest request = new EntityDeleteRequest(entityTypeParam, fullUri, entity);
        request.setId(id);
        if (entity.getType() == null) {
            entity.setType(entityTypeParam.getType());
        }
        if (entity.getCeilingType() == null) {
            entity.setCeilingType(entityTypeParam.getCeilingType());
        }
        request.clearEntityInfoType();
        return request;
    }

    protected static void _fillInfoCriterias(EntityRequest request,
                                             Map<String, List<String>> requestParams,
                                             Set<String> infoFilter) {
        List<String> infoTypes = requestParams.get(GeneralRequestParameter.ENTITY_INFO_TYPE);
        if (infoTypes != null) {
            for (String infoTypeString : infoTypes) {
                if (infoFilter.contains(infoTypeString)) {
                    request.addEntityInfoType(EntityInfoType.instance(infoTypeString));
                }
            }
        }
    }

    public static CollectionEntryCreateFreshRequest makeCollectionCreateFreshRequest(EntityTypeParameter entityTypeParam, URI fullUri,
                                                                                     CollectionEntryTypeParameter collectionEntryTypeParameter) {
        CollectionEntryCreateFreshRequest request = new CollectionEntryCreateFreshRequest(entityTypeParam, fullUri, collectionEntryTypeParameter);
        request.addEntityInfoType(EntityInfoType.Form);
        return request;
    }
}
