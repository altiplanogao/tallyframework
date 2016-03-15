package com.taoswork.tallybook.module.elevator.launcher;

import com.taoswork.tallybook.dataservice.mongo.config.MongoDatasourceConfiguration;
import com.taoswork.tallybook.module.elevator.dataservice.ElevatorModuleDataService;
import com.taoswork.tallybook.module.elevator.dataservice.conf.ModuleConfiguration;
import com.taoswork.tallybook.module.elevator.def.ElevatorModule;
import com.taoswork.tallybook.module.IModule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2016/3/11.
 */
@SpringBootApplication(exclude={MongoAutoConfiguration.class})
public class ElevatorTomcatApplication {
    public static final String MODULE_SERVICE = "elevator.service";

    private static Log logger = LogFactory.getLog(ElevatorTomcatApplication.class);

    protected Class<? extends MongoDatasourceConfiguration> mongoDatasourceConfigurationClass(){
        return null;
    }

    @Bean
    public ElevatorModuleDataService dataService() {
        Class<? extends MongoDatasourceConfiguration> dsc = mongoDatasourceConfigurationClass();
        if (dsc == null) {
            return new ElevatorModuleDataService();
        } else {
            return new ElevatorModuleDataService(dsc);
        }
    }

    @Bean
    protected ServletContextListener listener() {
        return new ServletContextListener() {

            @Override
            public void contextInitialized(ServletContextEvent sce) {
                logger.info("ServletContext initialized");
            }

            @Override
            public void contextDestroyed(ServletContextEvent sce) {
                logger.info("ServletContext destroyed");
            }

        };
    }

    @Bean
    public HttpInvokerServiceExporter serviceExporter(){
        ElevatorModuleDataService dataService = dataService();
        ElevatorModule module = dataService.getService(ModuleConfiguration.MODULE_BEAN);

        HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();
        exporter.setServiceInterface(IModule.class);
        exporter.setService(module);
        exporter.afterPropertiesSet();

        return exporter;
    }

    @Bean
    public HandlerMapping handlerMapping(){
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setOrder(0);
        Map<String, Object> urlMapping = new HashMap<String, Object>();
        urlMapping.put(MODULE_SERVICE, serviceExporter());
        mapping.setUrlMap(urlMapping);
        return mapping;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(ElevatorTomcatApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}
