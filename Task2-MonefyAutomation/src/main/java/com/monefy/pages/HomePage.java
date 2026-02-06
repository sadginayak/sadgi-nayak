package com.monefy.pages;

import com.monefy.base.BasePage;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class HomePage extends BasePage {

	private By btnContinue = AppiumBy.id("com.monefy.app.lite:id/buttonContinue");
	private By btnCloseOffer = AppiumBy.id("com.monefy.app.lite:id/buttonClose");
	private By btnIncome = AppiumBy.id("com.monefy.app.lite:id/income_button_title");
	private By btnExpense = AppiumBy.id("com.monefy.app.lite:id/expense_button_title");
	private By balanceText = AppiumBy.id("com.monefy.app.lite:id/balance_amount");

	public HomePage() {
		super();
		handleOnboarding();
	}

	/**
	 * Clicks the 'Continue' button repeatedly and click close on premium offer screen.
	 */
	private void handleOnboarding() {
	    try {
	        while (waitForElementFluently(btnContinue, 2, 250)) {
	            click(btnContinue);
	        }	        
	        if (waitForElementFluently(btnCloseOffer, 3, 250)) {
	            click(btnCloseOffer);
	        }
	    } catch (Exception e) {
	        System.out.println("Handled onboarding via Fluent Wait.");
	    }
	}

	public void clickAddIncome() {
		click(btnIncome); 
	}
	public void clickAddExpense() { 
		click(btnExpense); 
	}
	public void openTransactions() { 
		click(balanceText); 
	}

	public double getBalanceValue() {
		String raw = getText(balanceText);
		String cleaned = raw.replaceAll("[^0-9.-]", "");
		return Double.parseDouble(cleaned);
	}
}