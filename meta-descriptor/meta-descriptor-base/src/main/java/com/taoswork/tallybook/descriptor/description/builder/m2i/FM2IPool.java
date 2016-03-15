package com.taoswork.tallybook.descriptor.description.builder.m2i;

import com.taoswork.tallybook.descriptor.description.builder.m2i.basic.*;
import com.taoswork.tallybook.descriptor.description.builder.m2i.list.*;
import com.taoswork.tallybook.descriptor.description.builder.m2i.map.AdornedLookupMapFM2I;
import com.taoswork.tallybook.descriptor.description.builder.m2i.map.BasicMapFM2I;
import com.taoswork.tallybook.descriptor.description.builder.m2i.map.EntityMapFM2I;
import com.taoswork.tallybook.descriptor.description.builder.m2i.map.LookupMapFM2I;
import com.taoswork.tallybook.descriptor.description.descriptor.field.IFieldInfo;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
public class FM2IPool {
    private final Map<Class, IFM2I> fm2is;
    public FM2IPool(){
        fm2is = new ConcurrentHashMap<Class, IFM2I>();
        addFM2Is();
    }

    protected void addFM2Is(){
        addFM2I(new StringFM2I());
        addFM2I(new EnumFM2I());
        addFM2I(new BooleanFM2I());
        addFM2I(new DateFM2I());
        addFM2I(new ForeignEntityFM2I());
        addFM2I(new ExternalForeignEntityFM2I());
        addFM2I(new PaleFM2I());

        addFM2I(new PrimitiveListFM2I());
        addFM2I(new BasicListFM2I());
        addFM2I(new EntityListFM2I());
        addFM2I(new LookupListFM2I());
        addFM2I(new AdornedLookupListFM2I());

        addFM2I(new BasicMapFM2I());
        addFM2I(new EntityMapFM2I());
        addFM2I(new LookupMapFM2I());
        addFM2I(new AdornedLookupMapFM2I());
    }

    protected void addFM2I(FM2I fm2i){
        Class<? extends IFieldMeta> c = fm2i.targetMeta();
        if(fm2is.containsKey(c)){
            throw new IllegalArgumentException("Duplicated handler");
        }
        fm2is.put(c, fm2i);
    }

    public IFieldInfo createByFM2I(IClassMeta topMeta, String prefix, IFieldMeta fm, Collection<Class> collectionTypeReferenced){
        Class<? extends IFieldMeta> c = fm.getClass();
        IFM2I fm2i = fm2is.get(c);
        if(fm2i != null)
            return fm2i.createInfo(topMeta, prefix, fm, collectionTypeReferenced);
        return null;
    }
}
