package com.taoswork.tallybook.dynamic.dataservice.server.service;

import com.taoswork.tallybook.dynamic.dataio.reference.*;
import com.taoswork.tallybook.dynamic.datameta.description.infos.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.infos.IEntityInfo;
import com.taoswork.tallybook.dynamic.datameta.metadata.IClassMetadata;
import com.taoswork.tallybook.dynamic.dataservice.IDataService;
import com.taoswork.tallybook.dynamic.dataservice.core.dao.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.core.dao.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dynamic.dataservice.core.dao.query.dto.PropertyFilterCriteria;
import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.DynamicEntityService;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.dynamic.dataservice.manage.DataServiceManager;
import com.taoswork.tallybook.dynamic.dataservice.server.io.EntityActionPaths;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.*;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.*;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.result.EntityInfoResult;
import com.taoswork.tallybook.dynamic.dataservice.server.io.translator.request.Request2CtoTranslator;
import com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response.ActionsBuilder;
import com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response.LinkBuilder;
import com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response.ResponseTranslator;
import com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response.ResultTranslator;
import com.taoswork.tallybook.general.authority.core.basic.Access;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;
import com.taoswork.tallybook.general.extension.collections.MapBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.hateoas.UriTemplate;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by Gao Yuan on 2015/5/29.
 */
public class FrontEndEntityService implements IFrontEndEntityService {
    private final static Logger LOGGER = LoggerFactory.getLogger(FrontEndEntityService.class);

    private final IDataService dataService;
    private final DataServiceManager dataServiceManager;
    private final DynamicEntityService dynamicEntityService;
    private final MessageSource errorMessageSource;

    public FrontEndEntityService(DataServiceManager dataServiceManager, IDataService dataService) {
        this.dataServiceManager = dataServiceManager;
        this.dataService = dataService;
        this.dynamicEntityService = dataService.getService(DynamicEntityService.COMPONENT_NAME);
        this.errorMessageSource = dataService.getService(IDataService.ERROR_MESSAGE_SOURCE_BEAN_NAME);
    }

    public static FrontEndEntityService newInstance(DataServiceManager dataServiceManager, IDataService dataService) {
        return new FrontEndEntityService(dataServiceManager, dataService);
    }

    private void appendAuthorizedActions(EntityRequest request, EntityResponse response, ActionsBuilder.CurrentStatus currentStatus) {
        Access access = dynamicEntityService.getAuthorizeAccess(request.getEntityType(), Access.Crudq);
        Collection<String> actions = ActionsBuilder.makeActions(access, currentStatus);
        response.setActions(actions);
    }

    private void appendInfoFields(EntityRequest request, EntityResponse response, Locale locale) {
        Class<? extends Persistable> entityCeilingType = request.getEntityType();
        Class<? extends Persistable> entityType = response.getEntityType();
        if (entityType == null)
            return;

        List<IEntityInfo> entityInfos = new ArrayList<IEntityInfo>();
        for (EntityInfoType infoType : request.getEntityInfoTypes()) {
            IEntityInfo entityInfo = dynamicEntityService.describe(entityType, infoType, locale);
            entityInfos.add(entityInfo);
        }

        EntityInfoResult infoResult = null;
        for (IEntityInfo entityInfo : entityInfos) {
            if (infoResult == null) {
                infoResult = ResultTranslator.convertInfoResult(request, response);
            }
            if (entityInfo != null) {
                infoResult.setIdFieldIfEmpty(entityInfo.getIdField());
                infoResult.setNameFieldIfEmpty(entityInfo.getNameField());
                infoResult.addDetail(entityInfo.getType(), entityInfo);
            }
        }

        response.setInfos(infoResult);
    }

    private ResponseTranslator responseTranslator() {
        return new ResponseTranslator(errorMessageSource);
    }

    @Override
    public EntityInfoResponse getInfoResponse(EntityInfoRequest request, Locale locale) {
        EntityInfoResponse response = new EntityInfoResponse();
        responseTranslator().translateInfoResponse(request, response, locale);
        this.appendInfoFields(request, response, locale);
        this.appendAuthorizedActions(request, response, ActionsBuilder.CurrentStatus.Nothing);
        LinkBuilder.buildLinkForInfoResults(request, response);
        return response;
    }

