package com.bank.ui;

import com.bank.model.AccountManager;
import com.bank.model.BankAccount;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Main GUI for the Bank Account Manager application.
 * Provides opening screen and navigation to all banking features.
 * Uses Apple macOS theme for native look and feel.
 */
public class BankManagerGUI extends JFrame {
    private AccountManager accountManager;
    private static final Color APPLE_BG = new Color(245, 245, 247); // macOS light gray
    private static final Color APPLE_ACCENT = new Color(0, 122, 255); // Apple blue
    private static final Color APPLE_TEXT = new Color(29, 29, 31); // macOS dark text
    private static final Color APPLE_SECONDARY_TEXT = new Color(142, 142, 147); // macOS secondary gray

    /**
     * Constructor for the Bank Manager GUI.
     */
    public BankManagerGUI() {
        // Set native macOS look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Fallback to default if system L&F fails
        }
        
        accountManager = AccountManager.getInstance();
        initializeFrame();
        showOpeningScreen();
    }

    /**
     * Initializes the main frame properties.
     */
    private void initializeFrame() {
        setTitle("Bank Account Manager");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
    }

    /**
     * Displays the opening screen.
     */
    private void showOpeningScreen() {
        JPanel openingPanel = new JPanel();
        openingPanel.setLayout(new GridBagLayout());
        openingPanel.setBackground(APPLE_BG);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Title
        JLabel titleLabel = new JLabel("Bank Account Manager");
        titleLabel.setFont(AppleTheme.getTitleFont(42));
        titleLabel.setForeground(APPLE_TEXT);
        gbc.gridy = 0;
        openingPanel.add(titleLabel, gbc);

        // Version
        JLabel versionLabel = new JLabel("v1.0 • Premium Edition");
        versionLabel.setFont(AppleTheme.getLabelFont(13));
        versionLabel.setForeground(APPLE_SECONDARY_TEXT);
        gbc.gridy = 1;
        openingPanel.add(versionLabel, gbc);

        // Separator
        JSeparator separator = new JSeparator();
        separator.setPreferredSize(new Dimension(300, 1));
        separator.setBackground(AppleTheme.BORDER_COLOR);
        separator.setForeground(AppleTheme.BORDER_COLOR);
        gbc.gridy = 2;
        openingPanel.add(separator, gbc);

        // Get Started Button
        JButton getStartedButton = PremiumComponents.createPremiumButton("Get Started", APPLE_ACCENT);
        getStartedButton.addActionListener(e -> showMainMenu());

        gbc.gridy = 3;
        gbc.insets = new Insets(20, 15, 15, 15);
        openingPanel.add(getStartedButton, gbc);

        setContentPane(openingPanel);
    }

    /**
     * Displays the main menu with banking features.
     */
    private void showMainMenu() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridBagLayout());
        menuPanel.setBackground(APPLE_BG);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Title
        JLabel titleLabel = new JLabel("Bank Account Manager - Main Menu");
        titleLabel.setFont(new Font("San Francisco", Font.BOLD, 26));
        titleLabel.setForeground(APPLE_TEXT);
        gbc.gridy = 0;
        menuPanel.add(titleLabel, gbc);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(7, 1, 10, 15));
        buttonsPanel.setBackground(APPLE_BG);

        // Create Account Button
        JButton createAccountBtn = PremiumComponents.createPremiumMenuButton("1. Create Account", APPLE_ACCENT);
        createAccountBtn.addActionListener(e -> new CreateAccountDialog(this, accountManager).setVisible(true));
        buttonsPanel.add(createAccountBtn);

        // Deposit Button - Green
        JButton depositBtn = PremiumComponents.createPremiumMenuButton("2. Deposit Funds", AppleTheme.ACCENT_GREEN);
        depositBtn.addActionListener(e -> new DepositDialog(this, accountManager).setVisible(true));
        buttonsPanel.add(depositBtn);

        // Withdraw Button - Red
        JButton withdrawBtn = PremiumComponents.createPremiumMenuButton("3. Withdraw Funds", AppleTheme.ACCENT_RED);
        withdrawBtn.addActionListener(e -> new WithdrawDialog(this, accountManager).setVisible(true));
        buttonsPanel.add(withdrawBtn);

        // Transfer Button - Blue
        JButton transferBtn = PremiumComponents.createPremiumMenuButton("4. Transfer Funds", APPLE_ACCENT);
        transferBtn.addActionListener(e -> new TransferDialog(this, accountManager).setVisible(true));
        buttonsPanel.add(transferBtn);

        // View Statement Button - Teal
        JButton viewStatementBtn = PremiumComponents.createPremiumMenuButton("5. View Statement", new Color(0, 172, 193));
        viewStatementBtn.addActionListener(e -> new ViewStatementDialog(this, accountManager).setVisible(true));
        buttonsPanel.add(viewStatementBtn);

        // Apply Interest Button - Green
        JButton interestBtn = PremiumComponents.createPremiumMenuButton("6. Apply Interest", AppleTheme.ACCENT_GREEN);
        interestBtn.addActionListener(e -> new InterestDialog(this, accountManager).setVisible(true));
        buttonsPanel.add(interestBtn);

        // Exit Button - Red
        JButton exitBtn = PremiumComponents.createPremiumMenuButton("Exit Application", AppleTheme.ACCENT_RED);
        exitBtn.addActionListener(e -> System.exit(0));
        buttonsPanel.add(exitBtn);

        gbc.gridy = 1;
        gbc.insets = new Insets(20, 80, 20, 80);
        menuPanel.add(buttonsPanel, gbc);

        setContentPane(menuPanel);
        revalidate();
        repaint();
    }
}
