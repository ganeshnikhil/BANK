================================================================================
BANK MANAGEMENT SYSTEM
Version: 1.0
================================================================================

PROJECT OVERVIEW:
This is a comprehensive Bank Account Manager application built with Java Swing UI
and backed by a robust model-view architecture. It allows users to create accounts,
deposit/withdraw funds, transfer money between accounts, and view account statements.

================================================================================
SYSTEM REQUIREMENTS:
================================================================================
- Java Development Kit (JDK) 17 or higher
- Maven 3.8.1 or higher
- IntelliJ IDEA / Eclipse IDE (optional but recommended)
- macOS / Windows / Linux

================================================================================
PROJECT STRUCTURE:
================================================================================
BankAccountManager/
├── src/
│   ├── main/java/com/bank/
│   │   ├── BankManagerApp.java            (Main entry point)
│   │   ├── model/
│   │   │   ├── BankAccount.java          (Account model)
│   │   │   ├── Transaction.java          (Transaction record)
│   │   │   └── AccountManager.java       (Manager for accounts)
│   │   └── ui/
│   │       ├── BankManagerGUI.java       (Main window)
│   │       ├── CreateAccountDialog.java  (Feature 1)
│   │       ├── DepositDialog.java        (Feature 2)
│   │       ├── WithdrawDialog.java       (Feature 3)
│   │       ├── TransferDialog.java       (Feature 4)
│   │       └── ViewStatementDialog.java  (Feature 5)
│   └── test/java/com/bank/
│       ├── BankAccountTest.java          (14 JUnit tests)
│       ├── AccountManagerTest.java       (12 JUnit tests)
│       └── TransactionTest.java          (8 JUnit tests)
├── pom.xml                               (Maven configuration)
└── README.txt                            (This file)

================================================================================
BUILDING THE PROJECT:
================================================================================

1. Navigate to the project directory:
   cd BankAccountManager

2. Compile the project:
   mvn clean compile

3. Build the project (creates JAR file):
   mvn clean package

================================================================================
RUNNING THE APPLICATION:
================================================================================

Option 1: Run from Terminal (after building)
  mvn exec:java -Dexec.mainClass="com.bank.BankManagerApp"

Option 2: Run the JAR file directly
  java -jar target/bank-account-manager-1.0.0-shaded.jar

Option 3: Run from IDE (IntelliJ IDEA / Eclipse)
  - Right-click on BankManagerApp.java
  - Select "Run 'BankManagerApp.main()'"

================================================================================
RUNNING JUNIT TESTS:
================================================================================

1. Run all tests:
   mvn test

2. Run specific test class:
   mvn test -Dtest=BankAccountTest

3. Run specific test method:
   mvn test -Dtest=BankAccountTest#testDeposit_IncreaseBalance

4. Generate test report:
   mvn surefire-report:report
   (Report will be at: target/reports/surefire.html)

5. Run tests with coverage:
   mvn jacoco:report
   (Coverage report will be at: target/site/jacoco/index.html)

================================================================================
FEATURES IMPLEMENTED:
================================================================================

FR-01: Create Account
  - Create new bank account with account number, holder name, initial balance
  - Input validation for all parameters
  - Support for multiple account types (Savings, Current, Student)

FR-02: Deposit Funds
  - Deposit money into selected account
  - Amount validation and error handling
  - Transaction recorded with timestamp

FR-03: Withdraw Funds
  - Withdraw money with balance check
  - Maximum withdrawal limit enforced
  - Transaction recorded with details

FR-04: Transfer Funds
  - Transfer between two different accounts
  - Validation for sufficient balance
  - Both accounts updated atomically

FR-05: View Statement
  - Display account balance and details
  - Complete transaction history with timestamps
  - Formatted output for easy reading

FR-06: Apply Interest
  - Calculate and apply annual interest to accounts
  - Interest rates vary by account type:
    * Savings Account: 4% per annum
    * Student Account: 6% per annum
    * Current Account: 0% (no interest)
  - Interest added as transaction in history
  - Account statement shows applicable interest rate

================================================================================
USER INTERFACE:
================================================================================

Opening Screen:
  - Displays application title and version
  - "Get Started" button leads to main menu
  - Clean and simple design

Main Menu:
  - 7 options: Create Account, Deposit, Withdraw, Transfer, View Statement, Apply Interest, Exit
  - Modern flat design with blue color scheme
  - Responsive dialogs for each operation

================================================================================
TEST COVERAGE:
================================================================================

Total Test Cases: 34
- Positive Tests: 14 (valid inputs returning correct output)
- Negative Tests: 9 (invalid inputs throwing exceptions)
- Boundary Tests: 7 (edge values)
- Parameterized Tests: 4 (multiple inputs)

Test Coverage by Class:
  - BankAccount: 14 tests
  - AccountManager: 12 tests
  - Transaction: 8 tests

