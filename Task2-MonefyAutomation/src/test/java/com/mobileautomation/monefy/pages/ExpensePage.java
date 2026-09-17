package com.mobileautomation.monefy.pages;

import com.mobileautomation.basepage.BasePage;
import com.mobileautomation.monefy.locators.ExpensePageLocators;
import com.mobileautomation.platform.PlatformHook;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class ExpensePage extends BasePage {

    public ExpensePage(AppiumDriver driver, PlatformHook platformHook) {
        super(driver, platformHook);
    }

    public void selectCategory(String categoryName) {
        By android = ExpensePageLocators.androidCategory(categoryName);
        By ios = ExpensePageLocators.iosCategory(categoryName);
        click(android, ios);
    }

    public void enterAmount(String amount) {
        processAmount(amount, false);
    }

    public void editAmount(String amount) {
        processAmount(amount, true);
    }
    public void goBack() {
        navigateBack();
    }
    public void goBackFromEditExpense() {
        navigateBack(); // dismiss the category-reselection screen
        navigateBack(); // exit edit screen back to Home
    }
    private void processAmount(String amount, boolean shouldClear) {
        // Wait for the keyboard to be ready before interacting with it.
        isVisible(ExpensePageLocators.ANDROID_BTN_ACTION, ExpensePageLocators.IOS_BTN_ACTION);

        if (shouldClear) {
            String currentText = getText(ExpensePageLocators.ANDROID_AMOUNT_DISPLAY,
                    ExpensePageLocators.IOS_AMOUNT_DISPLAY);

            while (!currentText.equals("0")) {
                click(ExpensePageLocators.ANDROID_BTN_CLEAR, ExpensePageLocators.IOS_BTN_CLEAR);
                String newText = getText(ExpensePageLocators.ANDROID_AMOUNT_DISPLAY,
                        ExpensePageLocators.IOS_AMOUNT_DISPLAY);
                if (newText.equals(currentText)) break;
                currentText = newText;
            }
        }

        for (char digit : amount.toCharArray()) {
            if (Character.isDigit(digit)) {
                By androidKey = ExpensePageLocators.androidKeyboardDigit(digit);
                By iosKey = ExpensePageLocators.iosKeyboardDigit(digit);
                click(androidKey, iosKey);
            }
        }

        click(ExpensePageLocators.ANDROID_BTN_ACTION, ExpensePageLocators.IOS_BTN_ACTION);
    }
}