package com.taoswork.tallybook.dynamic.datameta.metadata.service;

import com.taoswork.tallybook.dynamic.datameta.metadata.IClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.impl.MetadataServiceImpl;
import com.taoswork.tallybook.testframework.domain.business.impl.CompanyImpl;
import com.taoswork.tallybook.testframework.domain.business.impl.DepartmentImpl;
import com.taoswork.tallybook.testframework.domain.business.impl.EmployeeImpl;
import com.taoswork.tallybook.testframework.domain.business.impl.ParkingSpaceImpl;
import org.junit.After;
import org.junit.Before;

/**
 * Created by Gao Yuan on 2015/11/16.
 */
public class MetadataServiceTest_Fields_Base {
    protected final MetadataService metadataService;
    protected final IClassMetadata companyMetadata;
    protected final IClassMetadata departmentMetadata;
    protected final IClassMetadata employeeMetadata;
    protected final IClassMetadata parkSpaceMetadata;
    protected final IClassMetadata[] metadatas;

    protected MetadataServiceTest_Fields_Base(){
        metadataService = new MetadataServiceImpl();
        companyMetadata = metadataService.generateMetadata(CompanyImpl.class, null);
        departmentMetadata = metadataService.generateMetadata(DepartmentImpl.class, null);
        employeeMetadata = metadataService.generateMetadata(EmployeeImpl.class, null);
        parkSpaceMetadata = metadataService.generateMetadata(ParkingSpaceImpl.class, null);
        metadatas = new IClassMetadata[]{
            companyMetadata,
            departmentMetadata,
            employeeMetadata,
            parkSpaceMetadata};
    }
}
