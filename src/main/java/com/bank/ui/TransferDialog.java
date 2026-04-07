package com.bank.ui;

import com.bank.model.AccountManager;
import com.bank.model.BankAccount;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog for transferring funds between accounts.
 */
public class TransferDialog extends JDialog {
    private JComboBox<String> fromAccountCombo;
    private JComboBox<String> toAccountCombo;
    private JTextField amountField;

    /**
     * Constructor for Transfer Dialog.
     * @param parent parent frame
     * @param accountManager account manager instance
     */
    public TransferDialog(JFrame parent, AccountManager accountManager) {
        super(parent, "Transfer Funds", true);
        setSize(500, 350);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(AppleTheme.BG_PRIMARY);

        // From Account
        JLabel fromLabel = new JLabel("From Account:");
        fromLabel.setFont(AppleTheme.getBoldFont(12));
        fromLabel.setForeground(AppleTheme.TEXT_PRIMARY);
        fromAccountCombo = new JComboBox<>();
        fromAccountCombo.setFont(AppleTheme.getLabelFont(12));
        loadAccountsCombo(fromAccountCombo, accountManager);

        // To Account
        JLabel toLabel = new JLabel("To Account:");
        toLabel.setFont(AppleTheme.getBoldFont(12));
        toLabel.setForeground(AppleTheme.TEXT_PRIMARY);
        toAccountCombo = new JComboBox<>();
        toAccountCombo.setFont(AppleTheme.getLabelFont(12));
        loadAccountsCombo(toAccountCombo, accountManager);

        // Amount
        JLabel amountLabel = new JLabel("Transfer Amount (₹):");
        amountLabel.setFont(AppleTheme.getBoldFont(12));
        amountLabel.setForeground(AppleTheme.TEXT_PRIMARY);
        amountField = new JTextField();
        amountField.setFont(AppleTheme.getLabelFont(12));

        panel.add(fromLabel);
        panel.add(fromAccountCombo);
        panel.add(toLabel);
        panel.add(toAccountCombo);
        panel.add(amountLabel);
        panel.add(amountField);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(AppleTheme.BG_PRIMARY);

        JButton transferButton = PremiumComponents.createPremiumButton("Transfer", AppleTheme.ACCENT_BLUE);
        transferButton.addActionListener(e -> performTransfer(accountManager));

        JButton cancelButton = PremiumComponents.createPremiumButton("Cancel", AppleTheme.TEXT_SECONDARY);
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(transferButton);
        buttonPanel.add(cancelButton);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Loads accounts into a combo box.
     * @param combo the combo box to load
     * @param accountManager the account manager
     */
    private void loadAccountsCombo(JComboBox<String> combo, AccountManager accountManager) {
        combo.removeAllItems();
        for (BankAccount account : accountManager.getAllAccounts()) {
            combo.addItem(account.getAccountNumber() + " - " + account.getAccountHolderName());
        }
    }

    /**
     * Performs the transfer operation.
     * @param accountManager the account manager
     */
    private void performTransfer(AccountManager accountManager) {
        try {
            if (fromAccountCombo.getItemCount() == 0 || toAccountCombo.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "Need at least 2 accounts to transfer", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String fromSelected = (String) fromAccountCombo.getSelectedItem();
            String toSelected = (String) toAccountCombo.getSelectedItem();
            String fromAccountNumber = fromSelected.split(" - ")[0];
            String toAccountNumber = toSelected.split(" - ")[0];
            double amount = Double.parseDouble(amountField.getText().trim());

            if (fromAccountNumber.equals(toAccountNumber)) {
                JOptionPane.showMessageDialog(this, "Cannot transfer to the same account", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            accountManager.transferFunds(fromAccountNumber, toAccountNumber, amount);

            BankAccount fromAccount = accountManager.getAccount(fromAccountNumber);
            JOptionPane.showMessageDialog(this, 
                    "Transfer successful!\nNew Balance: ₹" + String.format("%.2f", fromAccount.getBalance()),
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
