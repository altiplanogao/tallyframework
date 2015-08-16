package com.taoswork.tallybook.business.dataservice.tallyadmin.service.tallyadmin.impl;

import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminEmployee;
import com.taoswork.tallybook.business.dataservice.tallyadmin.TallyAdminDataServiceDefinition;
import com.taoswork.tallybook.business.dataservice.tallyadmin.dao.AdminEmployeeDao;
import com.taoswork.tallybook.business.dataservice.tallyadmin.service.tallyadmin.AdminEmployeeService;
import com.taoswork.tallybook.dynamic.dataservice.entity.EntityServiceBase;
import com.taoswork.tallybook.general.dataservice.support.annotations.EntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Gao Yuan on 2015/5/12.
 */
@Service(AdminEmployeeService.SERVICE_NAME)
@EntityService(AdminEmployeeService.SERVICE_NAME)
public class AdminEmployeeServiceImpl
        extends EntityServiceBase
        implements AdminEmployeeService{

    @Resource(name = AdminEmployeeDao.COMPONENT_NAME)
    protected AdminEmployeeDao adminEmployeeDao;
//
//    @Resource(name = TallyUserDataService.COMPONENT_NAME)
//    protected TallyUserDataService tallyUserDataService;

    @Override
    public AdminEmployee readAdminEmployeeByPersonId(Long personId){
        return adminEmployeeDao.readAdminEmployeeByPersonId(personId);
    }

    @Override
    @Transactional(TallyAdminDataServiceDefinition.TADMIN_TRANSACTION_MANAGER_NAME)
    public AdminEmployee saveAdminEmployee(AdminEmployee employee){
        return adminEmployeeDao.save(employee);
    }
}
