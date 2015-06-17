package com.taoswork.tallybook.dynamic.dataservice.query.translator.impl;

import com.taoswork.tallybook.dynamic.datadomain.presentation.client.SupportedFieldType;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.ClassTreeMetadata;
import com.taoswork.tallybook.dynamic.dataservice.entity.metadata.FieldMetadata;
import com.taoswork.tallybook.dynamic.dataservice.query.criteria.restriction.Restriction;
import com.taoswork.tallybook.dynamic.dataservice.query.criteria.restriction.RestrictionFactory;
import com.taoswork.tallybook.dynamic.dataservice.query.criteria.util.FieldPathBuilder;
import com.taoswork.tallybook.dynamic.dataservice.query.translator.QueryTranslator;
import com.taoswork.tallybook.dynamic.dataservice.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dynamic.dataservice.query.dto.PropertyFilterCriteria;
import com.taoswork.tallybook.dynamic.dataservice.query.dto.PropertySortCriteria;
import com.taoswork.tallybook.dynamic.dataservice.query.dto.SortDirection;
import com.taoswork.tallybook.general.extension.collections.CollectionUtility;

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
            ClassTreeMetadata classTreeMetadata,
            CriteriaTransferObject cto){
        Integer firstResult = cto.getFirstResult();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = criteriaBuilder.createQuery(entityClz);
        Root<T> original = criteria.from(entityClz);

        criteria.select(original);

        List<Predicate> restrictions = new ArrayList<Predicate>();
        List<Order> sorts = new ArrayList<Order>();

        for(PropertyFilterCriteria pfc : cto.getFilterCriteriasCollection()){
            String propertyName = pfc.getPropertyName();
            List<String> values = pfc.getFilterValues();
            if(!CollectionUtility.isEmpty(values)){
                FieldPathBuilder fieldPathBuilder = new FieldPathBuilder();
                FieldMetadata fieldMetadata = classTreeMetadata.getFieldMetadata(propertyName);
                SupportedFieldType fieldType = fieldMetadata.getFieldType();
                Restriction restriction = RestrictionFactory.instance().getRestriction(fieldType);
                List<Object> convertedValues = restriction.convertValues(values);
                Predicate predicate = restriction.getPredicateProvider().buildPredicate(criteriaBuilder, fieldPathBuilder,
                        original, entityClz, propertyName, null, convertedValues);
                if(null != predicate){
                    restrictions.add(predicate);
                }
            }
        }

        for (PropertySortCriteria psc : cto.getSortCriterias()){
            String propertyName = psc.getPropertyName();
            SortDirection direction = psc.getSortDirection();
            if(null == direction){
                continue;
            } else {
                FieldMetadata fieldMetadata = classTreeMetadata.getFieldMetadata(propertyName);
                FieldPathBuilder fpb = new FieldPathBuilder();
                Path path = fpb.buildPath(original, propertyName);

                Expression exp = path;
                if(SortDirection.ASCENDING == direction) {
                    sorts.add(criteriaBuilder.asc(exp));
                }else {
                    sorts.add(criteriaBuilder.desc(exp));
                }
            }
        }

        criteria.where(restrictions.toArray(new Predicate[restrictions.size()]));
        criteria.orderBy(sorts);
        if(firstResult != null && sorts.isEmpty()){
//            sss
        }

        TypedQuery<T> typedQuery = entityManager.createQuery(criteria);

        addPaging(typedQuery, cto);
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
