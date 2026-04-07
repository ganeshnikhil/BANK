package com.bank.ui;

import com.bank.model.AccountManager;
import com.bank.model.BankAccount;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog for depositing funds into an account.
 * Uses Apple macOS theme for consistent styling.
 */
public class DepositDialog extends JDialog {
    private JComboBox<String> accountCombo;
    private JTextField amountField;
    private JTextField descriptionField;

    /**
     * Constructor for Deposit Dialog.
     * @param parent parent frame
     * @param accountManager account manager instance
     */
    public DepositDialog(JFrame parent, AccountManager accountManager) {
        super(parent, "Deposit Funds", true);
        setSize(500, 350);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(AppleTheme.BG_PRIMARY);

        // Select Account
        JLabel accountLabel = new JLabel("Select Account:");
        accountLabel.setFont(AppleTheme.getBoldFont(12));
        accountLabel.setForeground(AppleTheme.TEXT_PRIMARY);
        accountCombo = new JComboBox<>();
        accountCombo.setFont(AppleTheme.getLabelFont(12));
        accountCombo.setBackground(AppleTheme.BG_SECONDARY);
        accountCombo.setForeground(AppleTheme.TEXT_PRIMARY);
        loadAccountsCombo(accountManager);

        // Amount
        JLabel amountLabel = new JLabel("Deposit Amount (₹):");
        amountLabel.setFont(AppleTheme.getBoldFont(12));
        amountLabel.setForeground(AppleTheme.TEXT_PRIMARY);
        amountField = new JTextField();
        amountField.setFont(AppleTheme.getLabelFont(12));
        amountField.setBackground(AppleTheme.BG_SECONDARY);
        amountField.setForeground(AppleTheme.TEXT_PRIMARY);

        // Description
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setFont(AppleTheme.getBoldFont(12));
        descriptionLabel.setForeground(AppleTheme.TEXT_PRIMARY);
        descriptionField = new JTextField("Deposit");
        descriptionField.setFont(AppleTheme.getLabelFont(12));
        descriptionField.setBackground(AppleTheme.BG_SECONDARY);
        descriptionField.setForeground(AppleTheme.TEXT_PRIMARY);

        panel.add(accountLabel);
        panel.add(accountCombo);
        panel.add(amountLabel);
        panel.add(amountField);
        panel.add(descriptionLabel);
        panel.add(descriptionField);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(AppleTheme.BG_PRIMARY);

        JButton depositButton = PremiumComponents.createPremiumButton("Deposit", AppleTheme.ACCENT_GREEN);
        depositButton.addActionListener(e -> performDeposit(accountManager));

        JButton cancelButton = PremiumComponents.createPremiumButton("Cancel", AppleTheme.TEXT_SECONDARY);
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(depositButton);
        buttonPanel.add(cancelButton);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Loads accounts into the combo box.
     * @param accountManager the account manager
     */
    private void loadAccountsCombo(AccountManager accountManager) {
        accountCombo.removeAllItems();
        for (BankAccount account : accountManager.getAllAccounts()) {
            accountCombo.addItem(account.getAccountNumber() + " - " + account.getAccountHolderName());
        }
    }

    /**
     * Performs the deposit operation.
     * @param accountManager the account manager
     */
    private void performDeposit(AccountManager accountManager) {
        try {
            if (accountCombo.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "No accounts available", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String selected = (String) accountCombo.getSelectedItem();
            String accountNumber = selected.split(" - ")[0];
            double amount = Double.parseDouble(amountField.getText().trim());
            String description = descriptionField.getText().trim();

            if (description.isEmpty()) {
                description = "Deposit";
            }

            BankAccount account = accountManager.getAccount(accountNumber);
            account.deposit(amount, description);

            JOptionPane.showMessageDialog(this, 
                    "Deposit successful!\nNew Balance: ₹" + String.format("%.2f", account.getBalance()),
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
