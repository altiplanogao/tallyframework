package com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.datadomain.base.entity.valuecopier.EntityCopierPool;
import com.taoswork.tallybook.datadomain.base.entity.valuecopier.IEntityCopier;
import com.taoswork.tallybook.descriptor.dataio.copier.CopierContext;
import com.taoswork.tallybook.descriptor.dataio.copier.CopyException;
import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.basic.ExternalForeignEntityFieldCopier;
import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.basic.ForeignEntityFieldCopier;
import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.list.*;
import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.map.AdornedLookupMapFieldCopier;
import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.map.BasicMapFieldCopier;
import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.map.EntityMapFieldCopier;
import com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier.map.LookupMapFieldCopier;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.BasePrimitiveFieldMeta;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
public class FieldCopierSolution implements IFieldCopierSolution{
    private final EntityCopierPool entityCopierPool;

    private final Map<Class, IFieldCopier> fieldCopiers;

    public FieldCopierSolution(EntityCopierPool entityCopierPool) {
        this.entityCopierPool = entityCopierPool;
//        this.fieldCopierPool = new FieldCopierSolution();
        fieldCopiers = new ConcurrentHashMap<Class, IFieldCopier>();

        init();
    }

    private void init(){
        addFieldCopier(new EmbeddedFieldCopier(this));
        addFieldCopier(new ForeignEntityFieldCopier(this));
        addFieldCopier(new ExternalForeignEntityFieldCopier(this));

        addFieldCopier(new PrimitiveListFieldCopier(this));
        addFieldCopier(new BasicListFieldCopier(this));
        addFieldCopier(new EntityListFieldCopier(this));
        addFieldCopier(new LookupListFieldCopier(this));
        addFieldCopier(new AdornedLookupListFieldCopier(this));

        addFieldCopier(new BasicMapFieldCopier(this));
        addFieldCopier(new EntityMapFieldCopier(this));
        addFieldCopier(new LookupMapFieldCopier(this));
        addFieldCopier(new AdornedLookupMapFieldCopier(this));
    }

    private void addFieldCopier(IFieldCopier fieldcopier){
        Class<? extends IFieldMeta> c = fieldcopier.targetMeta();
        if(fieldCopiers.containsKey(c)){
            throw new IllegalArgumentException("Duplicated handler");
        }
        fieldCopiers.put(c, fieldcopier);
    }

    @Override
    public <T> T walkFieldsAndCopy(IClassMeta topMeta, IClassMeta classMeta,
                                   T source, int currentLevel, int levelLimit, CopierContext copierContext) throws IllegalAccessException, InstantiationException {
        if (source == null)
            return null;
        T target = (T) source.getClass().newInstance();

        if(classMeta == null)
            classMeta = topMeta;

        final Collection<String> handledFields;
        String valueCopierName = classMeta.getValueCopier();
        IEntityCopier valueCopier = this.entityCopierPool.getValueCopier(valueCopierName);
        if(valueCopier != null){
            valueCopier.copy(source, target);
            handledFields = valueCopier.handledFields();
            if (valueCopier.allHandled()){
                return target;
            }
        } else {
            handledFields = null;
        }

        Map<String, IFieldMeta> fieldMetaMap = classMeta.getReadonlyFieldMetaMap();
        for (Map.Entry<String, IFieldMeta> fieldMetaEntry : fieldMetaMap.entrySet()) {
            String fieldName = fieldMetaEntry.getKey();
            if (handledFields != null && handledFields.contains(fieldName)) {
                continue;
            }
            IFieldMeta fieldMeta = fieldMetaEntry.getValue();
            if (fieldMeta instanceof BasePrimitiveFieldMeta) {
                Field field = fieldMeta.getField();
                field.set(target, field.get(source));
                continue;
            }

            Class<? extends IFieldMeta> c = fieldMeta.getClass();
            IFieldCopier copier = fieldCopiers.get(c);
            if(copier != null){
                boolean copied = copier.copy(topMeta, fieldMeta, source, target, currentLevel, levelLimit, copierContext);
                if(!copied){
                    for(int i = 0 ; i < 5; ++i){
                        copied = copier.copy(topMeta, fieldMeta, source, target, currentLevel, levelLimit, copierContext);
                    }
                    throw new IllegalAccessException("The copier doesn't work:" + c);
                }
            }else{
                throw new IllegalAccessException("Un implemented copier:" + c);
            }

        }

        return target;
    }

    public static <T extends Persistable> T makeSafeCopyForQuery(IFieldCopierSolution solution, T rec, CopierContext copierContext) throws CopyException {
        return solution.makeSafeCopy(rec, 1, copierContext);
    }

    public static <T extends Persistable> T makeSafeCopyForRead(IFieldCopierSolution solution, T rec, CopierContext copierContext) throws CopyException {
        return solution.makeSafeCopy(rec, 2, copierContext);
    }

    @Override
    public <T extends Persistable> T makeSafeCopy(T rec, int levelLimit, CopierContext copierContext) throws CopyException {
        if (rec == null)
            return null;
        if (levelLimit < 1)
            levelLimit = 1;
        try {
            IClassMeta topMeta = copierContext.classMetaAccess.getClassMeta(rec.getClass(), false);
            return walkFieldsAndCopy(topMeta, null, rec, 0, levelLimit, copierContext);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new CopyException(e);
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new CopyException(e);
        }
    }
}
