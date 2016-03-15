package com.taoswork.tallybook.descriptor.description.service;

import com.taoswork.tallybook.descriptor.description.descriptor.field.typedcollection._CollectionFieldInfo;
import com.taoswork.tallybook.descriptor.description.infos.IEntityInfo;
import com.taoswork.tallybook.descriptor.description.infos.main.EntityInfo;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.service.impl.BaseMetaInfoServiceImpl;
import com.taoswork.tallybook.descriptor.service.MetaInfoService;
import com.taoswork.tallybook.descriptor.service.MetaService;
import com.taoswork.tallybook.descriptor.jpa.service.JpaMetaServiceImpl;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.impl.CompanyImpl;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.impl.DepartmentImpl;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.impl.EmployeeImpl;
import com.taoswork.tallybook.testmaterial.jpa.domain.business.impl.ParkingSpaceImpl;
import org.junit.Assert;

import java.util.Map;

/**
 * Created by Gao Yuan on 2015/11/16.
 */
public class MetaInfoServiceTest_Fields_Base {
    protected final MetaService metaService;
    protected final MetaInfoService metaInfoService;

    protected final IClassMeta companyMetadata;
    protected final IClassMeta departmentMetadata;
    protected final IClassMeta employeeMetadata;
    protected final IClassMeta parkSpaceMetadata;

    protected final EntityInfo companyInfo;
    protected final EntityInfo departmentInfo;
    protected final EntityInfo employeeInfo;
    protected final EntityInfo parkSpaceInfo;
    protected final EntityInfo[] metainfos;

    public MetaInfoServiceTest_Fields_Base() {
        metaService = new JpaMetaServiceImpl();
        metaInfoService = new BaseMetaInfoServiceImpl();

        companyMetadata = metaService.generateMeta(CompanyImpl.class, null);
        departmentMetadata = metaService.generateMeta(DepartmentImpl.class, null);
        employeeMetadata = metaService.generateMeta(EmployeeImpl.class, null);
        parkSpaceMetadata = metaService.generateMeta(ParkingSpaceImpl.class, null);

        companyInfo = metaInfoService.generateEntityMainInfo(companyMetadata);
        departmentInfo = metaInfoService.generateEntityMainInfo(departmentMetadata);
        employeeInfo = metaInfoService.generateEntityMainInfo(employeeMetadata);
        parkSpaceInfo = metaInfoService.generateEntityMainInfo(parkSpaceMetadata);
        metainfos = new EntityInfo[]{
                companyInfo,
                departmentInfo,
                employeeInfo,
                parkSpaceInfo
        };
    }

    protected static void assertValidCollectionFieldInfo(_CollectionFieldInfo collectionFieldInfo,
                                                         EntityInfo holder, Class entryClass) {
        Assert.assertNotNull(collectionFieldInfo);
        String entryType = collectionFieldInfo.getInstanceType();
        Assert.assertEquals(entryClass.getName(), entryType);
        Map<String, IEntityInfo> referencingEntryInfos = holder.getReferencing();
        IEntityInfo entryInfo = referencingEntryInfos.get(collectionFieldInfo.getInstanceType());
        Assert.assertNotNull(entryInfo);
    }

}
