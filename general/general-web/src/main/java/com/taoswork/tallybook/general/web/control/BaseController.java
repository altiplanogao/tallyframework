package com.taoswork.tallybook.general.web.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taoswork.tallybook.general.solution.threading.ThreadLocalHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Gao Yuan on 2015/5/6.
 */
public abstract class BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    public static final String AJAX_VIEW_NAME_PREFIX = "ajax:";
    public static final String AJAX_REQUEST_KEY = "ajax";
    public static final String MODAL_REQUEST_KEY = "modal";
    public static String ajaxViewName(String viewName){
        return AJAX_VIEW_NAME_PREFIX + viewName;
    }

    public BaseController(){
        LOGGER.info("[CONTROLLER: " +
                this.getClass().getSimpleName() + "] Constructor" );
    }

    public static boolean isModalRequest(HttpServletRequest request) {
        return isModalRequest(request, MODAL_REQUEST_KEY);
    }

    public static boolean isModalRequest(HttpServletRequest request, String modalKey) {
        boolean isModalByUrl = false;
        if(!StringUtils.isEmpty(modalKey)){
            String isModalString = request.getParameter(modalKey);
            isModalByUrl = ("".equals(isModalString) || "true".equals(isModalString));
            if(isModalByUrl)
                return true;
        }
        String requestedWithHeader = request.getHeader("RequestInModal");
        boolean result = "true".equals(requestedWithHeader);

        return result;
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
}
