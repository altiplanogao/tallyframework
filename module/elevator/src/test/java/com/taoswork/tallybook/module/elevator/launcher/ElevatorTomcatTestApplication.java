package com.taoswork.tallybook.module.elevator.launcher;

import com.taoswork.tallybook.dataservice.mongo.config.MongoDatasourceConfiguration;
import com.taoswork.tallybook.dataservice.mongo.config.TestDatasourceConfiguration;
import com.taoswork.tallybook.module.elevator.def.ElevatorModule;
import com.taoswork.tallybook.module.IModule;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

/**
 * Created by Gao Yuan on 2016/3/12.
 */
public class ElevatorTomcatTestApplication extends ElevatorTomcatApplication {
    public static final String PORT = "9000";

    @Override
    protected Class<? extends MongoDatasourceConfiguration> mongoDatasourceConfigurationClass() {
        return TestDatasourceConfiguration.class;
    }

    public static final String MODULE_RPC_IN_HTTPINVOKER = "moduleRpcInHttpInvoker";
    @Bean(name = MODULE_RPC_IN_HTTPINVOKER)
    public HttpInvokerProxyFactoryBean moduleService(){
        HttpInvokerProxyFactoryBean bean = new HttpInvokerProxyFactoryBean();
        bean.setServiceUrl("http://localhost:" + PORT + "/" + MODULE_SERVICE);
        bean.setServiceInterface(IModule.class);
//        HttpInvokerRequestExecutor requestExecutor = new HttpComponentsHttpInvokerRequestExecutor();
//        bean.setHttpInvokerRequestExecutor(requestExecutor);
        bean.afterPropertiesSet();
        return bean;
    }

    public static final String MODULE_RPC_IN_RMI = "moduleRpcInRmi";
    @Bean(name = MODULE_RPC_IN_RMI)
    public RmiProxyFactoryBean rmiModuleService(){
        RmiProxyFactoryBean bean = new RmiProxyFactoryBean();
        bean.setServiceUrl("rmi://localhost/" + ElevatorModule.FULLNAME);
        bean.setServiceInterface(IModule.class);
        return bean;
    }


}
