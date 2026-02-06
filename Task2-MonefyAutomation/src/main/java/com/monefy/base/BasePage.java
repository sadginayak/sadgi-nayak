package com.monefy.base;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.monefy.driver.DriverFactory;
import org.openqa.selenium.NoSuchElementException;
import java.time.Duration;
import io.appium.java_client.AppiumBy;

public class BasePage {
    protected AppiumDriver driver;
    protected WebDriverWait wait;

    public BasePage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    public boolean isDisplayed(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean waitForElementFluently(By locator, int timeoutSeconds, int pollingMillis) {
        Wait<AppiumDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeoutSeconds))
                .pollingEvery(Duration.ofMillis(pollingMillis))
                .ignoring(NoSuchElementException.class); // Ignore this while polling

        try {
            return fluentWait.until(d -> {
                return d.findElement(locator).isDisplayed();
            });
        } catch (Exception e) {
            return false; 
        }
    }
    
    public void clickNavigateUp() {
        try {
            click(AppiumBy.accessibilityId("Navigate up"));
        } catch (Exception e) {
            navigateBack();
        }
    }

    public void navigateBack() {
        driver.navigate().back();
    }
}