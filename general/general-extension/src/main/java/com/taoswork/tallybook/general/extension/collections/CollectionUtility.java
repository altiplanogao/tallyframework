package com.taoswork.tallybook.general.extension.collections;

import com.taoswork.tallybook.general.extension.utils.TPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/4/28.
 */
public class CollectionUtility {
    public static <T> T find(Collection<T> collection, final TPredicate<T> predicate){
        return (T)CollectionUtils.find(collection, new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                if(object == null){
                    return false;
                }
                T tObj = (T)object;
                if(null == tObj){
                    return false;
                }
                return predicate.evaluate(tObj);
            }
        });
    }

    public static <T> boolean isEmpty(Collection<T> collection){
        if(collection == null){
            return true;
        }
        return collection.isEmpty();
    }
}
