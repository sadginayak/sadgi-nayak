package com.mobileautomation.platform;

import com.mobileautomation.driver.Platform;
import org.openqa.selenium.By;

/**
 * Resolves platform-specific locators so Page Objects never branch on
 * platform themselves.
 *
 * Only locator resolution exists today - no business logic, driver
 * management, configuration, assertions, or other platform-specific APIs.
 * Independent of DriverFactory: DriverFactory creates the driver,
 * PlatformHook resolves locators, and neither references the other.
 */
public interface PlatformHook {

    /**
     * Resolves the locator for the current platform. Throws if the
     * requested platform has no locator implemented for this element,
     * rather than letting a null reach Appium's findElement().
     */
    default By resolve(By androidLocator, By iosLocator) {
        By resolved = select(androidLocator, iosLocator);
        if (resolved == null) {
            throw new IllegalStateException(
                    "Locator not implemented for this platform (android=" + androidLocator
                    + ", ios=" + iosLocator + ")");
        }
        return resolved;
    }

    /**
     * Picks the platform-appropriate By, without null-checking.
     */
    By select(By androidLocator, By iosLocator);

    /**
     * Selects the PlatformHook implementation for the given Platform.
     */
    static PlatformHook from(Platform platform) {
        return switch (platform) {
            case ANDROID -> new AndroidPlatformHook();
            case IOS -> new IOSPlatformHook();
        };
    }
}