package com.mobileautomation.monefy.utils;

public class TestCalculations {

    public static double calculateExpectedBalance(
            String deposit,
            String expense) {

        return Double.parseDouble(deposit)
                - Double.parseDouble(expense);
    }
}