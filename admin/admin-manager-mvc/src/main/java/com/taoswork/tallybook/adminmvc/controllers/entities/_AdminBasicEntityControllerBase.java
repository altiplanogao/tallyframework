package com.taoswork.tallybook.adminmvc.controllers.entities;

import com.taoswork.tallybook.application.core.conf.ApplicationCommonConfig;
import com.taoswork.tallybook.dataservice.server.io.response.EntityResponse;
import com.taoswork.tallybook.dataservice.server.io.response.result.EntityErrors;
import com.taoswork.tallybook.general.solution.message.CachedMessageLocalizedDictionary;
import com.taoswork.tallybook.general.solution.property.RuntimePropertiesPublisher;
import com.taoswork.tallybook.general.web.control.BaseController;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/11/25.
 */
abstract class _AdminBasicEntityControllerBase extends BaseController {

    protected static class VIEWS {
        static final String Redirect2Home = "redirect:/";
        static final String Redirect2Failure = "redirect:failure";
        static final String FramedView = "entity/content/framedView";
        static final String SimpleView = "entity/content/simpleView";
    }

    @Resource(name = ApplicationCommonConfig.COMMON_MESSAGE)
    private CachedMessageLocalizedDictionary commonMessage;

    private Map<String, String> getCommonMessage(Locale locale) {
        return commonMessage.getTranslated(locale);
    }

    protected void setCommonModelAttributes(Model model, Locale locale) {
        boolean production = RuntimePropertiesPublisher.instance().getBoolean("tally.production", false);
        Map<String, String> messageMap = getCommonMessage(locale);
        String messageDict = getObjectInJson(messageMap);

        model.addAttribute("messageDict", messageDict);
//        model.addAttribute("currentUrl", request.getRequestURL().toString());
        model.addAttribute("production", production);
    }

    //////////////////////////////////////////////////////////////////////////////////////
    ///         Helper                                                              //////
    //////////////////////////////////////////////////////////////////////////////////////
    protected String getEntityTypeName(Map<String, String> pathVars) {
        return super.getPathVariable(pathVars, "entityTypeName");
    }


    protected void setErrorModelAttributes(Model model, EntityResponse entityResponse) {
        EntityErrors errors = entityResponse.getErrors();
        if (errors != null && errors.containsError()) {
            model.addAttribute("errors", errors.getGlobal());
        }
    }

    protected String makeDataView(Model model, EntityResponse data) {
        model.addAttribute("data", data);
        model.addAttribute("success", data.success());
        return DataView;
    }

}
