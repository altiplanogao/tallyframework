package com.taoswork.tallybook.general.solution.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by Gao Yuan on 2015/5/15.
 */
public class TallyBookApplicationContextInitializer implements ApplicationContextInitializer {
    public static final String CONTEXT_CONFIG_ANNOTATED_CLASSES = "tallybookContextConfigAnnotatedClasses";
    private static final Logger LOGGER = LoggerFactory.getLogger(TallyBookApplicationContextInitializer.class);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
//        if(applicationContext instanceof AnnotationConfigWebApplicationContext){
//            AnnotationConfigWebApplicationContext annotationApplicationContext = (AnnotationConfigWebApplicationContext)applicationContext;
//            String[] confLocations = annotationApplicationContext.getConfigLocations();
//            String packagePrefix = TallyBookApplicationContextInitializer.class.getName();
//            String prefixMark = "tallybook";
//            int len = packagePrefix.indexOf(prefixMark) + prefixMark.length();
//            packagePrefix = packagePrefix.substring(0, len);
//            List<Class<?>> annotationClz = new ArrayList<Class<?>>();
//            for(String location : confLocations){
//                if(location.startsWith(packagePrefix)){
//                    try {
//                        Class clz = Class.forName(location);
//                        annotationClz.add(clz);
//                    } catch (ClassNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            annotationApplicationContext.register(annotationClz.toArray(new Class[]{}));
//            annotationApplicationContext.refresh();
//        }
        LOGGER.info("initialize Done !");
    }
}
