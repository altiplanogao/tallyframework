package com.taoswork.tallybook.general.solution.web.view.thymeleaf;

import com.taoswork.tallybook.general.extension.utils.UrlUtility;
import org.springframework.web.servlet.View;
import org.thymeleaf.spring4.view.AbstractThymeleafView;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/4/21.
 */
public class TallyBookThymeleafViewResolver extends ThymeleafViewResolver {
    private static final String ESCAPE_MAPPING_VIEW_VALUE = "NULL";
    private static final String TEMPLATE_VIEW_SLOT_NAME = "templateName";

    protected Map<String, String> layoutMap = new HashMap<String, String>();
    protected String defaultLayout = "layout/entityLayout";

    public Map<String, String> getLayoutMap() {
        return layoutMap;
    }

    public void setLayoutMap(Map<String, String> layoutMap) {
        this.layoutMap = layoutMap;
    }

    public String getDefaultLayout() {
        return defaultLayout;
    }

    public void setDefaultLayout(String defaultLayout) {
        this.defaultLayout = defaultLayout;
    }

    @Override
    protected boolean canHandle(String viewName, Locale locale) {
        // TODO Auto-generated method stub
        return super.canHandle(viewName, locale);
    }

    @Override
    public View resolveViewName(String viewName, Locale locale)
            throws Exception {
        // TODO Auto-generated method stub
        return super.resolveViewName(viewName, locale);
    }

    @Override
    public void removeFromCache(String viewName, Locale locale) {
        // TODO Auto-generated method stub
        super.removeFromCache(viewName, locale);
    }

    @Override
    protected View loadView(String viewName, Locale locale) throws Exception {
        String originalViewName = viewName;
        viewName = UrlUtility.findLongestPrefixMatchingValue(originalViewName, layoutMap,
                ESCAPE_MAPPING_VIEW_VALUE, defaultLayout);

        AbstractThymeleafView view = (AbstractThymeleafView) super.loadView(viewName, locale);
        view.addStaticVariable(TEMPLATE_VIEW_SLOT_NAME, originalViewName);
        return view;
    }
}
