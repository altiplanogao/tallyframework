package com.taoswork.tallybook.dynamic.dataservice.server.service;

import com.taoswork.tallybook.dynamic.datameta.description.infos.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.infos.IEntityInfo;
import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.EntityResult;
import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.DynamicEntityService;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.*;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.*;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.result.EntityInfoResult;
import com.taoswork.tallybook.dynamic.dataservice.server.io.translator.request.Request2CtoTranslator;
import com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response.ActionsBuilder;
import com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response.LinkBuilder;
import com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response.ResponseTranslator;
import com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response.ResultTranslator;
import com.taoswork.tallybook.general.authority.core.basic.Access;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * Created by Gao Yuan on 2015/5/29.
 */
public class FrontEndEntityService implements IFrontEndEntityService {

    private final DynamicEntityService dynamicEntityService;

    public FrontEndEntityService(DynamicEntityService dynamicEntityService){
        this.dynamicEntityService = dynamicEntityService;
    }

    public static FrontEndEntityService newInstance(DynamicEntityService dynamicEntityService){
        return new FrontEndEntityService(dynamicEntityService);
    }

    private void appendAuthorizedActions(EntityRequest request, EntityResponse response, ActionsBuilder.CurrentStatus currentStatus){
        Access access = dynamicEntityService.getAuthorizeAccess(request.getEntityType(), Access.Crudq);
        Collection<String> actions = ActionsBuilder.makeActions(access, currentStatus);
        response.setActions(actions);
    }

    private void appendInfoFields(EntityRequest request, EntityResponse response, Locale locale){
        Class<?> entityCeilingType = request.getEntityType();
        Class<?> entityType = response.getEntityType();

        List<IEntityInfo> entityInfos = new ArrayList<IEntityInfo>();
        for (EntityInfoType infoType : request.getEntityInfoTypes()){
            IEntityInfo entityInfo = null;
            entityInfo = dynamicEntityService.describe(entityType, infoType, locale);
            entityInfos.add(entityInfo);
        }

        EntityInfoResult infoResult = null;
        for(IEntityInfo entityInfo : entityInfos){
            if(infoResult == null){
                infoResult = ResultTranslator.convertEntityInfoResult(request, response);
            }
            if(entityInfo != null){
                infoResult.addDetail(entityInfo.getType(), entityInfo);
            }
        }

        response.setInfo(infoResult);
    }

    @Override
    public EntityResponse getInfoResponse(EntityRequest request, Locale locale){
        EntityResponse response = new EntityResponse();
        ResponseTranslator.translate(request, response);
        this.appendInfoFields(request, response, locale);
        this.appendAuthorizedActions(request, response, ActionsBuilder.CurrentStatus.Nothing);
        LinkBuilder.buildLinkForInfoResults(request.getFullUrl(), response);
        return response;
    }

    @Override
    public EntityQueryResponse query(EntityQueryRequest request, Locale locale) throws ServiceException {
        Class<?> entityType = request.getEntityType();

        CriteriaTransferObject cto = Request2CtoTranslator.translate(request);
        CriteriaQueryResult<?> data = dynamicEntityService.query(entityType, cto);

        EntityQueryResponse response = ResponseTranslator.translateQueryResponse(request, data);
        this.appendInfoFields(request, response, locale);
        this.appendAuthorizedActions(request, response, ActionsBuilder.CurrentStatus.Nothing);
        LinkBuilder.buildLinkForQueryResults(request.getFullUrl(), response);
        return response;
    }

    @Override
    public EntityCreateFreshResponse createFresh(EntityCreateFreshRequest request, Locale locale) {
        Class<?> entityType = request.getEntityType();
        Object data = dynamicEntityService.makeDissociatedObject(entityType);
        EntityResult er = new EntityResult();
        er.setEntity(data);

        EntityCreateFreshResponse response = ResponseTranslator.translateCreateFreshResponse(request, er);
        this.appendInfoFields(request, response, locale);
        this.appendAuthorizedActions(request, response, ActionsBuilder.CurrentStatus.Adding);
        LinkBuilder.buildLinkForNewInstanceResults(request.getFullUrl(), response);
        return response;
    }

    @Override
    public EntityCreateResponse create(EntityCreateRequest request, Locale locale) {
        Class<?> entityType = request.getEntityType();
        EntityCreateResponse response = new EntityCreateResponse();
        try {
            EntityResult data = dynamicEntityService.create(request.getEntity());
            ResponseTranslator.translateCreateResponse(request, data, null, response);
        }catch (ServiceException e) {
            ResponseTranslator.translateCreateResponse(request, null, e, response);
        }
        return response;
    }

    @Override
    public EntityReadResponse read(EntityReadRequest request, Locale locale) throws ServiceException {
        Class<?> entityType = request.getEntityType();
        EntityResult data = dynamicEntityService.read(entityType, request.getId());

        EntityReadResponse response = ResponseTranslator.translateReadResponse(request, data);
        this.appendInfoFields(request, response, locale);
        this.appendAuthorizedActions(request, response, ActionsBuilder.CurrentStatus.EditAheadReading);
        LinkBuilder.buildLinkForReadResults(request.getFullUrl(), response);
        return response;
    }

    @Override
    public EntityUpdateResponse update(EntityUpdateRequest request, Locale locale) throws ServiceException {
        Class<?> entityType = request.getEntityType();
        EntityResult data = dynamicEntityService.update(request.getEntity());
        EntityUpdateResponse response = ResponseTranslator.translateUpdateResponse(request, data);
        return response;
    }

    @Override
    public EntityDeleteResponse delete(EntityDeletePostRequest request, Locale locale) throws ServiceException {
        Class<?> entityType = request.getEntityType();
        boolean deleted = dynamicEntityService.delete(request.getEntity(), request.getId());
        EntityDeleteResponse response = ResponseTranslator.translateDeleteResponse(request, deleted);
        return response;
    }
}
