package com.taoswork.tallybook.dataservice.server.io.translator.response;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.datadomain.base.entity.validation.error.EntityValidationErrors;
import com.taoswork.tallybook.datadomain.base.entity.validation.error.FieldValidationErrors;
import com.taoswork.tallybook.datadomain.base.entity.validation.error.ValidationError;
import com.taoswork.tallybook.dataservice.PersistableResult;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dataservice.core.entityprotect.validate.EntityValueValidationException;
import com.taoswork.tallybook.dataservice.exception.NoSuchRecordException;
import com.taoswork.tallybook.dataservice.exception.ServiceException;
import com.taoswork.tallybook.dataservice.security.NoPermissionException;
import com.taoswork.tallybook.dataservice.server.io.request.*;
import com.taoswork.tallybook.dataservice.server.io.response.*;
import com.taoswork.tallybook.dataservice.server.io.response.result.EntityErrors;
import com.taoswork.tallybook.dataservice.server.io.response.result.EntityInstanceResult;
import com.taoswork.tallybook.dataservice.server.io.response.result.EntityQueryResult;
import com.taoswork.tallybook.descriptor.dataio.in.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

import java.util.Collection;
import java.util.Locale;

/**
 * Created by Gao Yuan on 2015/6/19.
 */
public class ResponseTranslator {
    private final static Logger LOGGER = LoggerFactory.getLogger(ResponseTranslator.class);

    private final MessageSource messageSource;

