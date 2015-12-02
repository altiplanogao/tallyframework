package com.taoswork.tallybook.adminmvc.controllers.entities;

import com.taoswork.tallybook.dynamic.datameta.metadata.IClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed.ExternalForeignEntityFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed.ForeignEntityFieldMetadata;
import com.taoswork.tallybook.dynamic.dataservice.IDataService;
import com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.dynamic.dataservice.manage.DataServiceManager;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.parameter.EntityTypeParameter;
import com.taoswork.tallybook.dynamic.dataservice.server.io.request.parameter.EntityTypeParameterBuilder;
import org.apache.commons.httpclient.URI;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/11/25.
 */
@Controller(AdminBasicEntityOperationSupportController.CONTROLLER_NAME)
@RequestMapping("/{entityTypeName:^[\\w|\\-|\\.]+$}")
public class AdminBasicEntityOperationSupportController extends _AdminBasicEntityControllerBase {
    public static final String CONTROLLER_NAME = "AdminBasicEntityOperationSupportController";

    @Resource(name = DataServiceManager.COMPONENT_NAME)
    protected DataServiceManager dataServiceManager;

    /**
     * @param request
     * @param response
     * @param model
     * @param pathVars
     * @param fieldName
     * @param ids
     * @param requestParams
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{field:.*}/details", method = RequestMethod.GET)
    public @ResponseBody Map<String, String> getCollectionValueDetails(HttpServletRequest request, HttpServletResponse response, Model model,
                                                                       @PathVariable Map<String, String> pathVars,
                                                                       @PathVariable(value="field") String fieldName,
                                                                       @RequestParam String ids,
                                                                       @RequestParam MultiValueMap<String, String> requestParams) throws Exception {
        return null;
    }

    /**
     * Shows the modal dialog that is used to select a "to-one" collection item. For example, this could be used to show
     * a list of categories for the ManyToOne field "defaultCategory" in Product.
     *
     * @param request
     * @param response
     * @param model
     * @param pathVars
     * @param fieldName
     * @return the return view path
     * @throws Exception
     */
    @RequestMapping(value = "/{field:.*}/select", method = RequestMethod.GET)
    public String select(HttpServletRequest request, HttpServletResponse response, Model model,
                         @PathVariable Map<String, String> pathVars,
                         @PathVariable(value="field") String fieldName,
                         @RequestParam(required = false) String requestingEntityId, /* could be null for new entity*/
                         @RequestParam  MultiValueMap<String, String> requestParams) throws Exception {
        String entityTypeName = getEntityTypeName(pathVars);
        EntityTypeParameter entityTypes = EntityTypeParameterBuilder.getBy(dataServiceManager, entityTypeName);
        Class entityType = entityTypes.getCeilingType();
        if (entityType == null) {
            return VIEWS.Redirect2Home;
        }
        IDataService dataService = dataServiceManager.getDataService(entityType.getName());
        DynamicEntityMetadataAccess metadataAccess = dataService.getService(DynamicEntityMetadataAccess.COMPONENT_NAME);
        Class instantiable = metadataAccess.getRootInstantiableEntityType(entityType);
        IClassMetadata cm = metadataAccess.getClassMetadata(instantiable, false);
        IFieldMetadata fieldMetadata = cm.getFieldMetadata(fieldName);

        if(fieldMetadata instanceof ForeignEntityFieldMetadata){
            ForeignEntityFieldMetadata foreignEntityFieldMetadata = (ForeignEntityFieldMetadata) fieldMetadata;
            String fieldEntityType = foreignEntityFieldMetadata.getEntityType().getName();

            String oldUri = request.getRequestURI();
            URI uri = new URI(request.getRequestURI(), false);
            uri.setPath(fieldEntityType);
            return "forward:/" + uri.toString();
        }else if(fieldMetadata instanceof ExternalForeignEntityFieldMetadata){
            ExternalForeignEntityFieldMetadata foreignEntityFieldMetadata = (ExternalForeignEntityFieldMetadata) fieldMetadata;
            String fieldEntityType = foreignEntityFieldMetadata.getEntityType().getName();

            String oldUri = request.getRequestURI();
            URI uri = new URI(request.getRequestURI(), false);
            uri.setPath(fieldEntityType);
            return "forward:/" + uri.toString();
        }

        return "";
    }

    @RequestMapping(value = "/{field:.*}/typeahead", method = RequestMethod.GET)
    public @ResponseBody List<Map<String, String>> getTypeaheadResults(HttpServletRequest request,
                                                                       HttpServletResponse response, Model model,
                                                                       @PathVariable Map<String, String> pathVars,
                                                                       @PathVariable(value="field") String fieldName,
                                                                       @RequestParam(required = false) String query,
                                                                       @RequestParam(required = false) String requestingEntityId,
                                                                       @RequestParam MultiValueMap<String, String> requestParams) throws Exception {
        return null;
    }


}
