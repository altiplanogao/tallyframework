package com.taoswork.tallybook.testmaterial.mongo.domain.business.dataprotect;

import com.taoswork.tallybook.datadomain.base.entity.valuegate.BaseEntityGate;
import com.taoswork.tallybook.testmaterial.mongo.domain.business.ICompany;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Random;

/**
 * Created by Gao Yuan on 2015/10/4.
 */
public class CompanyGate extends BaseEntityGate<ICompany> {
    private Random random = new Random();

    @Override
    protected void doStore(ICompany entity, ICompany oldEntity) {
        if (oldEntity != null) {
            entity.setCreationDate(oldEntity.getCreationDate());
        }
        if (StringUtils.isEmpty(entity.getName())) {
            entity.setName("Company " + random.nextInt(100));
        }
        if (StringUtils.isEmpty(entity.getTaxCode())) {
            entity.setTaxCode("" + random.nextDouble());
        }
        if (null == entity.getCreationDate()) {
            entity.setCreationDate(new Date());
        }
    }

    @Override
    protected void doFetch(ICompany entity) {

    }
}
