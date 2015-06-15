package com.taoswork.tallybook.adminmvc.views.thymeleaf.processor;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.element.AbstractElementProcessor;

/**
 * Created by Gao Yuan on 2015/5/14.
 */
public class ValueProcessor extends AbstractElementProcessor {
    public static final String ELEMENT_NAME = "value";

    public ValueProcessor() {
        super(ELEMENT_NAME);
    }

    @Override
    protected ProcessorResult processElement(Arguments arguments, Element element) {
        return ProcessorResult.OK;
    }

    @Override
    public int getPrecedence() {
        return 1000;
    }
}
