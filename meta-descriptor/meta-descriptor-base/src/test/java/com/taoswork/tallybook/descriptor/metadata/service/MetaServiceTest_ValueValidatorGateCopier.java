package com.taoswork.tallybook.descriptor.metadata.service;

import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.service.MetaService;
import com.taoswork.tallybook.descriptor.service.impl.GeneralMetaServiceImpl;
import com.taoswork.tallybook.testmaterial.general.domain.meta.A;
import com.taoswork.tallybook.testmaterial.general.domain.meta.AA;
import com.taoswork.tallybook.testmaterial.general.domain.meta.AAA;
import com.taoswork.tallybook.testmaterial.general.domain.meta.AAB;
import com.taoswork.tallybook.testmaterial.general.domain.meta.validator.AAAValueValidator;
import com.taoswork.tallybook.testmaterial.general.domain.meta.validator.AAValueValidator;
import com.taoswork.tallybook.testmaterial.general.domain.meta.validator.AValueValidator;
import com.taoswork.tallybook.testmaterial.general.domain.meta.valuecopier.AAACopier;
import com.taoswork.tallybook.testmaterial.general.domain.meta.valuecopier.AACopier;
import com.taoswork.tallybook.testmaterial.general.domain.meta.valuecopier.ACopier;
import com.taoswork.tallybook.testmaterial.general.domain.meta.valuegate.AAAGate;
import com.taoswork.tallybook.testmaterial.general.domain.meta.valuegate.AAGate;
import com.taoswork.tallybook.testmaterial.general.domain.meta.valuegate.AGate;
import com.taoswork.tallybook.testmaterial.general.utils.CollectionAssert;
import com.taoswork.tallybook.testmaterial.general.utils.Converter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/11/11.
 */
public class MetaServiceTest_ValueValidatorGateCopier {
    private MetaService metaService;
    private IClassMeta acm;
    private IClassMeta aacm;
    private IClassMeta aaacm;
    private IClassMeta aabcm;

    @Before
    public void setup() {
        metaService = new GeneralMetaServiceImpl();
        aaacm = metaService.generateMeta(AAA.class, null, true);
        aabcm = metaService.generateMeta(AAB.class, null, true);
        aacm = metaService.generateMeta(AA.class, null, true);
        acm = metaService.generateMeta(A.class, null, true);
    }

    @After
    public void teardown() {
        metaService = null;
    }

    @Test
    public void testValueValidator() {
        Converter<String, Class> converter = new Converter<String, Class>() {
            @Override
            public Class convert(String from) {
                try {
                    return Class.forName(from);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
        CollectionAssert.ensureFullyCover(aaacm.getReadonlyValidators(), converter, AAAValueValidator.class, AAValueValidator.class, AValueValidator.class);
        CollectionAssert.ensureFullyCover(aacm.getReadonlyValidators(), converter, AAValueValidator.class, AValueValidator.class);
        CollectionAssert.ensureFullyCover(aabcm.getReadonlyValidators(), converter, AAValueValidator.class, AValueValidator.class);
        CollectionAssert.ensureFullyCover(acm.getReadonlyValidators(), converter, AValueValidator.class);
    }

    @Test
    public void testValueGate() {
        Converter<String, Class> converter = new Converter<String, Class>() {
            @Override
            public Class convert(String from) {
                try {
                    return Class.forName(from);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
        CollectionAssert.ensureFullyCover(aaacm.getReadonlyValueGates(), converter, AAAGate.class, AAGate.class, AGate.class);
        CollectionAssert.ensureFullyCover(aacm.getReadonlyValueGates(), converter, AAGate.class, AGate.class);
        CollectionAssert.ensureFullyCover(aabcm.getReadonlyValueGates(), converter, AAGate.class, AGate.class);
        CollectionAssert.ensureFullyCover(acm.getReadonlyValueGates(), converter, AGate.class);
    }

    @Test
    public void testValueCopier() {
        Assert.assertEquals(AAACopier.class.getName(), aaacm.getValueCopier());
        Assert.assertEquals(AACopier.class.getName(), aacm.getValueCopier());
        Assert.assertEquals(AACopier.class.getName(), aabcm.getValueCopier());
        Assert.assertEquals(ACopier.class.getName(), acm.getValueCopier());
    }
}
