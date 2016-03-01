package com.taoswork.tallybook.authority.core.resource.impl;

import com.taoswork.tallybook.authority.core.resource.IKProtection;
import com.taoswork.tallybook.authority.core.resource.IKProtectionCase;
import com.taoswork.tallybook.authority.core.resource.IKProtectionCenter;
import com.taoswork.tallybook.authority.core.resource.KCaseFitting;
import com.taoswork.tallybook.authority.core.resource.link.IKProtectionMapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Gao Yuan on 2016/2/8.
 */
public abstract class BaseKProtectionCenter
        implements IKProtectionCenter {
//
//    private final IKProtectionMapping mapping;
//
//    public BaseKProtectionCenter(IKProtectionMapping mapping) {
//        this.mapping = mapping;
//    }

    protected abstract IKProtectionMapping getMapping();

    @Override
    public IKProtection getProtection(String resource) {
        resource = getMapping().correctResource(resource);
        return getDirectResourceProtection(resource);
    }

    protected abstract IKProtection getDirectResourceProtection(String resource);

    @Override
    public KCaseFitting getFitting(String resource,
                                    Object instance) {
        IKProtection protection = getProtection(resource);

        List<String> matchingCasesCode = new ArrayList<String>();
        List<String> unmatchedCasesCode = new ArrayList<String>();
        for (IKProtectionCase _case : protection.getCases()) {
            if (_case.isMatch(instance)) {
                matchingCasesCode.add(_case.getCode());
            } else {
                unmatchedCasesCode.add(_case.getCode());
            }
        }
        return new KCaseFitting(
                resource,
                protection.isMasterControlled(),
                protection.getProtectionMode(),
                matchingCasesCode, unmatchedCasesCode);
    }

    @Override
    public KCaseFitting getFitting(boolean matchingPreferred,
                                    String resource,
                                    Object... instances) {
        if (instances.length == 0) {
            throw new IllegalArgumentException();
        }
        IKProtection protection = getProtection(resource);

        Set<String> matchingCasesCode = new HashSet<String>();
        Set<String> unmatchedCasesCode = new HashSet<String>();
        for (IKProtectionCase _case : protection.getCases()) {
            boolean matching = false;
            boolean unmatched = false;
            for (Object ins : instances) {
                if (_case.isMatch(ins)) {
                    matching = true;
                } else {
                    unmatched = true;
                }
            }
            if ((matchingPreferred && matching) || (!(matchingPreferred || unmatched))) {
                matchingCasesCode.add(_case.getCode());
            } else {
                unmatchedCasesCode.add(_case.getCode());
            }
//            if(matchingPreferred){
//                if(matching){
//                    matchingFilter.add(filter.getCode());
//                }else{
//                    unmatchedFilter.add(filter.getCode());
//                }
//            }else { // !matchingPreferred
//                if(unmatched){
//                    unmatchedFilter.add(filter.getCode());
//                }else {
//                    matchingFilter.add(filter.getCode());
//                }
//            }

//            Tip:
//            ((matchingPreferred && matching) || ((!matchingPreferred) && (!unmatched)))
//                ===
//            ((matchingPreferred && matching) || (!(matchingPreferred || unmatched)))
        }
        return new KCaseFitting(
                resource,
                protection.isMasterControlled(),
                protection.getProtectionMode(),
                matchingCasesCode, unmatchedCasesCode);
    }

}
