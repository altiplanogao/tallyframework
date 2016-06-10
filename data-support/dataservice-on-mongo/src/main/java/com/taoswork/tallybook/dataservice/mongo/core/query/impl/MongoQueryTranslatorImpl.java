package com.taoswork.tallybook.dataservice.mongo.core.query.impl;

import com.taoswork.tallybook.datadomain.base.presentation.FieldType;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.PropertyFilterCriteria;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.PropertySortCriteria;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.SortDirection;
import com.taoswork.tallybook.dataservice.mongo.core.query.MongoQueryTranslator;
import com.taoswork.tallybook.dataservice.mongo.core.query.criteria.restriction.Restriction;
import com.taoswork.tallybook.dataservice.mongo.core.query.criteria.restriction.RestrictionFactory;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;
import com.taoswork.tallybook.descriptor.metadata.IFieldMeta;
import com.taoswork.tallybook.descriptor.metadata.classmetadata.ClassMetaUtils;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.basic.ForeignEntityFieldMeta;
import com.taoswork.tallybook.general.extension.collections.CollectionUtility;
import com.taoswork.tallybook.general.solution.proppath.PathBuilder;
import org.mongodb.morphia.AdvancedDatastore;
import org.mongodb.morphia.query.Criteria;
import org.mongodb.morphia.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by Gao Yuan on 2016/2/17.
 */
public class MongoQueryTranslatorImpl implements MongoQueryTranslator {
    @Override
    public <T> Query<T> constructListQuery(AdvancedDatastore datastore,
                                           String collection,
                                           Class<T> entityClz,
                                           IClassMeta classTreeMeta,
                                           CriteriaTransferObject cto) {
        return constructGeneralQuery(datastore, collection, entityClz, classTreeMeta, cto, false);
    }

    @Override
    public <T> Query<T> constructCountQuery(AdvancedDatastore datastore,
                                            String collection, Class<T> entityClz, IClassMeta classTreeMeta, CriteriaTransferObject cto) {
        return constructGeneralQuery(datastore, collection, entityClz, classTreeMeta, cto, true);
    }

    protected <T> Query<T> constructGeneralQuery(AdvancedDatastore datastore,
                                                 String collection,
                                                 Class<T> entityClz,
                                                 IClassMeta classTreeMeta,
                                                 CriteriaTransferObject cto, boolean isCount) {
        Query<T> query = datastore.createQuery(collection, entityClz);
        if (!isCount) {
            addPaging(query, cto);
        }

        String root = "";

        List<Criteria> criterias = new ArrayList<Criteria>();

        for (PropertyFilterCriteria pfc : cto.getFilterCriteriasCollection()) {
            String propertyName = pfc.getPropertyName();
            List<String> values = pfc.getFilterValues();
            if (!CollectionUtility.isEmpty(values)) {
                IFieldMeta fieldMeta = ClassMetaUtils.getRoutedFieldMeta(classTreeMeta, propertyName);
                if (fieldMeta == null) {
                    continue;
                }
                String explicitPath = PathBuilder.buildPath(root, propertyName);
                if (fieldMeta instanceof ForeignEntityFieldMeta) {
                    ForeignEntityFieldMeta foreignEntityFieldMeta = (ForeignEntityFieldMeta) fieldMeta;
                    String idField = foreignEntityFieldMeta.getIdField();
                    explicitPath = PathBuilder.buildPath(root, propertyName, idField);
                }
                FieldType fieldType = fieldMeta.getFieldType();
                Restriction restriction = RestrictionFactory.instance().getRestriction(fieldType, fieldMeta.getFieldClass());
                List<Object> convertedValues = restriction.convertValues(fieldMeta.getFieldClass(), values);

                Criteria criteria = restriction.getPredicateProvider().buildPredicate(query, explicitPath, convertedValues);
                if (null != criteria) {
                    criterias.add(criteria);
                }
            }
        }
        query.and(criterias.toArray(new Criteria[criterias.size()]));

        //order
        if (!isCount) {
            StringJoiner sorts = new StringJoiner(",");
            for (PropertySortCriteria psc : cto.getSortCriterias()) {
                String propertyName = psc.getPropertyName();
                SortDirection direction = psc.getSortDirection();
                if (null == direction) {
                    continue;
                } else {
                    IFieldMeta fieldMeta = classTreeMeta.getFieldMeta(propertyName);
                    String path = PathBuilder.buildPath(root, propertyName);

                    if (SortDirection.ASCENDING == direction) {
                        sorts.add(path);
                    } else {
                        sorts.add("-" + path);
                    }
                }
            }
            if (sorts.length() > 0) {
                query.order(sorts.toString());
            }
        }

        return query;
    }

    protected static void addPaging(Query response, CriteriaTransferObject query) {
        addPaging(response, query.getFirstResult(), query.getPageSize());
    }

    protected static void addPaging(Query query, long firstResult, int maxResults) {
        if (firstResult > 0) {
            query.offset((int) firstResult);
        }
        if (maxResults > 0) {
            query.limit(maxResults);
        }
    }


}