All tests use JUnit 5 annotations: @Test, @BeforeEach, @AfterEach, 
@DisplayName, @ParameterizedTest, @ValueSource

================================================================================
VALIDATION RULES:
================================================================================

BankAccount Creation:
  - Account number cannot be empty
  - Account holder name cannot be empty
  - Initial balance must be >= 0
  - Initial balance cannot exceed ₹1,000,000

Deposit:
  - Amount must be > 0
  - Amount cannot exceed ₹1,000,000
  - Description is optional

Withdrawal:
  - Amount must be > 0
  - Amount cannot exceed ₹100,000
  - Sufficient balance required
  - Description is optional

Transfer:
  - Amount must be > 0
  - Source and destination accounts must be different
  - Both accounts must exist
  - Sufficient balance in source account

Interest:
  - Only applicable to Savings and Student accounts
  - Current accounts do not earn interest
  - Interest is calculated annually on current balance
  - Interest is recorded as a separate transaction type

================================================================================
INTEREST CALCULATION:
================================================================================

How Interest Works:
1. Navigate to Main Menu → 6. Apply Interest
2. Select an account from the dropdown
3. View the annual interest rate and calculated interest amount
4. Click "Apply Interest" to credit the amount to the account
5. Interest appears in transaction history as "INTEREST" type

Interest Rates by Account Type:
  - Savings Account: 4% per annum
  - Student Account: 6% per annum
  - Current Account: 0% (no interest earned)

Example:
  Account: Savings Account
  Balance: ₹10,000
  Interest Rate: 4%
  Annual Interest: ₹400
  New Balance After Interest: ₹10,400

================================================================================
KNOWN LIMITATIONS:
================================================================================

1. Data Persistence: Accounts are stored in memory only. Data is lost when
   application is closed. For persistent storage, implement file I/O or database.

2. User Authentication: No login/authentication system implemented.

3. Account Deletion: Accounts can be deleted even if they have active transactions.

4. Concurrent Transactions: No thread-safety measures for concurrent access.

================================================================================
TROUBLESHOOTING:
================================================================================

1. "Command not found: mvn"
   - Ensure Maven is installed and PATH is set correctly
   - Test with: mvn -version

2. "Java version error"
   - Ensure JDK 17+ is installed
   - Test with: java -version

3. Tests failing to run
   - Clear Maven cache: mvn clean test
   - Verify JUnit 5 dependency in pom.xml

4. GUI not appearing
   - Ensure you're using a display server (not SSH without X11 forwarding)
   - Try running with: mvn exec:java -Dexec.mainClass="com.bank.BankManagerApp"

================================================================================
DEPENDENCIES:
================================================================================

JUnit Jupiter (JUnit 5): 5.10.0
  - Testing framework for Java

Gson: 2.10.1
  - Optional: For JSON serialization (not currently used)

================================================================================
CLASS DESIGN OVERVIEW:
================================================================================

Inheritance:
  - BankAccount is the main entity
  - Transaction is an inner concern of BankAccount
  - AccountManager (Singleton) manages collection of BankAccounts

Encapsulation:
  - All fields are private with public getters
  - Balance modifications only through deposit()/withdraw()
  - Transaction history is immutable (returns copy)

Abstraction:
  - AccountManager provides high-level operations
  - UI dialogs abstract away underlying model complexity

Composition:
  - AccountManager contains BankAccounts
  - BankAccount contains Transactions

================================================================================
IDE SETUP INSTRUCTIONS:
================================================================================

IntelliJ IDEA:
  1. File → Open → Select project folder
  2. Keep default Maven configuration
  3. Wait for Maven to download dependencies
  4. Right-click on src/main/java/com/bank/BankManagerApp.java → Run
  5. For tests: Right-click on src/test/java → Run Tests

Eclipse:
  1. File → Import → Existing Maven Projects
  2. Browse to project folder → Finish
  3. Wait for Maven dependencies to download
  4. Right-click BankManagerApp.java → Run As → Java Application
  5. For tests: Right-click on test folder → Run As → JUnit Test



================================================================================
SUBMISSION CHECKLIST:
================================================================================
[✓] Opening screen displays application title and version
[✓] 6 functional features implemented and menu-accessible
[✓] 3+ Java classes with OOP design (Inheritance, Interfaces)
[✓] Input validation and exception handling
[✓] pom.xml present and build successful
[✓] Javadoc comments on all classes and methods
[✓] 34 JUnit test cases with all required annotations
[✓] All test assertions used (assertEquals, assertTrue, assertThrows, etc.)
[✓] JUnit setup instructions documented
[✓] Screenshot of tests passing
[✓] Proper folder structure
[✓] README.txt with compilation and run instructions
[✓] Interest calculation feature with multiple account types

================================================================================
CONTACT & SUPPORT:
================================================================================
For issues or questions, refer to the SRD_YourName.docx and 
TestCases_YourName.docx documents for detailed specifications.

================================================================================
Last Updated: 2026-04-07
