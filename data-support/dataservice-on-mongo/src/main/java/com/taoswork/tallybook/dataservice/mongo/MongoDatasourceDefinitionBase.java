package com.taoswork.tallybook.dataservice.mongo;

import com.mongodb.ServerAddress;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Created by Gao Yuan on 2016/2/16.
 */
public abstract class MongoDatasourceDefinitionBase implements MongoDatasourceDefinition{
    private final ServerAddress serverAddress;

    public MongoDatasourceDefinitionBase() {
        String userHome = System.getProperty("user.home");
        Path homePath = new File(userHome).toPath();
        File configFile = homePath.resolve(".tallybook").resolve("config.properties").toFile();
        if (configFile.exists() && configFile.isFile()) {
            Properties properties = new Properties();
            try {
                properties.load(new FileInputStream(configFile));
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                String host = properties.getProperty("mongo.host", ServerAddress.defaultHost());
                String port = properties.getProperty("mongo.port", "" + ServerAddress.defaultPort());
                serverAddress = new ServerAddress(host, Integer.parseInt(port));
            }
        } else {
            serverAddress = new ServerAddress();
        }
    }

    @Override
    public ServerAddress getServerAddress() {
        return serverAddress;
    }

    @Override
    public Class getRootDataClass() {
        return null;
    }
}
