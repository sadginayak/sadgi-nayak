package com.mobileautomation.monefy.locators;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class TransactionsPageLocators {

    public static final By ANDROID_TRANSACTION_AMOUNT_TEXT =
            AppiumBy.id("com.monefy.app.lite:id/textViewTransactionAmount");
    public static final By IOS_TRANSACTION_AMOUNT_TEXT = null; // iOS not yet implemented

    /**
     * Dynamic - resolves a transaction row by its displayed amount text.
     * Pulled out of the test (it was previously embedded raw in the test method).
     */
	
	  public static By androidTransactionRow(String amountText) { return
	  AppiumBy.xpath("//android.widget.TextView[contains(@text, '" + amountText +
	  "')]"); }
	 
	/*
	 * public static By androidTransactionRow(String amountText) { return
	 * AppiumBy.androidUIAutomator(
	 * "new UiSelector().className(\"android.widget.TextView\").text(\"" +
	 * amountText + "\")"); }
	 */

    public static By iosTransactionRow(String amountText) {
        return null; // iOS not yet implemented
    }
}