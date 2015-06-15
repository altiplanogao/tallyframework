package com.taoswork.tallybook.adminmvc.views.thymeleaf;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.Set;

/**
 * Created by Gao Yuan on 2015/5/14.
 */
public class TallyBookAdminDialect extends AbstractDialect{
    public static final String COMPONENT_NAME = "tallyBookAdminThymeleafDialect";

    public static final String PREFIX = "tb_admin";

    private Set<IProcessor> processors = null;

    public void setProcessors(Set<IProcessor> processors) {
        this.processors = processors;
    }

    @Override
    public Set<IProcessor> getProcessors() {
        return super.getProcessors();
    }

    @Override
    public String getPrefix() {
        return PREFIX;
    }
}
