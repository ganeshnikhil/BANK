package com.bank.ui;

import com.bank.model.AccountManager;
import com.bank.model.BankAccount;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog for applying annual interest to a selected account.
 * Displays account information and allows users to apply interest.
 */
public class InterestDialog extends JDialog {
    private AccountManager accountManager;
    private JComboBox<String> accountComboBox;

    /**
     * Constructor for the Interest Dialog.
     * @param parent the parent frame
     * @param accountManager the account manager
     */
    public InterestDialog(JFrame parent, AccountManager accountManager) {
        super(parent, "Apply Interest", true);
        this.accountManager = accountManager;
        initializeDialog();
    }

    /**
     * Initializes the dialog components with Apple theme.
     */
    private void initializeDialog() {
        setSize(500, 300);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(AppleTheme.BG_PRIMARY);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Title
        JLabel titleLabel = new JLabel("Apply Annual Interest");
        titleLabel.setFont(AppleTheme.getBoldFont(20));
        titleLabel.setForeground(AppleTheme.TEXT_PRIMARY);
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        // Account Selection
        JLabel accountLabel = new JLabel("Select Account:");
        accountLabel.setFont(AppleTheme.getBoldFont(14));
        accountLabel.setForeground(AppleTheme.TEXT_PRIMARY);
        gbc.gridy = 1;
        panel.add(accountLabel, gbc);

        accountComboBox = new JComboBox<>();
        populateAccountComboBox();
        accountComboBox.setFont(AppleTheme.getLabelFont(12));
        accountComboBox.setPreferredSize(new Dimension(400, 30));
        accountComboBox.addActionListener(e -> updateInterestInfo());
        gbc.gridy = 2;
        panel.add(accountComboBox, gbc);

        // Interest Information Panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(2, 2, 15, 10));
        infoPanel.setBackground(AppleTheme.BG_SECONDARY);
        infoPanel.setBorder(BorderFactory.createLineBorder(AppleTheme.BORDER_COLOR, 1));

        JLabel interestRateLabel = new JLabel("Interest Rate:");
        interestRateLabel.setFont(AppleTheme.getBoldFont(12));
        interestRateLabel.setForeground(AppleTheme.TEXT_PRIMARY);
        JLabel interestRateValue = new JLabel();
        interestRateValue.setFont(AppleTheme.getLabelFont(12));
        interestRateValue.setForeground(AppleTheme.ACCENT_BLUE);
        interestRateValue.setName("interestRateValue");

        JLabel annualInterestLabel = new JLabel("Annual Interest:");
        annualInterestLabel.setFont(AppleTheme.getBoldFont(12));
        annualInterestLabel.setForeground(AppleTheme.TEXT_PRIMARY);
        JLabel annualInterestValue = new JLabel();
        annualInterestValue.setFont(AppleTheme.getLabelFont(12));
        annualInterestValue.setForeground(AppleTheme.ACCENT_GREEN);
        annualInterestValue.setName("annualInterestValue");

        infoPanel.add(interestRateLabel);
        infoPanel.add(interestRateValue);
        infoPanel.add(annualInterestLabel);
        infoPanel.add(annualInterestValue);

        gbc.gridy = 3;
        gbc.insets = new Insets(20, 15, 20, 15);
        panel.add(infoPanel, gbc);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonsPanel.setBackground(AppleTheme.BG_PRIMARY);

        JButton applyButton = PremiumComponents.createPremiumButton("Apply Interest", AppleTheme.ACCENT_GREEN);
        applyButton.addActionListener(e -> applyInterest());

        JButton cancelButton = PremiumComponents.createPremiumButton("Cancel", AppleTheme.TEXT_SECONDARY);
        cancelButton.addActionListener(e -> dispose());

        buttonsPanel.add(applyButton);
        buttonsPanel.add(cancelButton);

        gbc.gridy = 4;
        gbc.insets = new Insets(15, 15, 15, 15);
        panel.add(buttonsPanel, gbc);

        add(panel);
        updateInterestInfo();
    }

    /**
     * Populates the account combo box with all available accounts.
     */
    private void populateAccountComboBox() {
        accountComboBox.removeAllItems();
        for (BankAccount account : accountManager.getAllAccounts()) {
            accountComboBox.addItem(account.getAccountNumber() + " - " + account.getAccountHolderName());
        }
    }

    /**
     * Updates the interest rate and annual interest display.
     */
    private void updateInterestInfo() {
        if (accountComboBox.getItemCount() == 0) {
            return;
        }

        String selectedItem = (String) accountComboBox.getSelectedItem();
        String accountNumber = selectedItem.split(" - ")[0];
        BankAccount account = accountManager.getAccount(accountNumber);

        if (account != null) {
            double interestRate = account.calculateAnnualInterest() / account.getBalance() * 100;
            if (account.getBalance() == 0) {
                interestRate = 0;
            }
            double annualInterest = account.calculateAnnualInterest();

            // Update labels by finding them in the panel
            Component[] components = ((JPanel) ((JPanel) getContentPane().getComponent(0)).getComponent(3)).getComponents();
            for (Component comp : components) {
                if (comp instanceof JLabel && "interestRateValue".equals(((JLabel) comp).getName())) {
                    ((JLabel) comp).setText(String.format("%.2f%%", interestRate));
                }
                if (comp instanceof JLabel && "annualInterestValue".equals(((JLabel) comp).getName())) {
                    ((JLabel) comp).setText("₹" + String.format("%.2f", annualInterest));
                }
            }
        }
    }

    /**
     * Applies interest to the selected account.
     */
    private void applyInterest() {
        if (accountComboBox.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "No accounts available", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String selectedItem = (String) accountComboBox.getSelectedItem();
        String accountNumber = selectedItem.split(" - ")[0];
        BankAccount account = accountManager.getAccount(accountNumber);

        if (account == null) {
            JOptionPane.showMessageDialog(this, "Account not found", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double interestAmount = account.calculateAnnualInterest();
            account.applyInterest();
            JOptionPane.showMessageDialog(this,
                    "Interest Applied Successfully!\n\n" +
                    "Account: " + account.getAccountNumber() + "\n" +
                    "Interest Amount: ₹" + String.format("%.2f", interestAmount) + "\n" +
                    "New Balance: ₹" + String.format("%.2f", account.getBalance()),
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
