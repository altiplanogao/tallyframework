package com.taoswork.tallybook.dataservice.server.service;

import com.taoswork.tallybook.authority.core.Access;
import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.datadomain.base.restful.EntityAction;
import com.taoswork.tallybook.datadomain.base.restful.EntityActionPaths;
import com.taoswork.tallybook.dataservice.IDataService;
import com.taoswork.tallybook.dataservice.PersistableResult;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.PropertyFilterCriteria;
import com.taoswork.tallybook.dataservice.exception.ServiceException;
import com.taoswork.tallybook.dataservice.manage.DataServiceManager;
import com.taoswork.tallybook.dataservice.server.io.request.*;
import com.taoswork.tallybook.dataservice.server.io.response.*;
import com.taoswork.tallybook.dataservice.server.io.response.result.EntityInfoResult;
import com.taoswork.tallybook.dataservice.server.io.response.result.InfoTypeOption;
import com.taoswork.tallybook.dataservice.server.io.translator.request.Request2CtoTranslator;
import com.taoswork.tallybook.dataservice.server.io.translator.response.ActionsBuilder;
import com.taoswork.tallybook.dataservice.server.io.translator.response.LinkBuilder;
import com.taoswork.tallybook.dataservice.server.io.translator.response.ResponseTranslator;
import com.taoswork.tallybook.dataservice.server.io.translator.response.ResultTranslator;
import com.taoswork.tallybook.dataservice.service.EntityMetaAccess;
import com.taoswork.tallybook.dataservice.service.IEntityService;
import com.taoswork.tallybook.descriptor.dataio.reference.EntityFetchException;
import com.taoswork.tallybook.descriptor.dataio.reference.EntityRecords;
import com.taoswork.tallybook.descriptor.dataio.reference.ExternalReference;
import com.taoswork.tallybook.descriptor.dataio.reference.IEntityRecordsFetcher;
import com.taoswork.tallybook.descriptor.description.infos.EntityInfoType;
import com.taoswork.tallybook.descriptor.description.infos.IEntityInfo;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
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
    private final IEntityService entityService;
    private final MessageSource errorMessageSource;

    public FrontEndEntityService(DataServiceManager dataServiceManager, IDataService dataService) {
        this.dataServiceManager = dataServiceManager;
        this.dataService = dataService;
        this.entityService = dataService.getService(IEntityService.COMPONENT_NAME);
        this.errorMessageSource = dataService.getService(IDataService.ERROR_MESSAGE_SOURCE_BEAN_NAME);
    }

    public static FrontEndEntityService newInstance(DataServiceManager dataServiceManager, IDataService dataService) {
        return new FrontEndEntityService(dataServiceManager, dataService);
    }

    private void appendAuthorizedActions(EntityRequest request, EntityResponse response, ActionsBuilder.CurrentStatus currentStatus) {
        Access access = entityService.getAuthorizeAccess(request.getEntityType(), Access.Crudq);
        Collection<EntityAction> actions = ActionsBuilder.makeActions(access, currentStatus);
        Collection<String> actionsString = EntityAction.convertToTypes(actions, new HashSet<String>());
        response.setActions(actionsString);
    }

    private void appendInfoFields(EntityRequest request, EntityResponse response, Locale locale, InfoTypeOption infoTypeOption, boolean withHierarchy) {
        final Class<? extends Persistable> useType;

        switch (infoTypeOption) {
            case UseCeiling:
                useType = response.getEntityCeilingType();
                break;
            case UseFactual:
                useType = response.getEntityType();
                break;
            default:
                throw new IllegalArgumentException();
        }

        if (useType == null)
            return;

        List<IEntityInfo> entityInfos = new ArrayList<IEntityInfo>();
        for (EntityInfoType infoType : request.getEntityInfoTypes()) {
            IEntityInfo entityInfo = entityService.describe(useType, withHierarchy, infoType, locale);
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
                infoResult.addDetail(entityInfo.getInfoType(), entityInfo);
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
        this.appendInfoFields(request, response, locale, InfoTypeOption.UseCeiling, false);
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
            result = entityService.query(entityType, cto, externalReference);
            if (externalReference.hasReference()) {
                fillExternalReference(externalReference);
            }
        } catch (ServiceException e) {
            se = e;
        } finally {
            responseTranslator().translateQueryResponse(request, result, se, response, locale);
            this.appendInfoFields(request, response, locale, InfoTypeOption.UseCeiling, true);
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
            result = entityService.makeDissociatedPersistable(entityType);
        } catch (ServiceException e) {
            se = e;
        } finally {
            responseTranslator().translateCreateFreshResponse(request, result, se, response, locale);
            this.appendInfoFields(request, response, locale, InfoTypeOption.UseFactual, false);
            this.appendAuthorizedActions(request, response, ActionsBuilder.CurrentStatus.Adding);
            LinkBuilder.buildLinkForNewInstanceResults(request, response);
        }
        return response;
    }

    @Override
    public EntityCreateResponse create(EntityCreateRequest request, Locale locale) {
        String beanUriTemplate = LinkBuilder.buildLinkForReadInstance(request.getResourceName());
        EntityCreateResponse response = new EntityCreateResponse(request.getUri(), beanUriTemplate);
        PersistableResult result = null;
        ServiceException se = null;
        try {
            result = entityService.create(request.getEntity());
            Map<String, Object> m = new MapBuilder<String, Object>().append(EntityActionPaths.ID_KEY, result.getIdValue());
            String beanUri = (new UriTemplate(beanUriTemplate)).expand(m).toString();
            response = new EntityCreateResponse(request.getUri(), beanUri);
        } catch (ServiceException e) {
            se = e;
        } finally {
            responseTranslator().translateCreateResponse(request, result, se, response, locale);
            this.appendInfoFields(request, response, locale, InfoTypeOption.UseFactual, false);
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
            result = entityService.read(entityType, request.getId(), externalReference);
            if (externalReference.hasReference()) {
                fillExternalReference(externalReference);
            }
        } catch (ServiceException e) {
            se = e;
        } finally {
            responseTranslator().translateReadResponse(request, result, se, response, locale);
            this.appendInfoFields(request, response, locale, InfoTypeOption.UseFactual, false);
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
            result = entityService.update(request.getEntity());
        } catch (ServiceException e) {
            se = e;
        } finally {
            responseTranslator().translateUpdateResponse(request, result, se, response, locale);
            this.appendInfoFields(request, response, locale, InfoTypeOption.UseFactual, false);
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
            deleted = entityService.delete(request.getEntity(), request.getId());
        } catch (ServiceException e) {
            se = e;
        } finally {
            responseTranslator().translateDeleteResponse(request, deleted, se, response, locale);
        }
        return response;
    }

    @Override
    public CollectionEntryCreateFreshResponse collectionEntryCreateFresh(CollectionEntryCreateFreshRequest request, Locale locale) {
        CollectionEntryCreateFreshResponse response = new CollectionEntryCreateFreshResponse(request.getUri());
        PersistableResult result = null;
        ServiceException se = null;
        try {
            Class<?> entityType = request.getEntryPresentationClass();
            result = entityService.makeDissociatedPersistable(request.getEntryPresentationClass());
        } catch (ServiceException e) {
            se = e;
        } finally {
            responseTranslator().translateCollectionEntryCreateFreshResponse(request, result, se, response, locale);
            this.appendInfoFields(request, response, locale, InfoTypeOption.UseFactual, false);
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
                            IDataService externalDataService = dataServiceManager.getDataService(entityTypeName);
                            IEntityService externalEntityService = externalDataService.getService(IEntityService.COMPONENT_NAME);
                            EntityMetaAccess externalMetaAccess = externalDataService.getService(EntityMetaAccess.COMPONENT_NAME);
                            IClassMeta classMeta = externalMetaAccess.getClassMeta(entityType, false);
                            Field idField = classMeta.getIdField();
                            CriteriaTransferObject cto = new CriteriaTransferObject();
                            cto.setPageSize(ids.size());
                            List<String> idStrings = new ArrayList<String>();
                            for (Object id : ids) {
                                if (id != null)
                                    idStrings.add(id.toString());
                            }
                            cto.addFilterCriteria(new PropertyFilterCriteria(classMeta.getIdFieldName(), idStrings));
                            CriteriaQueryResult cqr = externalEntityService.query(entityType, cto);
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