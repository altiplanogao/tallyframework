package com.taoswork.tallybook.module.elevator.dataservice.conf;

import com.taoswork.tallybook.module.elevator.def.ElevatorModule;
import com.taoswork.tallybook.module.io.IModule;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.remoting.rmi.RmiServiceExporter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import javax.servlet.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2016/3/9.
 */
@Configuration
public class ModuleConfiguration //extends SpringBootServletInitializer
 {
//    public static final String MODULE_SERVICE = "elevator.service";
//+    public static final String SERVICE_EXPORTER = "elevator.service.exporter";
     public static final String MODULE_BEAN = "module.bean";

    @Bean(name = MODULE_BEAN)
    public ElevatorModule module() {
        return new ElevatorModule();
    }

    @Bean
    public RmiServiceExporter serviceExporter() {
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceInterface(IModule.class);
        exporter.setServiceName(ElevatorModule.FULLNAME);
        exporter.setService(module());

        return exporter;
    }
}
