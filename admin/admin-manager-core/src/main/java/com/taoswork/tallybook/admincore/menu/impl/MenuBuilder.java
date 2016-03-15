package com.taoswork.tallybook.admincore.menu.impl;

import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminEmployee;
import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminGroup;
import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminProtection;
import com.taoswork.tallybook.business.datadomain.tallybusiness.subject.Bu;
import com.taoswork.tallybook.business.datadomain.tallybusiness.subject.Employee;
import com.taoswork.tallybook.business.datadomain.tallybusiness.subject.Role;
import com.taoswork.tallybook.business.datadomain.tallyuser.Person;
import com.taoswork.tallybook.general.solution.menu.IMenuEntryUpdater;
import com.taoswork.tallybook.general.solution.menu.Menu;
import com.taoswork.tallybook.general.solution.menu.MenuEntryBuilder;

/**
 * Created by Gao Yuan on 2016/3/15.
 */
class MenuBuilder {
    public static final String USER_GROUP_NAME = "TBMG.User";
    public static final String PERSON_ENTRY_NAME = "TBME.Person";
    public static final String BU_ENTRY_NAME = "TBME.BusinessUnit";
    public static final String ADMIN_SECURITY_GROUP_NAME = "TBMG.Security";
    public static final String ADMIN_EMP_ENTRY_NAME = "TBME.AdminEmployee";
    public static final String ADMIN_ROLE_ENTRY_NAME = "TBME.AdminRole";
    public static final String ADMIN_RES_ENTRY_NAME = "TBME.Resource";

    public static final String BUSINESS_GROUP_NAME = "TBME.Business";
    public static final String BUSINESS_BU_ENTRY_NAME = "TBME.Business";
    public static final String BUSINESS_EMP_ENTRY_NAME = "TBME.Employee";
    public static final String BUSINESS_ROLE_ENTRY_NAME = "TBME.Role";

    public static Menu buildMenu(IMenuEntryUpdater updater){
        MenuEntryBuilder builder = MenuEntryBuilder.createRootNode();
        builder.beginEntry()
                    .key("user").name(USER_GROUP_NAME).description("fa-user")
                    .beginEntry()
                        .key("person").name(PERSON_ENTRY_NAME).icon("glyphicon-user").entity(Person.class)
                    .endEntry()
                    .beginEntry()
                        .key("bu").name(BU_ENTRY_NAME).icon("glyphicon-user").entity(Bu.class)
                    .endEntry()
                .beginSiblingEntry()
                    .key("admin-security").name(ADMIN_SECURITY_GROUP_NAME).icon("fa-user-secret")
                    .beginEntry()
                       .key("admin-employee").name(ADMIN_EMP_ENTRY_NAME).icon("glyphicon-user").entity(AdminEmployee.class)
                    .beginSiblingEntry()
                        .key("admin-role").name(ADMIN_ROLE_ENTRY_NAME).icon("glyphicon-user").entity(AdminGroup.class)
                    .beginSiblingEntry()
                        .key("admin-res").name(ADMIN_RES_ENTRY_NAME).icon("glyphicon-user").entity(AdminProtection.class)
                    .endEntry()
                .beginSiblingEntry()
                    .key("business").name(BUSINESS_GROUP_NAME).icon("fa-briefcase")
                    .beginEntry()
                        .key("bu").name(BUSINESS_BU_ENTRY_NAME).icon("").entity(Bu.class)
                    .beginSiblingEntry()
                        .key("employee").name(BUSINESS_EMP_ENTRY_NAME).entity(Employee.class)
                    .beginSiblingEntry()
                        .key("role").name(BUSINESS_ROLE_ENTRY_NAME).entity(Role.class)
                    .endEntry()
                .endEntry();

        return builder.makeMenu(updater);
    }
}