    @Override
    public EntityQueryResponse query(EntityQueryRequest request, Locale locale) {
        Class<? extends Persistable> entityType = request.getEntityType();
        EntityQueryResponse response = new EntityQueryResponse(request.getUri());
        CriteriaQueryResult<? extends Persistable> result = null;
        ServiceException se = null;
        try {
            CriteriaTransferObject cto = Request2CtoTranslator.translate(request);
            ExternalReference externalReference = new ExternalReference();
            result = dynamicEntityService.query(entityType, cto, externalReference);
            if (externalReference.hasReference()) {
                fillExternalReference(externalReference);
            }
        } catch (ServiceException e) {
            se = e;
        } finally {
            responseTranslator().translateQueryResponse(request, result, se, response, locale);
            this.appendInfoFields(request, response, locale);
            this.appendAuthorizedActions(request, response, ActionsBuilder.CurrentStatus.Nothing);
            LinkBuilder.buildLinkForQueryResults(request, response);
        }
        return response;
    }

    @Override
    public EntityCreateFreshResponse createFresh(EntityCreateFreshRequest request, Locale locale) {
        EntityCreateFreshResponse response = new EntityCreateFreshResponse(request.getUri());
        PersistableResult result = null;
        ServiceException se = null;
        try {
            Class<? extends Persistable> entityType = request.getEntityType();
            result = dynamicEntityService.makeDissociatedPersistable(entityType);
        } catch (ServiceException e) {
            se = e;
        } finally {
            responseTranslator().translateCreateFreshResponse(request, result, se, response, locale);
            this.appendInfoFields(request, response, locale);
            this.appendAuthorizedActions(request, response, ActionsBuilder.CurrentStatus.Adding);
            LinkBuilder.buildLinkForNewInstanceResults(request, response);
        }
        return response;
    }

    @Override
    public EntityCreateResponse create(EntityCreateRequest request, Locale locale) {
        String beanUriTemplate =  LinkBuilder.buildLinkForReadInstance(request.getResourceName());
        EntityCreateResponse response = new EntityCreateResponse(request.getUri(), beanUriTemplate);
        PersistableResult result = null;
        ServiceException se = null;
        try {
            result = dynamicEntityService.create(request.getEntity());
            Map<String, Object> m = new MapBuilder<String, Object>().append(EntityActionPaths.ID_KEY, result.getIdValue());
            String beanUri = (new UriTemplate(beanUriTemplate)).expand(m).toString();
            response = new EntityCreateResponse(request.getUri(), beanUri);
        } catch (ServiceException e) {
            se = e;
        } finally {
            responseTranslator().translateCreateResponse(request, result, se, response, locale);
            this.appendInfoFields(request, response, locale);
            this.appendAuthorizedActions(request, response, ActionsBuilder.CurrentStatus.Adding);
        }
        return response;
    }

    @Override
    public EntityReadResponse read(EntityReadRequest request, Locale locale) {
        Class<? extends Persistable> entityType = request.getEntityType();
        EntityReadResponse response = new EntityReadResponse(request.getUri());
        PersistableResult result = null;
        ServiceException se = null;
        try {
            ExternalReference externalReference = new ExternalReference();
            result = dynamicEntityService.read(entityType, request.getId(), externalReference);
            if (externalReference.hasReference()) {
                fillExternalReference(externalReference);
            }
        } catch (ServiceException e) {
            se = e;
        } finally {
            responseTranslator().translateReadResponse(request, result, se, response, locale);
            this.appendInfoFields(request, response, locale);
            this.appendAuthorizedActions(request, response, ActionsBuilder.CurrentStatus.EditAheadReading);
            LinkBuilder.buildLinkForReadResults(request, response);
        }
        return response;
    }

