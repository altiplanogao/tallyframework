package com.taoswork.tallybook.dataservice.config.helper;

import com.taoswork.tallybook.dataservice.IDataServiceDefinition;
import com.taoswork.tallybook.general.extension.collections.SetBuilder;
import com.taoswork.tallybook.general.solution.message.MessageUtility;
import com.taoswork.tallybook.general.solution.property.RuntimeEnvironmentPropertyPlaceholderConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.util.List;

public class DataServiceBeanCreationHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataServiceBeanCreationHelper.class);

    protected final IDataServiceDefinition dataServiceDefinition;

    // **************************************************** //
    //  Constructor                                         //
    // **************************************************** //


    public DataServiceBeanCreationHelper(IDataServiceDefinition dataServiceDefinition) {
        this.dataServiceDefinition = dataServiceDefinition;
    }


    // **************************************************** //
    //  RuntimeEnvironmentPropertyPlaceholderConfigurer     //
    // **************************************************** //

    public RuntimeEnvironmentPropertyPlaceholderConfigurer createDefaultRuntimeEnvironmentPropertyPlaceholderConfigurer() {
        return createAPropertyPlaceholderConfigurer(
                new ClassPathResource(dataServiceDefinition.getPropertiesResourceDirectory()));
    }

    public RuntimeEnvironmentPropertyPlaceholderConfigurer createAPropertyPlaceholderConfigurer(Resource resourcePath, Resource... overrideResources) {
        if (resourcePath instanceof ClassPathResource) {
            ClassPathResource classPathResource = (ClassPathResource) resourcePath;
            if (!classPathResource.getPath().endsWith("/")) {
                resourcePath = new ClassPathResource(classPathResource.getPath() + "/");
            }
        }
        RuntimeEnvironmentPropertyPlaceholderConfigurer configurer = new RuntimeEnvironmentPropertyPlaceholderConfigurer();
        configurer.setPropertyPathResources(new SetBuilder<Resource>().append(resourcePath));
        configurer.setOverrideFileResources(overrideResources);
        return configurer;
    }

    // **************************************************** //
    //  MessageSource                                       //
    // **************************************************** //

    public MessageSource createEntityMessageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        final String[] messageDirs = dataServiceDefinition.getEntityMessageDirectory().split(
                IDataServiceDefinition.FILE_DELIMTER);

        List<String> basenameList = MessageUtility.getMessageBasenames(resolver, messageDirs, null);
        basenameList.add("classpath:/entity-messages/EntityGeneralMessages");
        basenameList.add("classpath:/entity-messages/EntitySupportMessages");
        ms.setBasenames(basenameList.toArray(new String[basenameList.size()]));

        return ms;
    }

    public MessageSource createErrorMessageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        final String[] messageDirs = dataServiceDefinition.getErrorMessageDirectory().split(
                IDataServiceDefinition.FILE_DELIMTER);

        List<String> basenameList = MessageUtility.getMessageBasenames(resolver, messageDirs, null);
        basenameList.add("classpath:/error-messages/ValidationMessages");
        basenameList.add("classpath:/error-messages/ServiceExceptionMessages");
        ms.setBasenames(basenameList.toArray(new String[basenameList.size()]));

        return ms;
    }


}
