package com.taoswork.tallybook.dataservice.core.metaaccess.impl;

import com.taoswork.tallybook.dataservice.IDataService;
import com.taoswork.tallybook.dataservice.core.description.FriendlyMetaInfoService;
import com.taoswork.tallybook.dataservice.service.EntityMetaAccess;
import com.taoswork.tallybook.descriptor.description.infos.EntityInfoType;
import com.taoswork.tallybook.descriptor.description.infos.IEntityInfo;
import com.taoswork.tallybook.descriptor.description.infos.main.EntityInfo;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.service.MetaInfoService;
import com.taoswork.tallybook.general.solution.threading.annotations.GuardedBy;
import org.apache.commons.collections4.map.LRUMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Locale;
import java.util.Map;

/**
 * Assume we have classes as following:
 * interface IPerson{}                  [entity interface]
 * class Person implements IPerson{}    [entity class],[entity root class]
 * class Male extends Person{}          [entity class]
 * class Female extends Person{}        [entity class]
 */
public abstract class BaseEntityMetaAccess extends BaseEntityMetaAccess_metapart {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityMetaAccess.class);

    private static class ClassScopeWithLocale extends ClassScopeExtension<Locale> {
        public ClassScopeWithLocale(ClassScope classScope, Locale note) {
            super(classScope, note);
        }

        public ClassScopeWithLocale(String clazzName, boolean withSuper, boolean withHierarchy, Locale note) {
            super(clazzName, withSuper, withHierarchy, note);
        }

        public ClassScopeWithLocale(Class clazz, boolean withSuper, boolean withHierarchy, Locale note) {
            super(clazz, withSuper, withHierarchy, note);
        }

        public ClassScope getClassScope() {
            return new ClassScope(this);
        }
    }

    private static class ClassScopeWithLocaleAndType extends ClassScopeExtension<_FriendlyEntityInfoType> {
        public ClassScopeWithLocaleAndType(ClassScope classScope, _FriendlyEntityInfoType note) {
            super(classScope, note);
        }

        public ClassScopeWithLocaleAndType(ClassScope classScope, Locale locale, EntityInfoType entityInfoType) {
            this(classScope, new _FriendlyEntityInfoType(entityInfoType, locale));
        }

        public ClassScopeWithLocaleAndType(String clazzName, boolean withSuper, boolean withHierarchy, _FriendlyEntityInfoType note) {
            super(clazzName, withSuper, withHierarchy, note);
        }

        public ClassScopeWithLocaleAndType(Class clazz, boolean withSuper, boolean withHierarchy, _FriendlyEntityInfoType note) {
            super(clazz, withSuper, withHierarchy, note);
        }

        public ClassScopeWithLocale getClassScopeWithLocale() {
            return new ClassScopeWithLocale(this, this.getNote().getLocale());
        }

        public EntityInfoType getInfoType() {
            return this.getNote().getEntityInfoType();
        }
    }

    @Resource(name = IDataService.DATASERVICE_NAME_S_BEAN_NAME)
    private String dataServiceName;

    @Resource(name = MetaInfoService.SERVICE_NAME)
    private MetaInfoService metaInfoService;

    @Resource(name = FriendlyMetaInfoService.SERVICE_NAME)
    private FriendlyMetaInfoService friendlyMetaInfoService;

    @GuardedBy(value = "self", lockOrder = 3)
    private Map<ClassScope, EntityInfo> entityInfoMap = null;
    @GuardedBy(value = "self", lockOrder = 2)
    private Map<ClassScopeWithLocale, EntityInfo> entityInfoMapWithLocale = null;
    @GuardedBy(value = "self", lockOrder = 1)
    private Map<ClassScopeWithLocaleAndType, IEntityInfo> entityInfoMapWithLocaleType = null;

    @PostConstruct
    public void init() {
        super.init();
        entityInfoMap = new LRUMap();
        entityInfoMapWithLocale = new LRUMap();
        entityInfoMapWithLocaleType = new LRUMap();
    }

    //Used for easy debug
    protected void clearCacheMaps() {
        super.clearCacheMaps();
        entityInfoMap.clear();
        entityInfoMapWithLocale.clear();
        entityInfoMapWithLocaleType.clear();
    }

    private EntityInfo calcEntityInfo(IClassMeta classMeta) {
        return metaInfoService.generateEntityMainInfo(classMeta);
    }

    private EntityInfo calcFriendlyEntityInfo(EntityInfo rawEntityInfo, Locale locale) {
        return friendlyMetaInfoService.makeFriendly(rawEntityInfo, locale);
    }

    @Override
    public IEntityInfo getEntityInfo(Class<?> entityType, boolean withHierarchy, Locale locale, EntityInfoType infoType) {
        ClassScope classScope = new ClassScope(entityType, true, withHierarchy);
        ClassScopeWithLocaleAndType scope = new ClassScopeWithLocaleAndType(classScope, locale, infoType);
        return getEntityInfo_Locale_Type(entityType, scope);
    }

    protected IEntityInfo getEntityInfo_Locale_Type(Class<?> entityType, ClassScopeWithLocaleAndType scopeWithLocaleAndType) {
        synchronized (entityInfoMapWithLocaleType) {
            IEntityInfo entityInfo = entityInfoMapWithLocaleType.get(scopeWithLocaleAndType);
            if (entityInfo == null) {
                ClassScopeWithLocale classScopeWithLocale = scopeWithLocaleAndType.getClassScopeWithLocale();
                EntityInfoType infoType = scopeWithLocaleAndType.getInfoType();
                entityInfo = calcEntityInfo_Locale_Type(entityType, classScopeWithLocale, infoType);
                entityInfoMapWithLocaleType.put(scopeWithLocaleAndType, entityInfo);
            }
            return entityInfo;
        }
    }

    protected EntityInfo getEntityInfo_Locale(Class<?> entityType, ClassScopeWithLocale classScopeWithLocale) {
        synchronized (entityInfoMapWithLocale) {
            EntityInfo entityInfo = entityInfoMapWithLocale.get(classScopeWithLocale);
            if (entityInfo == null) {
                ClassScope classScope = classScopeWithLocale.getClassScope();
                Locale locale = classScopeWithLocale.getNote();
                entityInfo = calcEntityInfo_Locale(entityType, classScope, locale);
                entityInfoMapWithLocale.put(classScopeWithLocale, entityInfo);
            }
            return entityInfo;
        }
    }

    protected EntityInfo getEntityInfo(Class<?> entityType, ClassScope classScope) {
        synchronized (entityInfoMap) {
            EntityInfo entityInfo = entityInfoMap.get(classScope);
            if (entityInfo == null) {
                entityInfo = calcEntityInfo(entityType, classScope);
                entityInfoMap.put(classScope, entityInfo);
            }
            return entityInfo;
        }
    }

    private IEntityInfo calcEntityInfo_Locale_Type(Class<?> entityType, ClassScopeWithLocale scopeWithLocale, EntityInfoType infoType) {
        EntityInfo entityLocInfo = getEntityInfo_Locale(entityType, scopeWithLocale);
        return metaInfoService.convert(entityLocInfo, infoType);
    }

    private EntityInfo calcEntityInfo_Locale(Class<?> entityType, ClassScope classScope, Locale locale) {
        EntityInfo entityInfo = getEntityInfo(entityType, classScope);
        return calcFriendlyEntityInfo(entityInfo, locale);
    }

    private EntityInfo calcEntityInfo(Class<?> entityType, ClassScope classScope) {
        boolean withHierarchy = classScope.isWithHierarchy();
        IClassMeta metadata = this.getClassMeta(entityType, withHierarchy);
        EntityInfo entityInfo = calcEntityInfo(metadata);
        return entityInfo;
    }
}
