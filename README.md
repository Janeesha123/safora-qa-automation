# Safora QA Automation Project

## 🧪 Overview
This project automates the "Contact Us" form on Safora website using Selenium WebDriver and TestNG.

Target Site:
https://safora.se/en/contact.html

---

## ⚙️ Tech Stack
- Java
- Selenium WebDriver
- TestNG
- ChromeDriver

---

## 🚀 How to Run

### Tools Used
- Java JDK 8+
- Maven installed
- Chrome browser
- ChromeDriver (matching version)

---

### 1. Clone Repository
git clone https://github.com/your-username/safora-qa-automation.git


---

### 2. Open project
- Open in IntelliJ IDEA or Eclipse
- Import as Maven project

---

### 3. Run Tests
Open terminal inside project folder and run:
mvn test

---
## Test Coverage
- Valid form submission
- Invalid email validation
- Invalid phone validation
- Empty form validation
- Message length validation

---

## ⚠️ Notes
- The user must scroll down to reach the Contact Us form located at the bottom of the page
- reCAPTCHA requires manual solving during execution , It cannot be fully automated using Selenium
- Solve image verification if shown
- Tests use explicit waits for stability
