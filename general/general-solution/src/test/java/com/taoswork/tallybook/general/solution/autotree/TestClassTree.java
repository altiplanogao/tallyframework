package com.taoswork.tallybook.general.solution.autotree;

import com.taoswork.tallybook.general.solution.autotree.morkdata.StringTree;
import com.taoswork.tallybook.general.solution.autotree.morkdata.StringTreeAccessor;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/5/22.
 */
public class TestClassTree {
    @Test
    public void testDataTree(){
        String delimiter = "*";
        {
            StringTree stringTree = new StringTree("A");
            StringTreeAccessor setting = new StringTreeAccessor();
            setting.allowAll();

            setting.add(stringTree, "AAAA");
            setting.add(stringTree, "AAA");
            setting.add(stringTree, "AA");
            setting.add(stringTree, "AAB");
            setting.add(stringTree, "BBC");

            String xx = stringTree.buildTreeString(false, delimiter);
            String xxx = stringTree.getRoot().buildTreeString(false, delimiter);

            Assert.assertEquals(xx, "(0)A*(1)AA*(2)AAA*(3)AAAA*(2)AAB*");
            Assert.assertEquals(xxx, "(0)*(1)A*(2)AA*(3)AAA*(4)AAAA*(3)AAB*(1)B*(2)BB*(3)BBC*");
        }
        {
            StringTree stringTree = new StringTree("A");
            StringTreeAccessor setting = new StringTreeAccessor();
            setting.denyAll().setAllowChild(true);

            setting.add(stringTree, "AAAA");
            setting.add(stringTree, "BBC");

            String xx = stringTree.buildTreeString(false, delimiter);
            String xxx = stringTree.getRoot().buildTreeString(false, delimiter);

            Assert.assertEquals(xx, "(0)A*(1)AA*(2)AAA*(3)AAAA*");
            Assert.assertEquals(xxx, xx);
        }
        {
            StringTree stringTree = new StringTree("AA");
            StringTreeAccessor setting = new StringTreeAccessor();
            setting.denyAll().setAllowChild(true);

            setting.add(stringTree, "AAAA");
            setting.add(stringTree, "A");
            setting.add(stringTree, "BBC");

            String xx = stringTree.buildTreeString(false, delimiter);
            String xxx = stringTree.getRoot().buildTreeString(false, delimiter);

            Assert.assertEquals(xx, "(0)AA*(1)AAA*(2)AAAA*");
            Assert.assertEquals(xxx, xx);
        }
        {
            StringTree stringTree = new StringTree("AA");
            StringTreeAccessor setting = new StringTreeAccessor();
            setting.denyAll().setAllowParent(true);

            setting.add(stringTree, "AAAA");
            setting.add(stringTree, "A");
            setting.add(stringTree, "BBC");

            String xx = stringTree.buildTreeString(false, delimiter);
            String xxx = stringTree.getRoot().buildTreeString(false, delimiter);

            Assert.assertEquals(xx, "(0)AA*");
            Assert.assertEquals(xxx, "(0)A*(1)AA*");
        }
    }
}
