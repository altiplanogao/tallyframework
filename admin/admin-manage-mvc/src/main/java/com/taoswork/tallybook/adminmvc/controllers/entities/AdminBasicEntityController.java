package com.taoswork.tallybook.adminmvc.controllers.entities;

import com.taoswork.tallybook.admincore.menu.AdminMenuService;
import com.taoswork.tallybook.admincore.web.model.service.AdminCommonModelService;
import com.taoswork.tallybook.application.core.conf.ApplicationCommonConfig;
import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminEmployee;
import com.taoswork.tallybook.business.datadomain.tallyuser.Person;
import com.taoswork.tallybook.dynamic.datameta.description.infos.EntityInfoType;
import com.taoswork.tallybook.dynamic.datameta.description.infos.IEntityInfo;
import com.taoswork.tallybook.dynamic.dataservice.IDataService;
import com.taoswork.tallybook.dynamic.dataservice.core.entityservice.EntityActionNames;
import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.Entity;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.*;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.translator.Parameter2RequestTranslator;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.*;
import com.taoswork.tallybook.dynamic.dataservice.server.io.response.result.EntityErrors;
import com.taoswork.tallybook.dynamic.dataservice.server.service.FrontEndEntityService;
import com.taoswork.tallybook.dynamic.dataservice.server.service.IFrontEndEntityService;
import com.taoswork.tallybook.general.dataservice.management.parameter.EntityTypeParameterBuilder;
import com.taoswork.tallybook.general.dataservice.management.manager.DataServiceManager;
import com.taoswork.tallybook.general.solution.menu.IMenu;
import com.taoswork.tallybook.general.solution.menu.MenuPath;
import com.taoswork.tallybook.general.solution.message.CachedMessageLocalizedDictionary;
import com.taoswork.tallybook.general.solution.property.RuntimePropertiesPublisher;
import com.taoswork.tallybook.general.web.control.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 *    Action            Method      Success :                       Error:NoRecord      Error:Validation    Error:Permission
 * 1. query             get         Grid in SimplePage/FramedPage   N/A                 N/A                 Error In Page
 * 2. createFresh       get         Edit in SimplePage/FramedPage   N/A                 N/A                 Error In Page
 * 3. read              get         Edit in SimplePage/FramedPage   Error in Page       N/A                 Error In Page
 * 4. create            post        Redirect Read Page              N/A                 AJAX: Error         AJAX: Error
 * 5. update            post        Redirect Read Page              AJAX: Error         AJAX: Error         AJAX: Error
 * 6. delete            post        Redirect Read Page              AJAX: Error         N/A                 AJAX: Error
 */
@Controller(AdminBasicEntityController.CONTROLLER_NAME)
@RequestMapping("/{entityTypeName:^[\\w|\\-|\\.]+$}")
public class AdminBasicEntityController extends BaseController {
    private static Logger LOGGER = LoggerFactory.getLogger(AdminBasicEntityController.class);

    public static final String CONTROLLER_NAME = "AdminBasicEntityController";
    private static class VIEWS{
        static final String Redirect2Home = "redirect:/";
        static final String Redirect2Failure = "redirect:failure";
        static final String FramedView = "entity/content/framedView";
        static final String SimpleView = "entity/content/simpleView";
    }

    @Resource(name = AdminMenuService.SERVICE_NAME)
    protected AdminMenuService adminMenuService;

    @Resource(name = AdminCommonModelService.COMPONENT_NAME)
    protected AdminCommonModelService adminCommonModelService;

    @Resource(name = DataServiceManager.COMPONENT_NAME)
    protected DataServiceManager dataServiceManager;

    @Resource(name = ApplicationCommonConfig.COMMON_MESSAGE)
    private CachedMessageLocalizedDictionary commonMessage;

    private Map<String, String> getCommonMessage(Locale locale){
        return commonMessage.getTranslated(locale);
    }

    private Helper helper = new Helper();

    private Set<String> getParamInfoFilter(){
        return EntityInfoType.PageSupportedType;
    }

