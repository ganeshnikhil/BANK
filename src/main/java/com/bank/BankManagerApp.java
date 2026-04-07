package com.bank;

import com.bank.ui.BankManagerGUI;

/**
 * Main entry point for the Bank Account Manager application.
 * Initializes and starts the Swing GUI application.
 */
public class BankManagerApp {
    
    /**
     * Main method to start the application.
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            BankManagerGUI gui = new BankManagerGUI();
            gui.setVisible(true);
        });
    }
}
