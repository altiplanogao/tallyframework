package com.taoswork.tallybook.general.authority.core;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/6/4.
 */
public class PermissionTest {
//    public enum CharGender implements IFriendlyEnum {
//        Male('m'),
//        Female('f');
//
//        private char value;
//        private static final Map<Character, CharGender> stringToEnum = new HashMap<Character, CharGender>();
//        static {
//            for(CharGender gender : values()){
//                stringToEnum.put(gender.value, gender);
//            }
//        }
//
//        CharGender(char value) {
//            this.value = value;
//        }
//
//        @Override
//        public String getType() {
//            Male.ordinal(); //->int
//            CharGender xx = CharGender.values()[0];//int ->
//
//            Male.name(); // ->String
//            CharGender.valueOf("Male"); //String ->
//            return null;
//        }
//
//        @Override
//        public String getFriendlyType() {
//            return null;
//        }
//    }
//
//    public enum StringGender implements IFriendlyEnum {
//        Male("male"),
//        Female("female");
//
//        private String value;
//        private static final Map<String, StringGender> stringToEnum = new HashMap<String, StringGender>();
//        static {
//            for(StringGender gender : values()){
//                stringToEnum.put(gender.value, gender);
//            }
//        }
//
//        StringGender(String value) {
//            this.value = value;
//        }
//
//        @Override
//        public String getType() {
//            Male.ordinal(); //->int
//            CharGender xx = CharGender.values()[0];//int ->
//
//            Male.name(); // ->String
//            CharGender.valueOf("Male"); //String ->
//            return null;
//        }
//
//        @Override
//        public String getFriendlyType() {
//            return null;
//        }
//    }
//
//    public enum StringGender2 implements IFriendlyEnum {
//        Male("male"),
//        Female("female");
//
//        private String value;
//        private static final Map<String, StringGender2> stringToEnum = new HashMap<String, StringGender2>();
//        static {
//            for(StringGender2 gender : values()){
//                stringToEnum.put(gender.value, gender);
//            }
//        }
//
//        StringGender2(String value) {
//            this.value = value;
//        }
//
//        @Override
//        public String getType() {
//            Male.ordinal(); //->int
//            CharGender xx = CharGender.values()[0];//int ->
//
//            Male.name(); // ->String
//            CharGender.valueOf("Male"); //String ->
//            return null;
//        }
//
//        @Override
//        public String getFriendlyType() {
//            return null;
//        }
//
//        @Override
//        public String toString() {
//            return value;
//        }
//
//        public static StringGender2 fromString(String gender){
//            return stringToEnum.get(gender);
//        }
//    }
    @Test
    public void testEnum(){
    }

/*
    private IPermissionUser makeUser4CrudPermission(boolean generalCrud, boolean xCrud, boolean yCrud){
        ResourceCriteria generalTFile = new ResourceCriteria().setType(TFile.RESOURCE_TYPE_NAME);
        ResourceCriteria secretTFileX = new ResourceCriteria().setType(TFile.RESOURCE_TYPE_NAME).setFilter("ByTag").setFilterParameter("X");
        ResourceCriteria secretTFileY = new ResourceCriteria().setType(TFile.RESOURCE_TYPE_NAME).setFilter("ByTag").setFilterParameter("Y");

        PermissionEntry generalPermEntry = new PermissionEntryImpl()
                .setResourceDescriptor(generalTFile)
                .setAccess(new ResourceAccess()
                        .setCrudAll(generalCrud));
        PermissionEntry secretXPermEntry = new PermissionEntryImpl()
                .setResourceDescriptor(secretTFileX)
                .setAccess(new ResourceAccess()
                        .setCrudAll(xCrud));
        PermissionEntry secretYPermEntry = new PermissionEntryImpl()
                .setResourceDescriptor(secretTFileY)
                .setAccess(new ResourceAccess()
                        .setCrudAll(yCrud));

        Permission permission = new PermissionImpl();
        IPermissionUser permissionUser = new EasyPermissionUser4Test();
        permissionUser.getPermissions().add(permission);

        return permissionUser;
    }

    private IPermissionUser makeUser4ExtendedPermission(boolean generalAccess, boolean xAccess, boolean yAccess){
        ResourceCriteria generalTFile = new ResourceCriteria().setType(TFile.RESOURCE_TYPE_NAME);
        ResourceCriteria secretTFileX = new ResourceCriteria().setType(TFile.RESOURCE_TYPE_NAME).setFilter("ByTag").setFilterParameter("X");
        ResourceCriteria secretTFileY = new ResourceCriteria().setType(TFile.RESOURCE_TYPE_NAME).setFilter("ByTag").setFilterParameter("Y");

        PermissionEntry generalPermEntry = new PermissionEntryImpl()
                .setResourceDescriptor(generalTFile)
                .setAccess(new ResourceAccess()
                        .setExtendedAccess(TFile.ACCESS_EXECUTE, generalAccess));
        PermissionEntry secretXPermEntry = new PermissionEntryImpl()
                .setResourceDescriptor(secretTFileX)
                .setAccess(new ResourceAccess()
                        .setExtendedAccess(TFile.ACCESS_EXECUTE, xAccess));
        PermissionEntry secretYPermEntry = new PermissionEntryImpl()
                .setResourceDescriptor(secretTFileY)
                .setAccess(new ResourceAccess()
                        .setExtendedAccess(TFile.ACCESS_EXECUTE, yAccess));

        Permission permission = new PermissionImpl();
        IPermissionUser permissionUser = new EasyPermissionUser4Test();
        permissionUser.getPermissions().add(permission);

        return permissionUser;
    }

    @Before
    public void setup(){

    }

    @Test
    public void testPermissionAllowAllOnResourceRoot(){
        final IPermissionUser permissionUser = makeUser4CrudPermission(true, true, false);
        TFileAccessor tFileAccessor = new TFileAccessor();
        tFileAccessor.setSecurityVerifier(new TFileSecurityVerifer());


        TFile file = new TFile("A");
        Assert.assertTrue(tFileAccessor.create(file));
        Assert.assertTrue(tFileAccessor.read(file));
        Assert.assertTrue(tFileAccessor.update(file));
        Assert.assertTrue(tFileAccessor.delete(file));
        Assert.assertTrue(tFileAccessor.query());
        Assert.assertTrue(tFileAccessor.execute(file));

        file = new TFile("X");
        Assert.assertTrue(tFileAccessor.create(file));
        Assert.assertTrue(tFileAccessor.read(file));
        Assert.assertTrue(tFileAccessor.update(file));
        Assert.assertTrue(tFileAccessor.delete(file));
        Assert.assertTrue(tFileAccessor.query());
        Assert.assertFalse(tFileAccessor.execute(file));

        file = new TFile("Y");
        Assert.assertFalse(tFileAccessor.create(file));
        Assert.assertFalse(tFileAccessor.read(file));
        Assert.assertFalse(tFileAccessor.update(file));
        Assert.assertFalse(tFileAccessor.delete(file));
        Assert.assertFalse(tFileAccessor.query());
        Assert.assertTrue(tFileAccessor.execute(file));


    }*/
}
