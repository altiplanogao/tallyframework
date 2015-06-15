package com.taoswork.tallybook.adminmvc.views.thymeleaf.processor;

import com.taoswork.tallybook.general.solution.menu.Menu;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;

/**
 * Created by Gao Yuan on 2015/5/14.
 */
//@Component(AdminMenuValueAttrProcessor.COMPONENT_NAME)
public class AdminMenuValueAttrProcessor extends AbstractDataAccessAttrProcessor {
    public static final String COMPONENT_NAME = "AdminMenuValueAttrProcessor";
    public static final String ATTR_NAME = "admin_menu";

    public AdminMenuValueAttrProcessor() {
        super(ATTR_NAME);
    }

    @Override
    protected void modifyModelAttribute(Arguments arguments, Element element, String attributeName) {
        String resultVarName = element.getAttributeValue(attributeName);
        Menu employee = getMenu();

        if(employee != null){
            addToModel(arguments, resultVarName, employee);
        }
    }

    @Override
    public int getPrecedence() {
        return 1000;
    }

    public Menu getMenu() {
        return null;
    }
}
