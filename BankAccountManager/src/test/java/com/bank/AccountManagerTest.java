package com.bank;

import com.bank.model.AccountManager;
import com.bank.model.BankAccount;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test cases for AccountManager class.
 * Tests account creation, deletion, transfer, and search operations.
 */
@DisplayName("Account Manager Tests")
class AccountManagerTest {
    private AccountManager accountManager;

    @BeforeEach
    void setUp() {
        accountManager = AccountManager.getInstance();
        accountManager.reset();
    }

    @AfterEach
    void tearDown() {
        accountManager.reset();
    }

    // ============== POSITIVE TEST CASES ==============

    @Test
    @DisplayName("TC-015: Create account successfully")
    void testCreateAccount_Success() {
        BankAccount account = accountManager.createAccount("ACC001", "Alice Smith", 10000.0, "Savings");
        assertNotNull(account);
        assertEquals("ACC001", account.getAccountNumber());
        assertEquals("Alice Smith", account.getAccountHolderName());
    }

    @Test
    @DisplayName("TC-016: Get account by account number")
    void testGetAccount_Success() {
        accountManager.createAccount("ACC001", "Bob Johnson", 5000.0, "Current");
        BankAccount account = accountManager.getAccount("ACC001");
        assertNotNull(account);
        assertEquals("Bob Johnson", account.getAccountHolderName());
    }

    @Test
    @DisplayName("TC-017: Transfer funds between accounts")
    void testTransferFunds_Success() {
        accountManager.createAccount("ACC001", "Sender", 10000.0, "Savings");
        accountManager.createAccount("ACC002", "Receiver", 5000.0, "Savings");

        accountManager.transferFunds("ACC001", "ACC002", 3000.0);

        BankAccount sender = accountManager.getAccount("ACC001");
        BankAccount receiver = accountManager.getAccount("ACC002");

        assertEquals(7000.0, sender.getBalance(), 0.01);
        assertEquals(8000.0, receiver.getBalance(), 0.01);
    }

    @Test
    @DisplayName("TC-018: Delete account successfully")
    void testDeleteAccount_Success() {
        accountManager.createAccount("ACC001", "Charlie Brown", 2000.0, "Savings");
        assertNotNull(accountManager.getAccount("ACC001"));

        accountManager.deleteAccount("ACC001");
        assertNull(accountManager.getAccount("ACC001"));
    }

    @Test
    @DisplayName("TC-019: Search account by name")
    void testSearchByName_Success() {
        accountManager.createAccount("ACC001", "Diana Prince", 5000.0, "Savings");
        accountManager.createAccount("ACC002", "Diana Ross", 3000.0, "Current");

        List<BankAccount> results = accountManager.searchByName("Diana");
        assertEquals(2, results.size());
    }

    // ============== NEGATIVE TEST CASES ==============

    @Test
    @DisplayName("TC-020: Create duplicate account throws exception")
    void testCreateAccount_Duplicate_ThrowsException() {
        accountManager.createAccount("ACC001", "Test User", 1000.0, "Savings");
        assertThrows(IllegalArgumentException.class,
                () -> accountManager.createAccount("ACC001", "Another User", 2000.0, "Current"));
    }

    @Test
    @DisplayName("TC-021: Transfer to non-existent account throws exception")
    void testTransferFunds_InvalidAccount_ThrowsException() {
        accountManager.createAccount("ACC001", "Sender", 10000.0, "Savings");
        assertThrows(IllegalArgumentException.class,
                () -> accountManager.transferFunds("ACC001", "ACC999", 1000.0));
    }

    @Test
    @DisplayName("TC-022: Delete non-existent account throws exception")
    void testDeleteAccount_NotFound_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> accountManager.deleteAccount("ACC999"));
    }

    // ============== BOUNDARY TEST CASES ==============

    @Test
    @DisplayName("TC-023: Create account with zero balance")
    void testCreateAccount_ZeroBalance_Success() {
        BankAccount account = accountManager.createAccount("ACC001", "Zero Balance User", 0.0, "Savings");
        assertEquals(0.0, account.getBalance(), 0.01);
    }

    @Test
    @DisplayName("TC-024: Transfer entire account balance")
    void testTransferFunds_CompleteBalance_Success() {
        accountManager.createAccount("ACC001", "Full Transfer Sender", 5000.0, "Savings");
        accountManager.createAccount("ACC002", "Receiver", 0.0, "Savings");

        accountManager.transferFunds("ACC001", "ACC002", 5000.0);

        BankAccount sender = accountManager.getAccount("ACC001");
        BankAccount receiver = accountManager.getAccount("ACC002");

        assertEquals(0.0, sender.getBalance(), 0.01);
        assertEquals(5000.0, receiver.getBalance(), 0.01);
    }

    @Test
    @DisplayName("TC-025: Get total balance across all accounts")
    void testGetTotalBalance_Success() {
        accountManager.createAccount("ACC001", "User1", 5000.0, "Savings");
        accountManager.createAccount("ACC002", "User2", 3000.0, "Savings");
        accountManager.createAccount("ACC003", "User3", 2000.0, "Savings");

        double totalBalance = accountManager.getTotalBalance();
        assertEquals(10000.0, totalBalance, 0.01);
    }

    @Test
    @DisplayName("TC-026: Get account count")
    void testGetAccountCount_Success() {
        accountManager.createAccount("ACC001", "User1", 1000.0, "Savings");
        accountManager.createAccount("ACC002", "User2", 2000.0, "Current");

        assertEquals(2, accountManager.getAccountCount());
    }
}
