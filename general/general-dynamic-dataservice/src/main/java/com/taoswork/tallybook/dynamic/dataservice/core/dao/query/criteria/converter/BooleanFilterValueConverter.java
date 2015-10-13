/*
 * #%L
 * BroadleafCommerce Open Admin Platform
 * %%
 * Copyright (C) 2009 - 2013 Broadleaf Commerce
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.taoswork.tallybook.dynamic.dataservice.core.dao.query.criteria.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class BooleanFilterValueConverter implements FilterValueConverter<Boolean> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BooleanFilterValueConverter.class);
    private static final Set<String> trues;
    private static final Set<String> falses;

    static {
        trues = new HashSet<String>();
        trues.add("true");
        trues.add("yes");
        trues.add("t");
        trues.add("y");

        falses = new HashSet<String>();
        falses.add("false");
        falses.add("no");
        falses.add("f");
        falses.add("n");
    }

    @Override
    public Boolean convert(Class type, String stringValue) {
        if(Boolean.class.isAssignableFrom(type)){
            stringValue = stringValue.toLowerCase();
            if(trues.contains(stringValue)){
                return true;
            }else if(falses.contains(stringValue)){
                return false;
            }
            return null;
        }else {
            return null;
        }
    }

}
