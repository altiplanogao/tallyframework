package com.taoswork.tallybook.business.dataservice.tallyadmin.service.userdetails.impl;

import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminEmployee;
import com.taoswork.tallybook.business.datadomain.tallyuser.Person;
import com.taoswork.tallybook.business.dataservice.tallyadmin.TallyAdminDataService;
import com.taoswork.tallybook.business.dataservice.tallyadmin.service.tallyadmin.AdminEmployeeService;
import com.taoswork.tallybook.business.dataservice.tallyadmin.service.userdetails.AdminEmployeeDetails;
import com.taoswork.tallybook.business.dataservice.tallyadmin.service.userdetails.AdminEmployeeDetailsService;
import com.taoswork.tallybook.business.dataservice.tallyuser.TallyUserDataService;
import com.taoswork.tallybook.business.dataservice.tallyuser.service.userdetails.FacetDetails;
import com.taoswork.tallybook.business.dataservice.tallyuser.service.userdetails.PersonDetails;
import com.taoswork.tallybook.business.dataservice.tallyuser.service.userdetails.PersonDetailsService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * Created by Gao Yuan on 2015/5/10.
 */
//@Service(AdminEmployeeDetailsService.COMPONENT_NAME)
//unable to declare @Service component here, because we don't have bean TallyUserDataService declared in this maven module
public class AdminEmployeeDetailsServiceImpl implements AdminEmployeeDetailsService {

    @Resource(name = TallyUserDataService.COMPONENT_NAME)
    private TallyUserDataService tallyUserDataService;

    @Resource(name = TallyAdminDataService.COMPONENT_NAME)
    private TallyAdminDataService tallyAdminDataService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        PersonDetailsService personDetailsService = tallyUserDataService.getService(PersonDetailsService.COMPONENT_NAME);
        PersonDetails personDetails = personDetailsService.getPersistentPersonDetails();
        if(null == personDetails){
            personDetails = personDetailsService.loadPersonByUsername(s);
        }
        if(personDetails == null){
            return null;
        }

        Person person = personDetails.getPerson();
        AdminEmployeeService adminEmployeeService = tallyAdminDataService.getService(AdminEmployeeService.SERVICE_NAME);
        AdminEmployee adminEmployee = adminEmployeeService.readAdminEmployeeByPersonId(person.getId());
        if(adminEmployee == null || adminEmployee.getId() == null){
            return null;
        }

        FacetDetails adminFacetDetails = new AdminEmployeeDetails(personDetails, adminEmployee, "", new ArrayList<GrantedAuthority>());

        personDetails.addFacetDetails(adminFacetDetails);
        return personDetails;
    }

}
