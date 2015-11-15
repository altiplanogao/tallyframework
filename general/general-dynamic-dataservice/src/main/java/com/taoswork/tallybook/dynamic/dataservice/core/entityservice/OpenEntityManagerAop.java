package com.taoswork.tallybook.dynamic.dataservice.core.entityservice;

import com.taoswork.tallybook.dynamic.dataservice.IDataService;
import com.taoswork.tallybook.dynamic.dataservice.IDataServiceDefinition;
import com.taoswork.tallybook.dynamic.dataservice.IDataServiceDelegate;
import com.taoswork.tallybook.dynamic.dataservice.core.exception.ServiceException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * By referrence of OpenEntityManagerInViewFilter
 */
@Aspect
public class OpenEntityManagerAop implements ApplicationContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(OpenEntityManagerAop.class);

    private IDataService dataService;

    private volatile EntityManagerFactory entityManagerFactory;

    public OpenEntityManagerAop(){
    }

    @Pointcut("execution(* com.taoswork.tallybook.dynamic.dataservice.core.entityservice.DynamicEntityPersistenceService.*(..))")
    public void persistenceMethod() {}

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(applicationContext instanceof IDataServiceDelegate){
            dataService = ((IDataServiceDelegate) applicationContext).theDataService();
        }
    }

    protected EntityManagerFactory lookupEntityManagerFactory() {
        IDataServiceDefinition dataServiceDefinition = dataService.getDataServiceDefinition();
        String emfBeanName = dataServiceDefinition.getEntityManagerName();
        String puName = dataServiceDefinition.getPersistenceUnit();
        return dataService.getService(EntityManagerFactory.class, emfBeanName);
    }

    protected EntityManager createEntityManager(EntityManagerFactory emf) {
        return emf.createEntityManager();
    }

    @Around("persistenceMethod()")
    public Object wrapWithEntityManager(ProceedingJoinPoint joinPoint) throws ServiceException {
        try {
            EntityManagerFactory emf = lookupEntityManagerFactory();
            boolean participate = false;
            Object result = null;

            if (TransactionSynchronizationManager.hasResource(emf)) {
                // Do not modify the EntityManager: just set the participate flag.
                participate = true;
            } else {
                try {
                    EntityManager em = createEntityManager(emf);
                    EntityManagerHolder emHolder = new EntityManagerHolder(em);
                    TransactionSynchronizationManager.bindResource(emf, emHolder);
//                    AsyncRequestInterceptor interceptor = new AsyncRequestInterceptor(emf, emHolder);
                } finally {
                }
            }

            result = joinPoint.proceed();

            if (!participate) {
                EntityManagerHolder emHolder = (EntityManagerHolder)
                    TransactionSynchronizationManager.unbindResource(emf);
                EntityManagerFactoryUtils.closeEntityManager(emHolder.getEntityManager());
            }

            return result;
        } catch (Throwable e) {
            throw ServiceException.treatAsServiceException(e);
        }
    }
}
