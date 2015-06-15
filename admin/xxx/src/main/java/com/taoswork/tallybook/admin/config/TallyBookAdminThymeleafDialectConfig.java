package com.taoswork.tallybook.admincore.conf;

import com.taoswork.tallybook.admincore.web.view.thymeleaf.TallyBookAdminDialect;
import com.taoswork.tallybook.admincore.web.view.thymeleaf.processor.AdminEmployeeValueAttrProcessor;
import com.taoswork.tallybook.admincore.web.view.thymeleaf.processor.ValueProcessor;
import com.taoswork.tallybook.general.extension.collections.SetBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.processor.IProcessor;
/**
 * Created by Gao Yuan on 2015/5/14.
 */
@Configuration
public class TallyBookAdminThymeleafDialectConfig {
    @Bean(name = AdminEmployeeValueAttrProcessor.COMPONENT_NAME)
    IProcessor adminEmployeeValueAttrProcessor(){
        return new AdminEmployeeValueAttrProcessor();
    }

    @Bean(name = TallyBookAdminDialect.COMPONENT_NAME)
    IDialect tallyBookAdminThymeleafDialect(){
        TallyBookAdminDialect dialect = new TallyBookAdminDialect();
        dialect.setProcessors(new SetBuilder<IProcessor>()
                .put(adminEmployeeValueAttrProcessor())
                .put(new ValueProcessor())
                .result());
        return dialect;
    }
}
