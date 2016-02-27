package com.taoswork.tallybook.dataservice.jpa.config.db.setting;

import com.taoswork.tallybook.dataservice.jpa.JpaDatasourceDefinition;
import org.hibernate.dialect.Dialect;

import javax.sql.DataSource;

/**
 * Created by Gao Yuan on 2015/5/17.
 */
public interface JpaDbSetting {
    public final static JpaDbSetting DEFAULT_DB_SETTING = null;

    Class<? extends Dialect> hibernateDialect();

    DataSource publishDataSourceWithDefinition(JpaDatasourceDefinition dsDefine);
}
