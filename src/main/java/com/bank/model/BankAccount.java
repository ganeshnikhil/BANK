package com.bank.model;

import java.io.Serializable;
import java.util.*;

/**
 * Represents a single bank account with balance, account holder details, and transaction history.
 * Provides methods for deposit, withdrawal, and balance inquiry operations.
 */
public class BankAccount implements Serializable {
    private static final long serialVersionUID = 1L;

    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private String accountType; // Savings, Current, etc.
    private List<Transaction> transactionHistory;
    private static final double MINIMUM_BALANCE = 0.0;
    private static final double MAXIMUM_WITHDRAWAL = 100000.0;
    private static final double SAVINGS_INTEREST_RATE = 0.04; // 4% per annum
    private static final double CURRENT_INTEREST_RATE = 0.0;  // No interest
    private static final double STUDENT_INTEREST_RATE = 0.06; // 6% per annum

    /**
     * Constructor for creating a new bank account.
     * @param accountNumber unique account number
     * @param accountHolderName name of the account holder
     * @param initialBalance initial balance (must be >= 0)
     * @param accountType type of account
     */
    public BankAccount(String accountNumber, String accountHolderName, double initialBalance, String accountType) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be empty");
        }
        if (accountHolderName == null || accountHolderName.trim().isEmpty()) {
            throw new IllegalArgumentException("Account holder name cannot be empty");
        }
        if (initialBalance < MINIMUM_BALANCE) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        if (initialBalance > MAXIMUM_WITHDRAWAL * 10) {
            throw new IllegalArgumentException("Initial balance exceeds maximum limit");
        }

        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
        this.accountType = accountType;
        this.transactionHistory = new ArrayList<>();

        if (initialBalance > 0) {
            this.transactionHistory.add(new Transaction(
                    Transaction.TransactionType.DEPOSIT,
                    initialBalance,
                    "Account opening balance",
                    balance
            ));
        }
    }

    /**
     * Deposits money into the account.
     * @param amount amount to deposit
     * @param description description of the deposit
     * @throws IllegalArgumentException if amount is invalid
     */
    public void deposit(double amount, String description) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than 0");
        }
        if (amount > MAXIMUM_WITHDRAWAL * 10) {
            throw new IllegalArgumentException("Deposit amount exceeds maximum limit");
        }

        this.balance += amount;
        transactionHistory.add(new Transaction(
                Transaction.TransactionType.DEPOSIT,
                amount,
                description,
                this.balance
        ));
    }

    /**
     * Withdraws money from the account.
     * @param amount amount to withdraw
     * @param description description of the withdrawal
     * @throws IllegalArgumentException if amount is invalid or insufficient balance
     */
    public void withdraw(double amount, String description) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than 0");
        }
        if (amount > MAXIMUM_WITHDRAWAL) {
            throw new IllegalArgumentException("Withdrawal amount exceeds maximum limit of ₹" + MAXIMUM_WITHDRAWAL);
        }
        if (amount > this.balance) {
            throw new IllegalArgumentException("Insufficient balance. Current balance: ₹" + String.format("%.2f", this.balance));
        }

        this.balance -= amount;
        transactionHistory.add(new Transaction(
                Transaction.TransactionType.WITHDRAWAL,
                amount,
                description,
                this.balance
        ));
    }

    /**
     * Gets the current account balance.
     * @return the current balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Gets the account number.
     * @return the account number
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Gets the account holder name.
     * @return the account holder name
     */
    public String getAccountHolderName() {
        return accountHolderName;
    }

    /**
     * Gets the account type.
     * @return the account type
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * Gets the transaction history.
     * @return list of transactions sorted by most recent first
     */
    public List<Transaction> getTransactionHistory() {
        List<Transaction> sorted = new ArrayList<>(transactionHistory);
        Collections.sort(sorted);
        return sorted;
    }

    /**
     * Gets a subset of recent transactions.
     * @param limit maximum number of transactions to return
     * @return list of recent transactions
     */
    public List<Transaction> getRecentTransactions(int limit) {
        List<Transaction> recent = getTransactionHistory();
        return recent.subList(0, Math.min(limit, recent.size()));
    }

    /**
     * Gets the annual interest rate for this account type.
     * @return the annual interest rate as a decimal (e.g., 0.04 for 4%)
     */
    private double getInterestRate() {
        if (accountType.equalsIgnoreCase("Savings")) {
            return SAVINGS_INTEREST_RATE;
        } else if (accountType.equalsIgnoreCase("Student")) {
            return STUDENT_INTEREST_RATE;
        } else {
            return CURRENT_INTEREST_RATE;
        }
    }

    /**
     * Calculates annual interest on the current balance.
     * @return the calculated interest amount
     */
    public double calculateAnnualInterest() {
        return balance * getInterestRate();
    }

    /**
     * Applies annual interest to the account.
     * Interest is added as a transaction and reflected in the balance.
     * @throws IllegalArgumentException if account type does not earn interest
     */
    public void applyInterest() {
        double interestAmount = calculateAnnualInterest();
        if (interestAmount <= 0) {
            throw new IllegalArgumentException("This account type (" + accountType + ") does not earn interest");
        }
        this.balance += interestAmount;
        transactionHistory.add(new Transaction(
                Transaction.TransactionType.INTEREST,
                interestAmount,
                "Annual interest credited",
                this.balance
        ));
    }

    /**
     * Generates an account statement.
     * @return formatted account statement
     */
    public String generateStatement() {
        StringBuilder statement = new StringBuilder();
        statement.append("================== ACCOUNT STATEMENT ==================\n");
        statement.append("Account Number: ").append(accountNumber).append("\n");
        statement.append("Account Holder: ").append(accountHolderName).append("\n");
        statement.append("Account Type: ").append(accountType).append("\n");
        statement.append("Annual Interest Rate: ").append(String.format("%.2f", getInterestRate() * 100)).append("%\n");
        statement.append("Current Balance: ₹").append(String.format("%.2f", balance)).append("\n");
        statement.append("Total Transactions: ").append(transactionHistory.size()).append("\n");
        statement.append("======================================================\n");
        return statement.toString();
    }

    @Override
    public String toString() {
        return String.format("Account: %s | Holder: %s | Balance: ₹%.2f", 
                accountNumber, accountHolderName, balance);
    }
}
