package com.taoswork.tallybook.general.authority.core;

/**
 * Created by Gao Yuan on 2015/6/4.
 */
public class PermissionTest {
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
