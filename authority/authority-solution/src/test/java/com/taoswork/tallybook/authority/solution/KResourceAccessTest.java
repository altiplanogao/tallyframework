package com.taoswork.tallybook.authority.solution;

import com.taoswork.tallybook.authority.core.Access;
import com.taoswork.tallybook.authority.solution.domain.ResourceAccess;
import com.taoswork.tallybook.authority.solution.mockup.domain.access.FileResourceAccess;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public class KResourceAccessTest {
    @Test
    public void general() {
        Access accessCuq = Access.makeGeneralAccess(Access.CREATE, Access.UPDATE, Access.QUERY);
        ResourceAccess resourceAccess = new ResourceAccess();
        resourceAccess.setCanCreate(true).setCanUpdate(true).setCanQuery(true);
        Access access = resourceAccess.getAsAccess();
        ResourceAccess resourceAccessClone = ResourceAccess.createByAccess(access);
        ResourceAccess resourceAccessClone2 = new ResourceAccess();
        resourceAccessClone2.setByAccess(access);

        Assert.assertEquals(accessCuq, access);
        Assert.assertEquals(resourceAccess, resourceAccessClone);
        Assert.assertEquals(resourceAccess, resourceAccessClone2);
    }

    @Test
    public void extended() {
        Access rx = Access.makeExtendedAccess(FileResourceAccess.OPEN, FileResourceAccess.EXECUTE);
        FileResourceAccess fileResourceAccess = new FileResourceAccess();
        fileResourceAccess.setByAccess(rx);
        FileResourceAccess fileResourceAccess1 = new FileResourceAccess();
        fileResourceAccess1.setExtendedAccess(FileResourceAccess.OPEN, true).setExtendedAccess(FileResourceAccess.EXECUTE, true);
        Access rx1 = fileResourceAccess1.getAsAccess();

        FileResourceAccess fileResourceAccess2 = new FileResourceAccess();
        fileResourceAccess2.setCanOpen(true).setCanExecute(true);
        Access rx2 = fileResourceAccess2.getAsAccess();

        Assert.assertEquals(fileResourceAccess, fileResourceAccess1);
        Assert.assertEquals(fileResourceAccess, fileResourceAccess2);
        Assert.assertEquals(rx, rx1);
        Assert.assertEquals(rx, rx2);


    }
}
