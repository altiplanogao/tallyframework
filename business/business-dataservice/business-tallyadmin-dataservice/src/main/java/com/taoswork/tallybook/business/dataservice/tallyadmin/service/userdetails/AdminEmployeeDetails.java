package com.taoswork.tallybook.business.dataservice.tallyadmin.service.userdetails;

import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminEmployee;
import com.taoswork.tallybook.business.datadomain.tallyuser.FacetType;
import com.taoswork.tallybook.business.dataservice.tallyuser.service.userdetails.FacetDetails;
import com.taoswork.tallybook.business.dataservice.tallyuser.service.userdetails.PersonDetails;
import com.taoswork.tallybook.general.authority.domain.authentication.user.AccountStatus;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/5/10.
 */
public class AdminEmployeeDetails extends FacetDetails {
    protected AdminEmployee employee;
    public AdminEmployeeDetails(
            PersonDetails personDetails,
            AdminEmployee employee, String password, Collection<? extends GrantedAuthority> authorities) {
        super(personDetails, FacetType.Admin, FacetType.ADMIN_FACET_ID,
                personDetails.getUsername(), password,
                employee.getStatus() == null ? new AccountStatus() : employee.getStatus(),
                authorities );
        this.employee = employee;
    }

    public AdminEmployee getEmployee() {
        return employee;
    }

    public void setEmployee(AdminEmployee employee) {
        this.employee = employee;
    }
}
