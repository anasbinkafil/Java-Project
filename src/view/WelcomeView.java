package view;

import controller.NavigationController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeView extends JFrame implements ActionListener {
    private JButton loginButton, registerButton;

    public WelcomeView() {
        setTitle("Blood Bank Management System");
        setSize(680, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UITheme.applyWindowStyle(this);
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = UITheme.createHeaderPanel(
                "BLOOD BANK MANAGEMENT SYSTEM",
                "Clinical Integrity & Lifesaving Donor Network"
        );
        add(headerPanel, BorderLayout.NORTH);

        // Center Content Card
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);

        JPanel card = UITheme.createCardPanel();
        card.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel welcomeLabel = new JLabel("Welcome to Blood Bank System", SwingConstants.CENTER);
        welcomeLabel.setFont(UITheme.FONT_TITLE);
        welcomeLabel.setForeground(UITheme.TEXT_MAIN);
        gbc.gridy = 0;
        card.add(welcomeLabel, gbc);

        JLabel infoLabel = new JLabel("Please Login or Register to search blood inventory or submit urgent requests.", SwingConstants.CENTER);
        infoLabel.setFont(UITheme.FONT_SUBTITLE);
        infoLabel.setForeground(UITheme.TEXT_MUTED);
        gbc.gridy = 1;
        card.add(infoLabel, gbc);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setOpaque(false);

        loginButton = new JButton("Login to Account");
        UITheme.stylePrimaryButton(loginButton);
        loginButton.setPreferredSize(new Dimension(170, 40));
        loginButton.addActionListener(this);
        buttonPanel.add(loginButton);

        registerButton = new JButton("Register New User");
        UITheme.styleSecondaryButton(registerButton);
        registerButton.setPreferredSize(new Dimension(170, 40));
        registerButton.addActionListener(this);
        buttonPanel.add(registerButton);

        gbc.gridy = 2;
        gbc.insets = new Insets(20, 15, 5, 15);
        card.add(buttonPanel, gbc);

        centerWrapper.add(card);
        add(centerWrapper, BorderLayout.CENTER);

        // Footer
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(UITheme.WINDOW_BG);
        footer.setBorder(new EmptyBorder(10, 10, 15, 10));
        JLabel footerLabel = new JLabel("© 2026 Clinical Integrity BBMS • Hospital & Donor Network");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footerLabel.setForeground(UITheme.TEXT_MUTED);
        footer.add(footerLabel);
        add(footer, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            NavigationController.getInstance().openRegistrationView(this);
        } else if (e.getSource() == loginButton) {
            NavigationController.getInstance().openLoginView(this);
        }
    }
}
