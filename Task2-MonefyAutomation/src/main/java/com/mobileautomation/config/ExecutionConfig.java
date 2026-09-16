package com.mobileautomation.config;
import java.util.Map;

import com.mobileautomation.driver.Platform;
/**
 * Everything DriverFactory needs to build a driver, fully resolved by the caller
 * before this reaches DriverFactory. DriverFactory never resolves "which app" -
 * it only consumes what's already in this object.
 */
public record ExecutionConfig(
        Platform platform,
        String deviceName,
        String platformVersion,
        String app,              // path or "storage:filename=..." - already resolved by caller
        String endpointUrl,      // local Appium server or cloud grid URL
        Map<String, Object> extraCapabilities  // e.g. sauce:options, or empty map for local
) {
}
