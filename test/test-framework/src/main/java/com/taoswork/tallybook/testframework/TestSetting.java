package com.taoswork.tallybook.testframework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Gao Yuan on 2015/10/20.
 */
public class TestSetting {
    static {
        Properties localProperties = new Properties();
        String homeDir = System.getProperty("user.home");
        File file = new File(homeDir, "tallytest.properties");
        if (file.exists()) {
            try {
                localProperties.load(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        properties = localProperties;
    }

    private static final Properties properties;

    public static boolean useMysql(){
        String use = properties.getProperty("use.mysql", "true");
        return valueInBoolean(use);
    }

    private static boolean valueInBoolean (String val){
        return "true".equals(val.toLowerCase());
    }
}
