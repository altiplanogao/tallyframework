package com.taoswork.tallybook.general.solution.autotree.morkdata;

import com.taoswork.tallybook.general.solution.autotree.AutoTree;
import com.taoswork.tallybook.general.solution.autotree.AutoTreeGenealogyHelper;

/**
 * Created by Gao Yuan on 2015/5/22.
 */
public class StringGenealogyHelper extends AutoTreeGenealogyHelper<String> {

    @Override
    public AutoTree<String> createNode(String code) {
        return new StringTree(code);
    }

    @Override
    public String calcDataParent(String a, String referenceAncestor) {
        if(a != null && a.indexOf(referenceAncestor) == 0 && a.length() > referenceAncestor.length()){
            return new String(a.substring(0, a.length() - 1));
        }
        return null;
    }

    @Override
    public String calcDataParentRegardBranchNode(String a, String referenceBranchNode) {
        if(a != null && a.indexOf(referenceBranchNode) == 0 && a.length() > referenceBranchNode.length()){
            return null;
        }
        return new String(a.substring(0, a.length() - 1));
    }

    @Override
    public boolean isAncestorOf(String a, String b) {
        if(b.indexOf(a) == 0){
            if(b.length() > a.length()){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkEqual(String a, String b) {
        return a.equals(b);
    }
}
