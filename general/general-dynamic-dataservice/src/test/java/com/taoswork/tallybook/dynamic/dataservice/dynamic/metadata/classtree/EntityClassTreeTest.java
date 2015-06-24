package com.taoswork.tallybook.dynamic.dataservice.dynamic.metadata.classtree;

import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.classtree.EntityClass;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.classtree.EntityClassTree;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.classtree.EntityClassTreeAccessor;
import com.taoswork.tallybook.general.solution.quickinterface.IToString;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/5/23.
 */
public class EntityClassTreeTest {
    static interface Animal {
        public String type();
    }

    static interface Terrible {
        public int rate();
    }

    static class Dog implements Animal {
        @Override
        public String type() {
            return "DOG";
        }
    }

    static class BlackDog extends Dog implements Terrible {
        @Override
        public String type() {
            return "Black Dog";
        }

        @Override
        public int rate() {
            return 1;
        }
    }

    static class Cat implements Animal {
        @Override
        public String type() {
            return "Cat";
        }
    }

    static class BlackCat extends Cat implements Terrible {
        @Override
        public String type() {
            return "Black Cat";
        }

        @Override
        public int rate() {
            return 10;
        }
    }

    @Test
    public void testEntityClassTree() {
        IToString<EntityClass> entityClassIToString = new IToString<EntityClass>() {
            @Override
            public String makeString(EntityClass data) {
                return data.clz.getSimpleName();
            }
        };
        EntityClassTreeAccessor accessor = new EntityClassTreeAccessor();
        
        {
            EntityClassTree tree = new EntityClassTree(new EntityClass(Animal.class));
            accessor.add(tree, new EntityClass(Dog.class));
            accessor.add(tree, new EntityClass(Cat.class));
            accessor.add(tree, new EntityClass(BlackDog.class));
            accessor.add(tree, new EntityClass(BlackCat.class));

            String xxx = tree.buildTreeString(false, "*", entityClassIToString);
            Assert.assertEquals(xxx, "(0)Animal*(1)Dog*(2)BlackDog*(1)Cat*(2)BlackCat*");
        }
        {
            EntityClassTree tree = new EntityClassTree(new EntityClass(Terrible.class));
            accessor.add(tree, new EntityClass(Dog.class));
            accessor.add(tree, new EntityClass(Cat.class));
            accessor.add(tree, new EntityClass(BlackDog.class));
            accessor.add(tree, new EntityClass(BlackCat.class));

            String xxx = tree.buildTreeString(false, "*", entityClassIToString);
            Assert.assertEquals(xxx,
                    "(0)Terrible*(1)BlackDog*(1)BlackCat*");
        }

    }
}