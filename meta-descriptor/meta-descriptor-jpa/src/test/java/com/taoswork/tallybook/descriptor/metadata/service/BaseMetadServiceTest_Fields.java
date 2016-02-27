package com.taoswork.tallybook.descriptor.metadata.service;

import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.service.MetaService;
import com.taoswork.tallybook.descriptor.jpa.service.JpaMetaServiceImpl;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.impl.CompanyImpl;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.impl.DepartmentImpl;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.impl.EmployeeImpl;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.impl.ParkingSpaceImpl;

/**
 * Created by Gao Yuan on 2015/11/16.
 */
public class BaseMetadServiceTest_Fields {
    protected final MetaService metaService;
    protected final IClassMeta companyMetadata;
    protected final IClassMeta departmentMetadata;
    protected final IClassMeta employeeMetadata;
    protected final IClassMeta parkSpaceMetadata;
    protected final IClassMeta[] metadatas;

    protected BaseMetadServiceTest_Fields() {
        metaService = new JpaMetaServiceImpl();
        companyMetadata = metaService.generateMeta(CompanyImpl.class, null);
        departmentMetadata = metaService.generateMeta(DepartmentImpl.class, null);
        employeeMetadata = metaService.generateMeta(EmployeeImpl.class, null);
        parkSpaceMetadata = metaService.generateMeta(ParkingSpaceImpl.class, null);
        metadatas = new IClassMeta[]{
                companyMetadata,
                departmentMetadata,
                employeeMetadata,
                parkSpaceMetadata};
    }
}
