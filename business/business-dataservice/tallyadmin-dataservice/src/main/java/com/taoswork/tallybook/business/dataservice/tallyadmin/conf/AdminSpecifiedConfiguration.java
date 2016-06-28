package com.taoswork.tallybook.business.dataservice.tallyadmin.conf;

import com.taoswork.tallycheck.authority.solution.engine.PermissionEngine;
import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminEmployee;
import com.taoswork.tallybook.business.datadomain.tallyadmin.AdminGroup;
import com.taoswork.tallycheck.dataservice.mongo.core.entityservice.MongoEntityService;
import com.taoswork.tallycheck.dataservice.service.IEntityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * Created by Gao Yuan on 2016/2/26.
 */
@Configuration
public class AdminSpecifiedConfiguration {
    public static final String ADMIN_PERMISSION_ENGINE_NAME = "AdminPermissionEngine";

    @Resource(name = IEntityService.COMPONENT_NAME)
    protected MongoEntityService entityService;

    @Bean(name = ADMIN_PERMISSION_ENGINE_NAME)
    public PermissionEngine getPermissionEngine(){
        return new PermissionEngine(entityService, AdminEmployee.class, AdminGroup.class);
    }
}
