package com.monefy.tests;

import com.monefy.pages.HomePage;
import com.monefy.pages.ExpensePage; // Assuming you have this for keyboard input
import com.monefy.utils.TestData;   // Your TestData class
import io.appium.java_client.AppiumBy;
import org.testng.Assert;
import org.testng.annotations.Test;

public class E2EFinancialCorrectionTest extends BaseTest { // BaseTest handles driver start/stop

    @Test
    public void verifyTransactionCorrectionUpdatesBalance() {
        HomePage homePage = new HomePage();
        ExpensePage expensePage = new ExpensePage();

        // Step 1: Add Initial Income
        homePage.clickAddIncome();
        expensePage.enterAmount(TestData.INITIAL_DEPOSIT);
        expensePage.selectCategory(TestData.SALARY_CAT);

        // Step 2: Add Expense to be corrected
        homePage.clickAddExpense();
        expensePage.enterAmount(TestData.WRONG_EXPENSE);
        expensePage.selectCategory(TestData.FOOD_CAT);

        // Step 3: Navigate to correction
        homePage.openTransactions();
        
        // Find the wrong entry in the list and click it
        homePage.click(AppiumBy.xpath("//android.widget.TextView[contains(@text, '" + TestData.WRONG_EXPENSE + "')]"));
        homePage.click(AppiumBy.id("com.monefy.app.lite:id/textViewTransactionAmount"));
        
        // Step 4: Correct the value
        expensePage.editAmount(TestData.CORRECTED_EXPENSE);
     
        // Step 5: Double-back to return to the Home Dashboard
        //homePage.clickNavigateUp();
        homePage.clickNavigateUp();

        // Step 6: Automated Math Assertion
        double expected = TestData.calculateExpectedBalance(TestData.INITIAL_DEPOSIT,TestData.CORRECTED_EXPENSE);
        double actual = homePage.getBalanceValue();

        Assert.assertEquals(actual, expected, "The balance did not update correctly after editing the expense!");
    }
}