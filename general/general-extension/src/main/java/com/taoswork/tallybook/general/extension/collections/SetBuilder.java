package com.taoswork.tallybook.general.extension.collections;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/4/21.
 */
public class SetBuilder<TE> {
    protected Set<TE> innerSet;

    public SetBuilder(){
        this(new HashSet<TE>());
    }

    public SetBuilder(Set<TE> set){
        innerSet = set;
    }

    public static <TTE> SetBuilder instance(){
        SetBuilder<TTE> obj = new SetBuilder<TTE>();
        return obj;
    }

    public static <TTK> SetBuilder instance(TTK element){
        SetBuilder<TTK> obj = new SetBuilder<TTK>();
        obj.put(element);
        return obj;
    }

    public SetBuilder put(TE element){
        innerSet.add(element);
        return this;
    }

    public Set<TE> result(){
        return innerSet;
    }
}
