package com.taoswork.tallybook.descriptor.metadata.service;

import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.service.MetaService;
import com.taoswork.tallybook.descriptor.jpa.service.JpaMetaServiceImpl;
import com.taoswork.tallybook.testmaterial.general.domain.FieldsZooWithArray;
import com.taoswork.tallybook.testmaterial.general.domain.meta.AAA;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/6/26.
 */
public class MetaServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetaServiceTest.class);
    private MetaService metaService;

    @Before
    public void setup() {
        metaService = new JpaMetaServiceImpl();
    }

    @After
    public void teardown() {
        metaService = null;
    }

//    @Test
//    public void testGenericFields() {
//        Class<FieldsZoo> fieldsZooClz = FieldsZoo.class;
//        Field[] fields = fieldsZooClz.getDeclaredFields();
//        IClassMeta classMeta = metaService.generateMeta(fieldsZooClz, null);
//
//        for (Field field : fields) {
//            Class type = field.getType();
//            Type genericType = field.getGenericType();
//            LOGGER.info("Field: " + field.getName());
//            LOGGER.info("   Type: " + field.getType());
//
//            ParameterizedType parameterizedType = null;
//            LOGGER.info("   Generic: " + field.getGenericType());
//            if (genericType instanceof ParameterizedType) {
//                parameterizedType = (ParameterizedType) genericType;
//                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
//                if (actualTypeArguments != null && actualTypeArguments.length > 0) {
//                    for (Type typeArgument : actualTypeArguments) {
//                        LOGGER.info("       Para: " + typeArgument);
//                    }
//                }
//                Type rawType = parameterizedType.getRawType();
//
//                Assert.assertTrue(type.equals(rawType));
//            }
//
//            ////////////
//            IFieldMeta fieldMeta = classMeta.getReadonlyFieldMetaMap().get(field.getName());
//            Assert.assertNotNull(fieldMeta);
//        }
//    }

    @Test
    public void testGenericFieldsWithArray() {
        Class<FieldsZooWithArray> fieldsZooClz = FieldsZooWithArray.class;
        try {
            IClassMeta classMeta = metaService.generateMeta(fieldsZooClz, null);
        } catch (IllegalAccessError e) {
            return;
        }
        Assert.fail();
    }

    @Test
    public void testScanFieldsHierarchy() {
        Class<AAA> clz = AAA.class;
        {
            Field[] fields = clz.getDeclaredFields();
            IClassMeta classMeta = metaService.generateMeta(clz, null);
            int fieldCount = classMeta.getReadonlyFieldMetaMap().size();
            Assert.assertEquals(fieldCount, 1);
        }
        {
            Field[] fields = clz.getDeclaredFields();
            IClassMeta classMeta = metaService.generateMeta(clz, null, true);
            int fieldCount = classMeta.getReadonlyFieldMetaMap().size();
            Assert.assertEquals(fieldCount, 3);
        }
    }
}