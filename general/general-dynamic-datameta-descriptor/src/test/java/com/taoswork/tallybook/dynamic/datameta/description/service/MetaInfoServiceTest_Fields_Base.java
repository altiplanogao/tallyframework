package com.taoswork.tallybook.dynamic.datameta.description.service;

import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.typedcollection._CollectionFieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.descriptor.field.typedmap.MapFieldInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.IEntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.infos.main.EntityInfo;
import com.taoswork.tallybook.dynamic.datameta.description.service.impl.MetaInfoServiceImpl;
import com.taoswork.tallybook.dynamic.datameta.metadata.IClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.MetadataService;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.impl.MetadataServiceImpl;
import com.taoswork.tallybook.testframework.domain.business.impl.CompanyImpl;
import com.taoswork.tallybook.testframework.domain.business.impl.DepartmentImpl;
import com.taoswork.tallybook.testframework.domain.business.impl.EmployeeImpl;
import com.taoswork.tallybook.testframework.domain.business.impl.ParkingSpaceImpl;
import org.junit.Assert;

import java.util.Map;

/**
 * Created by Gao Yuan on 2015/11/16.
 */
public class MetaInfoServiceTest_Fields_Base {
    protected final MetadataService metadataService;
    protected final MetaInfoService metaInfoService;

    protected final IClassMetadata companyMetadata;
    protected final IClassMetadata departmentMetadata;
    protected final IClassMetadata employeeMetadata;
    protected final IClassMetadata parkSpaceMetadata;

    protected final EntityInfo companyInfo;
    protected final EntityInfo departmentInfo;
    protected final EntityInfo employeeInfo;
    protected final EntityInfo parkSpaceInfo;
    protected final EntityInfo[] metainfos;

    public MetaInfoServiceTest_Fields_Base(){
        metadataService = new MetadataServiceImpl();
        metaInfoService = new MetaInfoServiceImpl();

        companyMetadata = metadataService.generateMetadata(CompanyImpl.class, null);
        departmentMetadata = metadataService.generateMetadata(DepartmentImpl.class, null);
        employeeMetadata = metadataService.generateMetadata(EmployeeImpl.class, null);
        parkSpaceMetadata = metadataService.generateMetadata(ParkingSpaceImpl.class, null);

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
                                                       EntityInfo holder, Class entryClass){
        Assert.assertNotNull(collectionFieldInfo);
        String entryType = collectionFieldInfo.getInstanceType();
        Assert.assertEquals(entryClass.getName(), entryType);
        Map<String, IEntityInfo> referencingEntryInfos = holder.getReferencing();
        IEntityInfo entryInfo = referencingEntryInfos.get(collectionFieldInfo.getInstanceType());
        Assert.assertNotNull(entryInfo);
    }

    protected static void assertValidMapFieldInfo(MapFieldInfo mapFieldInfo, EntityInfo holder, Class keyClass, Class valueClass){
        Map<String, IEntityInfo> referencingEntryInfos = holder.getReferencing();
        Assert.assertNotNull(mapFieldInfo);

        if(keyClass != null) {
            String keyEntryType = mapFieldInfo.getKeyEntryType();
            Assert.assertEquals(keyClass.getName(), keyEntryType);
            IEntityInfo keyEntityInfo = referencingEntryInfos.get(keyEntryType);
            Assert.assertNotNull(keyEntityInfo);
        }
        if (valueClass != null) {
            String valueEntryType = mapFieldInfo.getValueEntryType();
            Assert.assertEquals(valueClass.getName(), valueEntryType);
            IEntityInfo valueEntityInfo = referencingEntryInfos.get(valueEntryType);
            Assert.assertNotNull(valueEntityInfo);
        }
    }
}
