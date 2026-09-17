package com.mobileautomation.basetest;

import com.mobileautomation.capabilities.CapabilitiesProvider;
import com.mobileautomation.config.ConfigReader;
import com.mobileautomation.config.ExecutionConfig;
import com.mobileautomation.driver.DriverFactory;
import com.mobileautomation.driver.Platform;
import com.mobileautomation.platform.PlatformHook;

import io.appium.java_client.AppiumDriver;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.util.HashMap;
import java.util.Map;

public class BaseTest {

    private static final ThreadLocal<String> currentEnvironment =
            new ThreadLocal<>();

    private static final ThreadLocal<AppiumDriver> currentDriver =
            new ThreadLocal<>();

    private static final ThreadLocal<PlatformHook> currentPlatformHook =
            new ThreadLocal<>();

    @Parameters({"platform", "device", "environment", "appPath"})
    @BeforeMethod
    public void setUp(
            @Optional String platformParam,
            @Optional String deviceKey,
            @Optional String environment,
            @Optional String appPath) {

        platformParam = resolve(platformParam, "platform");
        deviceKey = resolve(deviceKey, "device");
        environment = resolve(environment, "environment");
        appPath = resolve(appPath, "appPath");

        currentEnvironment.set(environment);

        Platform platform =
                Platform.valueOf(platformParam.toUpperCase());

        String deviceName =
                ConfigReader.getDeviceName(environment, platformParam, deviceKey);

        String platformVersion =
                ConfigReader.getDeviceVersion(environment, platformParam, deviceKey);

        String endpointUrl =
                ConfigReader.getEndpointUrl(environment);

        Map<String, Object> extraCapabilities =
                new HashMap<>(
                        CapabilitiesProvider
                                .from(environment)
                                .build()
                );
        
        extraCapabilities.putAll(getAdditionalCapabilities());

        ExecutionConfig config = new ExecutionConfig(
                platform,
                deviceName,
                platformVersion,
                appPath,
                endpointUrl,
                extraCapabilities
        );
       
        
        AppiumDriver driver =
                DriverFactory.getDriver(config);

        currentDriver.set(driver);

        currentPlatformHook.set(
                PlatformHook.from(platform)
        );
    }

    public  AppiumDriver getDriver() {
        AppiumDriver driver = currentDriver.get();

        if (driver == null) {
            throw new IllegalStateException(
                    "No driver available for the current test thread."
            );
        }

        return driver;
    }

    protected PlatformHook getPlatformHook() {
        PlatformHook hook = currentPlatformHook.get();

        if (hook == null) {
            throw new IllegalStateException(
                    "No PlatformHook available for the current test thread."
            );
        }

        return hook;
    }
    protected Map<String, Object> getAdditionalCapabilities() {
        return Map.of();
    }
    private String resolve(
            String testNgValue,
            String systemPropertyKey) {

        if (testNgValue != null && !testNgValue.isBlank()) {
            return testNgValue;
        }

        String fallback =
                System.getProperty(systemPropertyKey);

        if (fallback == null || fallback.isBlank()) {
            throw new IllegalStateException(
                    "Missing required parameter '"
                            + systemPropertyKey
                            + "' - set it in testng.xml or pass -D"
                            + systemPropertyKey
                            + "=<value>"
            );
        }

        return fallback;
    }

    @AfterMethod
    public void tearDown() {

        DriverFactory.quitDriver();

        currentDriver.remove();
        currentPlatformHook.remove();
        currentEnvironment.remove();
    }
}