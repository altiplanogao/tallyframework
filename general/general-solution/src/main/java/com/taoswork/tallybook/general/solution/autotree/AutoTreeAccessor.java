package com.taoswork.tallybook.general.solution.autotree;

import com.taoswork.tallybook.general.solution.quickinterface.ICallback2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Gao Yuan on 2015/5/22.
 */
public class AutoTreeAccessor<D> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AutoTreeAccessor.class);

    boolean allowChild = true;
    boolean allowParent = false;
    boolean allowBranch = false;

    private AutoTreeGenealogyHelper<D> genealogyHelper = null;

    public AutoTreeAccessor(AutoTreeGenealogyHelper<D> genealogyHelper){
        this.genealogyHelper = genealogyHelper;
    }

    public AutoTreeGenealogyHelper<D> getGenealogyHelper() {
        return genealogyHelper;
    }

    public boolean isAllowChild() {
        return allowChild;
    }

    public AutoTreeAccessor setAllowChild(boolean allowChild) {
        this.allowChild = allowChild;
        return this;
    }

    public boolean isAllowParent() {
        return allowParent;
    }

    public AutoTreeAccessor setAllowParent(boolean allowParent) {
        this.allowParent = allowParent;
        return this;
    }

    public boolean isAllowBranch() {
        return allowBranch;
    }

    public AutoTreeAccessor setAllowBranch(boolean allowBranch) {
        this.allowBranch = allowBranch;
        if(allowBranch){
            allowAll();
        }
        return this;
    }

    public AutoTreeAccessor allowAll(){
        allowChild = true;
        allowParent = true;
        allowBranch = true;
        return this;
    }

    public AutoTreeAccessor denyAll(){
        allowChild = false;
        allowParent = false;
        allowBranch = false;
        return this;
    }

    public AutoTree<D> add(AutoTree<D> existingNode, D newNodeData){
        return existingNode.add(this, newNodeData);
    }

    public <T extends AutoTree<D>> T copy(final T src){
        final AutoTree<D> newRoot = genealogyHelper.createNode(src.data);
        src.traverse(true, new ICallback2<Void, D, AutoTree.TraverseControl, AutoTreeException>(){
            @Override
            public Void callback(D parameter, AutoTree.TraverseControl control) throws AutoTreeException {
                newRoot.add(AutoTreeAccessor.this, parameter);
                return null;
            }
        }, true);

        return (T)newRoot;
    }
}
