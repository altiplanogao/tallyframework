package com.taoswork.tallybook.dynamic.datameta.metadata.service;

import com.taoswork.tallybook.dynamic.datameta.metadata.IClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.impl.MetadataServiceImpl;
import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta.A;
import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta.AA;
import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta.AAA;
import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta.AAB;
import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta.validator.AAAValueValidator;
import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta.validator.AAValueValidator;
import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta.validator.AValueValidator;
import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta.valuecopier.AAAValueCopier;
import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta.valuecopier.AAValueCopier;
import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta.valuecopier.AValueCopier;
import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta.valuegate.AAAValueGate;
import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta.valuegate.AAValueGate;
import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta.valuegate.AValueGate;
import com.taoswork.tallybook.testframework.general.CollectionAssert;
import com.taoswork.tallybook.testframework.general.Converter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/11/11.
 */
public class MetadataServiceTest_ValueValidatorGateCopier {
    private MetadataService metadataService;
    private IClassMetadata acm;
    private IClassMetadata aacm;
    private IClassMetadata aaacm;
    private IClassMetadata aabcm;

    @Before
    public void setup(){
        metadataService = new MetadataServiceImpl();
        aaacm = metadataService.generateMetadata(AAA.class, null, true);
        aabcm = metadataService.generateMetadata(AAB.class, null, true);
        aacm = metadataService.generateMetadata(AA.class, null, true);
        acm = metadataService.generateMetadata(A.class, null, true);
    }

    @After
    public void teardown(){
        metadataService = null;
    }

    @Test
    public void testValueValidator(){
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
    public void testValueGate(){
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
        CollectionAssert.ensureFullyCover(aaacm.getReadonlyValueGates(), converter, AAAValueGate.class, AAValueGate.class, AValueGate.class);
        CollectionAssert.ensureFullyCover(aacm.getReadonlyValueGates(), converter, AAValueGate.class, AValueGate.class);
        CollectionAssert.ensureFullyCover(aabcm.getReadonlyValueGates(), converter, AAValueGate.class, AValueGate.class);
        CollectionAssert.ensureFullyCover(acm.getReadonlyValueGates(), converter, AValueGate.class);
    }

    @Test
    public void testValueCopier(){
        Assert.assertEquals(AAAValueCopier.class.getName(), aaacm.getValueCopier());
        Assert.assertEquals(AAValueCopier.class.getName(), aacm.getValueCopier());
        Assert.assertEquals(AAValueCopier.class.getName(), aabcm.getValueCopier());
        Assert.assertEquals(AValueCopier.class.getName(), acm.getValueCopier());
    }
}
