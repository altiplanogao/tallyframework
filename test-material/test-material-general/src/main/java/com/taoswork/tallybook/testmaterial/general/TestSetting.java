package com.taoswork.tallybook.testmaterial.general;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Gao Yuan on 2016/2/5.
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

    protected static final Properties properties;

    protected static boolean valueInBoolean(String val) {
        return "true".equals(val.toLowerCase());
    }
}
