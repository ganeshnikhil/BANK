package com.bank.ui;

import com.bank.model.AccountManager;
import com.bank.model.BankAccount;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog for creating a new bank account.
 * Uses Apple macOS theme for consistent styling.
 */
public class CreateAccountDialog extends JDialog {
    private JTextField accountNumberField;
    private JTextField nameField;
    private JTextField balanceField;
    private JComboBox<String> accountTypeCombo;

    /**
     * Constructor for Create Account Dialog.
     * @param parent parent frame
     * @param accountManager account manager instance
     */
    public CreateAccountDialog(JFrame parent, AccountManager accountManager) {
        super(parent, "Create New Account", true);
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(AppleTheme.BG_PRIMARY);

        // Account Number
        JLabel accountNumberLabel = new JLabel("Account Number:");
        accountNumberLabel.setFont(AppleTheme.getBoldFont(12));
        accountNumberLabel.setForeground(AppleTheme.TEXT_PRIMARY);
        accountNumberField = new JTextField();
        accountNumberField.setFont(AppleTheme.getLabelFont(12));

        // Account Holder Name
        JLabel nameLabel = new JLabel("Account Holder Name:");
        nameLabel.setFont(AppleTheme.getBoldFont(12));
        nameLabel.setForeground(AppleTheme.TEXT_PRIMARY);
        nameField = new JTextField();
        nameField.setFont(AppleTheme.getLabelFont(12));

        // Initial Balance
        JLabel balanceLabel = new JLabel("Initial Balance (₹):");
        balanceLabel.setFont(AppleTheme.getBoldFont(12));
        balanceLabel.setForeground(AppleTheme.TEXT_PRIMARY);
        balanceField = new JTextField("0.00");
        balanceField.setFont(AppleTheme.getLabelFont(12));

        // Account Type
        JLabel accountTypeLabel = new JLabel("Account Type:");
        accountTypeLabel.setFont(AppleTheme.getBoldFont(12));
        accountTypeLabel.setForeground(AppleTheme.TEXT_PRIMARY);
        accountTypeCombo = new JComboBox<>(new String[]{"Savings", "Current", "Student"});
        accountTypeCombo.setFont(AppleTheme.getLabelFont(12));

        panel.add(accountNumberLabel);
        panel.add(accountNumberField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(balanceLabel);
        panel.add(balanceField);
        panel.add(accountTypeLabel);
        panel.add(accountTypeCombo);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(AppleTheme.BG_PRIMARY);

        JButton createButton = PremiumComponents.createPremiumButton("Create Account", AppleTheme.ACCENT_BLUE);
        createButton.addActionListener(e -> createAccount(accountManager));

        JButton cancelButton = PremiumComponents.createPremiumButton("Cancel", AppleTheme.TEXT_SECONDARY);
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(createButton);
        buttonPanel.add(cancelButton);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates the account with validated input.
     * @param accountManager the account manager
     */
    private void createAccount(AccountManager accountManager) {
        try {
            String accountNumber = accountNumberField.getText().trim();
            String name = nameField.getText().trim();
            double balance = Double.parseDouble(balanceField.getText().trim());
            String accountType = (String) accountTypeCombo.getSelectedItem();

            if (accountNumber.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            BankAccount account = accountManager.createAccount(accountNumber, name, balance, accountType);
            JOptionPane.showMessageDialog(this, "Account created successfully!\nAccount Number: " + account.getAccountNumber(), 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid balance amount", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
