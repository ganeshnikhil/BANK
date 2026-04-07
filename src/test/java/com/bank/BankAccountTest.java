package com.bank;

import com.bank.model.BankAccount;
import com.bank.model.Transaction;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test cases for BankAccount class.
 * Tests account creation, deposit, withdrawal, and balance inquiry operations.
 */
@DisplayName("Bank Account Tests")
class BankAccountTest {
    private BankAccount account;

    @BeforeEach
    void setUp() {
        account = new BankAccount("ACC001", "John Doe", 5000.0, "Savings");
    }

    @AfterEach
    void tearDown() {
        account = null;
    }

    // ============== POSITIVE TEST CASES ==============

    @Test
    @DisplayName("TC-001: Account creation with valid details")
    void testAccountCreation_Valid() {
        assertNotNull(account);
        assertEquals("ACC001", account.getAccountNumber());
        assertEquals("John Doe", account.getAccountHolderName());
        assertEquals(5000.0, account.getBalance(), 0.01);
        assertEquals("Savings", account.getAccountType());
    }

    @Test
    @DisplayName("TC-002: Deposit increases balance correctly")
    void testDeposit_IncreaseBalance() {
        double initialBalance = account.getBalance();
        account.deposit(1000.0, "Test deposit");
        assertEquals(initialBalance + 1000.0, account.getBalance(), 0.01);
    }

    @Test
    @DisplayName("TC-003: Withdrawal decreases balance correctly")
    void testWithdraw_DecreaseBalance() {
        double initialBalance = account.getBalance();
        account.withdraw(1000.0, "Test withdrawal");
        assertEquals(initialBalance - 1000.0, account.getBalance(), 0.01);
    }

    @Test
    @DisplayName("TC-004: Get balance returns current balance")
    void testGetBalance_ReturnsCurrentBalance() {
        assertEquals(5000.0, account.getBalance(), 0.01);
    }

    @Test
    @DisplayName("TC-005: Transaction history is recorded")
    void testTransactionHistory_IsRecorded() {
        account.deposit(500.0, "Deposit 1");
        account.withdraw(200.0, "Withdrawal 1");
        
        int historySize = account.getTransactionHistory().size();
        assertTrue(historySize >= 3, "History should contain at least 3 transactions");
    }

    // ============== NEGATIVE TEST CASES ==============

    @Test
    @DisplayName("TC-006: Negative deposit amount throws exception")
    void testDeposit_NegativeAmount_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-100.0, "Invalid"));
    }

    @Test
    @DisplayName("TC-007: Withdrawal exceeding balance throws exception")
    void testWithdraw_InsufficientBalance_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(10000.0, "Too much"));
    }

    @Test
    @DisplayName("TC-008: Invalid account creation throws exception")
    void testAccountCreation_EmptyName_ThrowsException() {
        assertThrows(IllegalArgumentException.class, 
                () -> new BankAccount("ACC002", "", 1000.0, "Savings"));
    }

    // ============== BOUNDARY TEST CASES ==============

    @Test
    @DisplayName("TC-009: Deposit of zero amount throws exception")
    void testDeposit_ZeroAmount_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> account.deposit(0.0, "Zero"));
    }

    @Test
    @DisplayName("TC-010: Withdrawal of zero amount throws exception")
    void testWithdraw_ZeroAmount_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(0.0, "Zero"));
    }

    @Test
    @DisplayName("TC-011: Withdrawal of exact balance successful")
    void testWithdraw_ExactBalance_Successful() {
        account.withdraw(5000.0, "Complete withdrawal");
        assertEquals(0.0, account.getBalance(), 0.01);
    }

    // ============== PARAMETERIZED TEST CASES ==============

    @ParameterizedTest
    @ValueSource(doubles = {100.0, 500.0, 1000.0, 2000.0})
    @DisplayName("TC-012: Multiple deposit amounts succeed")
    void testDeposit_MultipleAmounts_Success(double amount) {
        double initialBalance = account.getBalance();
        account.deposit(amount, "Parameterized deposit");
        assertEquals(initialBalance + amount, account.getBalance(), 0.01);
    }

    @ParameterizedTest
    @ValueSource(doubles = {100.0, 500.0, 1000.0})
    @DisplayName("TC-013: Multiple withdrawal amounts succeed")
    void testWithdraw_MultipleAmounts_Success(double amount) {
        double initialBalance = account.getBalance();
        account.withdraw(amount, "Parameterized withdrawal");
        assertEquals(initialBalance - amount, account.getBalance(), 0.01);
    }

    @Test
    @DisplayName("TC-014: Statement generation is non-null")
    void testGenerateStatement_IsNotNull() {
        String statement = account.generateStatement();
        assertNotNull(statement);
        assertTrue(statement.contains("ACCOUNT STATEMENT"));
        assertTrue(statement.contains("John Doe"));
    }
}
