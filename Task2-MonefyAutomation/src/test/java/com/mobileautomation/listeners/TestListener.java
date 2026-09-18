package com.mobileautomation.listeners;

import com.aventstack.extentreports.Status;
import io.appium.java_client.AppiumDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.xml.XmlTest;

import com.mobileautomation.basetest.BaseTest;
import com.mobileautomation.utilities.ExtentManager;
import com.mobileautomation.utilities.ScreenshotUtils;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
    	System.out.println("TestListener onTestStart fired");
        XmlTest xmlTest = result.getTestContext().getCurrentXmlTest();

        String appName = xmlTest.getParameter("appName");
        String environment = xmlTest.getParameter("environment");
        String platform = xmlTest.getParameter("platform");
        String device = xmlTest.getParameter("device");

        ExtentManager.startTest(
                appName,
                environment,
                platform,
                device,
                System.getProperty("qaName", "Sadgi"),
                result.getMethod().getMethodName()
        );
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentManager.getTest().log(Status.PASS, "Test passed");
        ExtentManager.unload();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        BaseTest testInstance = (BaseTest) result.getInstance();
        AppiumDriver driver = testInstance.getDriver();

        String path = ScreenshotUtils.takeScreenshot(driver, result.getMethod().getMethodName());
        ExtentManager.getTest().log(Status.FAIL, result.getThrowable());
        ExtentManager.getTest().addScreenCaptureFromPath(path);
        ExtentManager.unload();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentManager.getTest().log(Status.SKIP, "Test skipped");
        ExtentManager.unload();
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.flush();
    }
}