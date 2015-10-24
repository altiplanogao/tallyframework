package com.taoswork.tallybook.dynamic.datameta.metadata.utils;

import com.taoswork.tallybook.dynamic.datameta.testdata.clazzes.meta.AAA;
import com.taoswork.tallybook.general.extension.collections.CollectionUtility;
import com.taoswork.tallybook.general.extension.utils.TPredicate;
import com.taoswork.tallybook.testframework.general.CollectionAssert;
import com.taoswork.tallybook.testframework.general.Converter;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/6/26.
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

    static class TestChild extends TestSupper {
        public static final String staticC = "x";

        @Id
        public int idC;

        public String nameC;

        public transient String nativeTransientC;

        @javax.persistence.Transient
        public String persistenceTransientC;
    }

    @Test
    public void testFieldsList() {
        NativeClassHelper.FieldScanMethod fieldScanMethod = new NativeClassHelper.FieldScanMethod();
        fieldScanMethod.setScanSuper(false).setIncludeId(true).setIncludeStatic(true).setIncludeTransient(true);
        List<Field> fieldMap = NativeClassHelper.getFields(TestSupper.class, fieldScanMethod);
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

    private void ensureFields(List<Field> fieldMap, String... fieldNames) {
        CollectionAssert.ensureFullyCover(fieldMap, new Converter<Field, String>() {
            @Override
            public String convert(Field from) {
                return from.getName();
            }
        }, fieldNames);
    }

    @Test
    public void testSuperFields(){
        Class<AAA> clz = AAA.class;
        List<Field> _1Fields = NativeClassHelper.getFields(AAA.class, new NativeClassHelper.FieldScanMethod().setScanSuper(false));
        List<Field> _3Fields = NativeClassHelper.getFields(AAA.class, new NativeClassHelper.FieldScanMethod().setScanSuper(true));

        Assert.assertEquals(_1Fields.size(), 1);
        Assert.assertEquals(_3Fields.size(), 3);

        Assert.assertTrue("a".equals(_3Fields.get(0).getName()));
        Assert.assertTrue("aa".equals(_3Fields.get(1).getName()));
        Assert.assertTrue("aaa".equals(_3Fields.get(2).getName()));
    }
}
