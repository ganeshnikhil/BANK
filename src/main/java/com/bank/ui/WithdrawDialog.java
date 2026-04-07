package com.bank.ui;

import com.bank.model.AccountManager;
import com.bank.model.BankAccount;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog for withdrawing funds from an account.
 * Uses Apple macOS theme for consistent styling.
 */
public class WithdrawDialog extends JDialog {
    private JComboBox<String> accountCombo;
    private JTextField amountField;
    private JTextField descriptionField;

    /**
     * Constructor for Withdraw Dialog.
     * @param parent parent frame
     * @param accountManager account manager instance
     */
    public WithdrawDialog(JFrame parent, AccountManager accountManager) {
        super(parent, "Withdraw Funds", true);
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
        loadAccountsCombo(accountManager);

        // Amount
        JLabel amountLabel = new JLabel("Withdrawal Amount (₹):");
        amountLabel.setFont(AppleTheme.getBoldFont(12));
        amountLabel.setForeground(AppleTheme.TEXT_PRIMARY);
        amountField = new JTextField();
        amountField.setFont(AppleTheme.getLabelFont(12));

        // Description
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setFont(AppleTheme.getBoldFont(12));
        descriptionLabel.setForeground(AppleTheme.TEXT_PRIMARY);
        descriptionField = new JTextField("Withdrawal");
        descriptionField.setFont(AppleTheme.getLabelFont(12));

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

        JButton withdrawButton = PremiumComponents.createPremiumButton("Withdraw", AppleTheme.ACCENT_RED);
        withdrawButton.addActionListener(e -> performWithdraw(accountManager));

        JButton cancelButton = PremiumComponents.createPremiumButton("Cancel", AppleTheme.TEXT_SECONDARY);
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(withdrawButton);
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
     * Performs the withdrawal operation.
     * @param accountManager the account manager
     */
    private void performWithdraw(AccountManager accountManager) {
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
                description = "Withdrawal";
            }

            BankAccount account = accountManager.getAccount(accountNumber);
            account.withdraw(amount, description);

            JOptionPane.showMessageDialog(this, 
                    "Withdrawal successful!\nNew Balance: ₹" + String.format("%.2f", account.getBalance()),
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
