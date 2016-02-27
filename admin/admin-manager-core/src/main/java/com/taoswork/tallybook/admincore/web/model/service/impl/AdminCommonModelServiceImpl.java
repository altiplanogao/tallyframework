package com.taoswork.tallybook.admincore.web.model.service.impl;

import com.taoswork.tallybook.admincore.security.AdminSecurityService;
import com.taoswork.tallybook.admincore.web.model.service.AdminCommonModelService;
import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminEmployee;
import com.taoswork.tallybook.business.datadomain.tallyuser.Person;
import com.taoswork.tallybook.business.dataservice.tallyadmin.TallyAdminDataService;
import com.taoswork.tallybook.business.dataservice.tallyadmin.service.userdetails.AdminEmployeeDetailsService;
import com.taoswork.tallybook.business.dataservice.tallyuser.TallyUserDataService;
import com.taoswork.tallybook.general.solution.menu.Menu;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by Gao Yuan on 2015/5/14.
 */
@Component(AdminCommonModelService.COMPONENT_NAME)
public class AdminCommonModelServiceImpl implements AdminCommonModelService {
    @Resource(name = TallyUserDataService.COMPONENT_NAME)
    TallyUserDataService tallyUserDataService;

    @Resource(name = TallyAdminDataService.COMPONENT_NAME)
    TallyAdminDataService tallyAdminDataService;

    @Resource(name = AdminEmployeeDetailsService.COMPONENT_NAME)
    UserDetailsService adminEmployeeDetailsService;

    @Resource(name = AdminSecurityService.COMPONENT_NAME)
    AdminSecurityService adminSecurityService;

    @Override
    public AdminEmployee getPersistentAdminEmployee() {
        return adminSecurityService.getPersistentAdminEmployee();
    }


    @Override
    public Person getPersistentPerson() {
        return adminSecurityService.getPersistentPerson();
    }

    @Override
    public Menu getAdminMenu() {
        return null;
    }
}
