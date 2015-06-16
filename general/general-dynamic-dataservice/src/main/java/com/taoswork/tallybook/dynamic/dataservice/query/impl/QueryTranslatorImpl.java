package com.taoswork.tallybook.dynamic.dataservice.query.impl;

import com.taoswork.tallybook.dynamic.dataservice.query.QueryTranslator;
import com.taoswork.tallybook.dynamic.dataservice.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dynamic.dataservice.query.dto.PropertyFilterCriteria;
import com.taoswork.tallybook.dynamic.dataservice.query.dto.PropertySortCriteria;
import com.taoswork.tallybook.dynamic.dataservice.query.dto.SortDirection;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/5/29.
 */
public class QueryTranslatorImpl implements QueryTranslator {
    @Override
    public <T> TypedQuery<T> constructListQuery(
            EntityManager entityManager,
            Class<T> entityClz,
            CriteriaTransferObject criteriaTransferObject){
        Integer firstResult = criteriaTransferObject.getFirstResult();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = criteriaBuilder.createQuery(entityClz);
        Root<T> original = criteria.from(entityClz);

        criteria.select(original);

        for(PropertyFilterCriteria pfc : criteriaTransferObject.getFilterCriteriasCollection()){
            String propertyName = pfc.getPropertyName();
            List<String> values = pfc.getFilterValues();

        }

        for (PropertySortCriteria psc : criteriaTransferObject.getSortCriterias()){
            String propertyName = psc.getPropertyName();
            SortDirection direction = psc.getSortDirection();
            if(null == direction){
                continue;
            } else {

            }
        }

        List<Predicate> restrictions = new ArrayList<Predicate>();
        List<Order> sorts = new ArrayList<Order>();

        criteria.where(restrictions.toArray(new Predicate[restrictions.size()]));
        criteria.orderBy(sorts);
        if(firstResult != null && sorts.isEmpty()){
//            sss
        }

        TypedQuery<T> typedQuery = entityManager.createQuery(criteria);

        addPaging(typedQuery, criteriaTransferObject);
        return typedQuery;
    }

    protected static void addPaging(Query response, CriteriaTransferObject query) {
        addPaging(response, query.getFirstResult(), query.getMaxResultCount());
    }

    protected static void addPaging(Query response, Integer firstResult, Integer maxResults) {
        if (firstResult != null) {
            response.setFirstResult(firstResult);
        }
        if (maxResults != null) {
            response.setMaxResults(maxResults);
        }
    }
}
