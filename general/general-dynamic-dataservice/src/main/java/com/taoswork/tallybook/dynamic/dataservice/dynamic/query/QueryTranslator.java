package com.taoswork.tallybook.dynamic.dataservice.dynamic.query;

import com.taoswork.tallybook.dynamic.dataservice.dynamic.query.dto.CriteriaTransferObject;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Created by Gao Yuan on 2015/5/29.
 */
public interface QueryTranslator {
    <T> TypedQuery<T> constructListQuery(
            EntityManager entityManager,
            Class<T> entityClz, CriteriaTransferObject criteriaTransferObject);

}
