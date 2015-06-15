package com.taoswork.tallybook.admincore.security.impl;

import com.taoswork.tallybook.admincore.security.AdminSecurityService;
import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminEmployee;
import com.taoswork.tallybook.business.datadomain.tallyuser.FacetType;
import com.taoswork.tallybook.business.datadomain.tallyuser.Person;
import com.taoswork.tallybook.business.dataservice.tallyadmin.service.userdetails.AdminEmployeeDetails;
import com.taoswork.tallybook.business.dataservice.tallyuser.TallyUserDataService;
import com.taoswork.tallybook.business.dataservice.tallyuser.service.userdetails.PersonDetails;
import com.taoswork.tallybook.business.dataservice.tallyuser.service.userdetails.PersonDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Gao Yuan on 2015/5/14.
 */
@Service(AdminSecurityService.COMPONENT_NAME)
public class AdminSecurityServiceImpl implements AdminSecurityService {
    private static final String ANONYMOUS_USER_NAME = "anonymousUser";

    @Resource(name = TallyUserDataService.COMPONENT_NAME)
    TallyUserDataService tallyUserDataService;

    @Override
    public Person getPersistentPerson(){
        PersonDetailsService personDetailsService = tallyUserDataService.getService(PersonDetailsService.COMPONENT_NAME);
        PersonDetails personDetails = personDetailsService.getPersistentPersonDetails();
        if(null == personDetails){
            return null;
        }
        return personDetails.getPerson();
    }

    @Override
    public AdminEmployee getPersistentAdminEmployee(){
        PersonDetailsService personDetailsService = tallyUserDataService.getService(PersonDetailsService.COMPONENT_NAME);
        PersonDetails personDetails = personDetailsService.getPersistentPersonDetails();
        if(null == personDetails){
            return null;
        }
        AdminEmployeeDetails fd = personDetails.getFacetDetails(FacetType.Admin, FacetType.ADMIN_FACET_ID);
        if(fd == null){
            return null;
        }
        return fd.getEmployee();
    }
}
