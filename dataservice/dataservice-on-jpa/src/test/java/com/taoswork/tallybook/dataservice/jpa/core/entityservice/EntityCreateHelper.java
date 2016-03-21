package com.taoswork.tallybook.dataservice.jpa.core.entityservice;

import com.taoswork.tallybook.dataservice.IDataService;
import com.taoswork.tallybook.dataservice.PersistableResult;
import com.taoswork.tallybook.dataservice.core.persistence.InputEntityTranslator;
import com.taoswork.tallybook.dataservice.exception.ServiceException;
import com.taoswork.tallybook.dataservice.service.EntityMetaAccess;
import com.taoswork.tallybook.dataservice.service.IEntityService;
import com.taoswork.tallybook.descriptor.dataio.in.Entity;
import com.taoswork.tallybook.testmaterial.jpa.domain.zoo.ZooKeeper;
import com.taoswork.tallybook.testmaterial.jpa.domain.zoo.impl.ZooKeeperImpl;
import org.junit.Assert;

/**
 * Created by Gao Yuan on 2015/9/23.
 */
public class EntityCreateHelper {
    public static int createPeopleEntityWith(IDataService dataService,
                                             String namePrefix, int postfixStartingIndex, int createAttempt) {
        InputEntityTranslator translator = new InputEntityTranslator();
        JpaEntityService jpaEntityService = dataService.getService(IEntityService.COMPONENT_NAME);
        EntityMetaAccess metaAccess = dataService.getService(EntityMetaAccess.COMPONENT_NAME);

        int created = 0;
        try {
            for (int i = 0; i < createAttempt; ++i) {
                String name = namePrefix + (int) (postfixStartingIndex + i);

//                    int expected = i + 1;
                Entity adminEntity = new Entity();
                adminEntity
                        .setType(ZooKeeperImpl.class)
                        .setCeilingType(ZooKeeper.class)
                        .setProperty("name", name);
                ZooKeeper adminP = (ZooKeeper)translator.convert(metaAccess, adminEntity, null);
                PersistableResult<ZooKeeper> adminRes = jpaEntityService.create(adminP);
                ZooKeeper admin = adminRes.getValue();

                Long id = admin.getId();
                PersistableResult<ZooKeeper> adminFromDbRes = jpaEntityService.read(ZooKeeper.class, Long.valueOf(id));
                PersistableResult<ZooKeeperImpl> admin2FromDbRes = jpaEntityService.read(ZooKeeperImpl.class, Long.valueOf(id));

                ZooKeeper adminFromDb = adminFromDbRes.getValue();
                ZooKeeper admin2FromDb = admin2FromDbRes.getValue();

                Assert.assertEquals("Created and Read should be same: " + admin.getId() + " : " + adminFromDb.getId(),
                        admin.getId(), adminFromDb.getId());
//                    Assert.assertTrue("Created Object [" + admin.getId() + "] should have Id: " + expected, admin.getId().equals(0L + expected));

                Assert.assertEquals(admin.getUuid(), adminFromDb.getUuid());
                Assert.assertEquals(adminFromDb.getUuid(), admin2FromDb.getUuid());

                Assert.assertEquals(admin.getId(), adminFromDb.getId());
                Assert.assertEquals(adminFromDb.getId(), admin2FromDb.getId());

                Assert.assertNotNull(adminFromDb);
                Assert.assertTrue(adminFromDb.getName().equals(name));

                created++;
            }
        } catch (ServiceException exp) {
            Assert.fail();
        } finally {
            Assert.assertEquals(createAttempt, created);
        }
        return created;
    }
}
