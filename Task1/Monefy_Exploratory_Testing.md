# Exploratory Testing Report: Monefy (Android)

## 1. Overview
This report documents an exploratory testing session of the **Monefy Lite** Android application. The objective was to identify functional defects, usability risks, and data integrity issues.

---

## 2. Testing Charters

| ID | Charter | Focus | Priority |
| :--- | :--- | :--- | :--- |
| **CH-01** | **Data Correction Flow:** Validate the recalculation logic when editing existing records. | Data Integrity | **Medium** |
| **CH-02** | **Globalization Settings:** Test the impact of changing currency on existing balances. | Logic/Integrity | **High** |
| **CH-03** | **Boundary Value Analysis:** Test the limits of the numeric input fields (Min/Max). | Robustness | **Medium** |
| **CH-04** | **Visual Accessibility:** Test UI responsiveness to system-level/app-level font scaling. | UX/Accessibility | **Low** |
| **CH-05** | **Localization:** Verify UI text translation when changing the application language. | Localization | **Medium** |

---

## 3. Key Findings & Bug Reports

### CH-01: Transaction Correction (Data Integrity)
* **Status:** SUCCESS (Functional) / FAIL (UX)
* **Observation:** Recalculation is handled correctly by the database, but the user interface lacks save changes or update expenses button.
* **Discovery:** 
1. There is no explicit "Save" or "Update" button on the Edit expense screen. The user must rely on the "Back" button to trigger a save.
    
2.  The 'Record updated' confirmation message is very transient. On faster devices, it may disappear before a user can read it, potentially leading to uncertainty if the save action was successful.
* **Impact:** Users may struggle to understand how to persist changes.

### CH-02: Currency Logic (Functional Bug)
* **Status:** **FAILED / CRITICAL BUG**
* **Observation:** The application allows users to change the base currency without applying exchange rates to existing data.
* **Discovery:** 
1. **Label Swap:** A deposit of **1,000 ₹ (INR)** becomes **1,000 € (EUR)** immediately after changing settings.
    
2. **Setting Inconsistency:** The dashboard "Three Dots" menu restricts currency changes to Premium users, while the "Settings" menu allows it for free.

* **Impact:** Critical, financial data integrity is compromised as the unit of measure changes while the numeric value remains static.

### CH-03: Boundary Value Analysis (Input Limits)
* **Status:** SUCCESS
* **Observation:** Verified the robustness of the input fields against extreme financial places.
* **Discovery:** 
1. **Range Support:** The app successfully processes values from **0.01** to **999,999,999.99**.
    
2. **Visual Handling:** The UI successfully renders 9-digit strings and upto 2 digit decimal value without breaking.
* **Impact:** None.

### CH-04: UI Responsiveness (Visual Defect)
* **Status:** **MINOR BUG**
* **Observation:** Tested the application’s layout stability against increased system font sizes.
* **Discovery:** After increasing the font size in settings, several text labels shifted out of alignment or were partially cut off by their containers.
* **Impact:** Low; primarily a cosmetic issue, but could impact accessibility for users relying on high-contrast or large-text settings.

### CH-05: Localization
* **Status:** **MINOR BUG / INCOMPLETE LOCALIZATION**
* **Observation:** Tested the application's behavior when switching the primary language to German (Deutsch).
* **Discovery:** 

    **Partial Translation:** While system-level strings like months (e.g., "Februar") updated correctly, core UI elements, including Premium upgrade prompts and default category labels, remained in English.

* **Impact:** Medium. It creates a confusing user experience for non-English speakers and reduces the perceived quality of the application.
---

## 4. Risk Mitigation
1. **Currency Integrity:** Implement a mandatory data-reset prompt or a conversion warning when a user changes the base currency in Settings.
2. **User Confirmation:** Add explicit "Save" buttons to ensure users feel confident that their financial data has been persisted.


---

## 5. Automation Strategy
Based on the risks identified, I have selected the following flows for the E2E automation task:
1. **Financial Correction Flow:** (Add Deposit -> Add Expense -> Edit Expense via Back button -> Verify Balance).
