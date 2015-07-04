package com.taoswork.tallybook.general.solution.autotree;

import com.taoswork.tallybook.general.extension.collections.CollectionUtility;
import com.taoswork.tallybook.general.extension.utils.TPredicate;
import com.taoswork.tallybook.general.solution.quickinterface.ICallback;
import com.taoswork.tallybook.general.solution.quickinterface.ICallback2;
import com.taoswork.tallybook.general.solution.quickinterface.IChecker;
import com.taoswork.tallybook.general.solution.quickinterface.IToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/7/3.
 */
public class AutoTree <D> implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(AutoTree.class);

    protected final D data;
    protected AutoTree<D> parent;
    protected final List<AutoTree<D>> children = new ArrayList<AutoTree<D>>();

    public AutoTree(D data){
        this.data = data;
    }

    public D getData(){
        return data;
    }

    public AutoTree<D> getParent(){
        return parent;
    }

    public boolean isRoot(){
        return parent == null;
    }
    public <T extends AutoTree<D>> T getRoot(){
        AutoTree<D> node = this;
        while (node.parent != null){
            node = node.parent;
        }
        return (T)node;
    }

    public Collection<AutoTree<D>> getReadonlyChildren(){
        return Collections.unmodifiableCollection(this.children);
    }

    <N extends AutoTree<D>> AddResult<N> safeDirectAddChild(AutoTree<D> candidate, AutoTreeGenealogy<D> genealogy){
        final AutoTree<D> node = candidate;
        D expectedSameAsThis = genealogy.calcDirectSuper(node.data, this.data);
        if(genealogy.checkEqual(expectedSameAsThis, this.data)){
            AutoTree<D> shouldNoMatch = CollectionUtility.find(children, new TPredicate<AutoTree<D>>() {
                @Override
                public boolean evaluate(AutoTree<D> notNullObj) {
                    return notNullObj.data.equals(node.data);
                }
            });
            if(shouldNoMatch == null){
                node.parent = this;
                this.children.add(node);
                return new AddResult<N>(AddResult.Type.Success, (N)node);
            }else {
                return new AddResult<N>(AddResult.Type.Exist, (N)shouldNoMatch);
            }
        }
        return new AddResult<N>(AddResult.Type.Fail, null);
    }

    <N extends AutoTree<D>> AddResult<N> safeDirectAddParent(AutoTree<D> candidate, AutoTreeGenealogy<D> genealogy){
        final AutoTree<D> node = candidate;
        if(this.parent != null){
            if(genealogy.checkEqual(this.parent.data, candidate.data)){
                return new AddResult<N>(AddResult.Type.Exist, (N)this.parent);
            }else {
                return new AddResult<N>(AddResult.Type.Fail, null);
            }
        }
        D expected = genealogy.calcDirectSuper(this.data, node.data);
        if(genealogy.checkEqual(expected, node.data)){
            AddResult<N> tempResult = candidate.safeDirectAddChild(this, genealogy);
            if(tempResult.isOk()){
                return new AddResult<N>(tempResult.type, (N)candidate);
            }else {
                return tempResult;
            }
        }
        return new AddResult<N>(AddResult.Type.Fail, null);
    }

    public <N extends AutoTree<D>> N findChild(D data, AutoTreeGenealogy genealogy) {
        if (genealogy.checkEqual(this.data, data)) {
            return (N) this;
        }
        if (genealogy.isSuperOf(this.data, data)) {
            for (AutoTree<D> child : this.children) {
                N tt = child.findChild(data, genealogy);
                if (tt != null) {
                    return tt;
                }
            }
        }
        return null;
    }

    public <N extends AutoTree<D>> N find(D data, AutoTreeGenealogy genealogy, boolean searchSuper){
        if(genealogy.checkEqual(this.data, data)){
            return (N)this;
        }
        if(genealogy.isSuperOf(this.data, data)) {
            return findChild(data, genealogy);
        }
        if(searchSuper){
            return this.getRoot().findChild(data, genealogy);
        }else {
            return null;
        }
    }
    public String buildTreeString(boolean useIndent, String delimiter) {
        return this.buildTreeString(useIndent, delimiter, new IToString<D>() {
            @Override
            public String makeString(D data) {
                return data.toString();
            }
        });
    }

    public String buildTreeString(boolean useIndent, String delimiter, IToString<D> toString){
        StringBuilder sb = new StringBuilder();
        printTree(sb, useIndent, 0, delimiter, toString);
        return sb.toString();
    }

    private void printTree(StringBuilder sb, boolean useIndent, int indent, String delimiter, IToString<D> toString){
        sb.append("(" + indent + ")");
        if(useIndent) {
            for (int i = 0; i < indent; i++) {
                sb.append(".");
            }
        }
        sb.append(toString.makeString(data));
        sb.append(delimiter);
        if(null == children){
            return;
        }
        for (AutoTree<D> child : children){
            child.printTree(sb, useIndent, indent + 1, delimiter, toString);
        }
    }
    
    public static class TraverseControl{
        public boolean shouldBreak = false;
    }

    public void traverse(
            boolean rootToLeaves,
            ICallback<Void, D, AutoTreeException> elementCallback,
            boolean skipRoot){
        this.traverse(rootToLeaves, elementCallback, new IChecker<Integer>() {
            @Override
            public boolean check(Integer parameter) {
                return parameter.intValue() > 0;
            }
        });
    }

    public void traverse(
            boolean rootToLeaves,
            ICallback<Void, D, AutoTreeException> elementCallback,
            IChecker<Integer> canCallChecker){
        this.traverse(rootToLeaves, elementCallback, canCallChecker, 0);
    }

    private void traverse(
            boolean rootToLeaves,
            ICallback<Void, D, AutoTreeException> elementCallback,
            IChecker<Integer> canCallChecker,
            int depth) throws AutoTreeException{
        if(rootToLeaves){
            if(canCallChecker.check(depth)){
                elementCallback.callback(data);
            }
        }
        if (children != null && children.size() > 0){
            for (AutoTree<D> treeNode : children){
                treeNode.traverse(rootToLeaves, elementCallback, canCallChecker, depth + 1);
            }
        }
        if(!rootToLeaves) {
            if (canCallChecker.check(depth)) {
                elementCallback.callback(data);
            }
        }
    }

    public void traverse(
            boolean rootToLeaves,
            ICallback2<Void, D, TraverseControl, AutoTreeException> elementCallback,
            final boolean skipRoot){
        this.traverse(rootToLeaves, elementCallback, new IChecker<Integer>() {
            @Override
            public boolean check(Integer parameter) {
                if(skipRoot){
                    return parameter.intValue() > 0;
                }else {
                    return true;
                }
            }
        });
    }

    public void traverse(
            boolean rootToLeaves,
            ICallback2<Void, D, TraverseControl, AutoTreeException> elementCallback,
            IChecker<Integer> canCallChecker){
        this.traverse(rootToLeaves, elementCallback, canCallChecker, new TraverseControl(), 0);
    }

    private void traverse(
            boolean rootToLeaves,
            ICallback2<Void, D, TraverseControl, AutoTreeException> elementCallback,
            IChecker<Integer> canCallChecker,
            TraverseControl traverseControl,
            int depth) throws AutoTreeException{
        if(rootToLeaves){
            if(canCallChecker.check(depth)){
                elementCallback.callback(data, traverseControl);
                if(traverseControl.shouldBreak){
                    return;
                }
            }
        }
        if (children != null && children.size() > 0){
            for (AutoTree<D> treeNode : children){
                treeNode.traverse(rootToLeaves, elementCallback, canCallChecker, traverseControl, depth + 1);
                if(traverseControl.shouldBreak){
                    return;
                }
            }
        }
        if(!rootToLeaves) {
            if (canCallChecker.check(depth)) {
                elementCallback.callback(data, traverseControl);
                if(traverseControl.shouldBreak){
                    return;
                }
            }
        }
    }
}
