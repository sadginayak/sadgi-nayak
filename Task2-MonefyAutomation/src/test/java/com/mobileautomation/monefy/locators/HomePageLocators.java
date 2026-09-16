package com.mobileautomation.monefy.locators;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class HomePageLocators {

    public static final By ANDROID_BTN_CONTINUE = AppiumBy.id("com.monefy.app.lite:id/buttonContinue");
    public static final By IOS_BTN_CONTINUE = null; // iOS not yet implemented

    public static final By ANDROID_BTN_CLOSE_OFFER = AppiumBy.id("com.monefy.app.lite:id/buttonClose");
    public static final By IOS_BTN_CLOSE_OFFER = null; // iOS not yet implemented

    public static final By ANDROID_BTN_INCOME = AppiumBy.id("com.monefy.app.lite:id/income_button");
    public static final By IOS_BTN_INCOME = null; // iOS not yet implemented

    public static final By ANDROID_BTN_EXPENSE = AppiumBy.id("com.monefy.app.lite:id/expense_button");
    public static final By IOS_BTN_EXPENSE = null; // iOS not yet implemented

    public static final By ANDROID_BALANCE_TEXT = AppiumBy.id("com.monefy.app.lite:id/balance_amount");
    public static final By IOS_BALANCE_TEXT = null; // iOS not yet implemented
}