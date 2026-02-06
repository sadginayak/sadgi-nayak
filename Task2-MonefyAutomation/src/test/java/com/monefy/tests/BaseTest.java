package com.monefy.tests;

import com.monefy.driver.DriverFactory;
import io.appium.java_client.AppiumDriver;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected AppiumDriver driver;

    @BeforeMethod
    public void setUp() {
        // DriverFactory.getDriver() handles the creation and ThreadLocal storage
        driver = DriverFactory.getDriver();
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (driver != null) {
            // Log status to Sauce Labs
            String status = result.isSuccess() ? "passed" : "failed";
            driver.executeScript("sauce:job-result=" + status);
            
            DriverFactory.quitDriver();
        }
    }
}