    @Override
    public EntityUpdateResponse update(EntityUpdateRequest request, Locale locale) {
        EntityUpdateResponse response = new EntityUpdateResponse(request.getUri());
        PersistableResult result = null;
        ServiceException se = null;
        try {
            result = dynamicEntityService.update(request.getEntity());
        } catch (ServiceException e) {
            se = e;
        } finally {
            responseTranslator().translateUpdateResponse(request, result, se, response, locale);
            this.appendInfoFields(request, response, locale);
            this.appendAuthorizedActions(request, response, ActionsBuilder.CurrentStatus.EditAheadReading);
        }
        return response;
    }

    @Override
    public EntityDeleteResponse delete(EntityDeleteRequest request, Locale locale) {
        EntityDeleteResponse response = new EntityDeleteResponse(request.getUri());
        boolean deleted = false;
        ServiceException se = null;
        try {
            deleted = dynamicEntityService.delete(request.getEntity(), request.getId());
        } catch (ServiceException e) {
            se = e;
        } finally {
            responseTranslator().translateDeleteResponse(request, deleted, se, response, locale);
        }
        return response;
    }

    @Override
    public CollectionEntryCreateFreshResponse collectionEntryCreateFresh(CollectionEntryCreateFreshRequest request, Locale locale) {
        CollectionEntryCreateFreshResponse response = new CollectionEntryCreateFreshResponse();
        ObjectResult result = null;
        ServiceException se = null;
        try {
            Class<?> entityType = request.getEntityType();
            result = dynamicEntityService.makeDissociatedObject(entityType);
        } catch (ServiceException e) {
            se = e;
        } finally {
            responseTranslator().translateCollectionEntryCreateFreshResponse(request, result, se, response, locale);
            this.appendInfoFields(request, response, locale);
            this.appendAuthorizedActions(request, response, ActionsBuilder.CurrentStatus.Adding);
        }
        return response;
    }

    private void fillExternalReference(ExternalReference externalReference) throws ServiceException {
        if (null != externalReference) {
            try {
                Map<String, EntityRecords> records = externalReference.calcReferenceValue(new IEntityRecordsFetcher() {
                    @Override
                    public Map<Object, Object> fetch(Class entityType, Collection<Object> ids) throws EntityFetchException {
                        try {
                            String entityTypeName = entityType.getName();
                            Map<Object, Object> result = new HashMap<Object, Object>();
                            IDataService externalUsingDataService = dataServiceManager.getDataService(entityTypeName);
                            DynamicEntityService externalUsingEntityService = externalUsingDataService.getService(DynamicEntityService.COMPONENT_NAME);
                            DynamicEntityMetadataAccess externalUsingMetadataAccess = externalUsingDataService.getService(DynamicEntityMetadataAccess.COMPONENT_NAME);
                            IClassMetadata classMetadata = externalUsingMetadataAccess.getClassMetadata(entityType, false);
                            Field idField = classMetadata.getIdField();
                            CriteriaTransferObject cto = new CriteriaTransferObject();
                            cto.setPageSize(ids.size());
                            List<String> idStrings = new ArrayList<String>();
                            for (Object id : ids) {
                                if (id != null)
                                    idStrings.add(id.toString());
                            }
                            cto.addFilterCriteria(new PropertyFilterCriteria(classMetadata.getIdFieldName(), idStrings));
                            CriteriaQueryResult cqr = externalUsingEntityService.query(entityType, cto);
                            if (cqr.getTotalCount() > 0) {
                                List externalEntities = cqr.getEntityCollection();
                                for (Object extEntity : externalEntities) {
                                    Object id = idField.get(extEntity);
                                    result.put(id, extEntity);
                                }
                            }

                            return result;
                        } catch (ServiceException e) {
                            throw new EntityFetchException(e);
                        } catch (IllegalAccessException e) {
                            throw new EntityFetchException(e);
                        }
                    }
                });
                externalReference.fillReferencingSlots(records);
            } catch (EntityFetchException e) {
                Throwable innerException = e.getCause();
                if (innerException instanceof ServiceException) {
                    throw (ServiceException) innerException;
                } else {
                    throw new ServiceException(innerException);
                }
            }
        }
    }
}