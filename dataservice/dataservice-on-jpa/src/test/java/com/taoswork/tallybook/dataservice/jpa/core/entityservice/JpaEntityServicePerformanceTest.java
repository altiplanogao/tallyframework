package com.taoswork.tallybook.dataservice.jpa.core.entityservice;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.dataservice.IDataService;
import com.taoswork.tallybook.dataservice.PersistableResult;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.PropertyFilterCriteria;
import com.taoswork.tallybook.dataservice.core.persistence.InputEntityTranslator;
import com.taoswork.tallybook.dataservice.exception.ServiceException;
import com.taoswork.tallybook.dataservice.jpa.servicemockup.TallyMockupDataService;
import com.taoswork.tallybook.dataservice.service.EntityMetaAccess;
import com.taoswork.tallybook.dataservice.service.IEntityService;
import com.taoswork.tallybook.descriptor.dataio.in.Entity;
import com.taoswork.tallybook.general.solution.time.MethodTimeCounter;
import com.taoswork.tallybook.testmaterial.jpa.domain.zoo.ZooKeeper;
import com.taoswork.tallybook.testmaterial.jpa.domain.zoo.impl.ZooKeeperImpl;
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
public class JpaEntityServicePerformanceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(JpaEntityServicePerformanceTest.class);

    private IDataService dataService = null;
    private InputEntityTranslator translator = null;
    private EntityMetaAccess metaAccess = null;

    @Before
    public void setup() {
        dataService = new TallyMockupDataService();
        metaAccess = dataService.getService(EntityMetaAccess.COMPONENT_NAME);
        translator = new InputEntityTranslator();
    }

    @After
    public void teardown() {
        dataService = null;
        metaAccess = null;
        translator = null;
    }

    @Test
    public void testCRUDQ() throws ServiceException {
        int loopCount = 20;
        int inLoopAttempt = 20;
        JpaEntityService entityService = dataService.getService(IEntityService.COMPONENT_NAME);

        Set<Long> ids = new HashSet<Long>();
        final int total;
        {
            int created = 0;
            MethodTimeCounter methodTimeCounter = new MethodTimeCounter(LOGGER, "CREATE");
            for (int l = 0; l < loopCount; ++l) {
                String nameAAA = entityPrefix(l);
                for (int i = 0; i < inLoopAttempt; ++i) {
                    Entity adminEntity = new Entity();
                    adminEntity
                            .setType(ZooKeeperImpl.class)
                            .setCeilingType(ZooKeeper.class)
                            .setProperty("name", nameAAA + i);
                    ZooKeeper adminP = (ZooKeeper)translator.convert(metaAccess, adminEntity, null);
                    PersistableResult<ZooKeeper> adminRes = entityService.create(adminP);
                    ZooKeeper admin = adminRes.getValue();
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
                CriteriaQueryResult<ZooKeeper> zooKeepers = entityService.query(ZooKeeper.class, cto);
                query++;
            }
            methodTimeCounter.noticePerActionCostOnExit(query);
        }
        {
            int query = 0;
            MethodTimeCounter methodTimeCounter = new MethodTimeCounter(LOGGER, "QUERY MULTIPLE");
            for (int l = 0; l < loopCount; ++l) {
                String nameAAA = entityPrefix(l);
                for (int i = 0; i < inLoopAttempt; ++i) {
                    CriteriaTransferObject cto = new CriteriaTransferObject();
                    cto.addFilterCriteria(new PropertyFilterCriteria("name", nameAAA));
                    CriteriaQueryResult<ZooKeeper> zooKeepers = entityService.query(ZooKeeper.class, cto);
                    query++;
                }
            }
            methodTimeCounter.noticePerActionCostOnExit(query);
        }
        {
            int read = 0;
            MethodTimeCounter methodTimeCounter = new MethodTimeCounter(LOGGER, "READ");
            for (Long id : ids) {
                PersistableResult<ZooKeeper> adminFromDbRes = entityService.read(ZooKeeper.class, Long.valueOf(id));
                Assert.assertEquals(id, adminFromDbRes.getValue().getId());
                read++;
            }
            methodTimeCounter.noticePerActionCostOnExit(read);
        }
        {
            int updated = 0;
            MethodTimeCounter methodTimeCounter = new MethodTimeCounter(LOGGER, "UPDATE");
            for (Long id : ids) {
                PersistableResult<ZooKeeper> adminFromDbRes = entityService.read(ZooKeeper.class, Long.valueOf(id));
                ZooKeeper admin = adminFromDbRes.getValue();
                String oldEmail = admin.getEmail();
                String newEmail = admin.getName() + "@xxx.com";
                admin.setEmail(newEmail);
                PersistableResult<ZooKeeper> freshAdminFrom = entityService.update(admin);
                Assert.assertEquals(newEmail, freshAdminFrom.getValue().getEmail());
                updated++;
            }
            methodTimeCounter.noticePerActionCostOnExit(updated);
        }
        {
            int deleted = 0;
            MethodTimeCounter methodTimeCounter = new MethodTimeCounter(LOGGER, "DELETE");
            for (Long id : ids) {
                PersistableResult<ZooKeeper> adminFromDbRes = entityService.read(ZooKeeper.class, Long.valueOf(id));
                ZooKeeperImpl zk = new ZooKeeperImpl();
                zk.setId(id);
                boolean delOk = entityService.delete(zk);
                Assert.assertTrue(delOk);
                deleted++;
            }
            methodTimeCounter.noticePerActionCostOnExit(deleted);
        }

    }

    public String entityPrefix(int i) {
        return "name" + i + "_";
    }

}
