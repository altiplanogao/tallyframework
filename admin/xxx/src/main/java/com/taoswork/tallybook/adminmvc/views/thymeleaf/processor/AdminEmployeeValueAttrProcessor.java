package com.taoswork.tallybook.adminmvc.views.thymeleaf.processor;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;

/**
 * Created by Gao Yuan on 2015/5/14.
 */
//@Component(AdminEmployeeValueAttrProcessor.COMPONENT_NAME)
public class AdminEmployeeValueAttrProcessor extends AbstractDataAccessAttrProcessor {
    public static final String COMPONENT_NAME = "AdminEmployeeValueAttrProcessor";

    public static final String ATTR_NAME = "admin_employee";

    public AdminEmployeeValueAttrProcessor() {
        super(ATTR_NAME);
    }

//    @Resource(name = AdminCommonModelService.COMPONENT_NAME)
//    private AdminCommonModelService adminCommonModelService;

    @Override
    protected void modifyModelAttribute(Arguments arguments, Element element, String attributeName) {
        String resultVarName = element.getAttributeValue(attributeName);
//        AdminEmployee employee = adminCommonModelService.getPersistentAdminEmployee();
//
//        if(employee != null){
//            addToModel(arguments, resultVarName, employee);
//        }
    }

    @Override
    public int getPrecedence() {
        return 1000;
    }

}
