package com.taoswork.tallybook.admincore.menu.impl;

import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminEmployee;
import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminGroup;
import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminProtection;
import com.taoswork.tallybook.business.datadomain.tallybusiness.module.ModuleUsage;
import com.taoswork.tallybook.business.datadomain.tallybusiness.subject.*;
import com.taoswork.tallybook.business.datadomain.tallybusiness.work.WorkFeedback;
import com.taoswork.tallybook.business.datadomain.tallybusiness.work.WorkPlan;
import com.taoswork.tallybook.business.datadomain.tallybusiness.work.WorkTicket;
import com.taoswork.tallybook.business.datadomain.tallymanagement.ModuleEntry;
import com.taoswork.tallybook.business.datadomain.tallyuser.Person;
import com.taoswork.tallybook.general.solution.menu.IMenuEntryUpdater;
import com.taoswork.tallybook.general.solution.menu.Menu;
import com.taoswork.tallybook.general.solution.menu.MenuEntryBuilder;

/**
 * Created by Gao Yuan on 2016/3/15.
 */
class MenuBuilder {
    public static final String USER_GROUP_NAME = "TBMG.User";
    public static final String USER_PERSON_ENTRY_NAME = "TBME.Person";

    public static final String MANAGEMENT_GROUP_NAME = "TBMG.Management";
    public static final String MANAGEMENT_ADMIN_EMP_ENTRY_NAME = "TBME.AdminEmployee";
    public static final String MANAGEMENT_ADMIN_ROLE_ENTRY_NAME = "TBME.AdminRole";
    public static final String MANAGEMENT_ADMIN_RES_ENTRY_NAME = "TBME.AdminResource";
    public static final String MANAGEMENT_MODULE_ENTRY_ENTRY_NAME = "TBME.ModuleEntry";

    public static final String BUSINESS_GROUP_NAME = "TBME.Business";
    public static final String BUSINESS_BU_ENTRY_NAME = "TBME.BusinessUnit";
    public static final String BUSINESS_EMP_ENTRY_NAME = "TBME.Employee";
    public static final String BUSINESS_ROLE_ENTRY_NAME = "TBME.Role";
    public static final String BUSINESS_BP_ENTRY_NAME = "TBME.BusinessPartner";
    public static final String BUSINESS_ASSET_ENTRY_NAME = "TBME.Asset";
    public static final String BUSINESS_MODULE_USAGE_ENTRY_NAME = "TBME.ModuleUsage";
    public static final String BUSINESS_WORKPLAN_ENTRY_NAME = "TBME.WorkPlan";
    public static final String BUSINESS_WORKTICKET_ENTRY_NAME = "TBME.WorkTicket";
    public static final String BUSINESS_WORKFEEDBACK_ENTRY_NAME = "TBME.WorkFeedback";

    public static Menu buildMenu(IMenuEntryUpdater updater){
        MenuEntryBuilder builder = MenuEntryBuilder.createRootNode();
        builder.beginEntry()
                .key("user").name(USER_GROUP_NAME).icon("fa-user")
                    .beginEntry()
                        .key("person").name(USER_PERSON_ENTRY_NAME).icon("glyphicon-user").entity(Person.class)
                    .endEntry()
//                    .beginEntry()
//                        .key("bu").name(BU_ENTRY_NAME).icon("glyphicon-user").entity(Bu.class)
//                    .endEntry()
                .beginSiblingEntry()
                    .key("admin-security").name(MANAGEMENT_GROUP_NAME).icon("fa-user-secret")
                    .beginEntry()
                        .key("admin-res").name(MANAGEMENT_ADMIN_RES_ENTRY_NAME).icon("glyphicon-user").entity(AdminProtection.class)
                    .beginSiblingEntry()
                        .key("admin-employee").name(MANAGEMENT_ADMIN_EMP_ENTRY_NAME).icon("glyphicon-user").entity(AdminEmployee.class)
                    .beginSiblingEntry()
                        .key("admin-role").name(MANAGEMENT_ADMIN_ROLE_ENTRY_NAME).icon("glyphicon-user").entity(AdminGroup.class)
                    .beginSiblingEntry()
                        .key("module-entry").name(MANAGEMENT_MODULE_ENTRY_ENTRY_NAME).icon("glyphicon-user").entity(ModuleEntry.class)
                    .endEntry()
                .beginSiblingEntry()
                    .key("business").name(BUSINESS_GROUP_NAME).icon("fa-briefcase")
                    .beginEntry()
                        .key("bu").name(BUSINESS_BU_ENTRY_NAME).icon("").entity(Bu.class)
                    .beginSiblingEntry()
                        .key("employee").name(BUSINESS_EMP_ENTRY_NAME).entity(Employee.class)
                    .beginSiblingEntry()
                        .key("role").name(BUSINESS_ROLE_ENTRY_NAME).entity(Role.class)
                    .beginSiblingEntry()
                        .key("asset").name(BUSINESS_ASSET_ENTRY_NAME).entity(Asset.class)
                .beginSiblingEntry()
                        .key("bp").name(BUSINESS_BP_ENTRY_NAME).entity(Bp.class)
                    .beginSiblingEntry()
                        .key("moduleusage").name(BUSINESS_MODULE_USAGE_ENTRY_NAME).entity(ModuleUsage.class)
                    .beginSiblingEntry()
                        .key("workplan").name(BUSINESS_WORKPLAN_ENTRY_NAME).entity(WorkPlan.class)
                    .beginSiblingEntry()
                        .key("workticket").name(BUSINESS_WORKTICKET_ENTRY_NAME).entity(WorkTicket.class)
                    .beginSiblingEntry()
                        .key("workfeedback").name(BUSINESS_WORKFEEDBACK_ENTRY_NAME).entity(WorkFeedback.class)
                    .endEntry()
                .endEntry();

        return builder.makeMenu(updater);
    }
}
