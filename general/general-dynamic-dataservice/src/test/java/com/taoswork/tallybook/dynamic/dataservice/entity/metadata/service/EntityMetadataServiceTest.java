package com.taoswork.tallybook.dynamic.dataservice.entity.metadata.service;

import com.taoswork.tallybook.dynamic.dataservice.dynamic.data4test.domain.FieldsZoo;
import com.taoswork.tallybook.dynamic.dataservice.dynamic.data4test.domain.meta.AAA;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.service.impl.EntityMetadataServiceImpl;
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
public class EntityMetadataServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityMetadataServiceTest.class);
    private EntityMetadataService entityMetadataService;

    @Before
    public void setup() {
        entityMetadataService = new EntityMetadataServiceImpl();
    }

    @After
    public void teardown() {
        entityMetadataService = null;
    }

    @Test
    public void testGenericFields() {
        Class<FieldsZoo> fieldsZooClz = FieldsZoo.class;
        Field[] fields = fieldsZooClz.getDeclaredFields();
        ClassMetadata classMetadata = entityMetadataService.getClassMetadata(fieldsZooClz);

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
            FieldMetadata fieldMetadata = classMetadata.getReadonlyFieldMetadataMap().getOrDefault(field.getName(), null);
            Assert.assertNotNull(fieldMetadata);
        }
    }

    @Test
    public void testScanFieldsHierarchy() {
        Class<AAA> clz = AAA.class;
        Field[] fields = clz.getDeclaredFields();
        ClassMetadata classMetadata = entityMetadataService.getClassMetadata(clz);

        int fieldCount = classMetadata.getReadonlyFieldMetadataMap().size();
        Assert.assertEquals(fieldCount, 1);
    }
}