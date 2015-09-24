package com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.impl;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.ClassTreeMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClass;
import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClassGenealogy;
import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClassTree;
import com.taoswork.tallybook.dynamic.datameta.metadata.classtree.EntityClassTreeAccessor;
import com.taoswork.tallybook.dynamic.datameta.metadata.service.MetadataService;
import com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.DynamicEntityMetadataAccess;
import com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.helper.AEntityMetadataRawAccess;
import com.taoswork.tallybook.dynamic.dataservice.core.metaaccess.helper.impl.EntityMetadataRawAccessJPA;
import com.taoswork.tallybook.general.extension.collections.SetBuilder;
import com.taoswork.tallybook.general.solution.autotree.AutoTree;
import com.taoswork.tallybook.general.solution.autotree.AutoTreeException;
import com.taoswork.tallybook.general.solution.quickinterface.DataHolder;
import com.taoswork.tallybook.general.solution.quickinterface.ICallback;
import com.taoswork.tallybook.general.solution.quickinterface.ICallback2;
import com.taoswork.tallybook.general.solution.reflect.ClassUtility;
import com.taoswork.tallybook.general.solution.threading.annotations.EffectivelyImmutable;
import com.taoswork.tallybook.general.solution.threading.annotations.GuardedBy;
import org.apache.commons.collections.map.LRUMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by Gao Yuan on 2015/9/21.
 */
