package com.taoswork.tallybook.authority.solution.engine.filter.query;

import com.taoswork.tallybook.authority.core.IllegalCodePathException;
import com.taoswork.tallybook.authority.core.resource.IKProtection;
import com.taoswork.tallybook.authority.core.resource.IKProtectionCase;
import com.taoswork.tallybook.authority.core.resource.KAccessibleScope;
import com.taoswork.tallybook.authority.solution.engine.filter.IFilter;
import com.taoswork.tallybook.authority.solution.engine.filter.IMongoFilter;
import com.taoswork.tallybook.authority.solution.engine.resource.ResourceProtectionCase;
import org.mongodb.morphia.query.Criteria;
import org.mongodb.morphia.query.Query;

import java.util.*;

/**
 * Created by Gao Yuan on 2016/2/26.
 */
public abstract class MorphiaQInterrupter implements IQInterrupter {

    private static Criteria makeFitCriteria(IKProtectionCase _case, Query query) {
        if (_case instanceof ResourceProtectionCase) {
            ResourceProtectionCase rpc = (ResourceProtectionCase) _case;
            IFilter filter = rpc.getFilter();
            if(filter instanceof IMongoFilter) {
                IMongoFilter mongoFilter = (IMongoFilter) filter;
                if (mongoFilter != null) {
                    Criteria c = mongoFilter.makeMorphiaCriteria(query);
                    return c;
                }
            }
        }
        return null;
    }

    private static Criteria makeUnfitCriteria(IKProtectionCase _case, Query query) {
        if (_case instanceof ResourceProtectionCase) {
            ResourceProtectionCase rpc = (ResourceProtectionCase) _case;
            IFilter filter = rpc.getFilter();
            if(filter instanceof IMongoFilter){
                IMongoFilter mongoFilter =(IMongoFilter)filter;
                if (mongoFilter != null) {
                    Criteria c = mongoFilter.makeMorphiaUnmatchCriteria(query);
                    return c;
                }
            }
        }
        return null;
    }

    public static Criteria buildCriteria(Query emptyQuery, KAccessibleScope accessibleScope, IKProtection resourceProtection) {
        if (accessibleScope == null) {
            throw new IllegalCodePathException();
        }

        final Collection<IKProtectionCase> cases = resourceProtection.getCases();
        final Collection<String> passCasesCode = accessibleScope.passCases;
        final Collection<String> blockCasesCode = accessibleScope.blockCases;

//        final Set<IKProtectionCase> passCases = (passCasesCode != null ? new HashSet<IKProtectionCase>() : null);
//        final Set<IKProtectionCase> blockCases = (blockCasesCode != null ? new HashSet<IKProtectionCase>() : null);
        final Set<IKProtectionCase> passCases = (passCasesCode == null ? null :
                (passCasesCode.isEmpty() ? null : new HashSet<IKProtectionCase>()));
        final Set<IKProtectionCase> blockCases = (blockCasesCode == null ? null :
                (blockCasesCode.isEmpty() ? null : new HashSet<IKProtectionCase>()));

        final boolean hasPass = (passCases != null);
        final boolean hasBlock = (blockCases != null);

        for (IKProtectionCase _case : cases) {
            if (passCasesCode != null && passCasesCode.contains(_case.getCode())) {
                passCases.add(_case);
            } else if (blockCasesCode != null && blockCasesCode.contains(_case.getCode())) {
                blockCases.add(_case);
            }
        }

        if (accessibleScope.inUnionMode) {
            List<Criteria> criterias = new ArrayList<Criteria>();
            for (IKProtectionCase passCase : passCases) {
                Criteria c = makeFitCriteria(passCase, emptyQuery);
                if (c != null) {
                    criterias.add(c);
                }
            }
            if (!blockCases.isEmpty()) {
                List<Criteria> blockCriterias = new ArrayList<Criteria>();
                for (IKProtectionCase blockCase : blockCases) {
                    Criteria c = makeUnfitCriteria(blockCase, emptyQuery);
                    if (c != null) {
                        blockCriterias.add(c);
                    }
                }
                Criteria bc = emptyQuery.cloneQuery().and(blockCriterias.toArray(new Criteria[]{}));
                criterias.add(bc);
            }
            if (!criterias.isEmpty()) {
                //final
                return emptyQuery.or(criterias.toArray(new Criteria[criterias.size()]));
            }
            return null;
//            return query;
        } else {
            if (hasPass) {
                if (hasBlock) {
                    //[m,nm]
                    final Criteria mc, nmc;
                    {
                        List<Criteria> criterias = new ArrayList<Criteria>();
                        for (IKProtectionCase passCase : passCases) {
                            Criteria c = makeFitCriteria(passCase, emptyQuery);
                            if (c != null) {
                                criterias.add(c);
                            }
                        }
                        mc = emptyQuery.cloneQuery().or(criterias.toArray(new Criteria[]{}));
                    }
                    {
                        List<Criteria> blockCriterias = new ArrayList<Criteria>();
                        for (IKProtectionCase blockCase : blockCases) {
                            Criteria c = makeUnfitCriteria(blockCase, emptyQuery);
                            if (c != null) {
                                blockCriterias.add(c);
                            }
                        }
                        nmc = emptyQuery.cloneQuery().and(blockCriterias.toArray(new Criteria[]{}));
                    }
                    //final
                    return emptyQuery.and(mc, nmc);
                } else {
                    //[m,]
                    List<Criteria> criterias = new ArrayList<Criteria>();
                    for (IKProtectionCase passCase : passCases) {
                        Criteria c = makeFitCriteria(passCase, emptyQuery);
                        if (c != null) {
                            criterias.add(c);
                        }
                    }
                    //final
                    return emptyQuery.or(criterias.toArray(new Criteria[]{}));
                }
            } else {
                if (hasBlock) {
                    //[,nm]
                    List<Criteria> blockCriterias = new ArrayList<Criteria>();
                    for (IKProtectionCase blockCase : blockCases) {
                        Criteria c = makeUnfitCriteria(blockCase, emptyQuery);
                        if (c != null) {
                            blockCriterias.add(c);
                        }
                    }
                    //final
                    return emptyQuery.and(blockCriterias.toArray(new Criteria[]{}));
                } else {
                    //final
                    return null;
                }
            }
            //return query;
        }
    }
}