package com.taoswork.tallybook.general.dataservice.management.api;

import com.taoswork.tallybook.dynamic.dataservice.server.service.DynamicServerEntityService;
import com.taoswork.tallybook.general.dataservice.management.manager.DataServiceManager;
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

/**
 * Created by Gao Yuan on 2015/6/13.
 */
@Controller(TallyApiController.TALLY_API_CONTROLLER_NAME)
public class TallyApiController  {
    public static final String TALLY_API_CONTROLLER_NAME = "api";

    @Resource(name = DataServiceManager.COMPONENT_NAME)
    protected DataServiceManager dataServiceManager;

    @RequestMapping("/{resourceName:^[\\w|-]+$}")
    @ResponseBody
    public HttpEntity<TallyResource> getEntityList(
            @PathVariable(value="resourceName") String resourceName,
            @RequestParam MultiValueMap<String, String> requestParams){
        String entityType = dataServiceManager.getEntityInterfaceName(resourceName);
        DynamicServerEntityService dynamicServerEntityService = dataServiceManager.getDynamicServerEntityService(entityType);

        TallyResource resource = null;

        return new ResponseEntity<TallyResource>(resource, HttpStatus.OK);
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
