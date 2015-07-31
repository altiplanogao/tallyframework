package com.taoswork.tallybook.general.web.control;

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
    public static String ajaxViewName(String viewName){
        return AJAX_VIEW_NAME_PREFIX + viewName;
    }

    public BaseController(){
        LOGGER.info("[CONTROLLER: " +
                this.getClass().getSimpleName() + "] Constructor" );
    }

    public static boolean isAjaxRequest(HttpServletRequest request){
        return isAjaxRequest(request, AJAX_REQUEST_KEY);
    }

    public static boolean isAjaxRequest(HttpServletRequest request, String ajaxKey) {
        boolean isAjaxByUrl = false;
        if(!StringUtils.isEmpty(ajaxKey)){
            String isAjaxString = request.getParameter(ajaxKey);
            isAjaxByUrl = "true".equals(isAjaxString);
        }
        String requestedWithHeader = request.getHeader("X-Requested-With");
        boolean result = isAjaxByUrl || "XMLHttpRequest".equals(requestedWithHeader);

        return result;
    }
}
