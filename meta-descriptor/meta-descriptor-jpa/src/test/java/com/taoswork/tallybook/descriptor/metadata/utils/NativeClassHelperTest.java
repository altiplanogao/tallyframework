package com.taoswork.tallybook.descriptor.metadata.utils;

import com.taoswork.tallybook.general.solution.reflect.ClassUtility;
import com.taoswork.tallybook.general.solution.reflect.FieldScanMethod;
import com.taoswork.tallybook.general.solution.reflect.FieldScanner;
import com.taoswork.tallybook.testmaterial.general.domain.meta.A;
import com.taoswork.tallybook.testmaterial.general.domain.meta.AA;
import com.taoswork.tallybook.testmaterial.general.domain.meta.AAA;
import com.taoswork.tallybook.testmaterial.general.utils.CollectionAssert;
import com.taoswork.tallybook.testmaterial.general.utils.Converter;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.List;

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
        FieldScanMethod fieldScanMethod = new GeneralFieldScanMethod();
        fieldScanMethod.setScanSuper(false).setIncludeId(true).setIncludeStatic(true).setIncludeTransient(true);
        List<Field> fieldMap = FieldScanner.getFields(TestSupper.class, fieldScanMethod);
        ensureFields(fieldMap, "staticS", "idS", "nameS",
                "nativeTransientS", "persistenceTransientS");

        fieldMap = FieldScanner.getFields(TestChild.class, fieldScanMethod);
        ensureFields(fieldMap, "staticC", "idC", "nameC",
                "nativeTransientC", "persistenceTransientC");

        fieldScanMethod.setScanSuper(true);
        fieldMap = FieldScanner.getFields(TestChild.class, fieldScanMethod);
        ensureFields(fieldMap, "staticS", "idS", "nameS",
                "nativeTransientS", "persistenceTransientS",
                "staticC", "idC", "nameC",
                "nativeTransientC", "persistenceTransientC");

        fieldScanMethod.setIncludeId(false);
        fieldMap = FieldScanner.getFields(TestChild.class, fieldScanMethod);
        ensureFields(fieldMap, "staticS", "nameS",
                "nativeTransientS", "persistenceTransientS",
                "staticC", "nameC",
                "nativeTransientC", "persistenceTransientC");

        fieldScanMethod.setIncludeStatic(false);
        fieldMap = FieldScanner.getFields(TestChild.class, fieldScanMethod);
        ensureFields(fieldMap, "nameS",
                "nativeTransientS", "persistenceTransientS",
                "nameC",
                "nativeTransientC", "persistenceTransientC");

        fieldScanMethod.setIncludeTransient(false);
        fieldMap = FieldScanner.getFields(TestChild.class, fieldScanMethod);
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
    public void testSuperFields() {
        Class<AAA> clz = AAA.class;
        List<Field> _1Fields = FieldScanner.getFields(AAA.class, new GeneralFieldScanMethod().setScanSuper(false));
        List<Field> _3Fields = FieldScanner.getFields(AAA.class, new GeneralFieldScanMethod().setScanSuper(true));

        Assert.assertEquals(_1Fields.size(), 1);
        Assert.assertEquals(_3Fields.size(), 3);

        Assert.assertTrue("a".equals(_3Fields.get(0).getName()));
        Assert.assertTrue("aa".equals(_3Fields.get(1).getName()));
        Assert.assertTrue("aaa".equals(_3Fields.get(2).getName()));
    }

    @Test
    public void testGetSuperClasses() {
        {
            Class[] AAAsSuper = ClassUtility.getSuperClasses(AAA.class, false);
            Class[] AAAsSuperR = ClassUtility.getSuperClasses(AAA.class, true);

            Assert.assertArrayEquals(new Class[]{AA.class, A.class}, AAAsSuper);
            Assert.assertArrayEquals(new Class[]{A.class, AA.class}, AAAsSuperR);
        }
        {
            Class[] AAAOsSuper = ClassUtility.getSuperClasses(AAA.class, false, true);
            Class[] AAAOsSuperR = ClassUtility.getSuperClasses(AAA.class, true, true);

            Assert.assertArrayEquals(new Class[]{AA.class, A.class, Object.class}, AAAOsSuper);
            Assert.assertArrayEquals(new Class[]{Object.class, A.class, AA.class}, AAAOsSuperR);
        }
        {
            Class[] AsSuper = ClassUtility.getSuperClasses(A.class, false);
            Class[] AsSuperR = ClassUtility.getSuperClasses(A.class, true);

            Assert.assertArrayEquals(new Class[]{}, AsSuper);
            Assert.assertArrayEquals(new Class[]{}, AsSuperR);
        }
        {
            Class[] AOsSuper = ClassUtility.getSuperClasses(A.class, false, true);
            Class[] AOsSuperR = ClassUtility.getSuperClasses(A.class, true, true);

            Assert.assertArrayEquals(new Class[]{Object.class}, AOsSuper);
            Assert.assertArrayEquals(new Class[]{Object.class}, AOsSuperR);
        }
    }
}
