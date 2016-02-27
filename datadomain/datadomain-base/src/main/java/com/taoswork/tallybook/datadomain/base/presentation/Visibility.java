/*
 * #%L
 * BroadleafCommerce Common Libraries
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
package com.taoswork.tallybook.datadomain.base.presentation;

import com.taoswork.tallybook.general.extension.collections.StringChain;
import com.taoswork.tallybook.general.extension.utils.BitUtility;

/**
 * Created by IntelliJ IDEA.
 * User: jfischer
 * Date: 9/27/11
 * Time: 1:26 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Visibility {
    //avoid constructor
    private Visibility() {
    }

    public static final int GRID_VISIBLE = 0x00000001;
    public static final int FORM_VISIBALE = 0x00000002;

    public static final int HIDDEN_ALL = 0x00000000;
    public static final int VISIBLE_ALL = 0xFFFFFFFF;

    public static final int GRID_HIDE = (VISIBLE_ALL & (~GRID_VISIBLE));
    public static final int FORM_HIDE = (VISIBLE_ALL & (~FORM_VISIBALE));

    public static final int DEFAULT = VISIBLE_ALL;

    public static boolean gridVisible(int visibility) {
        return BitUtility.containsAll(visibility, GRID_VISIBLE);
    }

    public static boolean formVisible(int visibility) {
        return BitUtility.containsAll(visibility, FORM_VISIBALE);
    }

    public static int and(int va, int vb) {
        return va & vb;
    }

    public static int or(int va, int vb) {
        return va | vb;
    }

    public static String makeString(int visibility) {
        StringBuilder sb = new StringBuilder("Visible:");

        StringChain<String> sc = new StringChain<String>();
        sc.setAsJsonArray();
        if (gridVisible(visibility)) {
            sc.add("Grid");
        }
        if (formVisible(visibility)) {
            sc.add("Form");
        }

        if (sc.isEmpty()) {
            sb.append("NONE");
        } else {
            sb.append(sc.toString());
        }

        return sb.toString();
    }
}
