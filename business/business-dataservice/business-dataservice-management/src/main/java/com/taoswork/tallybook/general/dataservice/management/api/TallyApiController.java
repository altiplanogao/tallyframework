package com.taoswork.tallybook.general.dataservice.management.api;

import com.taoswork.tallybook.application.core.conf.ApplicationCommonConfig;
import com.taoswork.tallybook.dynamic.datameta.description.infos.EntityInfoType;
import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.DynamicEntityService;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.EntityQueryRequest;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.EntityReadRequest;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.translator.Parameter2RequestTranslator;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityQueryResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityReadResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.service.FrontEndEntityService;
import com.taoswork.tallybook.dynamic.dataservice.server.service.IFrontEndEntityService;
import com.taoswork.tallybook.general.dataservice.management.manager.DataServiceManager;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/6/13.
 */
@Controller(TallyApiController.TALLY_API_CONTROLLER_NAME)
@RequestMapping("/{entityResName:^[\\w|-]+$}")
public class TallyApiController  {
    public static final String TALLY_API_CONTROLLER_NAME = "TallyApiController";

    @Resource(name = DataServiceManager.COMPONENT_NAME)
    protected DataServiceManager dataServiceManager;

    @Resource(name = ApplicationCommonConfig.ERROR_MESSAGE_SOURCE)
    protected MessageSource errorMessageSource;

    private Set<String> getParamInfoFilter(){
        return EntityInfoType.ApiSupportedType;
    }

    @RequestMapping("")
    @ResponseBody
    public HttpEntity<?> getEntityList(
        HttpServletRequest request,
        @PathVariable(value="entityResName") String entityResName,
        @RequestParam MultiValueMap<String, String> requestParams) throws Exception{
        String entityType = dataServiceManager.getEntityInterfaceName(entityResName);

        EntityQueryRequest queryRequest = Parameter2RequestTranslator.makeQueryRequest(
            entityResName, entityType,
            request.getRequestURI(), UrlUtils.buildFullRequestUrl(request),
            requestParams, getParamInfoFilter());

        DynamicEntityService entityService = dataServiceManager.getDynamicEntityService(entityType);
        IFrontEndEntityService dynamicServerEntityService = FrontEndEntityService.newInstance(entityService, errorMessageSource);

        EntityQueryResponse response = dynamicServerEntityService.query(queryRequest, request.getLocale());
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<?> readEntity(
        HttpServletRequest request, Model model,
        @PathVariable(value = "entityResName") String entityResName,
        @PathVariable("id") String id,
        @PathVariable Map<String, String> pathVars) throws Exception {

        String entityType = dataServiceManager.getEntityInterfaceName(entityResName);
        EntityReadRequest readRequest = Parameter2RequestTranslator.makeReadRequest(entityResName, entityType,
            request.getRequestURI(), UrlUtils.buildFullRequestUrl(request), id);

        DynamicEntityService entityService = dataServiceManager.getDynamicEntityService(entityType);
        IFrontEndEntityService dynamicServerEntityService = FrontEndEntityService.newInstance(entityService, errorMessageSource);

        EntityReadResponse response = dynamicServerEntityService.read(readRequest, request.getLocale());
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }
 /*   @RequestMapping("/greeting")
    @ResponseBody
    public HttpEntity<Greeting> greeting(
            @RequestParam(value = "name", required = false, defaultValue = "World") String name) {

        Greeting greeting = new Greeting(String.format(TEMPLATE, name));
        greeting.add(linkTo(methodOn(GreetingController.class).greeting(name)).withSelfRel());

        return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
    }
*/
}
