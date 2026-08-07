package view;

import controller.NavigationController;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Enhanced UI Theme & Component Styling for Swing GUI.
 */
public class UITheme {

    // Rich Palette
    public static final Color WINDOW_BG = new Color(245, 247, 250);        // #F5F7FA Soft Cool Surface
    public static final Color CARD_BG = Color.WHITE;                       // #FFFFFF Pure White Container
    public static final Color CARD_BORDER = new Color(226, 232, 240);     // #E2E8F0 Soft Border
    public static final Color PRIMARY_RED = new Color(185, 28, 28);         // #B91C1C Deep Crimson Red
    public static final Color PRIMARY_RED_HOVER = new Color(153, 27, 27);   // Darker Red Hover
    public static final Color SECONDARY_TEAL = new Color(15, 118, 110);    // #0F766E Clinical Teal
    public static final Color TEXT_DARK = new Color(15, 23, 42);           // #0F172A Deep Navy Main Text
    public static final Color TEXT_MUTED = new Color(100, 116, 139);       // #64748B Muted Subtitle Text
    public static final Color INPUT_BORDER = new Color(203, 213, 225);     // #CBD5E1 Input Border

    // Pill Badge Colors
    public static final Color BADGE_BLUE_BG = new Color(232, 240, 254);    // #E8F0FE
    public static final Color BADGE_BLUE_TEXT = new Color(26, 115, 232);   // #1A73E8
    public static final Color BADGE_GREEN_BG = new Color(230, 244, 234);   // #E6F4EA
    public static final Color BADGE_GREEN_TEXT = new Color(19, 115, 51);   // #137333

