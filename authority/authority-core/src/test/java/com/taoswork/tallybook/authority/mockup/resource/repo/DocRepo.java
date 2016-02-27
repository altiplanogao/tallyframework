package com.taoswork.tallybook.authority.mockup.resource.repo;

import com.taoswork.tallybook.authority.core.resource.IKProtection;
import com.taoswork.tallybook.authority.core.resource.IKProtectionCase;
import com.taoswork.tallybook.authority.core.resource.KAccessibleScope;
import com.taoswork.tallybook.authority.mockup.resource.GuardedDocInstance;
import com.taoswork.tallybook.authority.mockup.resource.domain.GuardedDoc;

import java.util.*;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public class DocRepo implements ICoreAccessSensitiveRepo<GuardedDoc> {
    private List<GuardedDoc> docs = new ArrayList<GuardedDoc>();

    public void pushIn(GuardedDoc doc) {
        docs.add(doc);
    }

    @Override
    public List<GuardedDoc> query(KAccessibleScope accessibleScope, IKProtection resourceProtection) {
        List<GuardedDoc> result = new ArrayList<GuardedDoc>();
        if (accessibleScope == null) {
            return result;
        }
        Collection<String> passCasesCode = accessibleScope.passCases;
        Collection<String> blockCasesCode = accessibleScope.blockCases;

        Collection<IKProtectionCase> cases = resourceProtection.getCases();
        Set<IKProtectionCase> passCases = (passCasesCode != null ? new HashSet<IKProtectionCase>() : null);
        Set<IKProtectionCase> blockCases = (blockCasesCode != null ? new HashSet<IKProtectionCase>() : null);

        for (IKProtectionCase _case : cases) {
            if (passCasesCode != null && passCasesCode.contains(_case.getCode())) {
                passCases.add(_case);
            } else if (blockCasesCode != null && blockCasesCode.contains(_case.getCode())) {
                blockCases.add(_case);
            }
        }

        if (accessibleScope.inUnionMode) {
            for (GuardedDoc doc : docs) {
                GuardedDocInstance docInstance = new GuardedDocInstance(doc);
                boolean fit = false;
                if (passCases == null) {
                    fit = true;
                } else {
                    for (IKProtectionCase passCase : passCases) {
                        if (passCase.isMatch(docInstance)) {
                            fit = true;
                            break;
                        }
                    }
                }
                if (!fit) {
                    boolean blockFit = false;
                    if (blockCases == null) {
                        blockFit = false;
                    } else {
                        for (IKProtectionCase blockCase : blockCases) {
                            if (blockCase.isMatch(docInstance)) {
                                blockFit = true;
                                break;
                            }
                        }
                    }
                    fit = !blockFit;
                }
                if (fit) {
                    result.add(doc);
                }
            }
        } else {
            for (GuardedDoc doc : docs) {
                GuardedDocInstance docInstance = new GuardedDocInstance(doc);
                boolean fit = false;
                if (passCases == null) {
                    fit = true;
                } else {
                    for (IKProtectionCase passCase : passCases) {
                        if (passCase.isMatch(docInstance)) {
                            fit = true;
                            break;
                        }
                    }
                }
                if (fit) {
                    if (blockCases == null) {
                        fit = true;
                    } else {
                        for (IKProtectionCase blockCase : blockCases) {
                            if (blockCase.isMatch(docInstance)) {
                                fit = false;
                                break;
                            }
                        }
                    }
                }
                if (fit) {
                    result.add(doc);
                }
            }
        }
        return result;
    }
}
