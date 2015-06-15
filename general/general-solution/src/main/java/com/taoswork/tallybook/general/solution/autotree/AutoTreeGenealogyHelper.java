package com.taoswork.tallybook.general.solution.autotree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/5/22.
 */
public abstract class AutoTreeGenealogyHelper<D> {

    public abstract AutoTree<D> createNode(D d);

    public abstract D calcDataParent(D a, D referenceAncestor);

    public abstract D calcDataParentRegardBranchNode(D a, D referenceBranchNode);

    public abstract boolean isAncestorOf(D a, D b);

    public abstract boolean checkEqual(D a, D b);

    //returns a list: (a, b]
    public List<D> ancestor2Descendant(D a, D b){
        List<D> list = new ArrayList<D>();
        if(a.equals(b)){
            return list;
        }
        D d = b;
        do{
            list.add(0, d);
            d = calcDataParent(d, a);
            if(null == d){
                break;
            }
        }while (!d.equals(a));
        return list;
    }
}
