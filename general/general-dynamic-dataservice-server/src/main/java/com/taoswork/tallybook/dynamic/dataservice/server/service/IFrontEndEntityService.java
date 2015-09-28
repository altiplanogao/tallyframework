package com.taoswork.tallybook.dynamic.dataservice.server.service;

import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.*;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.*;

import java.util.Locale;

/**
 * Created by Gao Yuan on 2015/5/29.
 */
public interface IFrontEndEntityService {

    EntityResponse getInfoResponse(EntityRequest request, Locale locale);

    EntityQueryResponse query(EntityQueryRequest request, Locale locale) throws ServiceException;

    /**
     * Used in the 'get' method, returns an entity instance inside of EntityAddGetResponse
     * @param request
     * @param locale
     * @return
     * @throws ServiceException
     */
    EntityAddGetResponse createGet(EntityAddGetRequest request, Locale locale) throws ServiceException;

    EntityAddPostResponse createPost(EntityAddPostRequest request, Locale locale) throws ServiceException;

    EntityReadResponse read(EntityReadRequest readRequest, Locale locale) throws ServiceException;

    EntityUpdatePostResponse update(EntityUpdatePostRequest request, Locale locale) throws ServiceException;

    EntityDeletePostResponse delete(EntityDeletePostRequest deleteRequest, Locale locale) throws ServiceException;
}
