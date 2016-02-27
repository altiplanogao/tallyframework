package com.taoswork.tallybook.dataservice.jpa.core.dao.query.translator;

import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Created by Gao Yuan on 2015/5/29.
 */
public interface Cto2QueryTranslator {
    <T> TypedQuery<T> constructListQuery(
            EntityManager entityManager,
            Class<T> entityClz,
            IClassMeta classTreeMeta,
            CriteriaTransferObject cto);

    <T> TypedQuery<Long> constructCountQuery(
            EntityManager entityManager,
            Class<T> entityClz,
            IClassMeta classTreeMeta,
            CriteriaTransferObject cto);

}
