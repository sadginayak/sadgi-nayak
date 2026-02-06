# Monefy Mobile Automation (Task 2)

## 📱 Project Scope
This solution targets the **Monefy Android** application using **Sauce Labs** to ensure a standardized execution environment and demonstrate cloud-grid integration—essential for modern CI/CD scalability.

---

## 🏗️ Design Approach & Architecture

### **Page Object Model (POM)**
I implemented a  **POM** architecture to ensure a clean separation between the test scripts and the UI locators. This reduces code duplication and makes the framework highly maintainable; any UI change in the app only requires a single update in the corresponding Page class.

### **Class Responsibilities**
* **`DriverFactory`**: Manages thread-safe driver instances using a **Factory Pattern** and **ThreadLocal**.
* **`BasePage`**: The parent class for all pages, containing **Fluent Wait** wrappers and global interaction logic.

* **`ConfigReader`**: Centralized utility for credentials and environment properties.
* **`TestListener`**: Interfaces with TestNG to trigger report logging and screenshot capture on failure.

### **Smart Engineering**
* **Smart UI Polling**: Handles the calculator's "append" behavior by polling the UI until the input is reset to zero.
* **Data Integrity**: Uses Regex-based parsing to convert UI currency strings into `Double` for mathematical assertions.

---

## 📊 Reporting (Extent Reports)
The framework is integrated with **Extent Reports** to provide rich, HTML-based execution dashboards.
* **Visual Evidence**: Automatically embeds screenshots in the report upon test failure.

* **Access**: Reports are generated in the `test-output/` or `target/` directory after execution.

---

## ⚙️ Setup & Execution

1. **Configure Credentials**: Update `src/test/resources/config.properties`:
   - `username=YOUR_SAUCE_USERNAME`
   - `access.key=YOUR_SAUCE_ACCESS_KEY`

2. **Run Tests**:
   ```bash
   mvn clean test
🛠️ Tech Stack
Java | Appium | TestNG | Maven | Extent Reports