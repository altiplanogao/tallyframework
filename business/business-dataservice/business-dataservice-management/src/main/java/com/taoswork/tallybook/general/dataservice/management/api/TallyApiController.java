package com.taoswork.tallybook.general.dataservice.management.api;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.taoswork.tallybook.dynamic.dataservice.server.io.request.EntityQueryRequest;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.GeneralRequestParameter;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.translator.Parameter2RequestTranslator;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityInfoResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityQueryResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.range.QueryResultRange;
import com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response.ResponseTranslator;
import com.taoswork.tallybook.dynamic.dataservice.server.service.DynamicServerEntityService;
import com.taoswork.tallybook.general.dataservice.management.manager.DataServiceManager;
import com.taoswork.tallybook.general.extension.utils.CloneUtility;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/6/13.
 */
@Controller(TallyApiController.TALLY_API_CONTROLLER_NAME)
public class TallyApiController  {
    public static final String TALLY_API_CONTROLLER_NAME = "api";

    @Resource(name = DataServiceManager.COMPONENT_NAME)
    protected DataServiceManager dataServiceManager;

    @RequestMapping("/{entityKey:^[\\w|-]+$}")
    @ResponseBody
    public HttpEntity<?> getEntityList(
            @PathVariable(value="entityKey") String entityKey,
            @RequestParam MultiValueMap<String, String> requestParams){
        String entityType = dataServiceManager.getEntityInterfaceName(entityKey);
        DynamicServerEntityService dynamicServerEntityService = dataServiceManager.getDynamicServerEntityService(entityType);

        EntityQueryRequest request = Parameter2RequestTranslator.makeQueryRequest(
                entityKey, entityType,
                requestParams);

        EntityQueryResponse response = dynamicServerEntityService.getQueryRecords(request);
        EntityInfoResponse infoResponse = dynamicServerEntityService.getFriendlyInfoResponse(request, null);

        ResourceSupport resourceSupport = new ResourceSupport();

        resourceSupport.add(linkTo(methodOn(TallyApiController.class).getEntityList(entityKey, requestParams)).withSelfRel());
        QueryResultRange currentRange = response.makeRange();

        QueryResultRange next = currentRange.next();
        QueryResultRange pre = currentRange.pre();

        if(pre.isValid()){
            MultiValueMap<String, String> requestParamsCopy = CloneUtility.makeClone(requestParams);
            requestParamsCopy.remove(GeneralRequestParameter.REQUEST_START_INDEX);
            if(pre.getStart() != 0) {
                List<String> v = new ArrayList<String>();
                v.add("" + pre.getStart());
                requestParamsCopy.put(GeneralRequestParameter.REQUEST_START_INDEX, v);
            }
            resourceSupport.add(linkTo(methodOn(TallyApiController.class).getEntityList(entityKey, requestParamsCopy)).withRel("pre_page"));
        }
        if(next.isValid()){
            MultiValueMap<String, String> requestParamsCopy = CloneUtility.makeClone(requestParams);
            requestParamsCopy.remove(GeneralRequestParameter.REQUEST_START_INDEX);
            List<String> v = new ArrayList<String>();
            v.add("" + next.getStart());
            requestParamsCopy.put(GeneralRequestParameter.REQUEST_START_INDEX, v);

            resourceSupport.add(linkTo(methodOn(TallyApiController.class).getEntityList(entityKey, requestParamsCopy)).withRel("next_page"));
        }

        Map<String, Object> resourceResult = new HashMap<String, Object>();
        ResponseTranslator.mergeResult(resourceResult, response, infoResponse, resourceSupport.getLinks());

        return new ResponseEntity<Object>(resourceResult, HttpStatus.OK);
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
