package com.taoswork.tallybook.dynamic.dataservice.core.entityservice;

import com.taoswork.tallybook.dynamic.dataservice.IDataService;
import com.taoswork.tallybook.dynamic.dataservice.core.dao.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dynamic.dataservice.core.dao.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dynamic.dataservice.core.dao.query.dto.PropertyFilterCriteria;
import com.taoswork.tallybook.dynamic.dataio.reference.PersistableResult;
import com.taoswork.tallybook.dynamic.dataio.in.Entity;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import com.taoswork.tallybook.dynamic.dataservice.servicemockup.TallyMockupDataService;
import com.taoswork.tallybook.general.solution.time.MethodTimeCounter;
import com.taoswork.tallybook.testframework.domain.zoo.ZooKeeper;
import com.taoswork.tallybook.testframework.domain.zoo.impl.ZooKeeperImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;


/**
 * Created by Gao Yuan on 2015/11/10.
 */
public class DynamicEntityServicePerformanceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicEntityServicePerformanceTest.class);

    private IDataService dataService = null;

    @Before
    public void setup() {
        dataService = new TallyMockupDataService();
    }

    @After
    public void teardown() {
        dataService = null;
    }

    @Test
    public void testCRUDQ() throws ServiceException {
        int loopCount = 20;
        int inLoopAttempt = 20;
        DynamicEntityService dynamicEntityService = dataService.getService(DynamicEntityService.COMPONENT_NAME);

        Set<Long> ids = new HashSet<Long>();
        final int total;
        {
            int created = 0;
            MethodTimeCounter methodTimeCounter = new MethodTimeCounter(LOGGER, "CREATE");
            for (int l = 0; l < loopCount; ++l) {
                String nameAAA = entityPrefix(l);
                for(int i = 0 ; i < inLoopAttempt ; ++i) {
                    Entity adminEntity = new Entity();
                    adminEntity
                        .setType(ZooKeeperImpl.class)
                        .setCeilingType(ZooKeeper.class)
                        .setProperty("name", nameAAA + i);
                    PersistableResult<ZooKeeper> adminRes = dynamicEntityService.create(adminEntity);
                    ZooKeeper admin = adminRes.getEntity();
                    ids.add(admin.getId());
                    created++;
                }
            }
            Assert.assertEquals(loopCount * inLoopAttempt, ids.size());
            Assert.assertEquals(loopCount * inLoopAttempt, created);
            total = created;
            methodTimeCounter.noticePerActionCostOnExit(created);
        }
        {
            int query = 0;
            MethodTimeCounter methodTimeCounter = new MethodTimeCounter(LOGGER, "QUERY SINGLE");
            for (Long id : ids) {
                CriteriaTransferObject cto = new CriteriaTransferObject();
                cto.addFilterCriteria(new PropertyFilterCriteria("id", "" + id));
                CriteriaQueryResult<ZooKeeper> zooKeepers = dynamicEntityService.query(ZooKeeper.class, cto);
                query ++;
            }
            methodTimeCounter.noticePerActionCostOnExit(query);
        }
        {
            int query = 0;
            MethodTimeCounter methodTimeCounter = new MethodTimeCounter(LOGGER, "QUERY MULTIPLE");
            for (int l = 0; l < loopCount; ++l) {
                String nameAAA = entityPrefix(l);
                for(int i = 0 ; i < inLoopAttempt ; ++i) {
                    CriteriaTransferObject cto = new CriteriaTransferObject();
                    cto.addFilterCriteria(new PropertyFilterCriteria("name", nameAAA));
                    CriteriaQueryResult<ZooKeeper> zooKeepers = dynamicEntityService.query(ZooKeeper.class, cto);
                    query ++;
                }
            }
            methodTimeCounter.noticePerActionCostOnExit(query);
        }
        {
            int read = 0;
            MethodTimeCounter methodTimeCounter = new MethodTimeCounter(LOGGER, "READ");
            for (Long id : ids) {
                PersistableResult<ZooKeeper> adminFromDbRes = dynamicEntityService.read(ZooKeeper.class, Long.valueOf(id));
                Assert.assertEquals(id, adminFromDbRes.getEntity().getId());
                read++;
            }
            methodTimeCounter.noticePerActionCostOnExit(read);
        }
        {
            int updated = 0;
            MethodTimeCounter methodTimeCounter = new MethodTimeCounter(LOGGER, "UPDATE");
            for (Long id : ids) {
                PersistableResult<ZooKeeper> adminFromDbRes = dynamicEntityService.read(ZooKeeper.class, Long.valueOf(id));
                ZooKeeper admin = adminFromDbRes.getEntity();
                String oldEmail = admin.getEmail();
                String newEmail = admin.getName() + "@xxx.com";
                admin.setEmail(newEmail);
                PersistableResult<ZooKeeper> freshAdminFrom = dynamicEntityService.update(ZooKeeper.class, admin);
                Assert.assertEquals(newEmail, freshAdminFrom.getEntity().getEmail());
                updated ++;
            }
            methodTimeCounter.noticePerActionCostOnExit(updated);
        }
        {
            int deleted = 0;
            MethodTimeCounter methodTimeCounter = new MethodTimeCounter(LOGGER, "DELETE");
            for (Long id : ids) {
                PersistableResult<ZooKeeper> adminFromDbRes = dynamicEntityService.read(ZooKeeper.class, Long.valueOf(id));
                ZooKeeperImpl zk = new ZooKeeperImpl();
                zk.setId(id);
                boolean delOk = dynamicEntityService.delete(ZooKeeper.class, zk);
                Assert.assertTrue(delOk);
                deleted ++;
            }
            methodTimeCounter.noticePerActionCostOnExit(deleted);
        }

    }

    public String entityPrefix(int i){
        return "name" + i + "_";
    }

}
