package com.taoswork.tallybook.general.web.view.thymeleaf;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractCachingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Locale;

/**
 * Created by Gao Yuan on 2015/7/28.
 */
public class TallyBookDataViewResolver extends AbstractCachingViewResolver implements Ordered {
    public static final String JSON_VIEW_NAME = "objectview:json";

    private int order = 0;

    @Override
    protected View loadView(String viewName, Locale locale) throws Exception {
        if (JSON_VIEW_NAME.equals(viewName)) {
            View view = new MappingJackson2JsonView();
            return view;
        }
        return null;
    }

    @Override
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    //    @Override
//    public View resolveViewName(String viewName, Locale locale) throws Exception {
//        if("json".equals(viewName)){
//            View view = new MappingJackson2JsonView();
//            return view;
//        }
//        return null;
//    }
}
