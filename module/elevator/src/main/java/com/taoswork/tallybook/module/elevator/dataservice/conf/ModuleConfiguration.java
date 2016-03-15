package com.taoswork.tallybook.module.elevator.dataservice.conf;

import com.taoswork.tallybook.module.elevator.def.ElevatorModule;
import com.taoswork.tallybook.module.IModule;
import com.taoswork.tallybook.module.elevator.def.ModuleBroadcaster;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

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
    public ModuleBroadcaster broadcaster() {
        return new ModuleBroadcaster();
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