    // Fonts
    public static final Font FONT_HEADER_TITLE = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 20);
    public static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_LABEL = new Font("Segoe UI", Font.BOLD, 12);
    public static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_BODY_BOLD = new Font("Segoe UI", Font.BOLD, 13);

    public static void applyWindowStyle(JFrame frame) {
        frame.getContentPane().setBackground(WINDOW_BG);
    }

    // Top Header Navigation Bar
    public static JPanel createTopNavBar(JFrame frame, String activeTab) {
        JPanel navBar = new JPanel(new BorderLayout());
        navBar.setBackground(Color.WHITE);
        navBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, CARD_BORDER),
                new EmptyBorder(14, 32, 14, 32)
        ));

        // Logo + Brand Title
        JPanel brandPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        brandPanel.setOpaque(false);

        JLabel logo = new JLabel("BBMS");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        logo.setForeground(PRIMARY_RED);
        brandPanel.add(logo);

        navBar.add(brandPanel, BorderLayout.WEST);

        // Center Navigation Links
        JPanel navItems = new JPanel(new FlowLayout(FlowLayout.LEFT, 22, 0));
        navItems.setOpaque(false);

        User currentUser = NavigationController.getInstance().getCurrentUser();
        boolean isAdmin = (currentUser != null && currentUser.isAdmin());

        String[] tabs = isAdmin ? new String[]{"Search", "Request", "My Requests", "Manage Stock & Requests"}
                                : new String[]{"Search", "Request", "My Requests"};

        for (String tab : tabs) {
            JLabel tabLabel = new JLabel(tab);
            tabLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
            if (tab.equalsIgnoreCase(activeTab) || (activeTab.startsWith("Manage") && tab.startsWith("Manage"))) {
                tabLabel.setForeground(PRIMARY_RED);
                tabLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, PRIMARY_RED));
            } else {
                tabLabel.setForeground(TEXT_MUTED);
            }
            tabLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            tabLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (tab.equalsIgnoreCase("Search")) {
                        NavigationController.getInstance().openSearchBloodView(frame);
                    } else if (tab.equalsIgnoreCase("Request")) {
                        NavigationController.getInstance().openRequestBloodView(frame);
                    } else if (tab.equalsIgnoreCase("My Requests")) {
                        NavigationController.getInstance().openMyRequestView(frame, null);
                    } else if (tab.startsWith("Manage")) {
                        NavigationController.getInstance().openManageInventoryView(frame);
                    }
                }
            });
            navItems.add(tabLabel);
        }
        navBar.add(navItems, BorderLayout.CENTER);

        // Right side user profile & Logout
        JPanel rightSection = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        rightSection.setOpaque(false);

        if (currentUser != null) {
            JLabel userLabel = new JLabel(currentUser.getFullName() + (isAdmin ? " (Admin)" : ""));
            userLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
            userLabel.setForeground(TEXT_DARK);
            rightSection.add(userLabel);
        }

        JLabel logoutLabel = new JLabel("Logout");
        logoutLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        logoutLabel.setForeground(PRIMARY_RED);
        logoutLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                NavigationController.getInstance().setCurrentUser(null);
                NavigationController.getInstance().openWelcomeView(frame);
            }
        });
        rightSection.add(logoutLabel);

        navBar.add(rightSection, BorderLayout.EAST);

        return navBar;
    }

    public static JPanel createCardPanel() {
        JPanel card = new JPanel();
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(CARD_BORDER, 1, true),
                new EmptyBorder(24, 28, 24, 28)
        ));
        return card;
    }

    public static JPanel createFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(WINDOW_BG);
        footer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, CARD_BORDER),
                new EmptyBorder(15, 32, 15, 32)
        ));

        JLabel brand = new JLabel("BBMS Clinical Donor Network");
        brand.setFont(new Font("Segoe UI", Font.BOLD, 13));
        brand.setForeground(PRIMARY_RED);
        footer.add(brand, BorderLayout.WEST);

        JLabel copyright = new JLabel("© 2026 Blood Bank Management System. Institutional stability and urgent efficiency.", SwingConstants.RIGHT);
        copyright.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        copyright.setForeground(TEXT_MUTED);
        footer.add(copyright, BorderLayout.EAST);

        return footer;
    }

    public static void stylePrimaryButton(JButton button) {
        button.setFont(FONT_BODY_BOLD);
        button.setBackground(PRIMARY_RED);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(PRIMARY_RED_HOVER);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(PRIMARY_RED);
            }
        });
    }

    public static void styleOutlineButton(JButton button) {
        button.setFont(FONT_BODY_BOLD);
        button.setBackground(CARD_BG);
        button.setForeground(TEXT_DARK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(INPUT_BORDER, 1, true),
                new EmptyBorder(8, 16, 8, 16)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(241, 245, 249));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(CARD_BG);
            }
        });
    }

    public static void styleTextField(JTextField field) {
        field.setFont(FONT_BODY);
        field.setForeground(TEXT_DARK);
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(INPUT_BORDER, 1, true),
                new EmptyBorder(8, 10, 8, 10)
        ));
    }

    public static void styleComboBox(JComboBox<?> combo) {
        combo.setFont(FONT_BODY);
        combo.setForeground(TEXT_DARK);
        combo.setBackground(Color.WHITE);
    }

    public static void styleLabel(JLabel label) {
        label.setFont(FONT_LABEL);
        label.setForeground(TEXT_DARK);
    }

    // Custom Pill Badge Renderer for JTable columns
    public static class PillBadgeRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(new Font("Segoe UI", Font.BOLD, 12));
            
            String valStr = String.valueOf(value);
            if (valStr.matches("(A|B|AB|O)[+-]")) {
                label.setOpaque(true);
                label.setBackground(BADGE_BLUE_BG);
                label.setForeground(BADGE_BLUE_TEXT);
            } else if (valStr.contains("Bags") || valStr.equalsIgnoreCase("FULFILLED") || valStr.equalsIgnoreCase("APPROVED")) {
                label.setOpaque(true);
                label.setBackground(BADGE_GREEN_BG);
                label.setForeground(BADGE_GREEN_TEXT);
            } else {
                label.setOpaque(false);
                label.setForeground(TEXT_DARK);
            }
            return label;
        }
    }
}
