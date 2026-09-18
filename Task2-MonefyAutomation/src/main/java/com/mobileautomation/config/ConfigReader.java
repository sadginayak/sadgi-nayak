package com.mobileautomation.config;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Reads static, non-secret FACTS from config.properties, looked up by a
 * key that CI/TestNG supplies at runtime.
 *
 * Does NOT decide which platform, device, environment, or app to run -
 * those are CI/TestNG-supplied parameters, resolved wherever ExecutionConfig
 * is assembled, not here.
 *
 * Does NOT read app paths - those are per-build values supplied by
 * CI/TestNG directly (system property / TestNG parameter), never stored
 * in this file.
 *
 * Does NOT contain credentials - Sauce Labs / BrowserStack credentials
 * come from environment variables / CI secrets, read wherever
 * ExecutionConfig is assembled, not here.
 */
public class ConfigReader {

    private static final Properties prop = new Properties();
    private static final String CONFIG_FILE = "config.properties";
    private static volatile boolean loaded = false;

    /**
     * Lazy, not static-block, load. A static initializer that throws marks
     * the class permanently unusable for the rest of the JVM (every future
     * reference gets NoClassDefFoundError, not the real cause). Loading on
     * first actual use means a bad config file only breaks the tests that
     * really need it, with the real RuntimeException, not a classloading error.
     */
    private static void ensureLoaded() {
        if (loaded) return;
        synchronized (ConfigReader.class) {
            if (loaded) return;
            try (InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
                if (is == null) {
                    throw new RuntimeException("Could not find " + CONFIG_FILE + " on the classpath");
                }
                prop.load(is);
                loaded = true;
            } catch (IOException e) {
                throw new RuntimeException("Failed to load " + CONFIG_FILE, e);
            }
        }
    }

    /**
     * @param platform  "android" or "ios"
     * @param deviceKey the key CI/TestNG chose, e.g. "samsung"
     */
    public static String getDeviceName(String environment, String platform, String deviceKey) {
        return required("devices." + environment.toLowerCase() + "." + platform.toLowerCase() + "." + deviceKey + ".name");
    }

    public static String getDeviceVersion(String environment, String platform, String deviceKey) {
        return required("devices." + environment.toLowerCase() + "." + platform.toLowerCase() + "." + deviceKey + ".version");
    }

    /**
     * @param environment the environment name CI/TestNG chose, e.g. "saucelabs"
     */
    public static String getEndpointUrl(String environment) {
        return required("endpoints." + environment.toLowerCase() + ".url");
    }

    private static String required(String key) {
        ensureLoaded();
        String value = prop.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new RuntimeException("Missing required config key: " + key);
        }
        return value;
    }
}