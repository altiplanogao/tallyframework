package com.taoswork.tallybook.dynamic.dataservice.core.dao.query.translator.impl;

import com.taoswork.tallybook.dynamic.datameta.metadata.IClassMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.IFieldMetadata;
import com.taoswork.tallybook.dynamic.datameta.metadata.classmetadata.ClassMetadataUtils;
import com.taoswork.tallybook.dynamic.datameta.metadata.fieldmetadata.typed.ForeignEntityFieldMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.dao.query.criteria.restriction.Restriction;
import com.taoswork.tallybook.dynamic.dataservice.core.dao.query.criteria.restriction.RestrictionFactory;
import com.taoswork.tallybook.dynamic.dataservice.core.dao.query.criteria.util.FieldPathBuilder;
import com.taoswork.tallybook.dynamic.dataservice.core.dao.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dynamic.dataservice.core.dao.query.dto.PropertyFilterCriteria;
import com.taoswork.tallybook.dynamic.dataservice.core.dao.query.dto.PropertySortCriteria;
import com.taoswork.tallybook.dynamic.dataservice.core.dao.query.dto.SortDirection;
import com.taoswork.tallybook.dynamic.dataservice.core.dao.query.translator.Cto2QueryTranslator;
import com.taoswork.tallybook.general.datadomain.support.presentation.client.FieldType;
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
public class Cto2QueryTranslatorImpl implements Cto2QueryTranslator {
    @Override
    public <T> TypedQuery<T> constructListQuery(
            EntityManager entityManager,
            Class<T> entityClz,
            IClassMetadata classTreeMetadata,
            CriteriaTransferObject cto) {
        return constructGeneralQuery(entityManager,
                entityClz,
                classTreeMetadata,
                cto, false);
    }

    @Override
    public <T> TypedQuery<Long> constructCountQuery(
            EntityManager entityManager,
            Class<T> entityClz,
            IClassMetadata classTreeMetadata,
            CriteriaTransferObject cto) {
        return constructGeneralQuery(entityManager,
                entityClz,
                classTreeMetadata,
                cto, true);
    }

    private <T> TypedQuery constructGeneralQuery(
            EntityManager entityManager,
            Class<T> entityClz,
            IClassMetadata classTreeMetadata,
            CriteriaTransferObject cto, boolean isCount) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = criteriaBuilder.createQuery(entityClz);
        Root<T> original = criteria.from(entityClz);

        if (isCount) {
            CriteriaQuery rawCriteria = criteria;
            rawCriteria.select(criteriaBuilder.count(original));
        } else {
            criteria.select(original);
        }

        List<Predicate> restrictions = new ArrayList<Predicate>();
        List<Order> sorts = new ArrayList<Order>();

        for (PropertyFilterCriteria pfc : cto.getFilterCriteriasCollection()) {
            String propertyName = pfc.getPropertyName();
            List<String> values = pfc.getFilterValues();
            if (!CollectionUtility.isEmpty(values)) {
                FieldPathBuilder fieldPathBuilder = new FieldPathBuilder();
                //IFieldMetadata fieldMetadata = classTreeMetadata.getFieldMetadata(propertyName);
                IFieldMetadata fieldMetadata = ClassMetadataUtils.getRoutedFieldMetadata(classTreeMetadata, propertyName);
                if(fieldMetadata == null){
                    continue;
                }
                Path explicitPath = null;
                if(fieldMetadata instanceof ForeignEntityFieldMetadata){
                    ForeignEntityFieldMetadata foreignEntityFieldMetadata = (ForeignEntityFieldMetadata) fieldMetadata;
                    String idField = foreignEntityFieldMetadata.getIdField();
                    explicitPath = fieldPathBuilder.buildPathBySegments(original, propertyName, idField);
                    //path = fieldPathBuilder.buildPath(original, propertyName);//.getPath(root, fullPropertyName, builder);
                }
                FieldType fieldType = fieldMetadata.getFieldType();
                Restriction restriction = RestrictionFactory.instance().getRestriction(fieldType, fieldMetadata.getFieldClass());
                List<Object> convertedValues = restriction.convertValues(fieldMetadata.getFieldClass(), values);
                Predicate predicate = restriction.getPredicateProvider().buildPredicate(
                    criteriaBuilder, fieldPathBuilder,
                    original,
                    entityClz, propertyName,
                    explicitPath, convertedValues);
                if (null != predicate) {
                    restrictions.add(predicate);
                }
            }
        }
        criteria.where(restrictions.toArray(new Predicate[restrictions.size()]));

        if(!isCount) {
            for (PropertySortCriteria psc : cto.getSortCriterias()) {
                String propertyName = psc.getPropertyName();
                SortDirection direction = psc.getSortDirection();
                if (null == direction) {
                    continue;
                } else {
                    IFieldMetadata fieldMetadata = classTreeMetadata.getFieldMetadata(propertyName);
                    FieldPathBuilder fpb = new FieldPathBuilder();
                    Path path = fpb.buildPath(original, propertyName);

                    Expression exp = path;
                    if (SortDirection.ASCENDING == direction) {
                        sorts.add(criteriaBuilder.asc(exp));
                    } else {
                        sorts.add(criteriaBuilder.desc(exp));
                    }
                }
            }
            criteria.orderBy(sorts);
        }

        TypedQuery typedQuery = entityManager.createQuery(criteria);
        if(!isCount) {
            addPaging(typedQuery, cto);
        }
        return typedQuery;
    }

    protected static void addPaging(Query response, CriteriaTransferObject query) {
        addPaging(response, query.getFirstResult(), query.getPageSize());
    }

    protected static void addPaging(Query query, long firstResult, int maxResults) {
        if (firstResult > 0) {
            query.setFirstResult((int)firstResult);
        }
        if (maxResults > 0) {
            query.setMaxResults(maxResults);
        }
    }
}
