package com.taoswork.tallybook.adminmvc.views.thymeleaf.processor;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.NestableNode;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.attr.AbstractAttrProcessor;

import java.util.Map;

/**
 * Created by Gao Yuan on 2015/5/14.
 */
abstract class AbstractDataAccessAttrProcessor extends AbstractAttrProcessor {

    protected abstract void modifyModelAttribute(Arguments arguments, Element element, String attributeName);

    public AbstractDataAccessAttrProcessor(String attributeName) {
        super(attributeName);
    }



    protected void addToModel(Arguments arguments, String key, Object value) {
        ((Map<String, Object>) arguments.getExpressionEvaluationRoot()).put(key, value);
    }

    @Override
    protected ProcessorResult processAttribute(Arguments arguments, Element element, String attributeName) {
        modifyModelAttribute(arguments, element, attributeName);
        element.removeAttribute(attributeName);
        if(!element.hasChildren()){
            NestableNode parent = element.getParent();
            parent.removeChild(element);
        }
        return ProcessorResult.OK;
    }
}
