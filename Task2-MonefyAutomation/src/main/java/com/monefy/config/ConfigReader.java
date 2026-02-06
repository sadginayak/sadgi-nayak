package com.monefy.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties prop;

    static {
        try {
            // Using System property 'user.dir' makes paths reliable across different machines
            String configPath = System.getProperty("user.dir") + "/src/main/resources/config.properties";
            FileInputStream fis = new FileInputStream(configPath);
            prop = new Properties();
            prop.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Could not find config.properties at src/main/resources/");
        }
    }

    public static String getPlatform() {
        return prop.getProperty("platform");
    }

    public static String getSauceURL() {
        return prop.getProperty("sauceURL");
    }
    public static String getSauceUsername() {
        return prop.getProperty("username");
    }
    public static String getSauceAccesskey() {
        return prop.getProperty("accesskey");
    }

    public static String getAppPath() {
        String relativePath = getPlatform().equalsIgnoreCase("android") 
                              ? prop.getProperty("android.appPath") 
                              : prop.getProperty("ios.appPath");
        return System.getProperty("user.dir") + relativePath;
    }

    public static String getDeviceName() {
        return prop.getProperty("android.deviceName");
    }

    public static String getPlatformVersion() {
        return prop.getProperty("android.platformVersion");
    }
    
    public static String getUdid() {
        return prop.getProperty("android.udid");
    }
}