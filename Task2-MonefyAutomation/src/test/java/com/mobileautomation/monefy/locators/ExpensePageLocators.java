package com.mobileautomation.monefy.locators;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class ExpensePageLocators {

    public static final By ANDROID_BTN_CLEAR = AppiumBy.id("com.monefy.app.lite:id/buttonKeyboardClear");
    public static final By IOS_BTN_CLEAR = null; // iOS not yet implemented

    public static final By ANDROID_BTN_ACTION = AppiumBy.id("com.monefy.app.lite:id/keyboard_action_button");
    public static final By IOS_BTN_ACTION = null; // iOS not yet implemented

    public static final By ANDROID_AMOUNT_DISPLAY = AppiumBy.id("com.monefy.app.lite:id/amount_text");
    public static final By IOS_AMOUNT_DISPLAY = null; // iOS not yet implemented

    /**
     * Dynamic - one numeric keyboard key per digit.
     */
    public static By androidKeyboardDigit(char digit) {
        return AppiumBy.id("com.monefy.app.lite:id/buttonKeyboard" + digit);
    }

    public static By iosKeyboardDigit(char digit) {
        return null; // iOS not yet implemented
    }

    /**
     * Dynamic - category is selected by its visible text.
     */
	/*
	 * public static By androidCategory(String categoryName) { return
	 * AppiumBy.xpath("//*[@text='" + categoryName + "']"); }
	 */
    
    public static By androidCategory(String categoryName) {
        return AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"" + categoryName + "\")");
    }

    public static By iosCategory(String categoryName) {
        return null; // iOS not yet implemented
    }
}