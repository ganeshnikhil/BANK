package com.bank.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a single transaction in a bank account.
 * Tracks transaction type, amount, and timestamp.
 */
public class Transaction implements Serializable, Comparable<Transaction> {
    private static final long serialVersionUID = 1L;

    /** Transaction type enumeration */
    public enum TransactionType {
        DEPOSIT("Deposit"),
        WITHDRAWAL("Withdrawal"),
        TRANSFER_OUT("Transfer Out"),
        TRANSFER_IN("Transfer In"),
        INTEREST("Interest");

        private final String displayName;

        TransactionType(String displayName) {
            this.displayName = displayName;
        }

        /**
         * Gets the display name of the transaction type.
         * @return the display name
         */
        public String getDisplayName() {
            return displayName;
        }
    }

    private TransactionType type;
    private double amount;
    private LocalDateTime timestamp;
    private String description;
    private double balanceAfter;

    /**
     * Constructor for creating a transaction.
     * @param type the type of transaction
     * @param amount the transaction amount
     * @param description optional description of the transaction
     * @param balanceAfter the account balance after this transaction
     */
    public Transaction(TransactionType type, double amount, String description, double balanceAfter) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Transaction amount must be greater than 0");
        }
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.description = description;
        this.balanceAfter = balanceAfter;
    }

    /**
     * Gets the transaction type.
     * @return the transaction type
     */
    public TransactionType getType() {
        return type;
    }

    /**
     * Gets the transaction amount.
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Gets the transaction timestamp.
     * @return the timestamp
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Gets the transaction description.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the account balance after this transaction.
     * @return the balance after
     */
    public double getBalanceAfter() {
        return balanceAfter;
    }

    /**
     * Gets formatted transaction string for display.
     * @return formatted transaction string
     */
    public String getFormattedString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("[%s] %s: ₹%.2f | Balance: ₹%.2f | %s",
                timestamp.format(formatter),
                type.getDisplayName(),
                amount,
                balanceAfter,
                description);
    }

    @Override
    public int compareTo(Transaction other) {
        return other.timestamp.compareTo(this.timestamp); // Most recent first
    }

    @Override
    public String toString() {
        return getFormattedString();
    }
}
