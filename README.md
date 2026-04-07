# 🏦 Bank Management System

**Version:** 1.0

A comprehensive **Java Swing-based Bank Account Manager** built using a **Model-View architecture**. This application enables users to manage bank accounts efficiently with features like deposits, withdrawals, transfers, and interest calculation.

---

## 🚀 Project Overview

This system provides a complete banking simulation with:

* Account creation and management
* Secure transaction handling
* Interest calculation based on account type
* Transaction history tracking
* Fully tested backend using JUnit

---

## ⚙️ System Requirements

* ☕ Java JDK 17+
* 📦 Maven 3.8.1+
* 💻 IntelliJ IDEA / Eclipse (Recommended)
* 🖥 macOS / Windows / Linux

---

## 📁 Project Structure

```
BankAccountManager/
├── src/
│   ├── main/java/com/bank/
│   │   ├── BankManagerApp.java
│   │   ├── model/
│   │   │   ├── BankAccount.java
│   │   │   ├── Transaction.java
│   │   │   └── AccountManager.java
│   │   └── ui/
│   │       ├── BankManagerGUI.java
│   │       ├── CreateAccountDialog.java
│   │       ├── DepositDialog.java
│   │       ├── WithdrawDialog.java
│   │       ├── TransferDialog.java
│   │       └── ViewStatementDialog.java
│   └── test/java/com/bank/
│       ├── BankAccountTest.java
│       ├── AccountManagerTest.java
│       └── TransactionTest.java
├── pom.xml
└── README.md
```

---

## 🛠️ Build Instructions

```bash
# Navigate to project folder
cd BankAccountManager

# Compile
mvn clean compile

# Package (creates JAR)
mvn clean package
```

---

## ▶️ Running the Application

### Option 1: Using Maven

```bash
mvn exec:java -Dexec.mainClass="com.bank.BankManagerApp"
```

### Option 2: Run JAR

```bash
java -jar target/bank-account-manager-1.0.0-shaded.jar
```

### Option 3: Run from IDE

* Open project in IntelliJ/Eclipse
* Run `BankManagerApp.java`

---

## 🧪 Running Tests

```bash
# Run all tests
mvn test

# Run specific class
mvn test -Dtest=BankAccountTest

# Run specific method
mvn test -Dtest=BankAccountTest#testDeposit_IncreaseBalance
```

### 📊 Reports

* Test Report:
  `target/reports/surefire.html`

* Code Coverage:
  `target/site/jacoco/index.html`

---

## ✨ Features

### 🧾 FR-01: Create Account

* Supports Savings, Current, Student accounts
* Input validation included

### 💰 FR-02: Deposit Funds

* Validates amount
* Records transaction

### 💸 FR-03: Withdraw Funds

* Balance checks
* Withdrawal limits

### 🔁 FR-04: Transfer Funds

* Atomic transactions
* Validation for accounts and balance

### 📊 FR-05: View Statement

* Full transaction history
* Clean formatted output

### 📈 FR-06: Apply Interest

* Savings: 4%
* Student: 6%
* Current: 0%
* Interest added as transaction

---

## 💡 Interest Calculation Example

```
Account Type: Savings
Balance: ₹10,000
Interest Rate: 4%

Interest = ₹400
New Balance = ₹10,400
```

---

## 🎨 User Interface

### 🏠 Home Screen

* App title + version
* “Get Started” button

### 📋 Main Menu

* Create Account
* Deposit
* Withdraw
* Transfer
* View Statement
* Apply Interest
* Exit

---

## 🔍 Validation Rules

### Account Creation

* Non-empty account number & name
* Balance ≥ 0 and ≤ ₹1,000,000

### Deposit

* Amount > 0 and ≤ ₹1,000,000

### Withdrawal

* Amount > 0 and ≤ ₹100,000
* Must have sufficient balance

### Transfer

* Accounts must be different
* Sufficient balance required

---

## 🧪 Test Coverage

| Type           | Count |
| -------------- | ----- |
| Positive Tests | 14    |
| Negative Tests | 9     |
| Boundary Tests | 7     |
| Parameterized  | 4     |
| **Total**      | 34    |

### Coverage by Class

* BankAccount → 14 tests
* AccountManager → 12 tests
* Transaction → 8 tests

---

## ⚠️ Known Limitations

* ❌ No data persistence (in-memory only)
* 🔐 No authentication system
* 🗑 Accounts deletable even with transactions
* ⚡ No concurrency handling

---

## 📦 Dependencies

* **JUnit 5 (5.10.0)** → Testing
* **Gson (2.10.1)** → JSON (optional, not used yet)

---

## 🧠 Design Principles

### 🔒 Encapsulation

* Private fields with controlled access

### 🧱 Abstraction

* AccountManager handles business logic

### 🔗 Composition

* Accounts contain transactions

### 🧬 Architecture

* Model-View separation

---

## 🧑‍💻 IDE Setup

### IntelliJ IDEA

1. Open project
2. Auto-import Maven dependencies
3. Run `BankManagerApp`

### Eclipse

1. Import as Maven project
2. Run as Java Application

---

## 🌟 Future Improvements

* Add database (MySQL / MongoDB)
* Implement authentication system
* Add concurrency handling
* Improve UI (JavaFX or Web-based frontend)

---

## 📜 License

This project is for educational purposes.