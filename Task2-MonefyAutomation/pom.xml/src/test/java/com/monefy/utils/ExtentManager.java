package com.monefy.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports createInstance() {
        if (extent == null) {
            String fileName = "reports/MonefyAutomationReport.html";
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(fileName);
            
            sparkReporter.config().setTheme(Theme.STANDARD);
            sparkReporter.config().setDocumentTitle("Monefy Automation Test Report");
            sparkReporter.config().setEncoding("utf-8");
            sparkReporter.config().setReportName("Monefy E2E Test");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Environment", "Sauce Labs");
            extent.setSystemInfo("Platform", "Android");
            extent.setSystemInfo("QA", "Sadgi");
        }
        return extent;
    }
}