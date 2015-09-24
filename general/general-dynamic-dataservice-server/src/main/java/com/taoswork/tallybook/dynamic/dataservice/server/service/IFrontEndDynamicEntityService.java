package com.taoswork.tallybook.dynamic.dataservice.server.service;

import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.*;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.*;

import java.util.Locale;

/**
 * Created by Gao Yuan on 2015/5/29.
 */
public interface IFrontEndDynamicEntityService {

    EntityResponse getInfoResponse(EntityRequest request, Locale locale);

    EntityQueryResponse queryRecords(EntityQueryRequest request, Locale locale) throws ServiceException;

    EntityReadResponse readRecord(EntityReadRequest readRequest, Locale locale) throws ServiceException;

    /**
     * Used in the 'get' method, returns an entity instance inside of EntityAddGetResponse
     * @param request
     * @param locale
     * @return
     * @throws ServiceException
     */
    EntityAddGetResponse addGetRecord(EntityAddGetRequest request, Locale locale) throws ServiceException;

    EntityAddPostResponse addPostRecord(EntityAddPostRequest request, Locale locale) throws ServiceException;

    EntityUpdatePostResponse updateRecord(EntityUpdatePostRequest request, Locale locale) throws ServiceException;
}
