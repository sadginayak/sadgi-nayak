package com.mobileautomation.monefy.pages;

import com.mobileautomation.basepage.BasePage;
import com.mobileautomation.monefy.locators.HomePageLocators;
import com.mobileautomation.platform.PlatformHook;
import io.appium.java_client.AppiumDriver;
public class HomePage extends BasePage {

    public HomePage(AppiumDriver driver, PlatformHook platformHook) {
        super(driver, platformHook);
    }

    public void handleOnboarding() {

        while (isVisible(
                HomePageLocators.ANDROID_BTN_CONTINUE,
                HomePageLocators.IOS_BTN_CONTINUE)) {

            click(
                    HomePageLocators.ANDROID_BTN_CONTINUE,
                    HomePageLocators.IOS_BTN_CONTINUE);
        }

        if (isVisible(
                HomePageLocators.ANDROID_BTN_CLOSE_OFFER,
                HomePageLocators.IOS_BTN_CLOSE_OFFER)) {

            click(
                    HomePageLocators.ANDROID_BTN_CLOSE_OFFER,
                    HomePageLocators.IOS_BTN_CLOSE_OFFER);
        }
    }

    public void clickAddIncome() {
        click(
                HomePageLocators.ANDROID_BTN_INCOME,
                HomePageLocators.IOS_BTN_INCOME);
    }

    public void clickAddExpense() {
    	
        click(
            HomePageLocators.ANDROID_BTN_EXPENSE,
            HomePageLocators.IOS_BTN_EXPENSE
        );
    }
 
    public TransactionsPage openTransactions() {
        click(HomePageLocators.ANDROID_BALANCE_TEXT, HomePageLocators.IOS_BALANCE_TEXT);
        return new TransactionsPage(driver, platformHook);
    }
 
    public double getBalanceValue() {
        String raw = getText(HomePageLocators.ANDROID_BALANCE_TEXT, HomePageLocators.IOS_BALANCE_TEXT);
        String cleaned = raw.replaceAll("[^0-9.-]", "");
        return Double.parseDouble(cleaned);
    }

}