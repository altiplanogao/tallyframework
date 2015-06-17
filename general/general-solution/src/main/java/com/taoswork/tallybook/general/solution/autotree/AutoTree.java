package com.taoswork.tallybook.general.solution.autotree;

import com.taoswork.tallybook.general.extension.collections.CollectionUtility;
import com.taoswork.tallybook.general.extension.utils.TPredicate;
import com.taoswork.tallybook.general.solution.quickinterface.ICallback;
import com.taoswork.tallybook.general.solution.quickinterface.ICallback2;
import com.taoswork.tallybook.general.solution.quickinterface.IChecker;
import com.taoswork.tallybook.general.solution.quickinterface.IToString;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/5/22.
 */
public class AutoTree<D> implements Serializable{
    protected final D data;
    protected AutoTree<D> parent;
    protected final List<AutoTree<D>> children = new ArrayList<AutoTree<D>>();

    public AutoTree(D data){
        this.data = data;
    }

    public D getData(){
        return data;
    }

    void add(AutoTreeAccessor accessor, D... datas) {
        for(D d : datas){
            this.add(accessor, d);
        }
    }

    AutoTree<D> add( AutoTreeAccessor<D> accessor, D a){
        AutoTreeGenealogyHelper<D> genealogyHelper = accessor.getGenealogyHelper();
        if(genealogyHelper.isAncestorOf(data, a)){
            //data is ancestor of a
            if(accessor.allowChild){
                List<D> path = genealogyHelper.ancestor2Descendant(data, a);
                AutoTree<D> currenNode = this;
                for (D d : path){
                    D expectedP = genealogyHelper.calcDataParent(d, currenNode.data);
                    if(expectedP.equals(currenNode.data)){
                        currenNode = currenNode.safeAddChild(d, genealogyHelper);
                    }
                }
                return currenNode;
            }
        } else if(genealogyHelper.isAncestorOf(a, data)) {
            if(accessor.allowParent) {
                D p = genealogyHelper.calcDataParent(data, a);
                if (p != null) {
                    AutoTree<D> pnode = this.safeAddParent(p, genealogyHelper);
                    return pnode;
                }
            }
        } else if(accessor.allowBranch){
            //a is ancestor of data
            D p = genealogyHelper.calcDataParentRegardBranchNode(data, a);
            AutoTree<D> pnode = this.add(accessor, p);
            if(pnode != null){
                return pnode.add(accessor, a);
            }
        }
        return null;
    }

    AutoTree<D> findChild(D a, AutoTreeGenealogyHelper<D> genealogyHelper){
        if(genealogyHelper.isAncestorOf(data, a)){
            for(AutoTree<D> child : children){
                if(genealogyHelper.checkEqual(child.data, a)){
                    return child;
                }else {
                    return child.findChild(a, genealogyHelper);
                }
            }
            return null;
        } else {
            return null;
        }
    }

    private AutoTree<D> safeAddChild(AutoTree<D> aNode, AutoTreeGenealogyHelper<D> genealogyHelper){
        final D a = aNode.data;
        D p = genealogyHelper.calcDataParent(a, this.data);
        if(p.equals(this.data)){
            AutoTree<D> node = CollectionUtility.find(children, new TPredicate<AutoTree<D>>() {
                @Override
                public boolean evaluate(AutoTree<D> notNullObj) {
                    return notNullObj.data.equals(a);
                }
            });
            if(node == null){
                node = aNode;
                node.parent = this;
                children.add(node);
            }
            return node;
        }
        return null;
    }

    private AutoTree<D> safeAddChild(final D a, AutoTreeGenealogyHelper<D> genealogyHelper){
        return safeAddChild(genealogyHelper.createNode(a), genealogyHelper);
    }

    private AutoTree<D> safeAddParent(final D parentData, AutoTreeGenealogyHelper<D> genealogyHelper) {
        //
        if(this.parent != null){
            return parent;
        }
        D p = genealogyHelper.calcDataParent(this.data, parentData);
        if(parentData.equals(p)){
            AutoTree<D> parentNode = genealogyHelper.createNode(parentData);
            parentNode.safeAddChild(this, genealogyHelper);
            return parentNode;
        }
        return null;
    }

    public AutoTree<D> getParent(){
        return parent;
    }

    public <T extends AutoTree<D>> T getRoot(){
        AutoTree<D> node = this;
        while (node.parent != null){
            node = node.parent;
        }
        return (T)node;
    }

    public List<AutoTree<D>> getReadonlyChildren(){
        return Collections.unmodifiableList(this.children);
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

    public List<D> lineUp(){
        final List<D> result = new ArrayList<D>();
        traverse(true, new ICallback<Void, D, AutoTreeException>() {
            @Override
            public Void callback(D parameter) throws AutoTreeException {
                result.add(parameter);
                return null;
            }
        }, false);

        return Collections.unmodifiableList(result);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AutoTree<?> autoTree = (AutoTree<?>) o;

        List<?> leftLine = this.lineUp();
        List<?> rightLine = autoTree.lineUp();

        if (leftLine.size() == rightLine.size()) {
            int count = leftLine.size();
            for (int i = 0; i < count; ++i) {
                Object a = leftLine.get(i);
                Object b = rightLine.get(i);
                if (a == null) {
                    if (b != null) {
                        return false;
                    } else {
                        continue;
                    }
                } else {
                    if (!a.equals(b)) {
                        return false;
                    } else {
                        continue;
                    }
                }
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        List<?> leftLine = this.lineUp();
        int hashCode = 0;
        int i = 1;
        for (Object node : leftLine){
            if(i > 16){
                i = 1;
            }
            int hash = node.hashCode() << i;
            hashCode ^= hash;
            i ++;
        }

        return hashCode;
    }
}
