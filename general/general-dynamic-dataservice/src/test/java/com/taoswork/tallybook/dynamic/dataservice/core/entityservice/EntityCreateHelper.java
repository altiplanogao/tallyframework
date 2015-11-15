package com.taoswork.tallybook.dynamic.dataservice.core.entityservice;

import com.taoswork.tallybook.dynamic.dataio.reference.PersistableResult;
import com.taoswork.tallybook.dynamic.dataio.in.Entity;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.testframework.domain.zoo.ZooKeeper;
import com.taoswork.tallybook.testframework.domain.zoo.impl.ZooKeeperImpl;
import org.junit.Assert;

/**
 * Created by Gao Yuan on 2015/9/23.
 */
public class EntityCreateHelper {
    public static int createPeopleEntityWith(DynamicEntityService dynamicEntityService,
                                      String namePrefix, int postfixStartingIndex, int createAttempt) {
        int created = 0;
        try {
            for (int i = 0; i < createAttempt; ++i) {
                String name = namePrefix + (int) (postfixStartingIndex + i);

//                    int expected = i + 1;
                Entity adminEntity = new Entity();
                adminEntity
                    .setEntityType(ZooKeeperImpl.class)
                    .setEntityCeilingType(ZooKeeper.class)
                    .setProperty("name", name);
                PersistableResult<ZooKeeper> adminRes = dynamicEntityService.create(adminEntity);
                ZooKeeper admin = adminRes.getEntity();

                Long id = admin.getId();
                PersistableResult<ZooKeeper> adminFromDbRes = dynamicEntityService.read(ZooKeeper.class, Long.valueOf(id));
                PersistableResult<ZooKeeperImpl> admin2FromDbRes = dynamicEntityService.read(ZooKeeperImpl.class, Long.valueOf(id));

                ZooKeeper adminFromDb = adminFromDbRes.getEntity();
                ZooKeeper admin2FromDb = admin2FromDbRes.getEntity();

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
