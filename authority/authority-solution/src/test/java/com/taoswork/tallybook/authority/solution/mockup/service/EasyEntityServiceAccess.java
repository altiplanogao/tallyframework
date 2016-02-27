package com.taoswork.tallybook.authority.solution.mockup.service;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.dataservice.PersistableResult;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaQueryResult;
import com.taoswork.tallybook.dataservice.core.dao.query.dto.CriteriaTransferObject;
import com.taoswork.tallybook.dataservice.exception.ServiceException;
import com.taoswork.tallybook.dataservice.service.IEntityService;

import java.util.List;

/**
 * Created by Gao Yuan on 2016/2/24.
 */
public class EasyEntityServiceAccess {
    private final IEntityService entityService;

    public EasyEntityServiceAccess(IEntityService entityService) {
        this.entityService = entityService;
    }

    public <T extends Persistable> boolean create(Class<T> ceilingType, T entity){
        try {
            PersistableResult result = entityService.create(ceilingType, entity);
            if(result == null)
                return false;
            return null != result.getValue();
        } catch (ServiceException e) {
            e.printStackTrace();
            return false;
        } finally {
        }
    }

    public <T extends Persistable> T read(Class<T> ceilingType, Object key){
        try {
            PersistableResult<T> result = entityService.read(ceilingType, key);
            if(result == null)
                return null;
            return result.getValue();
        } catch (ServiceException e) {
            e.printStackTrace();
            return null;
        } finally {
        }
    }

    public <T extends Persistable> T update(Class<T> ceilingType, T entity){
        try {
            PersistableResult<T> result = entityService.update(ceilingType, entity);
            if(result == null)
                return null;
            return result.getValue();
        } catch (ServiceException e) {
            e.printStackTrace();
            return null;
        } finally {
        }
    }

    public <T extends Persistable> boolean delete(Class<T> ceilingType, T entity){
        try {
            boolean result = entityService.delete(ceilingType, entity);
            return result;
        } catch (ServiceException e) {
            e.printStackTrace();
            return false;
        } finally {
        }
    }

    public <T extends Persistable> T queryOne(Class<T> entityClz, CriteriaTransferObject query){
        List<T> r = query(entityClz, query);
        if(r != null && !r.isEmpty()){
            return r.get(0);
        }
        return null;
    }

    public <T extends Persistable> List<T> query(Class<T> entityClz, CriteriaTransferObject query){
        try {
            CriteriaQueryResult<T> result = entityService.query(entityClz, query);
            if(result != null){
                return result.getEntityCollection();
            }
            return null;
        } catch (ServiceException e) {
            e.printStackTrace();
            return null;
        }
    }
}
