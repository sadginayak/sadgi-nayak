package com.monefy.driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import com.monefy.config.ConfigReader;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


/**
 * Factory class for managing AppiumDriver instances.
 * This class uses a Factory Design Pattern to initialize platform-specific drivers (Android/iOS)
 * and employs ThreadLocal to ensure thread safety during parallel test execution.
 */
public class DriverFactory {
	
	
    private static ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();

    public static AppiumDriver getDriver() {
        if (driver.get() == null) {
            driver.set(createDriver());
        }
        return driver.get();
    }

    private static AppiumDriver createDriver() {
        AppiumDriver driverInstance;
        String platform = ConfigReader.getPlatform();
        
        String username = ConfigReader.getSauceUsername();
        String accesskey = ConfigReader.getSauceAccesskey();
        
        try {
            URL url = new URL(ConfigReader.getSauceURL());
            boolean isSauce = url.toString().contains("saucelabs.com");

            if (platform.equalsIgnoreCase("Android")) {
                UiAutomator2Options options = new UiAutomator2Options()
                        .setPlatformName("Android")
                        .setAutomationName("UiAutomator2")
                        .setDeviceName(ConfigReader.getDeviceName())
                        .setPlatformVersion(ConfigReader.getPlatformVersion())
                        .setApp(isSauce ? "storage:filename=monefy.apk" : ConfigReader.getAppPath())
                        .setNoReset(false);

                if (isSauce) {
                    addSauceOptions(options, "Android Monefy Test", username, accesskey);
                }
                driverInstance = new AndroidDriver(url, options);

            } else if (platform.equalsIgnoreCase("iOS")) {
                XCUITestOptions options = new XCUITestOptions()
                        .setPlatformName("iOS")
                        .setAutomationName("XCUITest")
                        .setDeviceName(ConfigReader.getDeviceName())
                        .setPlatformVersion(ConfigReader.getPlatformVersion())
                        .setApp(isSauce ? "storage:filename=monefy.ipa" : ConfigReader.getAppPath());

                if (isSauce) {
                    addSauceOptions(options, "iOS Monefy Test", username, accesskey);
                }
                driverInstance = new IOSDriver(url, options);
            } else {
                throw new RuntimeException("Invalid platform: " + platform);
            }

            driverInstance.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Driver: " + e.getMessage());
        }
        
        return driverInstance;
    }

    /**
     * Adds Sauce Labs specific options to the capabilities
     */
    private static void addSauceOptions(io.appium.java_client.remote.options.BaseOptions<?> options, String testName, String user, String key) {
        Map<String, Object> sauceOptions = new HashMap<>();
        sauceOptions.put("username", user);
        sauceOptions.put("accessKey", key);
        sauceOptions.put("appiumVersion", "stable");
        sauceOptions.put("build", "Monefy-Build-1");
        sauceOptions.put("name", testName);
        
        options.setCapability("sauce:options", sauceOptions);
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}