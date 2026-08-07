package view;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UITheme {
    // Exact Colors matching the uploaded Stitch MCP Screenshots
    public static final Color WINDOW_BG = new Color(247, 249, 252);        // #F7F9FC
    public static final Color CARD_BG = Color.WHITE;                       // #FFFFFF
    public static final Color CARD_BORDER = new Color(230, 235, 242);     // Soft border
    public static final Color PRIMARY_RED = new Color(162, 20, 27);         // #A2141B Dark Crimson Red
    public static final Color PRIMARY_RED_HOVER = new Color(130, 15, 20);   // Darker Red
    public static final Color TEXT_DARK = new Color(27, 28, 28);           // #1B1C1C
    public static final Color TEXT_MUTED = new Color(105, 112, 122);       // #69707A
    public static final Color INPUT_BORDER = new Color(226, 232, 240);     // #E2E8F0
    public static final Color BADGE_BLUE_BG = new Color(211, 230, 255);    // #D3E6FF
    public static final Color BADGE_BLUE_TEXT = new Color(0, 72, 129);

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

    public static JPanel createTopNavBar(JFrame frame, String activeTab) {
        JPanel navBar = new JPanel(new BorderLayout());
        navBar.setBackground(Color.WHITE);
        navBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, CARD_BORDER),
                new EmptyBorder(12, 30, 12, 30)
        ));

        // Brand Logo
        JLabel logo = new JLabel("BBMS");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        logo.setForeground(PRIMARY_RED);
        navBar.add(logo, BorderLayout.WEST);

        // Nav Items Center
        JPanel navItems = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 0));
        navItems.setOpaque(false);

        String[] tabs = {"Search", "Request", "My Requests"};
        for (String tab : tabs) {
            JLabel tabLabel = new JLabel(tab);
            tabLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
            if (tab.equalsIgnoreCase(activeTab)) {
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
                        controller.NavigationController.getInstance().openSearchBloodView(frame);
                    } else if (tab.equalsIgnoreCase("Request")) {
                        controller.NavigationController.getInstance().openRequestBloodView(frame);
                    } else if (tab.equalsIgnoreCase("My Requests")) {
                        controller.NavigationController.getInstance().openMyRequestView(frame, null);
                    }
                }
            });
            navItems.add(tabLabel);
        }
        navBar.add(navItems, BorderLayout.CENTER);

        // Right side Logout
        JLabel logoutLabel = new JLabel("Logout");
        logoutLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        logoutLabel.setForeground(TEXT_MUTED);
        logoutLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.NavigationController.getInstance().openWelcomeView(frame);
            }
        });
        navBar.add(logoutLabel, BorderLayout.EAST);

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
                new EmptyBorder(15, 30, 15, 30)
        ));

        JLabel brand = new JLabel("BBMS");
        brand.setFont(new Font("Segoe UI", Font.BOLD, 14));
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
        button.setBorder(new EmptyBorder(10, 18, 10, 18));
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
                button.setBackground(new Color(245, 247, 250));
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
}
