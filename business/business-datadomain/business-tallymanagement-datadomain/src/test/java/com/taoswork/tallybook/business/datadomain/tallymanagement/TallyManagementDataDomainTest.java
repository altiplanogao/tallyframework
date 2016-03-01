package com.taoswork.tallybook.business.datadomain.tallymanagement;

import org.junit.Assert;
import org.junit.Test;
import org.mongodb.morphia.Morphia;

/**
 * Created by Gao Yuan on 2015/10/19.
 */
public class TallyManagementDataDomainTest {
    @Test
    public void testCreateDb() {
        try {
            Morphia morphia = new Morphia();
            morphia.map(TallyManagementDataDomain.persistableEntities());
        } catch (Exception exp) {
            Assert.fail();
        }
    }
}
