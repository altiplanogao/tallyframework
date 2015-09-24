package com.taoswork.tallybook.dynamic.dataservice.core.entityservice;

import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.Entity;
import com.taoswork.tallybook.dynamic.dataservice.core.access.dto.EntityResult;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.testframework.domain.TPerson;
import com.taoswork.tallybook.testframework.domain.impl.TPersonImpl;
import org.junit.Assert;

import java.util.UUID;

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
                    .setEntityType(TPersonImpl.class)
                    .setEntityCeilingType(TPerson.class)
                    .setProperty("name", name)
                    .setProperty("uuid", UUID.randomUUID().toString());
                EntityResult<TPerson> adminRes = dynamicEntityService.create(adminEntity);
                TPerson admin = adminRes.getEntity();

                Long id = admin.getId();
                EntityResult<TPerson> adminFromDbRes = dynamicEntityService.read(TPerson.class, Long.valueOf(id));
                EntityResult<TPersonImpl> admin2FromDbRes = dynamicEntityService.read(TPersonImpl.class, Long.valueOf(id));

                TPerson adminFromDb = adminFromDbRes.getEntity();
                TPerson admin2FromDb = admin2FromDbRes.getEntity();

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
