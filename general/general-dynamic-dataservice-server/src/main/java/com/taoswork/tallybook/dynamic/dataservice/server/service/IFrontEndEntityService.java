package com.taoswork.tallybook.dynamic.dataservice.server.service;

import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.*;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.*;

import java.util.Locale;

/**
 * Created by Gao Yuan on 2015/5/29.
 */
public interface IFrontEndEntityService {

    EntityInfoResponse getInfoResponse(EntityInfoRequest request, Locale locale);

    EntityQueryResponse query(EntityQueryRequest request, Locale locale);

    /**
     * Used in the 'get' method, returns an entity instance inside of EntityCreateFreshResponse
     * @param request
     * @param locale
     * @return
     * @throws ServiceException
     */
    EntityCreateFreshResponse createFresh(EntityCreateFreshRequest request, Locale locale) ;

    EntityCreateResponse create(EntityCreateRequest request, Locale locale);

    EntityReadResponse read(EntityReadRequest readRequest, Locale locale);

    EntityUpdateResponse update(EntityUpdateRequest request, Locale locale);

    EntityDeleteResponse delete(EntityDeletePostRequest deleteRequest, Locale locale);
}
