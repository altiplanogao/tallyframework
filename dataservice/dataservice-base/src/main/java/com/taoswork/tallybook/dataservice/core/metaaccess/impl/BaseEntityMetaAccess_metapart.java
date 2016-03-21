package com.taoswork.tallybook.dataservice.core.metaaccess.impl;

import com.taoswork.tallybook.datadomain.base.entity.PersistEntity;
import com.taoswork.tallybook.dataservice.core.metaaccess.helper.EntityMetaRawAccess;
import com.taoswork.tallybook.dataservice.service.EntityMetaAccess;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.classmetadata.ImmutableClassMeta;
import com.taoswork.tallybook.descriptor.metadata.classtree.EntityClass;
import com.taoswork.tallybook.descriptor.metadata.classtree.EntityClassGenealogy;
import com.taoswork.tallybook.descriptor.metadata.classtree.EntityClassTree;
import com.taoswork.tallybook.descriptor.metadata.classtree.EntityClassTreeAccessor;
import com.taoswork.tallybook.descriptor.service.MetaService;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by Gao Yuan on 2015/9/21.
 */
abstract class BaseEntityMetaAccess_metapart implements EntityMetaAccess {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseEntityMetaAccess_metapart.class);

    @Resource(name = MetaService.SERVICE_NAME)
    private MetaService metaService;

    @EffectivelyImmutable
    private EntityMetaRawAccess entityMetaRawAccess;

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
    private Map<ClassScope, IClassMeta> classMetaMap = null;

    private Map<Class, Class> entityType2GuardianType;

    protected abstract Class<?> superPersistable();

    public abstract EntityMetaRawAccess makeEntityMetaRawAccess();

    protected void init() {
        entityMetaRawAccess = makeEntityMetaRawAccess();
        if (entityMetaRawAccess == null) {
            LOGGER.error("entityMetaRawAccess Not initialized !!!");
            throw new RuntimeException("entityMetaRawAccess Not initialized !!! BaseEntityMetaAccess cannot continue.");
        } else {
            Class<?>[] entityClasses = entityMetaRawAccess.getAllEntities();

            entityTypes = new HashSet<Class>();
            new SetBuilder<Class>(entityTypes).addAll(entityClasses);

            entityInterfaces = ClassUtility.getAllSupers(superPersistable(), entityClasses, true, true);

            entityTypesWithInterfaces = new HashSet<Class>();
            entityTypesWithInterfaces.addAll(entityTypes);
            entityTypesWithInterfaces.addAll(entityInterfaces);

            entityType2GuardianType = null;
        }

        ceiling2ClassTreeMap = new LRUMap();
        ceiling2RootInstantiable = new LRUMap();
        ceiling2Instantiables = new LRUMap();
        classMetaMap = new LRUMap();

        calcAllEntityMeta();
        calcAllInstantiable();
        calcEntityTypeGuardians();
    }

    protected void clearCacheMaps() {
        metaService.clearCache();
        ceiling2ClassTreeMap.clear();
        ceiling2RootInstantiable.clear();
        ceiling2Instantiables.clear();
        classMetaMap.clear();
        if (entityType2GuardianType != null) {
            entityType2GuardianType.clear();
            entityType2GuardianType = null;
        }
    }

    @Override
    public Collection<Class> getAllEntities(boolean _entity, boolean _interface) {
        Collection<Class> result = null;
        if (_entity && _interface) {
            result = this.entityTypesWithInterfaces;
        } else if (_entity) {
            result = this.entityTypes;
        } else if (_interface) {
            result = this.entityInterfaces;
        } else {
            result = new ArrayList<Class>();
        }
        return Collections.unmodifiableCollection(result);
    }

    @Override
    public Collection<Class> getInstantiableEntityTypes(Class<?> entityCeilingType) {
        synchronized (ceiling2Instantiables) {
            List<Class> root = ceiling2Instantiables.get(entityCeilingType);
            if (null == root) {
                if(!entityTypesWithInterfaces.contains(entityCeilingType)){
                    return null;
                }
                root = calcInstantiableEntityTypes(entityCeilingType);
                ceiling2Instantiables.put(entityCeilingType, root);
            }
            return root;
        }
    }

    @Override
    public <T> Class<T> getRootInstantiableEntityType(Class<T> entityCeilingType) {
        synchronized (ceiling2RootInstantiable) {
            Class<T> root = ceiling2RootInstantiable.get(entityCeilingType);
            if (null == root) {
                if(!entityTypesWithInterfaces.contains(entityCeilingType)){
                    return null;
                }
                root = calcRootInstantiableEntityClass(entityCeilingType);
                ceiling2RootInstantiable.put(entityCeilingType, root);
            }
            return root;
        }
    }

    @Override
    public <T> Class<T> getPermissionGuardian(Class<T> entityType) {
        calcEntityTypeGuardians();
        return entityType2GuardianType.get(entityType);
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
        synchronized (ceiling2ClassTreeMap) {
            EntityClassTree classTree = ceiling2ClassTreeMap.get(entityCeilingType);
            if (classTree == null) {
                if(!entityTypesWithInterfaces.contains(entityCeilingType)){
                    return null;
                }
                classTree = calcEntityClassTreeFromCeiling(entityCeilingType);
                ceiling2ClassTreeMap.put(entityCeilingType, classTree);
            }
            return classTree;
        }
    }

    @Override
    public IClassMeta getClassMeta(Class<?> entityType, boolean withHierarchy) {
        return calcClassMeta(entityType, withHierarchy);
    }

    @Override
    public IClassMeta getClassTreeMeta(Class<?> entityCeilingType) {
        IClassMeta classMeta = calcClassMeta(entityCeilingType, true);
        return classMeta;
    }

    /**
     * Work out a class-tree from the ceiling type.
     * There is an easy way to work it out (BUT: it is wrong):
     * 1. build-up a class-tree from the root-type (Serializable.class in our case):
     * 2. cut down the branch with its type equals ceilingType
     * The above method is wrong, because it cannot handle the case that multiple entityType with different super class shares same entityType interface, example:
     * interface IHasFur
     * interface IToy
     * interface IAnimal
     * class Dog : IHasFur, IAnimal
     * class BarbieDoll : IHasFur, IToy
     * The method above cannot handle it correctly.
     *
     * @param ceilingType
     * @return
     */
    private EntityClassTree calcEntityClassTreeFromCeiling(Class<?> ceilingType) {
        Class<?>[] entityClasses = entityMetaRawAccess.getAllEntities();

        EntityClassTreeAccessor entityClassTreeAccessor = new EntityClassTreeAccessor(entityClassGenealogy);
        entityClassTreeAccessor.setAllowBranch(false).setAllowParent(false);
        EntityClassTree root = new EntityClassTree(ceilingType);
        for (Class<?> entityClz : entityClasses) {
            entityClassTreeAccessor.add(root, entityClz);
        }
        return root;
    }

    private IClassMeta calcClassMeta(Class<?> entityType, boolean withHierarchy) {
        IClassMeta classMeta = null;
        synchronized (classMetaMap) {
            ClassScope classScope = new ClassScope(entityType, true, withHierarchy);
            classMeta = classMetaMap.get(classScope);
            if (null == classMeta) {
                String idFieldName = null;
                try {
                    Field idField = entityMetaRawAccess.getIdField(entityType);
                    if (idField != null) {
                        idFieldName = idField.getName();
                    }
                } catch (IllegalArgumentException e) {

                }

                if (withHierarchy) {
                    EntityClassTree entityClassTree = getEntityClassTree(entityType);
                    classMeta = metaService.generateMeta(entityClassTree, idFieldName, true);
                } else {
                    classMeta = metaService.generateMeta(entityType, idFieldName, true);
                }
                if (!(classMeta instanceof ImmutableClassMeta)) {
                    LOGGER.error("NOT A FINAL CLASS METADATA, PERHAPS LOW PERFORMANCE.");
                }
                classMetaMap.put(classScope, classMeta);
            }
        }
        return classMeta;
    }

    private void calcAllEntityMeta() {
        Class[] allEntities = entityMetaRawAccess.getAllEntities();
        for (Class<?> entity : allEntities) {
            calcClassMeta(entity, true);
            calcClassMeta(entity, false);
        }
    }

    private void calcAllInstantiable(){
        for (Class<?> entity : entityTypesWithInterfaces){
            getRootInstantiableEntityType(entity);
            getInstantiableEntityTypes(entity);
        }
    }

    private void calcEntityTypeGuardians() {
        if (entityType2GuardianType == null) {
            Map<Class, Class> temp = new HashMap<Class, Class>();
            for (Class entityType : entityTypesWithInterfaces) {
                Collection<Class> _interfaces = new ArrayList<Class>();
                _interfaces.add(entityType);
                ClassUtility.getAllSupers(superPersistable(), entityType, true, false, _interfaces);
                Class<?> fallback = null;
                Class<?> guardian = null;
                for (Class<?> _intf : _interfaces) {
                    PersistEntity persistEntity = _intf.getDeclaredAnnotation(PersistEntity.class);
                    if (persistEntity != null) {
                        Class annotationGuardian = persistEntity.permissionGuardian();
                        boolean asDefaultGuardian = persistEntity.asDefaultPermissionGuardian();
                        if (annotationGuardian != void.class) {
                            guardian = annotationGuardian;
                            break;
                        } else if (asDefaultGuardian) {
                            guardian = _intf;
                            break;
                        }
                    }
                    fallback = _intf;
                }
                if (guardian == null) {
                    guardian = fallback;
                }
                temp.put(entityType, guardian);
            }
            entityType2GuardianType = temp;
        }
    }
}
