package com.mobileautomation.basepage;

import com.mobileautomation.platform.PlatformHook;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Generic mechanics for operating a mobile page. Concrete Pages provide
 * application-specific behavior; this class knows nothing about any
 * specific app.
 *
 * Does not create/retrieve a driver, does not branch on platform (every
 * locator pair goes through PlatformHook), and holds no config, reporting,
 * business, or test-data knowledge.
 */
public abstract class BasePage {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);

    protected final AppiumDriver driver;
    protected final PlatformHook platformHook;
    protected final WebDriverWait wait;

    protected BasePage(AppiumDriver driver, PlatformHook platformHook) {
        this.driver = driver;
        this.platformHook = platformHook;
        this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
    }

    protected void click(By androidLocator, By iosLocator) {
        By locator = platformHook.resolve(androidLocator, iosLocator);
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void enterText(By androidLocator, By iosLocator, String text) {
        By locator = platformHook.resolve(androidLocator, iosLocator);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(text);
    }

    protected void clear(By androidLocator, By iosLocator) {
        By locator = platformHook.resolve(androidLocator, iosLocator);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).clear();
    }

    protected String getText(By androidLocator, By iosLocator) {
        By locator = platformHook.resolve(androidLocator, iosLocator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    /**
     * Only TimeoutException is caught - the sole legitimate "not found in
     * time" signal. Anything else (dead session, real bug) must propagate.
     */
    protected boolean isVisible(By androidLocator, By iosLocator) {
        By locator = platformHook.resolve(androidLocator, iosLocator);
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected boolean isPresent(By androidLocator, By iosLocator) {
        By locator = platformHook.resolve(androidLocator, iosLocator);
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }


    protected void navigateBack() {
        driver.navigate().back();
    }
}