package com.taoswork.tallybook.general.solution.reflect;

import com.taoswork.tallybook.general.solution.reflect.mockup.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/8/29.
 */
public class ClassUtilityTest {
    @Test
    public void testGetAllImplementedInterfaces(){
        {
            Collection<Class> serializableInterfaces = ClassUtility.getAllImplementedInterfaces(Serializable.class, Pug.class);
            int serializableInterfacesCnt = serializableInterfaces.size();
            Assert.assertEquals(serializableInterfacesCnt, 3);
            assertHas(serializableInterfaces, IAnimal.class, IDog.class, IPug.class);
        }
        {
            Collection<Class> animalInterfaces = ClassUtility.getAllImplementedInterfaces(IAnimal.class, Pug.class);
            int animalInterfacesCnt = animalInterfaces.size();
            Assert.assertEquals(animalInterfacesCnt, 2);
            assertHas(animalInterfaces, IDog.class, IPug.class);
        }
        {
            Collection<Class> furInterfaces = ClassUtility.getAllImplementedInterfaces(IHasFur.class, Pug.class);
            int furInterfacesCnt = furInterfaces.size();
            Assert.assertEquals(furInterfacesCnt, 2);
            assertHas(furInterfaces, IDog.class, IPug.class);
        }
        {
            Collection<Class> anyInterfaces = ClassUtility.getAllImplementedInterfaces(null, Pug.class);
            int anyInterfacesCnt = anyInterfaces.size();
            Assert.assertEquals(anyInterfacesCnt, 5);
            assertHas(anyInterfaces, Serializable.class, IHasFur.class, IAnimal.class, IDog.class, IPug.class);
        }
    }

    private void assertHas(Collection<Class> collection, Class ... classes){
        for (Class clz : classes){
            Assert.assertTrue(collection.contains(clz));
        }
    }
}
