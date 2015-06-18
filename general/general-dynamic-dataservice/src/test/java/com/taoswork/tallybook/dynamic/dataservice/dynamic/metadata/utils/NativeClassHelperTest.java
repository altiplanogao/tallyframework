package com.taoswork.tallybook.dynamic.dataservice.dynamic.metadata.utils;

import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.utils.NativeClassHelper;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/5/23.
 */
public class NativeClassHelperTest {
    static class TestSupper {
        public static final String staticS = "x";

        @Id
        public int idS;

        public String nameS;

        public transient String nativeTransientS;

        @javax.persistence.Transient
        public String persistenceTransientS;
    }

    static class TestChild extends TestSupper{
        public static final String staticC = "x";

        @Id
        public int idC;

        public String nameC;

        public transient String nativeTransientC;

        @javax.persistence.Transient
        public String persistenceTransientC;
    }

    @Test
    public void testFieldsList(){
        NativeClassHelper.FieldScanMethod fieldScanMethod = new NativeClassHelper.FieldScanMethod();
        fieldScanMethod.setScanSuper(false).setIncludeId(true).setIncludeStatic(true).setIncludeTransient(true);
        Map<String, Field> fieldMap = NativeClassHelper.getFields(TestSupper.class, fieldScanMethod);
        ensureFields(fieldMap, "staticS", "idS", "nameS",
                "nativeTransientS", "persistenceTransientS");

        fieldMap = NativeClassHelper.getFields(TestChild.class, fieldScanMethod);
        ensureFields(fieldMap, "staticC", "idC", "nameC",
                "nativeTransientC", "persistenceTransientC");

        fieldScanMethod.setScanSuper(true);
        fieldMap = NativeClassHelper.getFields(TestChild.class, fieldScanMethod);
        ensureFields(fieldMap, "staticS", "idS", "nameS",
                "nativeTransientS", "persistenceTransientS",
                "staticC", "idC", "nameC",
                "nativeTransientC", "persistenceTransientC");

        fieldScanMethod.setIncludeId(false);
        fieldMap = NativeClassHelper.getFields(TestChild.class, fieldScanMethod);
        ensureFields(fieldMap, "staticS", "nameS",
                "nativeTransientS", "persistenceTransientS",
                "staticC", "nameC",
                "nativeTransientC", "persistenceTransientC");

        fieldScanMethod.setIncludeStatic(false);
        fieldMap = NativeClassHelper.getFields(TestChild.class, fieldScanMethod);
        ensureFields(fieldMap, "nameS",
                "nativeTransientS", "persistenceTransientS",
                "nameC",
                "nativeTransientC", "persistenceTransientC");

        fieldScanMethod.setIncludeTransient(false);
        fieldMap = NativeClassHelper.getFields(TestChild.class, fieldScanMethod);
        ensureFields(fieldMap, "nameS",
                "nameC");

    }

    private void ensureFields(Map<String, Field> fieldMap, String... fieldNames){
        Set<String> fieldNameSets = new HashSet<String>();
        for(String fieldName : fieldNames){
            fieldNameSets.add(fieldName);
        }
        ensureFields(fieldMap, fieldNameSets);
    }

    private void ensureFields(Map<String, Field> fieldMap, Set<String> fieldNames){
        Assert.assertEquals(fieldMap.size(), fieldNames.size());
        for(String fieldName : fieldNames){
            Assert.assertTrue(fieldMap.containsKey(fieldName));
        }
    }
}
