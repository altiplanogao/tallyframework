package com.taoswork.tallybook.application.core.conf;

import com.taoswork.tallybook.general.solution.message.CachedMessageLocalizedDictionary;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gao Yuan on 2015/9/12.
 */
@Configuration
public class ApplicationCommonConfig {
    public static final String COMMON_MESSAGE = "commonMessage";
    public static final String COMMON_MESSAGE_SOURCE = "commonMessageSource";

    @Bean(name = COMMON_MESSAGE_SOURCE)
    public MessageSource commonMessageSource(){
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        List<String> basenames = new ArrayList<String>();
        basenames.add("classpath:/messages/CommonMessages");
        ms.setBasenames(basenames.toArray(new String[basenames.size()]));
        return ms;
    }

    @Bean(name = COMMON_MESSAGE)
    public CachedMessageLocalizedDictionary commonMessage(){
        Map<String , String> raw = new HashMap<String, String>();
        raw.put("yes", "yes");
        raw.put("no", "no");
        raw.put("ok", "ok");
        raw.put("cancel", "cancel");
        raw.put("close", "close");
        raw.put("loading", "loading");
        raw.put("error", "error");
        raw.put("errorOccurred", "errorOccurred");
        raw.put("delete", "delete");
        raw.put("deleting", "deleting");
        raw.put("deleteConfirm", "deleteConfirm");
        CachedMessageLocalizedDictionary commonMessage = new CachedMessageLocalizedDictionary(raw, commonMessageSource());
        return commonMessage;
    }

}