    public ResponseTranslator(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public void translateInstanceRequest(EntityInstancePostRequest request,
                                         EntityResponse response) {
        Entity entity = request.getEntity();
        translateRequest(request, response);
        response.setEntityCeilingType(entity.getCeilingType());
    }

    public void translateRequest(EntityRequest request,
                                 EntityResponse response) {
        response//.setResourceName(request.getResource())
                .setEntityCeilingType(request.getEntityType())
                .setEntityType(request.getEntityType())
//            .setUri(request.getUri())
        //.setEntityUri(request.getEntityUri());
        ;
    }

    public void translateInfoResponse(EntityInfoRequest request,
                                      EntityInfoResponse response,
                                      Locale locale) {
        translateRequest(request, response);

    }

    public void translateQueryResponse(EntityQueryRequest request,
                                       CriteriaQueryResult<?> criteriaResult,
                                       ServiceException e,
                                       EntityQueryResponse response,
                                       Locale locale) {
        translateRequest(request, response);

        EntityQueryResult queryResult = ResultTranslator.convertQueryResult(request, criteriaResult);
        if (criteriaResult != null) {
            response.setEntityType(criteriaResult.getEntityType());
        }
        response.setEntities(queryResult);

        handleServiceException(e, response, locale);
    }

    public void translateCreateFreshResponse(EntityCreateFreshRequest request,
                                             PersistableResult result,
                                             ServiceException e,
                                             EntityCreateFreshResponse response,
                                             Locale locale) {
        translateRequest(request, response);
        translateInstanceResponse(request, result, e, response);
        handleServiceException(e, response, locale);
    }

    public void translateCreateResponse(EntityCreateRequest request,
                                        PersistableResult result,
                                        ServiceException e,
                                        EntityCreateResponse response,
                                        Locale locale) {
        translateInstanceRequest(request, response);
        translateInstanceResponse(request, result, e, response);
        handleServiceException(e, response, locale);
    }

    public void translateReadResponse(EntityReadRequest request,
                                      PersistableResult result,
                                      ServiceException e,
                                      EntityReadResponse response,
                                      Locale locale) {
        translateRequest(request, response);
        translateInstanceResponse(request, result, e, response);
        handleServiceException(e, response, locale);
    }

    public void translateUpdateResponse(EntityUpdateRequest request,
                                        PersistableResult result,
                                        ServiceException e,
                                        EntityUpdateResponse response,
                                        Locale locale) {
        translateInstanceRequest(request, response);
        translateInstanceResponse(request, result, e, response);
        handleServiceException(e, response, locale);
    }

    public void translateDeleteResponse(EntityDeleteRequest request,
                                        boolean deleted,
                                        ServiceException e,
                                        EntityDeleteResponse response,
                                        Locale locale) {
        translateInstanceRequest(request, response);
        response.setDeleted(deleted);
        handleServiceException(e, response, locale);
    }

    public void translateCollectionEntryCreateFreshResponse(CollectionEntryCreateFreshRequest request,
                                                            PersistableResult result,
                                                            ServiceException e,
                                                            CollectionEntryCreateFreshResponse response,
                                                            Locale locale) {
        translateRequest(request, response);
        //       translateInstanceResponse(request, result, e, response);
        handleServiceException(e, response, locale);
    }

    private void translateInstanceResponse(EntityRequest request,
                                           PersistableResult result,
                                           ServiceException e,
                                           EntityInstanceResponse response) {
        if (result == null && e instanceof EntityValueValidationException) {
            result = ((EntityValueValidationException) e).getEntity();
        }
        if (result == null) {
            response.setEntityType(null);
            response.setEntity(null);
        } else {
            Persistable resultEntity = result.getValue();
            response.setEntityType(resultEntity.getClass());
            EntityInstanceResult instanceResult = ResultTranslator.convertInstanceResult(result);
            response.setEntity(instanceResult);
        }
    }

    private void handleServiceException(ServiceException e, EntityResponse response, Locale locale) {
        if (e != null) {
            EntityErrors errors = response.getErrors();
            if (e instanceof EntityValueValidationException) {
                EntityValueValidationException ve = (EntityValueValidationException) e;
                EntityValidationErrors validationError = ve.getEntityValidationError();
                if (!validationError.isValid()) {
                    this.translateValidationError(validationError, errors, locale);
                } else {
                    this.translateUnhandledServiceError(e, errors, locale);
                }
            } else if (e instanceof NoSuchRecordException) {
                this.translateNoSuchRecordError((NoSuchRecordException) e, errors, locale);
            } else if (e instanceof NoPermissionException) {
                errors.setAuthorized(false);
                this.translateNoPermissionError((NoPermissionException) e, errors, locale);
            } else {
                this.translateUnhandledServiceError(e, errors, locale);
            }
        }
    }

    private String getMessage(String code, Object[] args, Locale locale) {
        if (args == null)
            args = new Object[]{};
        return this.messageSource.getMessage(code, args, code, locale);
    }

    private void translateNoSuchRecordError(NoSuchRecordException exception, EntityErrors errors, Locale locale) {
        String errorCode = exception.getMessageCode();
        Object[] args = exception.getArgs();
        String msg = this.getMessage(errorCode, args, locale);
        errors.addGlobalErrorMessage(msg);
    }

    private void translateNoPermissionError(NoPermissionException exception, EntityErrors errors, Locale locale) {
        String errorCode = exception.getMessageCode();
        Object[] args = exception.getArgs();
        String msg = this.getMessage(errorCode, args, locale);
        errors.addGlobalErrorMessage(msg);
    }

    private void translateValidationError(EntityValidationErrors validationErrors,
                                          EntityErrors errors, Locale locale) {
        if (!validationErrors.isValid()) {
            Collection<FieldValidationErrors> fieldErrorsCollection = validationErrors.getFieldErrors();
            for (FieldValidationErrors fieldErrors : fieldErrorsCollection) {
                String fieldName = fieldErrors.getFieldName();
                Collection<ValidationError> errorMsgs = fieldErrors.getErrors();
                for (ValidationError emsg : errorMsgs) {
                    String msg = this.getMessage(emsg.getCode(), emsg.getArgs(), locale);
                    errors.addFieldErrorMessage(fieldName, msg);
                }
            }
            for (ValidationError emsg : validationErrors.getErrors()) {
                String msg = this.getMessage(emsg.getCode(), emsg.getArgs(), locale);
                errors.addGlobalErrorMessage(msg);
            }
        }
    }

    private void translateUnhandledServiceError(ServiceException serviceException, EntityErrors errors, Locale locale) {
        errors.addGlobalErrorMessage(serviceException.getMessage());
    }
}