    @RequestMapping(value = "info", method = RequestMethod.GET)
    public String info(HttpServletRequest request, HttpServletResponse response, Model model,
                       @PathVariable(value = "entityTypeName") String entityTypeName,
                       @PathVariable Map<String, String> pathVars,
                       @RequestParam MultiValueMap<String, String> requestParams) {

        EntityTypeParameter entityTypes = EntityTypeParameterBuilder.getBy(dataServiceManager, entityTypeName);
        Class entityType = entityTypes.getCeilingType();
        if(entityType == null){
            return VIEWS.Redirect2Home;
        }

        Locale locale = request.getLocale();
        IDataService dataService = dataServiceManager.getDataService(entityType.getName());
        IFrontEndEntityService frontEndEntityService = FrontEndEntityService.newInstance(dataService);

        EntityInfoRequest infoRequest = Parameter2RequestTranslator.makeInfoRequest(entityTypes,
            request.getRequestURI(), UrlUtils.buildFullRequestUrl(request), requestParams, getParamInfoFilter());
        infoRequest.addEntityInfoType(EntityInfoType.PageGrid);

        EntityInfoResponse entityResponse = frontEndEntityService.getInfoResponse(infoRequest, locale);
        return this.makeDataView(model, entityResponse);
    }

    /**
     * Renders the main entity listing for the specified class, which is based on the current entityTypeName with some optional
     * criteria
     *
     * @param request
     * @param response
     * @param model
     * @param entityTypeName
     * @param pathVars
     * @param requestParams
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String query(HttpServletRequest request, HttpServletResponse response, Model model,
                        @PathVariable(value = "entityTypeName") String entityTypeName,
                        @PathVariable Map<String, String> pathVars,
                        @RequestParam MultiValueMap<String, String> requestParams) {

        EntityTypeParameter entityTypes = EntityTypeParameterBuilder.getBy(dataServiceManager, entityTypeName);
        Class entityType = entityTypes.getCeilingType();
        if (entityType == null) {
            return VIEWS.Redirect2Home;
        }

        Locale locale = request.getLocale();
        IDataService dataService = dataServiceManager.getDataService(entityType.getName());
        IFrontEndEntityService frontEndEntityService = FrontEndEntityService.newInstance(dataService);

        EntityQueryRequest entityRequest = Parameter2RequestTranslator.makeQueryRequest(entityTypes,
            request.getRequestURI(), UrlUtils.buildFullRequestUrl(request), requestParams, getParamInfoFilter());
        entityRequest.addEntityInfoType(EntityInfoType.PageGrid);

        EntityQueryResponse entityQueryResponse = frontEndEntityService.query(entityRequest, locale);
        if (isAjaxDataRequest(request)) {
            return makeDataView(model, entityQueryResponse);
        }

        Person person = adminCommonModelService.getPersistentPerson();
        AdminEmployee employee = adminCommonModelService.getPersistentAdminEmployee();
        IMenu menu = adminMenuService.buildMenu(employee);
        CurrentPath currentPath = helper.buildCurrentPath(entityTypeName, request);

        model.addAttribute("menu", menu);
        model.addAttribute("current", currentPath);
        model.addAttribute("person", person);

        String entityResultInJson = getObjectInJson(entityQueryResponse);
        model.addAttribute("queryResult", entityResultInJson);

        model.addAttribute("viewType", "entityMainGrid");
        setCommonModelAttributes(model, locale);

        boolean success = entityQueryResponse.success();
        if (!success) {
            if (!entityQueryResponse.getErrors().isAuthorized()) {
                model.addAttribute("viewType", "noPermission");
            } else {
                setErrorModalAttributes(model, entityQueryResponse);
                model.addAttribute("viewType", "uncheckedError");
            }
        }
        if (isSimpleViewRequest(request)) {
            return VIEWS.SimpleView;
        } else {
            model.addAttribute("scope", "main");
            return VIEWS.FramedView;
        }
    }

    /**
     * Renders the modal form that is used to add a new parent level entity. Note that this form cannot render any
     * subcollections as operations on those collections require the parent level entity to first be saved and have
     * and id. Once the entity is initially saved, we will redirect the user to the normal manage entity screen where
     * they can then perform operations on sub collections.
     *
     * @param request
     * @param response
     * @param model
     * @param pathVars
     * @return the return view path
     * @throws Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String createFresh(HttpServletRequest request, HttpServletResponse response, Model model,
                              @PathVariable(value = "entityTypeName") String entityTypeName,
                              @PathVariable Map<String, String> pathVars,
                              @RequestParam(defaultValue = "") String modal) {

        EntityTypeParameter entityTypes = EntityTypeParameterBuilder.getBy(dataServiceManager, entityTypeName);
        Class entityType = entityTypes.getCeilingType();
        if (entityType == null) {
            return VIEWS.Redirect2Home;
        }

        Locale locale = request.getLocale();
        IDataService dataService = dataServiceManager.getDataService(entityType.getName());
        IFrontEndEntityService frontEndEntityService = FrontEndEntityService.newInstance(dataService);

        EntityCreateFreshRequest addRequest = Parameter2RequestTranslator.makeCreateFreshRequest(entityTypes,
            request.getRequestURI(), UrlUtils.buildFullRequestUrl(request));

        EntityCreateFreshResponse addResponse = frontEndEntityService.createFresh(addRequest, locale);
        if (isAjaxDataRequest(request)) {
            return makeDataView(model, addResponse);
        }

        model.addAttribute("currentAction", EntityActionNames.CREATE);
        model.addAttribute("formAction", request.getRequestURL().toString());

        Person person = adminCommonModelService.getPersistentPerson();
        AdminEmployee employee = adminCommonModelService.getPersistentAdminEmployee();
        IMenu menu = adminMenuService.buildMenu(employee);
        CurrentPath currentPath = helper.buildCurrentPath(entityTypeName, request);

        model.addAttribute("menu", menu);
        model.addAttribute("current", currentPath);
        model.addAttribute("person", person);

        model.addAttribute("formInfo", addResponse.getInfo().getDetail(EntityInfoType.Form));
        String entityResultInJson = getObjectInJson(addResponse);
        model.addAttribute("addData", entityResultInJson);

        model.addAttribute("viewType", "entityAdd");
        setCommonModelAttributes(model, locale);

        boolean success = addResponse.success();
        if (!success) {
            if (!addResponse.getErrors().isAuthorized()) {
                model.addAttribute("viewType", "noPermission");
            } else {
                setErrorModalAttributes(model, addResponse);
                model.addAttribute("viewType", "uncheckedError");
            }
        }
        if (isSimpleViewRequest(request)) {
            return VIEWS.SimpleView;
        } else {
            model.addAttribute("formScope", "main");
            return VIEWS.FramedView;
        }
    }

    /**
     * Processes the request to add a new entity. If successful, returns a redirect to the newly created entity.
     *
     * @param request
     * @param response
     * @param model
     * @param pathVars
     * @param entityForm
     * @param result
     * @return the return view path
     * @throws Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String create(HttpServletRequest request, HttpServletResponse response, Model model,
                         @PathVariable(value = "entityTypeName") String entityTypeName,
                         @PathVariable Map<String, String> pathVars,
                         @ModelAttribute(value = "entityForm") Entity entityForm, BindingResult result) {
        EntityTypeParameter entityTypes = EntityTypeParameterBuilder.getBy(dataServiceManager, entityTypeName, entityForm);
        Class entityType = entityTypes.getType();
        Class entityCeilingType = entityTypes.getCeilingType();
        if (entityCeilingType == null) {
            return VIEWS.Redirect2Home;
        }
        if (entityType == null) {
            return VIEWS.Redirect2Failure;
        }

        if (!(isAjaxRequest(request))) {
            return VIEWS.Redirect2Failure;
        }

        Locale locale = request.getLocale();
        IDataService dataService = dataServiceManager.getDataService(entityType.getName());
        IFrontEndEntityService frontEndEntityService = FrontEndEntityService.newInstance(dataService);

        EntityCreateRequest createRequest = Parameter2RequestTranslator.makeCreateRequest(entityTypes,
            request.getRequestURI(), UrlUtils.buildFullRequestUrl(request), entityForm);
        EntityCreateResponse createResponse = frontEndEntityService.create(createRequest, locale);

        boolean success = createResponse.success();
        if (!success) {
            return this.makeDataView(model, createResponse);
        } else {
            String resultUrl = request.getContextPath() + "/" + entityTypeName + "/" + createResponse.getEntity().getIdValue();
            return makeRedirectView(model, resultUrl);
        }
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
    public String read(HttpServletRequest request, HttpServletResponse response, Model model,
                       @PathVariable(value = "entityTypeName") String entityTypeName,
                       @PathVariable("id") String id,
                       @PathVariable Map<String, String> pathVars) {

        EntityTypeParameter entityTypes = EntityTypeParameterBuilder.getBy(dataServiceManager, entityTypeName);
        Class entityType = entityTypes.getCeilingType();
        if(entityType == null){
            return VIEWS.Redirect2Home;
        }

        Locale locale = request.getLocale();
        IDataService dataService = dataServiceManager.getDataService(entityType.getName());
        IFrontEndEntityService frontEndEntityService = FrontEndEntityService.newInstance(dataService);

        EntityReadRequest readRequest = Parameter2RequestTranslator.makeReadRequest(entityTypes,
            request.getRequestURI(), UrlUtils.buildFullRequestUrl(request), id);

        EntityReadResponse readResponse = frontEndEntityService.read(readRequest, locale);
        if (isAjaxDataRequest(request)) {
            return makeDataView(model, readResponse);
        }

        model.addAttribute("currentAction", EntityActionNames.READ);
        model.addAttribute("formAction", request.getRequestURL().toString());

        Person person = adminCommonModelService.getPersistentPerson();
        AdminEmployee employee = adminCommonModelService.getPersistentAdminEmployee();
        IMenu menu = adminMenuService.buildMenu(employee);
        CurrentPath currentPath = helper.buildCurrentPath(entityTypeName, request);
        if(readResponse.getEntity() != null){
//            currentPath.pushEntry(readResponse.getEntity().getDataName(), request.getRequestURI());
            model.addAttribute("entityName", readResponse.getEntity().getDataName());
        }

        model.addAttribute("menu", menu);
        model.addAttribute("current", currentPath);
        model.addAttribute("person", person);

        IEntityInfo formInfo = null;
        if(readResponse.getInfo() != null){
            formInfo = readResponse.getInfo().getDetail(EntityInfoType.Form);
        }
        model.addAttribute("formInfo", formInfo);
        String entityResultInJson = getObjectInJson(readResponse);
        model.addAttribute("readResult", entityResultInJson);

        model.addAttribute("viewType", "entityView");
        setCommonModelAttributes(model, locale);

        boolean success = readResponse.success();
        if(!success){
            if(!readResponse.getErrors().isAuthorized()){
                model.addAttribute("viewType", "noPermission");
            }else if(!readResponse.gotRecord()){
                model.addAttribute("viewType", "noSuchRecord");
                model.addAttribute("id", id);
            } else {
                setErrorModalAttributes(model, readResponse);
                model.addAttribute("viewType", "uncheckedError");
            }
        }
        if(isSimpleViewRequest(request)){
            return VIEWS.SimpleView;
        } else {
            model.addAttribute("formScope", "main");
            return VIEWS.FramedView;
        }
    }


    /**
     * Attempts to save the given entity. If validation is unsuccessful, it will re-render the entity form with
     * error fields highlighted. On a successful save, it will refresh the entity page.
     *
     * @param request
     * @param response
     * @param model
     * @param pathVars
     * @param id
     * @param entityForm
     * @param result
     * @return the return view path
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String update(HttpServletRequest request, HttpServletResponse response,
                         Model model,
                         @PathVariable(value = "entityTypeName") String entityTypeName,
                         @PathVariable Map<String, String> pathVars,
                         @PathVariable(value = "id") String id,
                         @ModelAttribute(value = "entityForm") Entity entityForm,
                         BindingResult result,
                         RedirectAttributes ra) {
        EntityTypeParameter entityTypes = EntityTypeParameterBuilder.getBy(dataServiceManager, entityTypeName, entityForm);
        Class entityType = entityTypes.getType();
        Class entityCeilingType = entityTypes.getCeilingType();
        if (entityCeilingType == null) {
            return VIEWS.Redirect2Home;
        }
        if (entityType == null) {
            return VIEWS.Redirect2Failure;
        }

        if (!(isAjaxRequest(request))) {
            return VIEWS.Redirect2Failure;
        }

        Locale locale = request.getLocale();
        IDataService dataService = dataServiceManager.getDataService(entityType.getName());
        IFrontEndEntityService frontEndEntityService = FrontEndEntityService.newInstance(dataService);

        EntityUpdateRequest updateRequest = Parameter2RequestTranslator.makeUpdateRequest(entityTypes,
            request.getRequestURI(), UrlUtils.buildFullRequestUrl(request), entityForm);
        EntityUpdateResponse updateResponse = frontEndEntityService.update(updateRequest, locale);

        boolean success = updateResponse.success();
        if (!success) {
            return this.makeDataView(model, updateResponse);
        } else {
            return makeRedirectView(model, request.getRequestURI());
        }
    }


    /**
     * Attempts to delete the given entity.
     *
     * @param request
     * @param response
     * @param model
     * @param pathVars
     * @param id
     * @return the return view path
     * @throws Exception
     */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public String delete(HttpServletRequest request, HttpServletResponse response, Model model,
                         @PathVariable(value = "entityTypeName") String entityTypeName,
                         @PathVariable Map<String, String> pathVars,
                         @PathVariable(value = "id") String id,
                         @ModelAttribute(value = "entityForm") Entity entityForm, BindingResult result) {
        EntityTypeParameter entityTypes = EntityTypeParameterBuilder.getBy(dataServiceManager, entityTypeName, entityForm);
        Class entityType = entityTypes.getType();
        Class entityCeilingType = entityTypes.getCeilingType();
        if(entityCeilingType == null){
            return VIEWS.Redirect2Home;
        }
        if(entityType == null){
            return VIEWS.Redirect2Failure;
        }

        if(!(isAjaxRequest(request))){
            return VIEWS.Redirect2Failure;
        }
        Locale locale = request.getLocale();
        IDataService dataService = dataServiceManager.getDataService(entityType.getName());
        IFrontEndEntityService frontEndEntityService = FrontEndEntityService.newInstance(dataService);

        EntityDeleteRequest deleteRequest = Parameter2RequestTranslator.makeDeleteRequest(entityTypes,
            request.getRequestURI(), UrlUtils.buildFullRequestUrl(request), id, entityForm);

        EntityDeleteResponse deleteResponse = frontEndEntityService.delete(deleteRequest, locale);
        boolean success = deleteResponse.success();
        if(!success){
            return this.makeDataView(model, deleteResponse);
        } else {
            String resultUrl = request.getContextPath() + "/" + entityTypeName;
            return makeRedirectView(model, resultUrl);
        }
    }


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
//     * Note that the request must contain a parameter called "key" when attempting to delete a collection item from a
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

