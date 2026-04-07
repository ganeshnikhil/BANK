package com.bank.ui;

import com.bank.model.AccountManager;
import com.bank.model.BankAccount;
import com.bank.model.Transaction;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog for viewing account statements and transaction history.
 */
public class ViewStatementDialog extends JDialog {
    /**
     * Constructor for View Statement Dialog.
     * @param parent parent frame
     * @param accountManager account manager instance
     */
    public ViewStatementDialog(JFrame parent, AccountManager accountManager) {
        super(parent, "View Statement", true);
        setSize(700, 600);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(AppleTheme.BG_PRIMARY);

        // Account Selection Panel
        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        selectionPanel.setBackground(AppleTheme.BG_PRIMARY);

        JLabel selectLabel = new JLabel("Select Account:");
        selectLabel.setFont(AppleTheme.getBoldFont(12));
        selectLabel.setForeground(AppleTheme.TEXT_PRIMARY);

        JComboBox<String> accountCombo = new JComboBox<>();
        accountCombo.setFont(AppleTheme.getLabelFont(12));
        accountCombo.setPreferredSize(new Dimension(300, 30));

        for (BankAccount account : accountManager.getAllAccounts()) {
            accountCombo.addItem(account.getAccountNumber() + " - " + account.getAccountHolderName());
        }

        selectionPanel.add(selectLabel);
        selectionPanel.add(accountCombo);

        // Statement Display Panel
        JTextArea statementArea = new JTextArea();
        statementArea.setFont(new Font("Courier New", Font.PLAIN, 11));
        statementArea.setEditable(false);
        statementArea.setMargin(new Insets(10, 10, 10, 10));
        statementArea.setBackground(AppleTheme.BG_SECONDARY);
        statementArea.setForeground(AppleTheme.TEXT_PRIMARY);

        JScrollPane scrollPane = new JScrollPane(statementArea);

        // Update statement when selection changes
        accountCombo.addActionListener(e -> {
            statementArea.setText("");
            if (accountCombo.getSelectedItem() != null) {
                String selected = (String) accountCombo.getSelectedItem();
                String accountNumber = selected.split(" - ")[0];
                BankAccount account = accountManager.getAccount(accountNumber);
                if (account != null) {
                    statementArea.append(account.generateStatement());
                    statementArea.append("\n");
                    statementArea.append("TRANSACTION HISTORY:\n");
                    statementArea.append("=====================================================\n");
                    for (Transaction transaction : account.getTransactionHistory()) {
                        statementArea.append(transaction.getFormattedString() + "\n");
                    }
                }
            }
        });

        // Close Button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(AppleTheme.BG_PRIMARY);

        JButton closeButton = PremiumComponents.createPremiumButton("Close", AppleTheme.TEXT_SECONDARY);
        closeButton.addActionListener(e -> dispose());

        buttonPanel.add(closeButton);

        panel.add(selectionPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
        
        // Load initial account if available
        if (accountCombo.getItemCount() > 0) {
            accountCombo.setSelectedIndex(0);
        }
    }
}
