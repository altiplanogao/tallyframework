package com.taoswork.tallybook.dataservice.jpa.core.dao.query.translator.impl;

import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.PropertyFilterCriteria;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.PropertySortCriteria;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.SortDirection;
import com.taoswork.tallybook.dataservice.jpa.core.dao.query.criteria.restriction.Restriction;
import com.taoswork.tallybook.dataservice.jpa.core.dao.query.criteria.restriction.RestrictionFactory;
import com.taoswork.tallybook.dataservice.jpa.core.dao.query.criteria.util.FieldPathBuilder;
import com.taoswork.tallybook.dataservice.jpa.core.dao.query.translator.Cto2QueryTranslator;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.classmetadata.ClassMetaUtils;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.basic.ForeignEntityFieldMeta;
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
            IClassMeta classTreeMeta,
            CriteriaTransferObject cto) {
        return constructGeneralQuery(entityManager,
                entityClz,
                classTreeMeta,
                cto, false);
    }

    @Override
    public <T> TypedQuery<Long> constructCountQuery(
            EntityManager entityManager,
            Class<T> entityClz,
            IClassMeta classTreeMeta,
            CriteriaTransferObject cto) {
        return constructGeneralQuery(entityManager,
                entityClz,
                classTreeMeta,
                cto, true);
    }

    private <T> TypedQuery constructGeneralQuery(
            EntityManager entityManager,
            Class<T> entityClz,
            IClassMeta classTreeMeta,
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

        for (PropertyFilterCriteria pfc : cto.getFilterCriteriasCollection()) {
            String propertyName = pfc.getPropertyName();
            List<String> values = pfc.getFilterValues();
            if (!CollectionUtility.isEmpty(values)) {
                FieldPathBuilder fieldPathBuilder = new FieldPathBuilder();
                //IFieldMeta fieldMeta = classTreeMeta.getFieldMeta(propertyName);
                IFieldMeta fieldMeta = ClassMetaUtils.getRoutedFieldMeta(classTreeMeta, propertyName);
                if (fieldMeta == null) {
                    continue;
                }
                Path explicitPath = null;
                if (fieldMeta instanceof ForeignEntityFieldMeta) {
                    ForeignEntityFieldMeta foreignEntityFieldMeta = (ForeignEntityFieldMeta) fieldMeta;
                    String idField = foreignEntityFieldMeta.getIdField();
                    explicitPath = fieldPathBuilder.buildPathBySegments(original, propertyName, idField);
                    //path = fieldPathBuilder.buildPath(original, propertyName);//.getPath(root, fullPropertyName, builder);
                }
                if (explicitPath == null) {
                    explicitPath = fieldPathBuilder.buildPath(original, propertyName);
                }
                FieldType fieldType = fieldMeta.getFieldType();
                Restriction restriction = RestrictionFactory.instance().getRestriction(fieldType, fieldMeta.getFieldClass());
                List<Object> convertedValues = restriction.convertValues(fieldMeta.getFieldClass(), values);
                Predicate predicate = restriction.getPredicateProvider().buildPredicate(
                        criteriaBuilder,
                        entityClz, propertyName,
                        explicitPath, convertedValues);
                if (null != predicate) {
                    restrictions.add(predicate);
                }
            }
        }
        criteria.where(restrictions.toArray(new Predicate[restrictions.size()]));

        if (!isCount) {
            List<Order> sorts = new ArrayList<Order>();
            for (PropertySortCriteria psc : cto.getSortCriterias()) {
                String propertyName = psc.getPropertyName();
                SortDirection direction = psc.getSortDirection();
                if (null == direction) {
                    continue;
                } else {
                    IFieldMeta fieldMeta = classTreeMeta.getFieldMeta(propertyName);
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
        if (!isCount) {
            addPaging(typedQuery, cto);
        }
        return typedQuery;
    }

    protected static void addPaging(Query response, CriteriaTransferObject query) {
        addPaging(response, query.getFirstResult(), query.getPageSize());
    }

    protected static void addPaging(Query query, long firstResult, int maxResults) {
        if (firstResult > 0) {
            query.setFirstResult((int) firstResult);
        }
        if (maxResults > 0) {
            query.setMaxResults(maxResults);
        }
    }
}
