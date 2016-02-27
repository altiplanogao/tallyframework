package com.taoswork.tallybook.testmaterial.general.utils;

import com.taoswork.tallybook.general.extension.collections.CollectionUtility;
import com.taoswork.tallybook.general.extension.utils.TPredicate;
import org.junit.Assert;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/10/24.
 */
public class CollectionAssert {
    public static <T> void ensureFullyCover(Collection<T> container, T... keys) {
        ensureFullyCover(container, new Converter<T, T>() {
            @Override
            public T convert(T from) {
                return from;
            }
        }, keys);
    }

    public static <T, K> void ensureFullyCover(Collection<T> container, final Converter<T, K> converter, K... keys) {
        Set<K> keySet = new HashSet<K>();
        for (K key : keys) {
            keySet.add(key);
        }
        Assert.assertEquals(container.size(), keySet.size());
        for (final K key : keys) {
            T node = CollectionUtility.find(container, new TPredicate<T>() {
                @Override
                public boolean evaluate(T notNullObj) {
                    K kcand = converter.convert(notNullObj);
                    return key.equals(kcand);
                }
            });
            Assert.assertNotNull(node);
        }
    }
}
