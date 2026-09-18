package com.mobileautomation.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

public class ExtentManager {
    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static final String REPORTS_DIR = "reports/";
    private static final int MAX_REPORTS_TO_KEEP = 5;

    /**
     * Creates (once per JVM/suite run) the ExtentReports instance.
     * All app/environment-specific values are received as parameters —
     * this class never resolves them itself, same principle as DriverFactory
     * never reading config directly.
     */
    public static synchronized ExtentReports createInstance(String appName, String environment,
                                                              String platform, String device, String qaName) {
        if (extent == null) {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = REPORTS_DIR + appName + "_Report_" + timestamp + ".html";

            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(fileName);
            sparkReporter.config().setTheme(Theme.STANDARD);
            sparkReporter.config().setDocumentTitle(appName + " Automation Test Report");
            sparkReporter.config().setEncoding("utf-8");
            sparkReporter.config().setReportName(appName + " E2E Test");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

            extent.setSystemInfo("Application", appName);
            extent.setSystemInfo("Environment", environment);
            extent.setSystemInfo("Platform", platform);
            extent.setSystemInfo("Device", device);
            extent.setSystemInfo("QA", qaName);

            cleanupOldReports(appName);
        }
        return extent;
    }

    public static void startTest(String appName, String environment, String platform,
                                  String device, String qaName, String testName) {
        ExtentTest extentTest = createInstance(appName, environment, platform, device, qaName)
                .createTest(testName);
        test.set(extentTest);
    }

    public static ExtentTest getTest() {
        return test.get();
    }

    public static void unload() {
        test.remove();
    }

    public static synchronized void flush() {
        if (extent != null) {
            extent.flush();
        }
    }

    /**
     * Keeps only the most recent MAX_REPORTS_TO_KEEP report files per app,
     * deleting older ones. Local-only concern — CI workspaces are wiped
     * between runs, so this never runs there in practice.
     */
    private static void cleanupOldReports(String appName) {
        File dir = new File(REPORTS_DIR);
        if (!dir.exists()) return;

        File[] matchingReports = dir.listFiles((d, name) ->
                name.startsWith(appName + "_Report_") && name.endsWith(".html"));

        if (matchingReports == null || matchingReports.length <= MAX_REPORTS_TO_KEEP) return;

        Arrays.sort(matchingReports, Comparator.comparingLong(File::lastModified));

        int filesToDelete = matchingReports.length - MAX_REPORTS_TO_KEEP;
        for (int i = 0; i < filesToDelete; i++) {
            matchingReports[i].delete();
        }
    }
}