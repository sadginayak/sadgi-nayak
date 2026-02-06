package com.monefy.utils;

import io.appium.java_client.AppiumDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestUtils {

    /**
     * Capture screenshot and return file path
     */
	public static String takeScreenshot(AppiumDriver driver, String testName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String dirPath = System.getProperty("user.dir") + "/screenshots/";
        String filePath = dirPath + testName + "_" + timestamp + ".png";

        // Create screenshots folder if it doesn't exist
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            File srcFile = driver.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFile, new File(filePath));
        } catch (IOException e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }
        return filePath;
    }
    
}
