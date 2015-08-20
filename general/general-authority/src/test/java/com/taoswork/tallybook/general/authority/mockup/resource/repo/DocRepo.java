package com.taoswork.tallybook.general.authority.mockup.resource.repo;

import com.taoswork.tallybook.general.authority.core.resource.AccessibleFitting;
import com.taoswork.tallybook.general.authority.core.resource.IResourceFilter;
import com.taoswork.tallybook.general.authority.core.resource.IResourceProtection;
import com.taoswork.tallybook.general.authority.mockup.resource.GuardedDocInstance;
import com.taoswork.tallybook.general.authority.mockup.resource.domain.GuardedDoc;

import java.util.*;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public class DocRepo {
    private List< GuardedDoc> docs = new ArrayList<GuardedDoc>();

    public void pushIn(GuardedDoc doc){
        docs.add(doc);
    }

    public List<GuardedDoc> query(AccessibleFitting accessibleFitting, IResourceProtection resourceProtection){
        List<GuardedDoc> result = new ArrayList<GuardedDoc>();
        if(accessibleFitting == null){
            return result;
        }
        Collection<String> passFiltersCode = accessibleFitting.passFilters;
        Collection<String> blockFiltersCode = accessibleFitting.blockFilters;

        Collection<IResourceFilter> filters = resourceProtection.getFilters();
        Set<IResourceFilter> passFilters = (passFiltersCode != null ? new HashSet<IResourceFilter>() : null);
        Set<IResourceFilter> blockFilters = (blockFiltersCode != null ? new HashSet<IResourceFilter>() : null);

        for(IResourceFilter filter : filters){
            if(passFiltersCode != null && passFiltersCode.contains(filter.getCode())){
                passFilters.add(filter);
            }else if (blockFiltersCode != null && blockFiltersCode.contains(filter.getCode())){
                blockFilters.add(filter);
            }
        }

        if(accessibleFitting.inAnyMode){
            for (GuardedDoc doc : docs){
                GuardedDocInstance docInstance = new GuardedDocInstance(doc);
                boolean fit = false;
                if(passFilters == null){
                    fit = true;
                } else {
                    for(IResourceFilter passFilter : passFilters){
                        if(passFilter.isMatch(docInstance)){
                            fit = true;
                            break;
                        }
                    }
                }
                if(!fit){
                    boolean blockFit = false;
                    if(blockFilters == null){
                        blockFit = false;
                    } else {
                        for (IResourceFilter blockFilter : blockFilters){
                            if(blockFilter.isMatch(docInstance)){
                                blockFit = true;
                                break;
                            }
                        }
                    }
                    fit = !blockFit;
                }
                if (fit){
                    result.add(doc);
                }
            }
        }else {
            for (GuardedDoc doc : docs) {
                GuardedDocInstance docInstance = new GuardedDocInstance(doc);
                boolean fit = false;
                if(passFilters == null){
                    fit = true;
                } else {
                    for (IResourceFilter passFilter : passFilters) {
                        if (passFilter.isMatch(docInstance)) {
                            fit = true;
                            break;
                        }
                    }
                }
                if(fit){
                    if(blockFilters == null){
                        fit = true;
                    } else {
                        for (IResourceFilter blockFilter : blockFilters) {
                            if (blockFilter.isMatch(docInstance)) {
                                fit = false;
                                break;
                            }
                        }
                    }
                }
                if (fit){
                    result.add(doc);
                }
            }
        }
        return result;
    }
}
