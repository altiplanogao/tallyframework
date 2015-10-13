package com.taoswork.tallybook.dynamic.dataservice.core.dao.query.translator;

import com.taoswork.tallybook.dynamic.datameta.metadata.ClassTreeMetadata;
import com.taoswork.tallybook.dynamic.dataservice.core.dao.query.dto.CriteriaTransferObject;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Created by Gao Yuan on 2015/5/29.
 */
public interface Cto2QueryTranslator {
    <T> TypedQuery<T> constructListQuery(
            EntityManager entityManager,
            Class<T> entityClz,
            ClassTreeMetadata classTreeMetadata,
            CriteriaTransferObject cto);

    <T> TypedQuery<Long> constructCountQuery(
            EntityManager entityManager,
            Class<T> entityClz,
            ClassTreeMetadata classTreeMetadata,
            CriteriaTransferObject cto);

}
