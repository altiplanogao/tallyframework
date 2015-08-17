package com.taoswork.tallybook.adminmvc.controllers.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taoswork.tallybook.admincore.menu.AdminMenuService;
import com.taoswork.tallybook.admincore.web.model.service.AdminCommonModelService;
import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminEmployee;
import com.taoswork.tallybook.business.datadomain.tallyuser.Person;
import com.taoswork.tallybook.dynamic.datameta.description.infos.EntityInfoType;
import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.DynamicEntityService;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.EntityQueryRequest;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.EntityReadRequest;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.translator.Parameter2RequestTranslator;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityQueryResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.EntityReadResponse;
import com.taoswork.tallybook.dynamic.dataservice.server.service.FrontEndDynamicEntityService;
import com.taoswork.tallybook.dynamic.dataservice.server.service.IFrontEndDynamicEntityService;
import com.taoswork.tallybook.general.dataservice.management.manager.DataServiceManager;
import com.taoswork.tallybook.general.solution.menu.Menu;
import com.taoswork.tallybook.general.solution.menu.MenuEntry;
import com.taoswork.tallybook.general.solution.menu.MenuEntryGroup;
import com.taoswork.tallybook.general.solution.property.RuntimePropertiesPublisher;
import com.taoswork.tallybook.general.solution.threading.ThreadLocalHelper;
import com.taoswork.tallybook.general.web.control.BaseController;
import com.taoswork.tallybook.general.web.view.thymeleaf.TallyBookDataViewResolver;
import org.springframework.security.web.util.UrlUtils;
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
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/4/28.
 */
@Controller(AdminBasicEntityController.CONTROLLER_NAME)
@RequestMapping("/{entityResName:^[\\w|-]+$}")
public class AdminBasicEntityController extends BaseController {
    public static final String CONTROLLER_NAME = "AdminBasicEntityController";

    @Resource(name = AdminMenuService.SERVICE_NAME)
    protected AdminMenuService adminMenuService;

    @Resource(name = AdminCommonModelService.COMPONENT_NAME)
    protected AdminCommonModelService adminCommonModelService;

    @Resource(name = DataServiceManager.COMPONENT_NAME)
    protected DataServiceManager dataServiceManager;

    private ThreadLocal<ObjectMapper> objectMapper = ThreadLocalHelper.createThreadLocal(ObjectMapper.class);
    private Helper helper = new Helper();

    private String getObjectInJson(Object data) {
        try {
            return objectMapper.get().writeValueAsString(data);
        } catch (JsonProcessingException exp) {
            throw new RuntimeException(exp);
        }
    }

    private Set<String> getParamInfoFilter(){
        return EntityInfoType.PageSupportedType;
    }

    @RequestMapping(value = "info", method = RequestMethod.GET)
    public String readEntityInfo(
        HttpServletRequest request,
        HttpServletResponse response,
        Model model,
        @PathVariable(value = "entityResName") String entityResName,
        @PathVariable Map<String, String> pathVars,
        @RequestParam MultiValueMap<String, String> requestParams) {

        String entityType = dataServiceManager.getEntityInterfaceName(entityResName);
        //TODO: what if entityType == null

//        FrontEndDynamicEntityService dynamicServerEntityService = dataServiceManager.getFrontEndDynamicEntityService(entityType);
//        EntityRequest entityRequest =
//            Parameter2RequestTranslator.makeInfoRequest(entityResName, entityType, request.getRequestURI(), requestParams);
//        EntityQueryResponse entityQueryResponse = dynamicServerEntityService.queryRecords(entityRequest);
//        EntityInfoResponse entityInfoFriendlyResponse = dynamicServerEntityService.getFriendlyInfoResponse(entityRequest, request.getLocale());
//        EntityInfoRequest entityInfoRequest =
        return "";
    }

