package com.bank;

import com.bank.model.Transaction;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test cases for Transaction class.
 * Tests transaction creation and properties.
 */
@DisplayName("Transaction Tests")
class TransactionTest {
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        transaction = new Transaction(
                Transaction.TransactionType.DEPOSIT,
                1000.0,
                "Test deposit",
                6000.0
        );
    }

    @AfterEach
    void tearDown() {
        transaction = null;
    }

    // ============== POSITIVE TEST CASES ==============

    @Test
    @DisplayName("TC-027: Create transaction with valid data")
    void testTransactionCreation_Valid() {
        assertNotNull(transaction);
        assertEquals(Transaction.TransactionType.DEPOSIT, transaction.getType());
        assertEquals(1000.0, transaction.getAmount(), 0.01);
        assertEquals("Test deposit", transaction.getDescription());
        assertEquals(6000.0, transaction.getBalanceAfter(), 0.01);
    }

    @Test
    @DisplayName("TC-028: Get transaction timestamp is not null")
    void testGetTimestamp_NotNull() {
        assertNotNull(transaction.getTimestamp());
    }

    @Test
    @DisplayName("TC-029: Get formatted string is not null")
    void testGetFormattedString_NotNull() {
        String formatted = transaction.getFormattedString();
        assertNotNull(formatted);
        assertTrue(formatted.contains("₹"));
        assertTrue(formatted.contains("Deposit"));
    }

    @Test
    @DisplayName("TC-030: Transaction types have display names")
    void testTransactionType_DisplayName() {
        assertEquals("Deposit", Transaction.TransactionType.DEPOSIT.getDisplayName());
        assertEquals("Withdrawal", Transaction.TransactionType.WITHDRAWAL.getDisplayName());
        assertEquals("Transfer Out", Transaction.TransactionType.TRANSFER_OUT.getDisplayName());
        assertEquals("Transfer In", Transaction.TransactionType.TRANSFER_IN.getDisplayName());
    }

    // ============== NEGATIVE TEST CASES ==============

    @Test
    @DisplayName("TC-031: Zero amount transaction throws exception")
    void testTransactionCreation_ZeroAmount_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Transaction(Transaction.TransactionType.DEPOSIT, 0.0, "Invalid", 5000.0));
    }

    @Test
    @DisplayName("TC-032: Negative amount transaction throws exception")
    void testTransactionCreation_NegativeAmount_ThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Transaction(Transaction.TransactionType.DEPOSIT, -500.0, "Invalid", 5000.0));
    }

    // ============== BOUNDARY TEST CASES ==============

    @Test
    @DisplayName("TC-033: Very small positive amount accepted")
    void testTransactionCreation_SmallAmount_Success() {
        Transaction smallTransaction = new Transaction(
                Transaction.TransactionType.DEPOSIT,
                0.01,
                "Penny",
                5000.01
        );
        assertEquals(0.01, smallTransaction.getAmount(), 0.001);
    }

    @ParameterizedTest
    @ValueSource(doubles = {100.0, 1000.0, 50000.0})
    @DisplayName("TC-034: Multiple transaction amounts succeed")
    void testTransactionCreation_MultipleAmounts_Success(double amount) {
        Transaction paramTransaction = new Transaction(
                Transaction.TransactionType.WITHDRAWAL,
                amount,
                "Test",
                5000.0 - amount
        );
        assertEquals(amount, paramTransaction.getAmount(), 0.01);
    }
}