public abstract class DynamicEntityMetadataAccessImplBase_Metapart implements DynamicEntityMetadataAccess {
    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicEntityMetadataAccessImplBase_Metapart.class);

    @Resource(name = MetadataService.SERVICE_NAME)
    private MetadataService metadataService;

    @EffectivelyImmutable
    private AEntityMetadataRawAccess entityMetaRawAccess;

    private final EntityClassGenealogy entityClassGenealogy = new EntityClassGenealogy();

    @EffectivelyImmutable
    private Set<Class> entityTypes = null;
    @EffectivelyImmutable
    private Collection<Class> entityInterfaces = null;

    @GuardedBy(value = "self", lockOrder = 5)
    private Map<Class, EntityClassTree> ceiling2ClassTreeMap = null;

    @GuardedBy("self")
    private Map<Class, Class> ceiling2RootInstanceable = null;

    @GuardedBy("self")
    private Map<Class, List<Class>> ceiling2Instanceables = null;

    @GuardedBy(value = "self", lockOrder = 4)
    private Map<ClassScope, ClassMetadata> classMetadataMap = null;

    public abstract EntityManager getEntityManager();

    protected void init(){
        EntityManager entityManager = getEntityManager();
        entityMetaRawAccess = new EntityMetadataRawAccessJPA();
        entityMetaRawAccess.setEntityManager(entityManager);
        if (entityMetaRawAccess == null) {
            LOGGER.error("entityMetaRawAccess Not initialized !!!");
            throw new RuntimeException("entityMetaRawAccess Not initialized !!! DynamicEntityMetadataAccessImplBase cannot continue.");
        } else {
            Class<?>[] entityClasses = entityMetaRawAccess.getAllEntityClasses();
            entityTypes = new HashSet<Class>();
            new SetBuilder<Class>(entityTypes).addAll(entityClasses);
            entityInterfaces = ClassUtility.getAllImplementedInterfaces(Serializable.class, entityClasses);
        }

        ceiling2ClassTreeMap = new LRUMap();
        ceiling2RootInstanceable = new LRUMap();
        ceiling2Instanceables = new LRUMap();
        classMetadataMap = new LRUMap();

    }

    protected void clearCacheMaps(){
        ceiling2ClassTreeMap.clear();
        ceiling2RootInstanceable.clear();
        ceiling2Instanceables.clear();
        classMetadataMap.clear();

    }

    @Override
    public Collection<Class> getAllEntityTypes() {
        return Collections.unmodifiableCollection(entityTypes);
    }

    @Override
    public Collection<Class> getAllEntityInterfaces() {
        return Collections.unmodifiableCollection(entityInterfaces);
    }

    @Override
    public Collection<Class> getInstanceableEntityTypes(Class<?> entityCeilingType) {
        synchronized (ceiling2Instanceables) {
            List<Class> root = ceiling2Instanceables.getOrDefault(entityCeilingType, null);
            if (null == root) {
                root = calcInstanceableEntityTypes(entityCeilingType);
                ceiling2Instanceables.put(entityCeilingType, root);
            }
            return root;
         }
    }

    @Override
    public <T> Class<T> getRootInstanceableEntityClass(Class<T> entityCeilingType) {
        synchronized (ceiling2RootInstanceable) {
            Class<T> root = ceiling2RootInstanceable.getOrDefault(entityCeilingType, null);
            if (null == root) {
                root = calcRootInstanceableEntityClass(entityCeilingType);
                ceiling2RootInstanceable.put(entityCeilingType, root);
            }
            return root;
        }
    }

    /**
     * Get the root persistive entity class,
     *
     * @param entityType:
     * @param <T>
     * @return Person
     */
    private <T> Class<T> calcRootInstanceableEntityClass(Class<T> entityType) {
        final DataHolder<Class<T>> result = new DataHolder<Class<T>>();
        EntityClassTree subTree = getEntityClassTree(entityType);
        subTree.traverse(true, new ICallback2<Void, EntityClass, AutoTree.TraverseControl, AutoTreeException>() {
            @Override
            public Void callback(EntityClass parameter, AutoTree.TraverseControl control) throws AutoTreeException {
                if (parameter.isInstanceable()) {
                    result.put((Class<T>) parameter.clz);
                    control.shouldBreak = true;
                }
                return null;
            }
        }, false);
        return result.data;
    }

    /**
     * Calc the instance-able entity-types from ceiling-type,
     *
     * @param entityCeilingType:
     * @return
     */
    private List<Class> calcInstanceableEntityTypes(Class<?> entityCeilingType) {
        final List<Class> results = new ArrayList<Class>();
        EntityClassTree subTree = getEntityClassTree(entityCeilingType);
        subTree.traverse(true, new ICallback<Void, EntityClass, AutoTreeException>() {
            @Override
            public Void callback(EntityClass parameter) throws AutoTreeException {
                if (parameter.isInstanceable()) {
                    results.add(parameter.clz);
                }
                return null;
            }
        }, false);
        return results;
    }

    @Override
    public EntityClassTree getEntityClassTree(Class<?> entityCeilingType) {
        synchronized (ceiling2ClassTreeMap){
            EntityClassTree classTree = ceiling2ClassTreeMap.getOrDefault(entityCeilingType, null);
            if (classTree == null) {
                classTree = calcEntityClassTreeFromCeiling(entityCeilingType);
                ceiling2ClassTreeMap.put(entityCeilingType, classTree);
            }
            return classTree;
        }
    }

    @Override
    public ClassMetadata getClassMetadata(Class<?> entityType, boolean withHierarchy) {
        return calcClassMetadata(entityType, withHierarchy);
    }

    @Override
    public ClassTreeMetadata getClassTreeMetadata(Class<?> entityCeilingType) {
        ClassMetadata classMetadata = calcClassMetadata(entityCeilingType, true);
        if(classMetadata instanceof ClassTreeMetadata){
            return (ClassTreeMetadata)classMetadata;
        }
        return null;
    }

    /**
     * Work out a class-tree from the ceiling type.
     * There is an easy way to work it out (BUT: it is wrong):
     *      1. build-up a class-tree from the root-type (Serializable.class in our case):
     *      2. cut down the branch with its type equals ceilingType
     * The above method is wrong, because it cannot handle the case that multiple entityType with different super class shares same entityType interface, example:
     *      interface IHasFur
     *      interface IToy
     *      interface IAnimal
     *      class Dog : IHasFur, IAnimal
     *      class BarbieDoll : IHasFur, IToy
     * The method above cannot handle it correctly.
     * @param ceilingType
     * @return
     */
    private EntityClassTree calcEntityClassTreeFromCeiling(Class<?> ceilingType){
        Class<?>[] entityClasses = entityMetaRawAccess.getAllEntityClasses();

        EntityClassTreeAccessor entityClassTreeAccessor = new EntityClassTreeAccessor(entityClassGenealogy);
        entityClassTreeAccessor.setAllowBranch(false).setAllowParent(false);
        EntityClassTree root = new EntityClassTree(ceilingType);
        for (Class<?> entityClz : entityClasses) {
            entityClassTreeAccessor.add(root, entityClz);
        }
        return root;
    }

    private ClassMetadata calcClassMetadata(Class<?> entityType, boolean withHierarchy) {
        ClassMetadata classMetadata = null;
        synchronized (classMetadataMap){
            ClassScope classScope = new ClassScope(entityType, true, withHierarchy);
            classMetadata = classMetadataMap.getOrDefault(classScope, null);
            if (null == classMetadata) {
                if(withHierarchy){
                    EntityClassTree entityClassTree = getEntityClassTree(entityType);
                    classMetadata = metadataService.generateMetadata(entityClassTree);
                }else{
                    classMetadata = metadataService.generateMetadata(entityType, true);
                    Field idField = entityMetaRawAccess.getIdField(entityType);
                    classMetadata.setIdField(idField);
                }
                classMetadataMap.put(classScope, classMetadata);
            }
        }
        return classMetadata;
    }
}
