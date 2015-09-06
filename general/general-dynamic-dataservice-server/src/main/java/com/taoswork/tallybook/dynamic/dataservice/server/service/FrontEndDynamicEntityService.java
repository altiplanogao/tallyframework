package com.taoswork.tallybook.dynamic.dataservice.server.service;

import com.taoswork.tallybook.dynamic.datameta.description.infos.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.infos.IEntityInfo;
import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.DynamicEntityService;
import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.EntityActionNames;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.EntityAddGetRequest;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.EntityQueryRequest;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.EntityReadRequest;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.EntityRequest;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityAddGetResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityQueryResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityReadResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityResponse;
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
public class FrontEndDynamicEntityService implements IFrontEndDynamicEntityService {
    /**
     * Actions:
     * Grid:
     *      CRUD
     * Read:
     *      Edit, Delete
     * Edit:
     *      Save, Delete
     * Add:
     *      Save
     */

    public FrontEndDynamicEntityService(DynamicEntityService dynamicEntityService){
        this.dynamicEntityService = dynamicEntityService;
    }

    public static FrontEndDynamicEntityService newInstance(DynamicEntityService dynamicEntityService){
        return new FrontEndDynamicEntityService(dynamicEntityService);
    }

    private final DynamicEntityService dynamicEntityService;

//    @Resource(name = MetaInfoService.SERVICE_NAME)
//    private MetaInfoService metaDescriptionService;
//
//    @Resource(name = DynamicEntityMetadataAccess.COMPONENT_NAME)
//    private DynamicEntityMetadataAccess dynamicEntityMetadataAccess;
    private void appendAuthorizedActions(EntityRequest request, EntityResponse response, ActionsBuilder.CurrentStatus currentStatus){
        Access access = dynamicEntityService.getAuthorizeAccess(request.getEntityType(), Access.Crudq);
        Collection<String> actions = ActionsBuilder.makeActions(access, currentStatus);
        response.setActions(actions);
    }

    private void appendInfoFields(EntityRequest request, EntityResponse response, Locale locale){
        Class<?> entityType = request.getEntityType();

        List<IEntityInfo> entityInfos = new ArrayList<IEntityInfo>();
        for (EntityInfoType infoType : request.getEntityInfoTypes()){
            IEntityInfo entityInfo = null;
            entityInfo = dynamicEntityService.describe(entityType, infoType, locale);
            entityInfos.add(entityInfo);
        }

        EntityInfoResult infoResult = null;
        for(IEntityInfo entityInfo : entityInfos){
            if(infoResult == null){
                infoResult = ResultTranslator.convertEntityInfoResult(request);
            }
            if(entityInfo != null){
                infoResult.addDetail(entityInfo.getType(), entityInfo);
            }
        }

        response.setInfo(infoResult);
    }

    @Override
    public EntityQueryResponse queryRecords(EntityQueryRequest request, Locale locale) throws ServiceException {
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
    public EntityResponse getInfoResponse(EntityRequest request, Locale locale){
        EntityResponse response = new EntityResponse();
        ResponseTranslator.translate(request, response);
        this.appendInfoFields(request, response, locale);
        this.appendAuthorizedActions(request, response, ActionsBuilder.CurrentStatus.Nothing);
        LinkBuilder.buildLinkForInfoResults(request.getFullUrl(), response);
        return response;
    }

    @Override
    public EntityReadResponse readRecord(EntityReadRequest request, Locale locale) throws ServiceException {
        Class<?> entityType = request.getEntityType();
        Object data = dynamicEntityService.find(entityType, request.getId());

        EntityReadResponse response = ResponseTranslator.translateReadResponse(request, data);
        this.appendInfoFields(request, response, locale);
        this.appendAuthorizedActions(request, response, ActionsBuilder.CurrentStatus.EditAheadReading);
        LinkBuilder.buildLinkForReadResults(request.getFullUrl(), response);
        return response;
    }

    @Override
    public EntityAddGetResponse addRecord(EntityAddGetRequest request, Locale locale) throws ServiceException {
        Class<?> entityType = request.getEntityType();
        Object data = dynamicEntityService.makeDissociatedObject(entityType);
        EntityAddGetResponse response = ResponseTranslator.translateAddGetResponse(request, data);
        this.appendInfoFields(request, response, locale);
        this.appendAuthorizedActions(request, response, ActionsBuilder.CurrentStatus.Adding);
        LinkBuilder.buildLinkForAddGetResults(request.getFullUrl(), response);
        return response;
    }
}
