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
package com.taoswork.tallybook.dynamic.dataservice.core.query.criteria.converter;

import com.taoswork.tallybook.general.extension.utils.IFriendlyEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnumFilterValueConverter implements FilterValueConverter<IFriendlyEnum> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnumFilterValueConverter.class);

    @Override
    public IFriendlyEnum convert(Class type, String stringValue) {
        if(IFriendlyEnum.class.isAssignableFrom(type)){
            IFriendlyEnum enumVal = null;
            try{
                enumVal = (IFriendlyEnum)type.getField(stringValue).get(null);
                return enumVal;
            }catch (Exception e){
                return null;
            }
        }else {
            return null;
        }
    }

}
