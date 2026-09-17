package com.mobileautomation.driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import java.net.URL;
import java.util.Map;

import com.mobileautomation.config.ExecutionConfig;

/**
 * DriverFactory is responsible for driver lifecycle and execution configuration only.
 * It must remain completely application-agnostic.
 *
 * Knows about: Platform, device, execution environment, Appium capabilities, grid/cloud endpoint.
 * Does NOT know about: any specific app, page, locator, test case, or business flow.
 */
public class DriverFactory {

    private static final ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();

    public static AppiumDriver getDriver(ExecutionConfig config) {
        if (driver.get() == null) {
            driver.set(createDriver(config));
        }
        return driver.get();
    }

    private static AppiumDriver createDriver(ExecutionConfig config) {
        try {
            URL url = new URL(config.endpointUrl());
            AppiumDriver driverInstance = switch (config.platform()) {
                case ANDROID -> new AndroidDriver(url, buildAndroidOptions(config));
                case IOS -> new IOSDriver(url, buildIosOptions(config));
            };
            return driverInstance;

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize driver: " + e.getMessage(), e);
        }
    }

    private static UiAutomator2Options buildAndroidOptions(ExecutionConfig config) {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setDeviceName(config.deviceName())
                .setPlatformVersion(config.platformVersion())
                .setApp(config.app());

        applyExtraCapabilities(options, config.extraCapabilities());
        return options;
    }

    private static XCUITestOptions buildIosOptions(ExecutionConfig config) {
        XCUITestOptions options = new XCUITestOptions()
                .setPlatformName("iOS")
                .setAutomationName("XCUITest")
                .setDeviceName(config.deviceName())
                .setPlatformVersion(config.platformVersion())
                .setApp(config.app());

        applyExtraCapabilities(options, config.extraCapabilities());
        return options;
    }

    private static void applyExtraCapabilities(io.appium.java_client.remote.options.BaseOptions<?> options,
                                                Map<String, Object> extraCapabilities) {
        extraCapabilities.forEach(options::setCapability);
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}