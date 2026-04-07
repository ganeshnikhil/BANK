package com.bank.ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * Premium UI components with rounded corners, shadows, and smooth interactions.
 * Provides custom buttons and panels with premium styling for a polished look.
 */
public class PremiumComponents {
    
    /**
     * Creates a premium rounded button with hover effects.
     * @param text the button text
     * @param backgroundColor the background color
     * @return the styled button
     */
    public static JButton createPremiumButton(String text, Color backgroundColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw rounded rectangle background
                g2.setColor(backgroundColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                // Draw text
                super.paintComponent(g);
            }
            
            @Override
            protected void paintBorder(Graphics g) {
                // No border for premium look
            }
        };
        
        button.setFont(AppleTheme.getBoldFont(13));
        button.setForeground(Color.WHITE);
        button.setBackground(backgroundColor);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(220, 44));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            private Color originalColor = backgroundColor;
            
            @Override
            public void mouseEntered(MouseEvent e) {
                originalColor = button.getBackground();
                button.setBackground(adjustBrightness(originalColor, 1.1f));
                button.repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalColor);
                button.repaint();
            }
        });
        
        return button;
    }
    
    /**
     * Creates a premium menu button with gradient effect.
     * @param text the button text
     * @param color the button color
     * @return the styled button
     */
    public static JButton createPremiumMenuButton(String text, Color color) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw subtle shadow
                g2.setColor(new Color(0, 0, 0, 10));
                g2.fillRoundRect(2, 3, getWidth() - 4, getHeight() - 4, 12, 12);
                
                // Draw gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, color,
                    0, getHeight(), adjustBrightness(color, 0.95f)
                );
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                
                // Draw text
                super.paintComponent(g);
            }
            
            @Override
            protected void paintBorder(Graphics g) {
                // No border for premium look
            }
        };
        
        button.setFont(AppleTheme.getBoldFont(14));
        button.setForeground(Color.WHITE);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(280, 48));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return button;
    }
    
    /**
     * Creates a premium panel with shadow and rounded corners.
     * @param backgroundColor the background color
     * @return the styled panel
     */
    public static JPanel createPremiumPanel(Color backgroundColor) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw shadow
                g2.setColor(new Color(0, 0, 0, 15));
                g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 15, 15);
                
                // Draw main background
                g2.setColor(backgroundColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                super.paintComponent(g);
            }
        };
        
        panel.setBackground(backgroundColor);
        panel.setOpaque(false);
        return panel;
    }
    
    /**
     * Adjusts the brightness of a color.
     * @param color the original color
     * @param factor the brightness factor (1.0 = no change, >1.0 = brighter)
     * @return the adjusted color
     */
    private static Color adjustBrightness(Color color, float factor) {
        int red = (int) Math.min(255, color.getRed() * factor);
        int green = (int) Math.min(255, color.getGreen() * factor);
        int blue = (int) Math.min(255, color.getBlue() * factor);
        return new Color(red, green, blue);
    }
    
    /**
     * Creates a premium text field with border and styling.
     * @return the styled text field
     */
    public static JTextField createPremiumTextField() {
        JTextField textField = new JTextField() {
            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(AppleTheme.BORDER_COLOR);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
            }
        };
        
        textField.setFont(AppleTheme.getLabelFont(12));
        textField.setForeground(AppleTheme.TEXT_PRIMARY);
        textField.setBackground(AppleTheme.BG_SECONDARY);
        textField.setCaretColor(AppleTheme.ACCENT_BLUE);
        textField.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        textField.setOpaque(true);
        
        return textField;
    }
    
    /**
     * Creates a premium combo box with styling.
     * @return the styled combo box
     */
    @SuppressWarnings("rawtypes")
    public static JComboBox createPremiumComboBox() {
        JComboBox comboBox = new JComboBox() {
            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(AppleTheme.BORDER_COLOR);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
            }
        };
        
        comboBox.setFont(AppleTheme.getLabelFont(12));
        comboBox.setForeground(AppleTheme.TEXT_PRIMARY);
        comboBox.setBackground(AppleTheme.BG_SECONDARY);
        comboBox.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        
        return comboBox;
    }
}
