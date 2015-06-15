package com.taoswork.tallybook.general.dataservice.support.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

/**
 * Created by Gao Yuan on 2015/5/10.
 */
public class DataServiceBeanNameGenerator extends AnnotationBeanNameGenerator {

    private static final String COMPONENT_ANNOTATION_CLASSNAME = "com.taoswork.tallybook.general.dataservice.support.annotations.EntityService";


    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        return super.generateBeanName(definition, registry);
//        if (definition instanceof AnnotatedBeanDefinition) {
//            String beanName = determineBeanNameFromAnnotation((AnnotatedBeanDefinition) definition);
//            if (StringUtils.hasText(beanName)) {
//                // Explicit bean name found.
//                return beanName;
//            }
//        }
        // Fallback: generate a unique default bean name.
  //      return "xxx";
//        return buildDefaultBeanName(definition, registry);
    }


}