    /**
     * Renders the main entity listing for the specified class, which is based on the current entityResName with some optional
     * criteria
     *
     * @param request
     * @param response
     * @param model
     * @param entityResName
     * @param pathVars
     * @param requestParams
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String searchEntityList(
        HttpServletRequest request, HttpServletResponse response, Model model,
        @PathVariable(value = "entityResName") String entityResName,
        @PathVariable Map<String, String> pathVars,
        @RequestParam MultiValueMap<String, String> requestParams) throws Exception{

        String entityType = dataServiceManager.getEntityInterfaceName(entityResName);
        //TODO: what if entityType == null

        EntityQueryRequest entityRequest =
            Parameter2RequestTranslator.makeQueryRequest(entityResName, entityType,
                request.getRequestURI(), UrlUtils.buildFullRequestUrl(request), requestParams, getParamInfoFilter());
        entityRequest.addEntityInfoType(EntityInfoType.PageGrid);

        DynamicEntityService entityService = dataServiceManager.getDynamicEntityService(entityType);
        IFrontEndDynamicEntityService dynamicServerEntityService = FrontEndDynamicEntityService.newInstance(entityService);

        EntityQueryResponse entityQueryResponse = dynamicServerEntityService.queryRecords(entityRequest, request.getLocale());

        if (isAjaxRequest(request)) {
            model.addAttribute("data", entityQueryResponse);
            return TallyBookDataViewResolver.JASON_VIEW_NAME;
        }

        Person person = adminCommonModelService.getPersistentPerson();
        AdminEmployee employee = adminCommonModelService.getPersistentAdminEmployee();
        Menu menu = adminMenuService.buildMenu(employee);
        CurrentPath currentPath = helper.buildCurrentPath(entityResName, request);

        boolean production = RuntimePropertiesPublisher.instance().getBoolean("tally.production", false);
//        model.addAttribute("currentUrl", request.getRequestURL().toString());
        model.addAttribute("production", production);
        model.addAttribute("menu", menu);
        model.addAttribute("current", currentPath);
        model.addAttribute("person", person);

        model.addAttribute("gridInfo", entityQueryResponse.getInfo().getDetail(EntityInfoType.PageGrid));
        String entityResultInJson = getObjectInJson(entityQueryResponse);
        model.addAttribute("queryResult", entityResultInJson);

        model.addAttribute("viewType", "mainGrid");
        return "entity/mainframe";
    }

    /**
     * Renders the main entity form for the specified entity
     *
     * @param request
     * @param response
     * @param model
     * @param pathVars
     * @param id
     * @return the return view path
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String viewEntityForm(
        HttpServletRequest request, HttpServletResponse response, Model model,
        @PathVariable(value = "entityResName") String entityResName,
        @PathVariable("id") String id,
        @PathVariable Map<String, String> pathVars) throws Exception {

        String entityType = dataServiceManager.getEntityInterfaceName(entityResName);
        EntityReadRequest readRequest = Parameter2RequestTranslator.makeReadRequest(entityResName, entityType,
            request.getRequestURI(), UrlUtils.buildFullRequestUrl(request), id);

        DynamicEntityService entityService = dataServiceManager.getDynamicEntityService(entityType);
        IFrontEndDynamicEntityService dynamicServerEntityService = FrontEndDynamicEntityService.newInstance(entityService);

        EntityReadResponse readResponse = dynamicServerEntityService.readRecord(readRequest, request.getLocale());

        if (isAjaxRequest(request)) {
            model.addAttribute("data", readResponse);
            return TallyBookDataViewResolver.JASON_VIEW_NAME;
        }

        Person person = adminCommonModelService.getPersistentPerson();
        AdminEmployee employee = adminCommonModelService.getPersistentAdminEmployee();
        Menu menu = adminMenuService.buildMenu(employee);
        CurrentPath currentPath = helper.buildCurrentPath(entityResName, request);

        boolean production = RuntimePropertiesPublisher.instance().getBoolean("tally.production", false);
//        model.addAttribute("currentUrl", request.getRequestURL().toString());
        model.addAttribute("production", production);
        model.addAttribute("menu", menu);
        model.addAttribute("current", currentPath);
        model.addAttribute("person", person);

        model.addAttribute("formInfo", readResponse.getInfo().getDetail(EntityInfoType.Form));
        String entityResultInJson = getObjectInJson(readResponse);
        model.addAttribute("readResult", entityResultInJson);

        model.addAttribute("viewType", "mainForm");
        return "entity/mainframe";
    }

//    /**
//     * Renders the modal form that is used to add a new parent level entity. Note that this form cannot render any
//     * subcollections as operations on those collections require the parent level entity to first be saved and have
//     * and id. Once the entity is initially saved, we will redirect the user to the normal manage entity screen where
//     * they can then perform operations on sub collections.
//     *
//     * @param request
//     * @param response
//     * @param model
//     * @param pathVars
//     * @param entityType
//     * @return the return view path
//     * @throws Exception
//     */
//    @RequestMapping(value = "/add", method = RequestMethod.GET)
//    public String viewAddEntityForm(HttpServletRequest request, HttpServletResponse response, Model model,
//                                    @PathVariable Map<String, String> pathVars,
//                                    @RequestParam(defaultValue = "") String entityType) throws Exception {
//        return "";
//    }
//
//    /**
//     * Processes the request to add a new entity. If successful, returns a redirect to the newly created entity.
//     *
//     * @param request
//     * @param response
//     * @param model
//     * @param pathVars
//     * @param entityForm
//     * @param result
//     * @return the return view path
//     * @throws Exception
//     */
//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    public String addEntity(HttpServletRequest request, HttpServletResponse response, Model model,
//                            @PathVariable Map<String, String> pathVars,
//                            @ModelAttribute(value = "entityForm") EntityForm entityObject, BindingResult result) throws Exception {
//        return "";
//    }
//
//    /**
//     * Attempts to save the given entity. If validation is unsuccessful, it will re-render the entity form with
//     * error fields highlighted. On a successful save, it will refresh the entity page.
//     *
//     * @param request
//     * @param response
//     * @param model
//     * @param pathVars
//     * @param id
//     * @param entityForm
//     * @param result
//     * @return the return view path
//     * @throws Exception
//     */
//    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
//    public String saveEntity(HttpServletRequest request, HttpServletResponse response, Model model,
//                             @PathVariable Map<String, String> pathVars,
//                             @PathVariable(value = "id") String id,
//                             @ModelAttribute(value = "entityForm") EntityForm entityForm, BindingResult result,
//                             RedirectAttributes ra) throws Exception {
//        return "";
//    }
//
//    /**
//     * Attempts to remove the given entity.
//     *
//     * @param request
//     * @param response
//     * @param model
//     * @param pathVars
//     * @param id
//     * @return the return view path
//     * @throws Exception
//     */
//    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
//    public String removeEntity(HttpServletRequest request, HttpServletResponse response, Model model,
//                               @PathVariable Map<String, String> pathVars,
//                               @PathVariable(value = "id") String id,
//                               @ModelAttribute(value = "entityForm") EntityForm entityForm, BindingResult result) throws Exception {
//        return "";
//    }


