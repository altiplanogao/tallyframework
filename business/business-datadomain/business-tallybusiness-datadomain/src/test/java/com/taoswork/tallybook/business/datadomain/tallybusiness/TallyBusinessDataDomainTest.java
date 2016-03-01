package com.taoswork.tallybook.business.datadomain.tallybusiness;

import org.junit.Assert;
import org.junit.Test;
import org.mongodb.morphia.Morphia;

/**
 * Created by Gao Yuan on 2015/10/19.
 */
public class TallyBusinessDataDomainTest {
    @Test
    public void testCreateDb() {
        try {
            Morphia morphia = new Morphia();
            morphia.map(TallyBusinessDataDomain.persistableEntities());
        } catch (Exception exp) {
            Assert.fail();
        }

    }
}
