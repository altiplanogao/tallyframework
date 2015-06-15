package com.taoswork.tallybook.general.solution.autotree.morkdata;

import com.taoswork.tallybook.general.solution.autotree.AutoTreeAccessor;

/**
 * Created by Gao Yuan on 2015/5/23.
 */
public class StringTreeAccessor extends AutoTreeAccessor<String> {
    public StringTreeAccessor() {
        super(new StringGenealogyHelper());
    }
}