    //////////////////////////////////////////////////////////////////////////////////////
    ///         Details                                                             //////
    //////////////////////////////////////////////////////////////////////////////////////
//
//    /**
//     * @param request
//     * @param response
//     * @param model
//     * @param pathVars
//     * @param collectionField
//     * @param ids
//     * @param requestParams
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value = "/{collectionField:.*}/details", method = RequestMethod.GET)
//    public
//    @ResponseBody
//    Map<String, String> getCollectionValueDetails(HttpServletRequest request, HttpServletResponse response, Model model,
//                                                  @PathVariable Map<String, String> pathVars,
//                                                  @PathVariable(value = "collectionField") String collectionField,
//                                                  @RequestParam String ids,
//                                                  @RequestParam MultiValueMap<String, String> requestParams) throws Exception {
//        return null;
//    }
//
//    /**
//     * Shows the modal popup for the current selected "to-one" field. For instance, if you are viewing a list of products
//     * then this method is invoked when a user clicks on the name of the default category field.
//     *
//     * @param request
//     * @param response
//     * @param model
//     * @param pathVars
//     * @param collectionField
//     * @param id
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value = "/{collectionField:.*}/{id}/view", method = RequestMethod.GET)
//    public String viewCollectionItemDetails(HttpServletRequest request, HttpServletResponse response, Model model,
//                                            @PathVariable Map<String, String> pathVars,
//                                            @PathVariable(value = "collectionField") String collectionField,
//                                            @PathVariable(value = "id") String id) throws Exception {
//        return "";
//    }
//
//    /**
//     * Returns the records for a given collectionField filtered by a particular criteria
//     *
//     * @param request
//     * @param response
//     * @param model
//     * @param pathVars
//     * @param collectionField
//     * @param criteriaForm
//     * @return the return view path
//     * @throws Exception
//     */
//    @RequestMapping(value = "/{id}/{collectionField:.*}", method = RequestMethod.GET)
//    public String getCollectionFieldRecords(HttpServletRequest request, HttpServletResponse response, Model model,
//                                            @PathVariable Map<String, String> pathVars,
//                                            @PathVariable(value = "id") String id,
//                                            @PathVariable(value = "collectionField") String collectionField,
//                                            @RequestParam MultiValueMap<String, String> requestParams) throws Exception {
//        return "";
//    }
//
//    /**
//     * Shows the modal dialog that is used to add an item to a given collection. There are several possible outcomes
//     * of this call depending on the type of the specified collection field.
//     * <p>
//     * <ul>
//     * <li>
//     * <b>Basic Collection (Persist)</b> - Renders a blank form for the specified target entity so that the user may
//     * enter information and associate the record with this collection. Used by fields such as ProductAttribute.
//     * </li>
//     * <li>
//     * <b>Basic Collection (Lookup)</b> - Renders a list grid that allows the user to click on an entity and select it.
//     * Used by fields such as "allParentCategories".
//     * </li>
//     * <li>
//     * <b>Adorned Collection (without form)</b> - Renders a list grid that allows the user to click on an entity and
//     * select it. The view rendered by this is identical to basic collection (lookup), but will perform the operation
//     * on an adorned field, which may carry extra meta-information about the created relationship, such as order.
//     * </li>
//     * <li>
//     * <b>Adorned Collection (with form)</b> - Renders a list grid that allows the user to click on an entity and
//     * select it. Once the user selects the entity, he will be presented with an empty form based on the specified
//     * "maintainedAdornedTargetFields" for this field. Used by fields such as "crossSellProducts", which in addition
//     * to linking an entity, provide extra fields, such as a promotional message.
//     * </li>
//     * <li>
//     * <b>Map Collection</b> - Renders a form for the target entity that has an additional key field. This field is
//     * populated either from the configured map keys, or as a result of a lookup in the case of a key based on another
//     * entity. Used by fields such as the mediaMap on a Sku.
//     * </li>
//     *
//     * @param request
//     * @param response
//     * @param model
//     * @param sectionKey
//     * @param id
//     * @param collectionField
//     * @param requestParams
//     * @return the return view path
//     * @throws Exception
//     */
//    @RequestMapping(value = "/{id}/{collectionField:.*}/add", method = RequestMethod.GET)
//    public String showAddCollectionItem(HttpServletRequest request, HttpServletResponse response, Model model,
//                                        @PathVariable Map<String, String> pathVars,
//                                        @PathVariable(value = "id") String id,
//                                        @PathVariable(value = "collectionField") String collectionField,
//                                        @RequestParam MultiValueMap<String, String> requestParams) throws Exception {
//        return "";
//    }
//
//    /**
//     * Adds the requested collection item
//     *
//     * @param request
//     * @param response
//     * @param model
//     * @param pathVars
//     * @param id
//     * @param collectionField
//     * @param entityForm
//     * @return the return view path
//     * @throws Exception
//     */
//    @RequestMapping(value = "/{id}/{collectionField:.*}/add", method = RequestMethod.POST)
//    public String addCollectionItem(HttpServletRequest request, HttpServletResponse response, Model model,
//                                    @PathVariable Map<String, String> pathVars,
//                                    @PathVariable(value = "id") String id,
//                                    @PathVariable(value = "collectionField") String collectionField,
//                                    @ModelAttribute(value = "entityForm") EntityForm entityForm, BindingResult result) throws Exception {
//        return "";
//    }
//
//    /**
//     * Shows the appropriate modal dialog to edit the selected collection item
//     *
//     * @param request
//     * @param response
//     * @param model
//     * @param pathVars
//     * @param id
//     * @param collectionField
//     * @param collectionItemId
//     * @return the return view path
//     * @throws Exception
//     */
//    @RequestMapping(value = "/{id}/{collectionField:.*}/{collectionItemId}", method = RequestMethod.GET)
//    public String showUpdateCollectionItem(HttpServletRequest request, HttpServletResponse response, Model model,
//                                           @PathVariable Map<String, String> pathVars,
//                                           @PathVariable(value = "id") String id,
//                                           @PathVariable(value = "collectionField") String collectionField,
//                                           @PathVariable(value = "collectionItemId") String collectionItemId) throws Exception {
////        return showViewUpdateCollection(request, model, pathVars, id, collectionField, collectionItemId,
////            "updateCollectionItem");
//        return "";
//
//    }
//
//    /**
//     * Shows the appropriate modal dialog to view the selected collection item. This will display the modal as readonly
//     *
//     * @param request
//     * @param response
//     * @param model
//     * @param pathVars
//     * @param id
//     * @param collectionField
//     * @param collectionItemId
//     * @return the return view path
//     * @throws Exception
//     */
//    @RequestMapping(value = "/{id}/{collectionField:.*}/{collectionItemId}/view", method = RequestMethod.GET)
//    public String showViewCollectionItem(HttpServletRequest request, HttpServletResponse response, Model model,
//                                         @PathVariable Map<String, String> pathVars,
//                                         @PathVariable(value = "id") String id,
//                                         @PathVariable(value = "collectionField") String collectionField,
//                                         @PathVariable(value = "collectionItemId") String collectionItemId) throws Exception {
////        String returnPath = showViewUpdateCollection(request, model, pathVars, id, collectionField, collectionItemId,
////            "viewCollectionItem");
////
////        // Since this is a read-only view, actions don't make sense in this context
////        EntityForm ef = (EntityForm) model.asMap().get("entityForm");
////        ef.removeAllActions();
////
////        return returnPath;
//        return "";
//    }
//
//    /**
//     * Updates the specified collection item
//     *
//     * @param request
//     * @param response
//     * @param model
//     * @param pathVars
//     * @param id
//     * @param collectionField
//     * @param entityForm
//     * @return the return view path
//     * @throws Exception
//     */
//    @RequestMapping(value = "/{id}/{collectionField:.*}/{collectionItemId}", method = RequestMethod.POST)
//    public String updateCollectionItem(HttpServletRequest request, HttpServletResponse response, Model model,
//                                       @PathVariable Map<String, String> pathVars,
//                                       @PathVariable(value = "id") String id,
//                                       @PathVariable(value = "collectionField") String collectionField,
//                                       @PathVariable(value = "collectionItemId") String collectionItemId,
//                                       @ModelAttribute(value = "entityForm") EntityForm entityForm, BindingResult result) throws Exception {
//        return "";
//    }
//
//    /**
//     * Updates the given colleciton item's sequence. This should only be triggered for adorned target collections
//     * where a sort field is specified -- any other invocation is incorrect and will result in an exception.
//     *
//     * @param request
//     * @param response
//     * @param model
//     * @param pathVars
//     * @param id
//     * @param collectionField
//     * @param collectionItemId
//     * @return an object explaining the state of the operation
//     * @throws Exception
//     */
//    @RequestMapping(value = "/{id}/{collectionField:.*}/{collectionItemId}/sequence", method = RequestMethod.POST)
//    public
//    @ResponseBody
//    Map<String, Object> updateCollectionItemSequence(HttpServletRequest request,
//                                                     HttpServletResponse response, Model model,
//                                                     @PathVariable Map<String, String> pathVars,
//                                                     @PathVariable(value = "id") String id,
//                                                     @PathVariable(value = "collectionField") String collectionField,
//                                                     @PathVariable(value = "collectionItemId") String collectionItemId,
//                                                     @RequestParam(value = "newSequence") String newSequence) throws Exception {
//        return null;
//    }
//
//    /**
//     * Removes the requested collection item
//     * <p>
//     * Note that the request must contain a parameter called "key" when attempting to remove a collection item from a
//     * map collection.
//     *
//     * @param request
//     * @param response
//     * @param model
//     * @param pathVars
//     * @param id
//     * @param collectionField
//     * @param collectionItemId
//     * @return the return view path
//     * @throws Exception
//     */
//    @RequestMapping(value = "/{id}/{collectionField:.*}/{collectionItemId}/delete", method = RequestMethod.POST)
//    public String removeCollectionItem(HttpServletRequest request, HttpServletResponse response, Model model,
//                                       @PathVariable Map<String, String> pathVars,
//                                       @PathVariable(value = "id") String id,
//                                       @PathVariable(value = "collectionField") String collectionField,
//                                       @PathVariable(value = "collectionItemId") String collectionItemId) throws Exception {
//        return null;
//    }

    //////////////////////////////////////////////////////////////////////////////////////
    ///         Helper                                                              //////
    //////////////////////////////////////////////////////////////////////////////////////
    class Helper {
        private CurrentPath buildCurrentPath(String sectionName, HttpServletRequest request) {
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
}
