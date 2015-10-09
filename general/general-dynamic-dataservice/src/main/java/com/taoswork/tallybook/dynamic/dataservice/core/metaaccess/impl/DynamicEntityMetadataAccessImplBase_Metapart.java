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
import com.taoswork.tallybook.general.datadomain.support.entity.PersistFriendly;
import com.taoswork.tallybook.general.datadomain.support.entity.Persistable;
import com.taoswork.tallybook.general.extension.collections.SetBuilder;
import com.taoswork.tallybook.general.solution.autotree.AutoTree;
import com.taoswork.tallybook.general.solution.autotree.AutoTreeException;
import com.taoswork.tallybook.general.solution.quickinterface.DataHolder;
import com.taoswork.tallybook.general.solution.quickinterface.ICallback;
import com.taoswork.tallybook.general.solution.quickinterface.ICallback2;
import com.taoswork.tallybook.general.solution.reflect.ClassUtility;
import com.taoswork.tallybook.general.solution.threading.annotations.EffectivelyImmutable;
import com.taoswork.tallybook.general.solution.threading.annotations.GuardedBy;
import org.apache.commons.collections4.map.LRUMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
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
    @EffectivelyImmutable
    private Set<Class> entityTypesWithInterfaces = null;

    @GuardedBy(value = "self", lockOrder = 5)
    private Map<Class, EntityClassTree> ceiling2ClassTreeMap = null;

    @GuardedBy("self")
    private Map<Class, Class> ceiling2RootInstantiable = null;

    @GuardedBy("self")
    private Map<Class, List<Class>> ceiling2Instantiables = null;

    @GuardedBy(value = "self", lockOrder = 4)
    private Map<ClassScope, ClassMetadata> classMetadataMap = null;

    private Map<Class, Class> entityType2GuardianType;

    public abstract EntityManager getEntityManager();

    protected void init(){
        EntityManager entityManager = getEntityManager();
        entityMetaRawAccess = new EntityMetadataRawAccessJPA();
        entityMetaRawAccess.setEntityManager(entityManager);
        if (entityMetaRawAccess == null) {
            LOGGER.error("entityMetaRawAccess Not initialized !!!");
            throw new RuntimeException("entityMetaRawAccess Not initialized !!! DynamicEntityMetadataAccessImplBase cannot continue.");
        } else {
            Class<?>[] entityClasses = entityMetaRawAccess.getAllEntities();

            entityTypes = new HashSet<Class>();
            new SetBuilder<Class>(entityTypes).addAll(entityClasses);

            entityInterfaces = ClassUtility.getAllSupers(Persistable.class, entityClasses, true, true);

            entityTypesWithInterfaces = new HashSet<Class>();
            entityTypesWithInterfaces.addAll(entityTypes);
            entityTypesWithInterfaces.addAll(entityInterfaces);

            entityType2GuardianType = null;
        }

        ceiling2ClassTreeMap = new LRUMap();
        ceiling2RootInstantiable = new LRUMap();
        ceiling2Instantiables = new LRUMap();
        classMetadataMap = new LRUMap();

        calcEntityTypeGuardians();
    }

    protected void clearCacheMaps() {
        ceiling2ClassTreeMap.clear();
        ceiling2RootInstantiable.clear();
        ceiling2Instantiables.clear();
        classMetadataMap.clear();
        if (entityType2GuardianType != null) {
            entityType2GuardianType.clear();
            entityType2GuardianType = null;
        }
    }

    @Override
    public Collection<Class> getAllEntities(boolean _entity, boolean _interface) {
        Collection<Class> result = null;
        if(_entity && _interface){
            result = this.entityTypesWithInterfaces;
        } else if(_entity){
            result = this.entityTypes;
        } else if(_interface){
            result = this.entityInterfaces;
        } else {
            result = new ArrayList<Class>();
        }
        return Collections.unmodifiableCollection(result);
    }

    @Override
    public Collection<Class> getInstantiableEntityTypes(Class<?> entityCeilingType) {
        synchronized (ceiling2Instantiables) {
            List<Class> root = ceiling2Instantiables.getOrDefault(entityCeilingType, null);
            if (null == root) {
                root = calcInstantiableEntityTypes(entityCeilingType);
                ceiling2Instantiables.put(entityCeilingType, root);
            }
            return root;
         }
    }

    @Override
    public <T> Class<T> getRootInstantiableEntityType(Class<T> entityCeilingType) {
        synchronized (ceiling2RootInstantiable) {
            Class<T> root = ceiling2RootInstantiable.getOrDefault(entityCeilingType, null);
            if (null == root) {
                root = calcRootInstantiableEntityClass(entityCeilingType);
                ceiling2RootInstantiable.put(entityCeilingType, root);
            }
            return root;
        }
    }

    @Override
    public <T> Class<T> getPermissionGuardian(Class<T> entityType) {
        calcEntityTypeGuardians();
        return entityType2GuardianType.getOrDefault(entityType, null);
    }

    /**
     * Get the root persistive entity class,
     *
     * @param entityType:
     * @param <T>
     * @return Person
     */
    private <T> Class<T> calcRootInstantiableEntityClass(Class<T> entityType) {
        final DataHolder<Class<T>> result = new DataHolder<Class<T>>();
        EntityClassTree subTree = getEntityClassTree(entityType);
        subTree.traverse(true, new ICallback2<Void, EntityClass, AutoTree.TraverseControl, AutoTreeException>() {
            @Override
            public Void callback(EntityClass parameter, AutoTree.TraverseControl control) throws AutoTreeException {
                if (parameter.isInstantiable()) {
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
    private List<Class> calcInstantiableEntityTypes(Class<?> entityCeilingType) {
        final List<Class> results = new ArrayList<Class>();
        EntityClassTree subTree = getEntityClassTree(entityCeilingType);
        subTree.traverse(true, new ICallback<Void, EntityClass, AutoTreeException>() {
            @Override
            public Void callback(EntityClass parameter) throws AutoTreeException {
                if (parameter.isInstantiable()) {
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
        Class<?>[] entityClasses = entityMetaRawAccess.getAllEntities();

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
        synchronized (classMetadataMap) {
            ClassScope classScope = new ClassScope(entityType, true, withHierarchy);
            classMetadata = classMetadataMap.getOrDefault(classScope, null);
            if (null == classMetadata) {
                if (withHierarchy) {
                    EntityClassTree entityClassTree = getEntityClassTree(entityType);
                    classMetadata = metadataService.generateMetadata(entityClassTree);
                } else {
                    classMetadata = metadataService.generateMetadata(entityType, true);
                    Field idField = entityMetaRawAccess.getIdField(entityType);
                    classMetadata.setIdField(idField);
                }
                classMetadataMap.put(classScope, classMetadata);
            }
        }
        return classMetadata;
    }

    private void calcEntityTypeGuardians(){
        if(entityType2GuardianType == null){
            Map<Class, Class> temp = new HashMap<Class, Class>();
            for(Class entityType : entityTypesWithInterfaces){
                Collection<Class> _interfaces = new ArrayList<Class>();
                _interfaces.add(entityType);
                ClassUtility.getAllSupers(Persistable.class, entityType, true, false, _interfaces);
                Class<?> fallback = null;
                Class<?> guardian = null;
                for(Class<?> _intf : _interfaces){
                    PersistFriendly persistFriendly = _intf.getDeclaredAnnotation(PersistFriendly.class);
                    if(persistFriendly != null){
                        Class annotationGuardian = persistFriendly.permissionGuardian();
                        boolean asDefaultGuardian = persistFriendly.asDefaultPermissionGuardian();
                        if(annotationGuardian != Void.class){
                            guardian = annotationGuardian;
                            break;
                        } else if(asDefaultGuardian){
                            guardian = _intf;
                            break;
                        }
                    }
                    fallback = _intf;
                }
                if(guardian == null){
                    guardian = fallback;
                }
                temp.put(entityType, guardian);
            }
            entityType2GuardianType = temp;
        }
    }
}