    private void setCommonModelAttributes(Model model, Locale locale) {
        boolean production = RuntimePropertiesPublisher.instance().getBoolean("tally.production", false);
        Map<String, String> messageMap = getCommonMessage(locale);
        String messageDict = getObjectInJson(messageMap);

        model.addAttribute("messageDict", messageDict);
//        model.addAttribute("currentUrl", request.getRequestURL().toString());
        model.addAttribute("production", production);
    }

    private void setErrorModalAttributes(Model model, EntityResponse entityResponse){
        EntityErrors errors = entityResponse.getErrors();
        if(errors != null && errors.containsError()){
            model.addAttribute("errors", errors.getGlobal());
        }
    }

    protected String makeDataView(Model model, EntityResponse data) {
        model.addAttribute("data", data);
        model.addAttribute("success", data.success());
        return DataView;
    }

    class Helper {
        private CurrentPath buildCurrentPath(String sectionName, HttpServletRequest request) {
            CurrentPath currentPath = new CurrentPath();
            MenuPath path = adminMenuService.findMenuPathByUrl(sectionName);
            if(path != null){
                String currentUrl = request.getRequestURL().toString();
                currentPath.setMenuEntries(path, adminMenuService.getEntriesOnPath(path));
                currentPath.setUrl(currentUrl);
            }

            return currentPath;
        }
    }
}
