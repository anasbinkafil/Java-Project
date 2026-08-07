package view;

import controller.AuthController;
import controller.NavigationController;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame implements ActionListener {
    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton, backButton;
    private AuthController authController;

    public LoginView() {
        authController = new AuthController();

        setTitle("User Login - Blood Bank System");
        setSize(650, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UITheme.applyWindowStyle(this);
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = UITheme.createHeaderPanel(
                "USER AUTHENTICATION",
                "Sign in to access search inventory and donor options"
        );
        add(headerPanel, BorderLayout.NORTH);

        // Center Content Card
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);

        JPanel card = UITheme.createCardPanel();
        card.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Form Title
        JLabel formTitle = new JLabel("Blood Bank Account Login", SwingConstants.CENTER);
        formTitle.setFont(UITheme.FONT_TITLE);
        formTitle.setForeground(UITheme.TEXT_MAIN);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 10, 15, 10);
        card.add(formTitle, gbc);

        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.gridwidth = 1;

        // Username
        JLabel userLabel = new JLabel("Username");
        UITheme.styleLabel(userLabel);
        gbc.gridx = 0; gbc.gridy = 1;
        card.add(userLabel, gbc);

        userField = new JTextField(18);
        UITheme.styleTextField(userField);
        gbc.gridx = 1; gbc.gridy = 1;
        card.add(userField, gbc);

        // Password
        JLabel passLabel = new JLabel("Password");
        UITheme.styleLabel(passLabel);
        gbc.gridx = 0; gbc.gridy = 2;
        card.add(passLabel, gbc);

        passField = new JPasswordField(18);
        UITheme.styleTextField(passField);
        gbc.gridx = 1; gbc.gridy = 2;
        card.add(passField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);

        loginButton = new JButton("Login");
        UITheme.stylePrimaryButton(loginButton);
        loginButton.setPreferredSize(new Dimension(120, 38));
        loginButton.addActionListener(this);
        buttonPanel.add(loginButton);

        backButton = new JButton("Back");
        UITheme.styleOutlineButton(backButton);
        backButton.setPreferredSize(new Dimension(100, 38));
        backButton.addActionListener(this);
        buttonPanel.add(backButton);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 10, 5, 10);
        card.add(buttonPanel, gbc);

        centerWrapper.add(card);
        add(centerWrapper, BorderLayout.CENTER);

        // Footer
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(UITheme.WINDOW_BG);
        footer.setBorder(new EmptyBorder(10, 10, 15, 10));
        JLabel footerLabel = new JLabel("© 2026 Clinical Integrity BBMS");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footerLabel.setForeground(UITheme.TEXT_MUTED);
        footer.add(footerLabel);
        add(footer, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            User user = authController.loginUser(username, password);
            if (user != null) {
                JOptionPane.showMessageDialog(this, "Login Successful! Welcome, " + user.getFullName());
                NavigationController.getInstance().openSearchBloodView(this);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password!", "Authentication Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == backButton) {
            NavigationController.getInstance().openWelcomeView(this);
        }
    }
}
