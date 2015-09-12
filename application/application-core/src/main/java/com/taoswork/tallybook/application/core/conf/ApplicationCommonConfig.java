package com.taoswork.tallybook.application.core.conf;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/9/12.
 */
@Configuration
public class ApplicationCommonConfig {
    public static final String COMMON_MESSAGE_SOURCE = "commonMessageSource";

    @Bean(name = COMMON_MESSAGE_SOURCE)
    public MessageSource commonMessageSource(){
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        List<String> basenames = new ArrayList<String>();
        basenames.add("classpath:/messages/CommonMessages");
        ms.setBasenames(basenames.toArray(new String[basenames.size()]));
        return ms;
    }
}
