package com.monefy.pages;

import com.monefy.base.BasePage;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class ExpensePage extends BasePage {

    // Using the Clear button ID you found
    private By btnClear = AppiumBy.id("com.monefy.app.lite:id/buttonKeyboardClear");
	//private By btnDel = AppiumBy.id("com.monefy.app.lite:id/buttonKeyboardDel");
    private By btnAction = AppiumBy.id("com.monefy.app.lite:id/keyboard_action_button");
    private By amountDisplay = AppiumBy.id("com.monefy.app.lite:id/amount_text");

    public void selectCategory(String categoryName) {
        click(AppiumBy.xpath("//*[@text='" + categoryName + "']"));
    }

    public void enterAmount(String amount) {
        processAmount(amount, false);
    }

    public void editAmount(String amount) {
        processAmount(amount, true);
    }

    private void processAmount(String amount, boolean shouldClear) {
        wait.until(d -> d.findElement(btnAction).isDisplayed());

        if (shouldClear) {
            // Smart Clear: Keep clicking delete until the display is reset to 0
            String currentText = getText(amountDisplay);
            
            // While the text isn't "0"
            while (!currentText.equals("0")) {
                click(btnClear);
                String newText = getText(amountDisplay);
                
                if (newText.equals(currentText)) break; 
                currentText = newText;
            }
        }

        // Enter New Digits
        for (char digit : amount.toCharArray()) {
            if (Character.isDigit(digit)) {
                click(AppiumBy.id("com.monefy.app.lite:id/buttonKeyboard" + digit));
            }
        }
        click(btnAction);
    }
}