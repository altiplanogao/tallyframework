package com.taoswork.tallybook.general.dataservice.management.api;

import com.taoswork.tallybook.dataservice.IDataService;
import com.taoswork.tallybook.dataservice.manage.DataServiceManager;
import com.taoswork.tallybook.dataservice.server.io.request.EntityQueryRequest;
import com.taoswork.tallybook.dataservice.server.io.request.EntityReadRequest;
import com.taoswork.tallybook.dataservice.server.io.request.parameter.EntityTypeParameter;
import com.taoswork.tallybook.dataservice.server.io.request.parameter.EntityTypeParameterBuilder;
import com.taoswork.tallybook.dataservice.server.io.request.translator.Parameter2RequestTranslator;
import com.taoswork.tallybook.dataservice.server.io.response.EntityQueryResponse;
import com.taoswork.tallybook.dataservice.server.io.response.EntityReadResponse;
import com.taoswork.tallybook.dataservice.server.service.FrontEndEntityService;
import com.taoswork.tallybook.dataservice.server.service.IFrontEndEntityService;
import com.taoswork.tallybook.descriptor.description.infos.EntityInfoType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/6/13.
 */
@Controller(TallyApiController.TALLY_API_CONTROLLER_NAME)
@RequestMapping("/{entityTypeName:^[\\w|\\-|\\.]+$}")
public class TallyApiController {
    public static final String TALLY_API_CONTROLLER_NAME = "TallyApiController";

    @Resource(name = DataServiceManager.COMPONENT_NAME)
    protected DataServiceManager dataServiceManager;

    private Set<String> getParamInfoFilter() {
        return EntityInfoType.ApiSupportedType;
    }

    @RequestMapping("")
    @ResponseBody
    public HttpEntity<?> getEntityList(HttpServletRequest request,
                                       @PathVariable(value = "entityTypeName") String entityTypeName,
                                       @RequestParam MultiValueMap<String, String> requestParams) throws Exception {
        EntityTypeParameter entityTypes = EntityTypeParameterBuilder.getBy(dataServiceManager, entityTypeName);
        Class entityType = entityTypes.getCeilingType();

        EntityQueryRequest queryRequest = Parameter2RequestTranslator.makeQueryRequest(
                entityTypes,
                uriFromRequest(request),
                requestParams, getParamInfoFilter());

        IDataService dataService = dataServiceManager.getDataService(entityType.getName());
        IFrontEndEntityService frontEndEntityService = FrontEndEntityService.newInstance(dataServiceManager, dataService);

        EntityQueryResponse response = frontEndEntityService.query(queryRequest, request.getLocale());
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<?> readEntity(HttpServletRequest request, Model model,
                                    @PathVariable(value = "entityTypeName") String entityTypeName,
                                    @PathVariable("id") String id,
                                    @PathVariable Map<String, String> pathVars) throws Exception {

        EntityTypeParameter entityTypes = EntityTypeParameterBuilder.getBy(dataServiceManager, entityTypeName);
        Class entityType = entityTypes.getCeilingType();

        EntityReadRequest readRequest = Parameter2RequestTranslator.makeReadRequest(entityTypes,
                uriFromRequest(request), id);

        IDataService dataService = dataServiceManager.getDataService(entityType.getName());
        IFrontEndEntityService frontEndEntityService = FrontEndEntityService.newInstance(dataServiceManager, dataService);

        EntityReadResponse response = frontEndEntityService.read(readRequest, request.getLocale());
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
    protected static URI uriFromRequest(HttpServletRequest request) {
        try {
            URI uriobj = new URI(null, null, request.getRequestURI(), request.getQueryString(), null);
            return uriobj;
        } catch (URISyntaxException e) {
            String query = request.getQueryString();
            String uri = request.getRequestURI();
            if (StringUtils.isEmpty(query)) {
                return URI.create(uri);
            } else {
                return URI.create(uri + "?" + query);
            }
        }
    }
}
