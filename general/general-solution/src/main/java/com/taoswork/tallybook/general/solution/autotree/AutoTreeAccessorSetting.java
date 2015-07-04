package com.taoswork.tallybook.general.solution.autotree;

/**
 * Created by Gao Yuan on 2015/7/3.
 */
public class AutoTreeAccessorSetting {
    boolean allowChild = true;
    boolean allowParent = false;
    boolean allowBranch = false;

    public boolean isAllowChild() {
        return allowChild;
    }

    public AutoTreeAccessorSetting setAllowChild(boolean allowChild) {
        this.allowChild = allowChild;
        return this;
    }

    public boolean isAllowParent() {
        return allowParent;
    }

    public AutoTreeAccessorSetting setAllowParent(boolean allowParent) {
        this.allowParent = allowParent;
        return this;
    }

    public boolean isAllowBranch() {
        return allowBranch;
    }

    public AutoTreeAccessorSetting setAllowBranch(boolean allowBranch) {
        this.allowBranch = allowBranch;
        if(allowBranch){
            allowAll();
        }
        return this;
    }

    public AutoTreeAccessorSetting allowAll(){
        allowChild = true;
        allowParent = true;
        allowBranch = true;
        return this;
    }

    public AutoTreeAccessorSetting denyAll(){
        allowChild = false;
        allowParent = false;
        allowBranch = false;
        return this;
    }
}
