package com.mobileautomation.monefy.tests;

import com.mobileautomation.basetest.BaseTest;
import com.mobileautomation.monefy.config.MonefyCapabilities;
import com.mobileautomation.monefy.pages.ExpensePage;
import com.mobileautomation.monefy.pages.HomePage;
import com.mobileautomation.monefy.pages.TransactionsPage;
import com.mobileautomation.monefy.utils.TestCalculations;
import com.mobileautomation.monefy.utils.TestData;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class E2EFinancialCorrectionTest extends BaseTest {

    @Override
    protected Map<String, Object> getAdditionalCapabilities() {
        return MonefyCapabilities.build();
    }

    @DataProvider(name = "financialCorrection")
    public Object[][] financialCorrectionData() {

        return new Object[][] {
                {
                        TestData.FinancialCorrection.DEPOSIT,
                        TestData.FinancialCorrection.WRONG_EXPENSE,
                        TestData.FinancialCorrection.CORRECTED_EXPENSE
                },
                //{ "100", "40", "1" },
                //{ "100", "50", "150" },
        };
    }

    @Test(dataProvider = "financialCorrection")
    public void verifyTransactionCorrectionUpdatesBalance(
            String deposit,
            String wrongExpense,
            String correctedExpense) {

        HomePage homePage =
                new HomePage(getDriver(), getPlatformHook());
        ExpensePage expensePage =
                new ExpensePage(getDriver(), getPlatformHook());

        homePage.handleOnboarding();

        homePage.clickAddIncome();
        expensePage.enterAmount(deposit);
        expensePage.selectCategory(TestData.SALARY_CATEGORY);

        homePage.clickAddExpense();
        expensePage.enterAmount(wrongExpense);
        expensePage.selectCategory(TestData.FOOD_CATEGORY);

        TransactionsPage transactionsPage =
                homePage.openTransactions();

        expensePage =
                transactionsPage.selectTransaction(wrongExpense);

        expensePage.editAmount(correctedExpense);

        expensePage.goBackFromEditExpense();

        double expected =
                TestCalculations.calculateExpectedBalance(
                        deposit,
                        correctedExpense);

        double actual =
                homePage.getBalanceValue();

        Assert.assertEquals(
                actual,
                expected,
                0.01,
                "Balance mismatch for deposit="
                        + deposit
                        + ", corrected="
                        + correctedExpense);
    }
}