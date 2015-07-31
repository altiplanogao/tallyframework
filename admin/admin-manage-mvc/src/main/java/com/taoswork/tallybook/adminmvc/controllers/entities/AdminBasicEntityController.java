package com.taoswork.tallybook.adminmvc.controllers.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taoswork.tallybook.admincore.menu.AdminMenuService;
import com.taoswork.tallybook.admincore.web.model.service.AdminCommonModelService;
import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminEmployee;
import com.taoswork.tallybook.business.datadomain.tallyuser.Person;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.EntityInfoType;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.EntityQueryRequest;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityInfoResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityQueryListGridResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityQueryResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.translator.Parameter2RequestTranslator;
import com.taoswork.tallybook.dynamic.dataservice.server.io.translator.response.ResponseTranslator;
import com.taoswork.tallybook.dynamic.dataservice.server.service.DynamicServerEntityService;
import com.taoswork.tallybook.general.dataservice.management.manager.DataServiceManager;
import com.taoswork.tallybook.general.solution.menu.Menu;
import com.taoswork.tallybook.general.solution.menu.MenuEntry;
import com.taoswork.tallybook.general.solution.menu.MenuEntryGroup;
import com.taoswork.tallybook.general.solution.property.RuntimePropertiesPublisher;
import com.taoswork.tallybook.general.web.control.BaseController;
import com.taoswork.tallybook.general.web.view.thymeleaf.TallyBookDataViewResolver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/4/28.
 */
@Controller(AdminBasicEntityController.CONTROLLER_NAME)
@RequestMapping("/{entityKey:^[\\w|-]+$}")
public class AdminBasicEntityController extends BaseController {
    public static final String CONTROLLER_NAME = "AdminBasicEntityController";

    @Resource(name = AdminMenuService.SERVICE_NAME)
    protected AdminMenuService adminMenuService;

    @Resource(name = AdminCommonModelService.COMPONENT_NAME)
    protected AdminCommonModelService adminCommonModelService;

    @Resource(name = DataServiceManager.COMPONENT_NAME)
    protected DataServiceManager dataServiceManager;

    private ThreadLocal<ObjectMapper> objectMapperThreadLocal = new ThreadLocal<ObjectMapper>();
    private ObjectMapper getObjectMapper(){
        ObjectMapper objectMapper = objectMapperThreadLocal.get();
        if(objectMapper == null){
            objectMapper = new ObjectMapper();
            objectMapperThreadLocal.set(objectMapper);
        }
        return objectMapper;
    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String viewEntityList(
            HttpServletRequest request,
            HttpServletResponse response,
            Model model,
            @PathVariable(value = "entityKey") String entityKey,
            @PathVariable Map<String, String> pathVars,
            @RequestParam MultiValueMap<String, String> requestParams){

        String entityType = dataServiceManager.getEntityInterfaceName(entityKey);
        //TODO: what if entityType == null

//        String entityType = entry.getEntity();
//        model.addAttribute("menu", entry);
//        String entityClassName =
//        new AdminModelDataBuilder()
//                .appendMenu(model, adminMenuService);
        DynamicServerEntityService dynamicServerEntityService = dataServiceManager.getDynamicServerEntityService(entityType);

        EntityQueryRequest entityRequest =
            Parameter2RequestTranslator.makeQueryRequest(entityKey, entityType, request.getRequestURI(), requestParams)
                .addEntityInfoType(EntityInfoType.Grid);
        EntityQueryResponse entityQueryResponse = dynamicServerEntityService.getQueryRecords(entityRequest);
        EntityInfoResponse entityInfoResponse = dynamicServerEntityService.getInfoResponse(entityRequest);
        EntityInfoResponse entityInfoFriendlyResponse = dynamicServerEntityService.getFriendlyInfoResponse(entityRequest, request.getLocale());

        String entityResultInJson = "";
        try{
            Map<String, Object> entityResult = ResponseTranslator.mergeResult(null, entityQueryResponse, entityInfoFriendlyResponse, null);

            if(isAjaxRequest(request)){
                model.addAttribute("data", entityResult);
                return TallyBookDataViewResolver.JASON_VIEW_NAME;
            }

            ObjectMapper objectMapper = getObjectMapper();
            entityResultInJson = objectMapper.writeValueAsString(entityResult);

        }catch (JsonProcessingException exp){
            throw new RuntimeException(exp);
        }

        EntityQueryListGridResponse entityQueryListGridResponse = ResponseTranslator.translate(entityQueryResponse, entityInfoResponse);
        entityQueryListGridResponse.setBaseUrl(request.getRequestURI());

        Person person = adminCommonModelService.getPersistentPerson();
        AdminEmployee employee =adminCommonModelService.getPersistentAdminEmployee();
        Menu menu = adminMenuService.buildMenu(employee);

        CurrentPath currentPath = buildCurrentPath(entityKey, request);

        boolean production = RuntimePropertiesPublisher.instance().getBoolean("tally.production", false);
//        model.addAttribute("currentUrl", request.getRequestURL().toString());
        model.addAttribute("production", production);
        model.addAttribute("menu", menu);
        model.addAttribute("current", currentPath);
        model.addAttribute("person", person);
        model.addAttribute("entityResponse", entityQueryListGridResponse);
        model.addAttribute("entityInfoResponse", entityInfoResponse);

        model.addAttribute("entityQueryResult", entityResultInJson);

        return "entity/mainframe";
    }

    private CurrentPath buildCurrentPath(String sectionName, HttpServletRequest request){
        CurrentPath currentPath = new CurrentPath();
        MenuEntry entry = adminMenuService.findMenuEntryByUrl(sectionName);
        MenuEntryGroup group = adminMenuService.findMenuEntryGroupByEntryKey(entry.getKey());
        String currentUrl = request.getRequestURL().toString();

        currentPath //.setMenu(menu)
                .setMenuGroup(group)
                .setMenuEntry(entry)
                .setUrl(currentUrl);

        return currentPath;
    }
}
