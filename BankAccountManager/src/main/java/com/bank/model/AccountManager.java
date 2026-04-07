package com.bank.model;

import java.io.Serializable;
import java.util.*;

/**
 * Manages multiple bank accounts and facilitates inter-account transfers.
 * Provides functionality to create, delete, and manage bank accounts.
 */
public class AccountManager implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, BankAccount> accounts;
    private static AccountManager instance;

    /**
     * Private constructor for singleton pattern.
     */
    private AccountManager() {
        this.accounts = new HashMap<>();
    }

    /**
     * Gets the singleton instance of AccountManager.
     * @return the AccountManager instance
     */
    public static synchronized AccountManager getInstance() {
        if (instance == null) {
            instance = new AccountManager();
        }
        return instance;
    }

    /**
     * Creates a new bank account.
     * @param accountNumber unique account number
     * @param accountHolderName name of account holder
     * @param initialBalance initial balance
     * @param accountType type of account
     * @return the created BankAccount
     * @throws IllegalArgumentException if account already exists or invalid inputs
     */
    public BankAccount createAccount(String accountNumber, String accountHolderName, 
                                     double initialBalance, String accountType) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be empty");
        }
        if (accounts.containsKey(accountNumber)) {
            throw new IllegalArgumentException("Account with number " + accountNumber + " already exists");
        }

        BankAccount account = new BankAccount(accountNumber, accountHolderName, initialBalance, accountType);
        accounts.put(accountNumber, account);
        return account;
    }

    /**
     * Retrieves an account by account number.
     * @param accountNumber the account number to search
     * @return the BankAccount or null if not found
     */
    public BankAccount getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    /**
     * Gets all accounts.
     * @return collection of all bank accounts
     */
    public Collection<BankAccount> getAllAccounts() {
        return accounts.values();
    }

    /**
     * Deletes an account.
     * @param accountNumber the account number to delete
     * @throws IllegalArgumentException if account not found
     */
    public void deleteAccount(String accountNumber) {
        if (!accounts.containsKey(accountNumber)) {
            throw new IllegalArgumentException("Account not found: " + accountNumber);
        }
        accounts.remove(accountNumber);
    }

    /**
     * Transfers money from one account to another.
     * @param fromAccountNumber source account number
     * @param toAccountNumber destination account number
     * @param amount transfer amount
     * @throws IllegalArgumentException if transfer is invalid
     */
    public void transferFunds(String fromAccountNumber, String toAccountNumber, double amount) {
        if (fromAccountNumber == null || fromAccountNumber.equals(toAccountNumber)) {
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }

        BankAccount fromAccount = getAccount(fromAccountNumber);
        if (fromAccount == null) {
            throw new IllegalArgumentException("Source account not found: " + fromAccountNumber);
        }

        BankAccount toAccount = getAccount(toAccountNumber);
        if (toAccount == null) {
            throw new IllegalArgumentException("Destination account not found: " + toAccountNumber);
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be greater than 0");
        }

        if (amount > fromAccount.getBalance()) {
            throw new IllegalArgumentException("Insufficient balance in source account");
        }

        // Perform transfer
        fromAccount.withdraw(amount, "Transfer to " + toAccountNumber + " (" + toAccount.getAccountHolderName() + ")");
        toAccount.deposit(amount, "Transfer from " + fromAccountNumber + " (" + fromAccount.getAccountHolderName() + ")");
    }

    /**
     * Searches for accounts by account holder name (case-insensitive).
     * @param name the name to search for
     * @return list of matching accounts
     */
    public List<BankAccount> searchByName(String name) {
        List<BankAccount> results = new ArrayList<>();
        if (name == null || name.trim().isEmpty()) {
            return results;
        }

        String searchTerm = name.toLowerCase();
        for (BankAccount account : accounts.values()) {
            if (account.getAccountHolderName().toLowerCase().contains(searchTerm)) {
                results.add(account);
            }
        }
        return results;
    }

    /**
     * Gets the total balance across all accounts.
     * @return total balance sum
     */
    public double getTotalBalance() {
        return accounts.values().stream()
                .mapToDouble(BankAccount::getBalance)
                .sum();
    }

    /**
     * Gets the number of active accounts.
     * @return number of accounts
     */
    public int getAccountCount() {
        return accounts.size();
    }

    /**
     * Resets the AccountManager (for testing purposes).
     */
    public void reset() {
        accounts.clear();
    }
}
