package com.taoswork.tallybook.admincore.web.model.service;

import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminEmployee;
import com.taoswork.tallybook.business.datadomain.tallyuser.Person;
import com.taoswork.tallybook.general.solution.menu.Menu;

/**
 * Created by Gao Yuan on 2015/5/14.
 */
public interface AdminCommonModelService {
    public static final String COMPONENT_NAME = "AdminCommonModelService";

    AdminEmployee getPersistentAdminEmployee();

    Person getPersistentPerson();

    Menu getAdminMenu();
}
