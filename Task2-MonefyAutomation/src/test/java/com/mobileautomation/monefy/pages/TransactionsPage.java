package com.mobileautomation.monefy.pages;

import com.mobileautomation.basepage.BasePage;
import com.mobileautomation.monefy.locators.TransactionsPageLocators;
import com.mobileautomation.platform.PlatformHook;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class TransactionsPage extends BasePage {

    public TransactionsPage(AppiumDriver driver, PlatformHook platformHook) {
        super(driver, platformHook);
    }

    /**
     * Finds the transaction row matching the given amount text, opens it,
     * then opens the amount detail - lands on the edit screen (ExpensePage).
     * The test no longer knows how a transaction is located or opened.
     */
    public ExpensePage selectTransaction(String amountText) {
        By androidRow = TransactionsPageLocators.androidTransactionRow(amountText);
        By iosRow = TransactionsPageLocators.iosTransactionRow(amountText);
        click(androidRow, iosRow);

        click(TransactionsPageLocators.ANDROID_TRANSACTION_AMOUNT_TEXT,
                TransactionsPageLocators.IOS_TRANSACTION_AMOUNT_TEXT);

        return new ExpensePage(driver, platformHook);
    }
}