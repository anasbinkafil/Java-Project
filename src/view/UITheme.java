package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UITheme {
    // Colors matching the Stitch MCP Clinical Integrity Theme
    public static final Color PRIMARY_COLOR = new Color(211, 47, 47);      // Crimson Red #D32F2F
    public static final Color PRIMARY_HOVER = new Color(183, 28, 28);      // Dark Red #B71C1C
    public static final Color SECONDARY_COLOR = new Color(0, 121, 107);    // Teal #00796B
    public static final Color SECONDARY_HOVER = new Color(0, 77, 64);      // Dark Teal #004D40
    public static final Color TERTIARY_COLOR = new Color(25, 118, 210);    // Navy #1976D2
    public static final Color HEADER_BG = new Color(24, 28, 30);           // #181C1E
    public static final Color WINDOW_BG = new Color(247, 250, 252);        // Clinical Soft #F7FAFC
    public static final Color CARD_BG = Color.WHITE;
    public static final Color TEXT_MAIN = new Color(24, 28, 30);
    public static final Color TEXT_MUTED = new Color(113, 128, 150);
    public static final Color BORDER_COLOR = new Color(226, 232, 240);

    // Fonts
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 20);
    public static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_HEADER = new Font("Segoe UI", Font.BOLD, 15);
    public static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_BODY_BOLD = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_LABEL = new Font("Segoe UI", Font.BOLD, 12);

    public static void applyWindowStyle(JFrame frame) {
        frame.getContentPane().setBackground(WINDOW_BG);
    }

    public static JPanel createHeaderPanel(String title, String subtitle) {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(HEADER_BG);
        headerPanel.setBorder(new EmptyBorder(18, 24, 18, 24));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(FONT_TITLE);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(titleLabel);

        if (subtitle != null && !subtitle.isEmpty()) {
            headerPanel.add(Box.createVerticalStrut(4));
            JLabel subLabel = new JLabel(subtitle);
            subLabel.setFont(FONT_SUBTITLE);
            subLabel.setForeground(new Color(203, 213, 225));
            subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            headerPanel.add(subLabel);
        }

        return headerPanel;
    }

    public static JPanel createCardPanel() {
        JPanel card = new JPanel();
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(20, 25, 20, 25)
        ));
        return card;
    }

    public static void stylePrimaryButton(JButton button) {
        button.setFont(FONT_BODY_BOLD);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(8, 16, 8, 16));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(PRIMARY_HOVER);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(PRIMARY_COLOR);
            }
        });
    }

    public static void styleSecondaryButton(JButton button) {
        button.setFont(FONT_BODY_BOLD);
        button.setBackground(SECONDARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(8, 16, 8, 16));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(SECONDARY_HOVER);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(SECONDARY_COLOR);
            }
        });
    }

    public static void styleOutlineButton(JButton button) {
        button.setFont(FONT_BODY_BOLD);
        button.setBackground(CARD_BG);
        button.setForeground(TEXT_MAIN);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(8, 16, 8, 16)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(237, 242, 247));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(CARD_BG);
            }
        });
    }

    public static void styleTextField(JTextField field) {
        field.setFont(FONT_BODY);
        field.setForeground(TEXT_MAIN);
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(6, 10, 6, 10)
        ));
    }

    public static void styleComboBox(JComboBox<?> combo) {
        combo.setFont(FONT_BODY);
        combo.setForeground(TEXT_MAIN);
        combo.setBackground(Color.WHITE);
    }

    public static void styleLabel(JLabel label) {
        label.setFont(FONT_LABEL);
        label.setForeground(TEXT_MAIN);
    }
}
