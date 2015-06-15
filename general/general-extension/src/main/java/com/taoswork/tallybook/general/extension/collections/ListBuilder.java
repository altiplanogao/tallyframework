package com.taoswork.tallybook.general.extension.collections;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/4/21.
 */
public class ListBuilder<TE> {
    protected List<TE> innerList;

    public ListBuilder(){
        innerList = new ArrayList<TE>();
    }

    public ListBuilder(List<TE> list){
        innerList = list;
    }

    public static <TTE> ListBuilder<TTE> instance(){
        ListBuilder<TTE> obj = new ListBuilder<TTE>();
        return obj;
    }

    public static <TTK> ListBuilder<TTK> instance(List<TTK> list) {
        ListBuilder<TTK> lb = new ListBuilder<TTK>(list);
        return lb;
    }

    public static <TTK> ListBuilder<TTK> instance(TTK element){
        ListBuilder<TTK> obj = new ListBuilder<TTK>();
        obj.put(element);
        return obj;
    }

    public ListBuilder put(TE element){
        innerList.add(element);
        return this;
    }

    public ListBuilder put(TE... elements){
        for(TE e : elements) {
            innerList.add(e);
        }
        return this;
    }

    public List<TE> result(){
        return innerList;
    }

    public ListBuilder clear(){
        innerList.clear();
        return this;
    }
}
