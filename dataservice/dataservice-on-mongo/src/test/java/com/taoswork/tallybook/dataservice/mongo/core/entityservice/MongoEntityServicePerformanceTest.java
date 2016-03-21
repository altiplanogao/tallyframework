package com.taoswork.tallybook.dataservice.mongo.core.entityservice;

import com.taoswork.tallybook.dataservice.IDataService;
import com.taoswork.tallybook.dataservice.PersistableResult;
import com.taoswork.tallybook.dataservice.config.IDatasourceConfiguration;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.PropertyFilterCriteria;
import com.taoswork.tallybook.dataservice.core.persistence.InputEntityTranslator;
import com.taoswork.tallybook.dataservice.exception.ServiceException;
import com.taoswork.tallybook.dataservice.mongo.servicemockup.TallyMockupMongoDataService;
import com.taoswork.tallybook.dataservice.mongo.servicemockup.datasource.TallyMockupMongoDatasourceConfiguration;
import com.taoswork.tallybook.dataservice.service.EntityMetaAccess;
import com.taoswork.tallybook.dataservice.service.IEntityService;
import com.taoswork.tallybook.descriptor.dataio.in.Entity;
import com.taoswork.tallybook.general.solution.time.MethodTimeCounter;
import com.taoswork.tallybook.testmaterial.mongo.domain.zoo.ZooKeeper;
import com.taoswork.tallybook.testmaterial.mongo.domain.zoo.impl.ZooKeeperImpl;
import org.bson.types.ObjectId;
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
public class MongoEntityServicePerformanceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MongoEntityServicePerformanceTest.class);

    private IDataService dataService = null;
    private InputEntityTranslator translator = null;
    private EntityMetaAccess metaAccess = null;

    @Before
    public void setup() {
        dataService = new TallyMockupMongoDataService();
        metaAccess = dataService.getService(EntityMetaAccess.COMPONENT_NAME);
        translator = new InputEntityTranslator();
    }

    @After
    public void teardown() {
        TallyMockupMongoDatasourceConfiguration.DatasourceDefinition mdbDef = dataService.getService(IDatasourceConfiguration.DATA_SOURCE_PATH_DEFINITION);
        mdbDef.dropDatabase();
        dataService = null;
        metaAccess = null;
        translator = null;
    }

    @Test
    public void testCRUDQ() throws ServiceException {
        int loopCount = 20;
        int inLoopAttempt = 20;
        IEntityService entityService = dataService.getService(IEntityService.COMPONENT_NAME);

        Set<ObjectId> ids = new HashSet<ObjectId>();
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
            for (ObjectId id : ids) {
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
            for (ObjectId id : ids) {
                PersistableResult<ZooKeeper> adminFromDbRes = entityService.read(ZooKeeper.class, (id));
                Assert.assertEquals(id, adminFromDbRes.getValue().getId());
                read++;
            }
            methodTimeCounter.noticePerActionCostOnExit(read);
        }
        {
            int updated = 0;
            MethodTimeCounter methodTimeCounter = new MethodTimeCounter(LOGGER, "UPDATE");
            for (ObjectId id : ids) {
                PersistableResult<ZooKeeper> adminFromDbRes = entityService.read(ZooKeeper.class, (id));
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
            for (ObjectId id : ids) {
                PersistableResult<ZooKeeper> adminFromDbRes = entityService.read(ZooKeeper.class, (id));
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
