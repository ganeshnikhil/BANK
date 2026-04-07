package com.bank.ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Apple macOS premium theme constants and utilities for premium UI styling.
 * Provides colors, fonts, and style utilities that match premium macOS design.
 */
public class AppleTheme {
    // Apple macOS Premium Colors
    public static final Color BG_PRIMARY = new Color(245, 245, 247);      // Light gray background
    public static final Color BG_SECONDARY = new Color(255, 255, 255);    // White background
    public static final Color BG_TERTIARY = new Color(240, 240, 245);     // Subtle gray
    public static final Color TEXT_PRIMARY = new Color(29, 29, 31);       // Dark text
    public static final Color TEXT_SECONDARY = new Color(142, 142, 147);  // Secondary gray text
    public static final Color TEXT_TERTIARY = new Color(174, 174, 178);   // Tertiary gray text
    public static final Color ACCENT_BLUE = new Color(0, 122, 255);       // Apple blue
    public static final Color ACCENT_GREEN = new Color(52, 211, 153);     // Premium green
    public static final Color ACCENT_RED = new Color(255, 59, 48);        // Apple red
    public static final Color BORDER_COLOR = new Color(229, 229, 234);    // Premium border
    public static final Color SHADOW_COLOR = new Color(0, 0, 0);          // Shadow
    
    // Font
    private static final String FONT_NAME = "San Francisco";
    
    /**
     * Gets the system font name (San Francisco for macOS).
     * @return the font name
     */
    public static String getFontName() {
        return FONT_NAME;
    }
    
    /**
     * Creates a styled button font.
     * @param size the font size
     * @return the font
     */
    public static Font getButtonFont(int size) {
        return new Font(FONT_NAME, Font.PLAIN, size);
    }
    
    /**
     * Creates a styled label font.
     * @param size the font size
     * @return the font
     */
    public static Font getLabelFont(int size) {
        return new Font(FONT_NAME, Font.PLAIN, size);
    }
    
    /**
     * Creates a styled bold font.
     * @param size the font size
     * @return the font
     */
    public static Font getBoldFont(int size) {
        return new Font(FONT_NAME, Font.BOLD, size);
    }
    
    /**
     * Creates a styled title font.
     * @param size the font size
     * @return the font
     */
    public static Font getTitleFont(int size) {
        return new Font(FONT_NAME, Font.BOLD, size);
    }
    
    /**
     * Creates a premium rounded border for panels.
     * @param radius the corner radius
     * @param color the border color
     * @return the border
     */
    public static Border createRoundedBorder(int radius, Color color) {
        return BorderFactory.createDashedBorder(color, 1, 4, 1, true);
    }
}
