package com.taoswork.tallybook.dynamic.dataservice.metaaccess.helper;

import com.taoswork.tallybook.dynamic.datadomain.presentation.PresentationClass;
import org.apache.commons.lang3.ArrayUtils;

import javax.persistence.EntityManager;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/5/21.
 */
public abstract class EntityMetadataHelper implements EntityMetadataRawAccess {

    public abstract EntityManager getEntityManager();

    public abstract void setEntityManager(EntityManager entityManager);

    public static boolean isExcludeClassFromPolymorphism(Class<?> clazz) {
        //We filter out abstract classes because they can't be instantiated.
        if (Modifier.isAbstract(clazz.getModifiers())) {
            return true;
        }

        //We filter out classes that are marked to exclude from polymorphism
        PresentationClass presentationClass = clazz.getAnnotation(PresentationClass.class);
        if (presentationClass == null) {
            return false;
        } else if (presentationClass.excludeFromPolymorphism()) {
            return true;
        }
        return false;
    }

    public static Class<?>[] sortEntities(Class<?> ceilingClz, List<Class<?>> entities) {
        /*
         * Sort entities with the most derived appearing first
         */
        Class<?>[] sortedEntities = new Class<?>[entities.size()];
        List<Class<?>> stageItems = new ArrayList<Class<?>>();
        stageItems.add(ceilingClz);
        int j = 0;
        while (j < sortedEntities.length) {
            List<Class<?>> newStageItems = new ArrayList<Class<?>>();
            boolean topLevelClassFound = false;
            for (Class<?> stageItem : stageItems) {
                Iterator<Class<?>> itr = entities.iterator();
                while(itr.hasNext()) {
                    Class<?> entity = itr.next();
                    checkitem: {
                        if (ArrayUtils.contains(entity.getInterfaces(), stageItem) || entity.equals(stageItem)) {
                            topLevelClassFound = true;
                            break checkitem;
                        }

                        if (topLevelClassFound) {
                            continue;
                        }

                        if (entity.getSuperclass().equals(stageItem) && j > 0) {
                            break checkitem;
                        }

                        continue;
                    }
                    sortedEntities[j] = entity;
                    itr.remove();
                    j++;
                    newStageItems.add(entity);
                }
            }
            if (newStageItems.isEmpty()) {
                throw new IllegalArgumentException("There was a gap in the inheritance hierarchy for (" + ceilingClz.getName() + ")");
            }
            stageItems = newStageItems;
        }
        ArrayUtils.reverse(sortedEntities);
        return sortedEntities;
    }
}