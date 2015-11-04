package com.taoswork.tallybook.dynamic.datameta.metadata.service;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.impl.MetadataServiceImpl;
import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.FieldsZoo;
import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta.AAA;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Gao Yuan on 2015/6/26.
 */
public class MetadataServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetadataServiceTest.class);
    private MetadataService metadataService;

    @Before
    public void setup() {
        metadataService = new MetadataServiceImpl();
    }

    @After
    public void teardown() {
        metadataService = null;
    }

    @Test
    public void testGenericFields() {
        Class<FieldsZoo> fieldsZooClz = FieldsZoo.class;
        Field[] fields = fieldsZooClz.getDeclaredFields();
        ClassMetadata classMetadata = metadataService.generateMetadata(fieldsZooClz);

        for (Field field : fields) {
            Class type = field.getType();
            Type genericType = field.getGenericType();
            LOGGER.info("Field: " + field.getName());
            LOGGER.info("   Type: " + field.getType());

            ParameterizedType parameterizedType = null;
            LOGGER.info("   Generic: " + field.getGenericType());
            if (genericType instanceof ParameterizedType) {
                parameterizedType = (ParameterizedType) genericType;
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                if (actualTypeArguments != null && actualTypeArguments.length > 0) {
                    for (Type typeArgument : actualTypeArguments) {
                        LOGGER.info("       Para: " + typeArgument);
                    }
                }
                Type rawType = parameterizedType.getRawType();

                Assert.assertTrue(type.equals(rawType));
            }

            ////////////
            IFieldMetadata fieldMetadata = classMetadata.getReadonlyFieldMetadataMap().get(field.getName());
            Assert.assertNotNull(fieldMetadata);
        }
    }

    @Test
    public void testScanFieldsHierarchy() {
        Class<AAA> clz = AAA.class;
        {
            Field[] fields = clz.getDeclaredFields();
            ClassMetadata classMetadata = metadataService.generateMetadata(clz);
            int fieldCount = classMetadata.getReadonlyFieldMetadataMap().size();
            Assert.assertEquals(fieldCount, 1);
        }
        {
            Field[] fields = clz.getDeclaredFields();
            ClassMetadata classMetadata = metadataService.generateMetadata(clz, true);
            int fieldCount = classMetadata.getReadonlyFieldMetadataMap().size();
            Assert.assertEquals(fieldCount, 3);
        }
    }
}