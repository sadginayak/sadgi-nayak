package com.monefy.utils;

public class TestData {
	    // Flow 1: Financial Correction
	    public static final String INITIAL_DEPOSIT = "1000";
	    public static final String WRONG_EXPENSE = "100";
	    public static final String CORRECTED_EXPENSE = "50";
	    
	    // Categories
	    public static final String SALARY_CAT = "Salary";
	    public static final String FOOD_CAT = "Food";

	    // Expected Calculation (Logic)
	    public static double calculateExpectedBalance(String deposit, String expense) {
	        return Double.parseDouble(deposit) - Double.parseDouble(expense);
	    }
}
