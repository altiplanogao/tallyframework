package com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response;

import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.Entity;
import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.EntityResult;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.dynamic.dataservice.core.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.core.security.NoPermissionException;
import com.taoswork.tallybook.dynamic.dataservice.core.validate.EntityValidationException;
import com.taoswork.tallybook.dynamic.dataservice.core.validate.EntityValidationResult;
import com.taoswork.tallybook.dynamic.dataservice.core.validate.field.result.ErrorMessage;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.*;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.*;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.result.EntityErrors;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.result.EntityInstanceResult;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.result.EntityQueryResult;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;
import java.util.Map;

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
        response.setResourceName(request.getResourceName())
            .setEntityCeilingType(entity.getEntityCeilingType())
            .setEntityType(entity.getEntityType())
            .setBaseUrl(request.getResourceURI());
    }

    public void translateRequest(EntityRequest request,
                                 EntityResponse response) {
        response.setResourceName(request.getResourceName())
            .setEntityCeilingType(request.getEntityType())
            .setEntityType(request.getEntityType())
            .setBaseUrl(request.getResourceURI());
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
        response.setEntityType(criteriaResult.getEntityType());
        response.setEntities(queryResult);

        handleServiceException(e, response, locale);
    }

    public void translateCreateFreshResponse(EntityCreateFreshRequest request,
                                             EntityResult result,
                                             ServiceException e,
                                             EntityCreateFreshResponse response,
                                             Locale locale) {
        translateRequest(request, response);
        translateInstanceResponse(request, result, e, response);
        handleServiceException(e, response, locale);
    }

    public void translateCreateResponse(EntityCreateRequest request,
                                        EntityResult result,
                                        ServiceException e,
                                        EntityCreateResponse response,
                                        Locale locale) {
        translateInstanceRequest(request, response);
        translateInstanceResponse(request, result, e, response);
        handleServiceException(e, response, locale);
    }

    public void translateReadResponse(EntityReadRequest request,
                                      EntityResult result,
                                      ServiceException e,
                                      EntityReadResponse response,
                                      Locale locale) {
        translateRequest(request, response);
        translateInstanceResponse(request, result, e, response);
        handleServiceException(e, response, locale);
    }

    public void translateUpdateResponse(EntityUpdateRequest request,
                                        EntityResult result,
                                        ServiceException e,
                                        EntityUpdateResponse response,
                                        Locale locale) {
        translateInstanceRequest(request, response);
        translateInstanceResponse(request, result, e, response);
        handleServiceException(e, response, locale);
    }

    public void translateDeleteResponse(EntityDeletePostRequest request,
                                        boolean deleted,
                                        ServiceException e,
                                        EntityDeleteResponse response,
                                        Locale locale) {
        translateInstanceRequest(request, response);
        response.setSuccess(deleted);
        handleServiceException(e, response, locale);
    }

    private void translateInstanceResponse(EntityRequest request,
                                           EntityResult result,
                                           ServiceException e,
                                           EntityInstanceResponse response) {
        if (result == null && e instanceof EntityValidationException) {
            result = ((EntityValidationException) e).getEntity();
        }
        if (result == null) {
            response.setEntityType(null);
            response.setEntity(null);
        } else {
            Persistable resultEntity = result.getEntity();
            response.setEntityType(resultEntity.getClass());
            EntityInstanceResult instanceResult = ResultTranslator.convertInstanceResult(result);
            response.setEntity(instanceResult);
        }
    }

    private void handleServiceException(ServiceException e, EntityResponse response, Locale locale) {
        if (e != null) {
            EntityErrors errors = response.getErrors();
            if (e instanceof EntityValidationException) {
                EntityValidationException ve = (EntityValidationException) e;
                EntityValidationResult validationResult = ve.getEntityValidationResult();
                if (!validationResult.isValid()) {
                    this.translateValidationError(validationResult, errors, locale);
                } else {
                    errors.setUnhandledException(e);
                }
            } else if (e instanceof NoPermissionException) {
                errors.setAuthorized(false);
                this.translateNoPermissionError((NoPermissionException) e, errors, locale);
            } else {
                errors.setUnhandledException(e);
            }
        }
    }

    private void translateNoPermissionError(NoPermissionException exception, EntityErrors errors, Locale locale) {
        String errorCode = exception.getMessageCode();
        Object[] args = exception.getArgs();
        String msg = this.messageSource.getMessage(errorCode, args, locale);
        errors.addGlobalErrorMessage(msg);
    }

    private void translateValidationError(EntityValidationResult validationResult, EntityErrors errors, Locale locale) {
        if (!validationResult.isValid()) {
            Map<String, List<ErrorMessage>> fieldErrors = validationResult.getFieldErrors();
            for (Map.Entry<String, List<ErrorMessage>> fieldError : fieldErrors.entrySet()) {
                String fieldName = fieldError.getKey();
                List<ErrorMessage> errorMsgs = fieldError.getValue();
                for (ErrorMessage emsg : errorMsgs) {
                    Object[] args = emsg.getArgs();
                    if (args == null)
                        args = new Object[]{};
                    String msg = this.messageSource.getMessage(emsg.getCode(), args, locale);
                    errors.addFieldErrorMessage(fieldName, msg);
                }
            }
        }
    }
}