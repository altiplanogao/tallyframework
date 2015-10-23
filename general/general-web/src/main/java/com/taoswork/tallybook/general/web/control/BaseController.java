package com.taoswork.tallybook.general.web.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taoswork.tallybook.general.solution.threading.ThreadLocalHelper;
import com.taoswork.tallybook.general.web.view.thymeleaf.TallyBookDataViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/5/6.
 */
public abstract class BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    protected static final String DataView = TallyBookDataViewResolver.JSON_VIEW_NAME;

    public static final String AJAX_VIEW_NAME_PREFIX = "ajax:";
    public static final String AJAX_REQUEST_KEY = "ajax";
    public static final String SIMPLE_VIEW_REQUEST_KEY = "simpleView";
    public static String ajaxViewName(String viewName){
        return AJAX_VIEW_NAME_PREFIX + viewName;
    }

    public BaseController(){
    }

    protected String getPathVariable(Map<String, String> pathVars, String pathKey) {
        return pathVars.get(pathKey);
    }

    public static boolean isSimpleViewRequest(HttpServletRequest request) {
        return isSimpleViewRequest(request, SIMPLE_VIEW_REQUEST_KEY);
    }

    public static boolean isSimpleViewRequest(HttpServletRequest request, String modalKey) {
        boolean isModalByUrl = false;
        if(!StringUtils.isEmpty(modalKey)){
            String isModalString = request.getParameter(modalKey);
            isModalByUrl = ("".equals(isModalString) || "true".equals(isModalString));
            if(isModalByUrl)
                return true;
        }
        String requestedWithHeader = request.getHeader("RequestInSimpleView");
        boolean result = "true".equals(requestedWithHeader);

        return result;
    }

    public static boolean isAjaxDataRequest(HttpServletRequest request){
        if(isAjaxRequest(request) && (!isSimpleViewRequest(request))){
            return true;
        }
        return false;
    }

    public static boolean isAjaxRequest(HttpServletRequest request){
        return isAjaxRequest(request, AJAX_REQUEST_KEY);
    }

    public static boolean isAjaxRequest(HttpServletRequest request, String ajaxKey) {
        boolean isAjaxByUrl = false;
        if(!StringUtils.isEmpty(ajaxKey)){
            String isAjaxString = request.getParameter(ajaxKey);
            isAjaxByUrl = "true".equals(isAjaxString);
            if(isAjaxByUrl)
                return true;
        }
        String requestedWithHeader = request.getHeader("X-Requested-With");
        boolean result = "XMLHttpRequest".equals(requestedWithHeader);

        return result;
    }

    private ThreadLocal<ObjectMapper> objectMapper = ThreadLocalHelper.createThreadLocal(ObjectMapper.class);
    protected String getObjectInJson(Object data) {
        try {
            return objectMapper.get().writeValueAsString(data);
        } catch (JsonProcessingException exp) {
            throw new RuntimeException(exp);
        }
    }

    protected String makeRedirectView(Model model, String url) {
        model.addAttribute("operation", "redirect");
        model.addAttribute("url", url);

        return DataView;
    }

}
