package view;

import controller.AuthController;
import controller.NavigationController;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Login view frame for user authentication.
 */
public class LoginView extends JFrame implements ActionListener {
    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton;
    private JLabel registerLink, backLink;
    private AuthController authController;

    public LoginView() {
        authController = new AuthController();

        // Standardized window properties
        setTitle("Login - Blood Bank Management System");
        setSize(780, 640);
        setMinimumSize(new Dimension(700, 580));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UITheme.applyWindowStyle(this);
        setLayout(new BorderLayout());

        // Centered form container
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        centerWrapper.setBorder(new EmptyBorder(25, 25, 25, 25));

        JPanel card = UITheme.createCardPanel();
        card.setLayout(new GridBagLayout());
        card.setPreferredSize(new Dimension(460, 440));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Back link
        backLink = new JLabel("←  BACK TO WELCOME");
        backLink.setFont(new Font("Segoe UI", Font.BOLD, 11));
        backLink.setForeground(UITheme.TEXT_MUTED);
        backLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                NavigationController.getInstance().openWelcomeView(LoginView.this);
            }
        });
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 5, 12, 5);
        card.add(backLink, gbc);

        // Titles
        JLabel titleLabel = new JLabel("Sign In to BBMS", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(UITheme.TEXT_DARK);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 5, 4, 5);
        card.add(titleLabel, gbc);

        JLabel subLabel = new JLabel("Enter your credentials to access inventory & requests.", SwingConstants.CENTER);
        subLabel.setFont(UITheme.FONT_SUBTITLE);
        subLabel.setForeground(UITheme.TEXT_MUTED);
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 5, 18, 5);
        card.add(subLabel, gbc);

        gbc.insets = new Insets(6, 5, 6, 5);

        // Credentials inputs
        JLabel userLabel = new JLabel("Username");
        UITheme.styleLabel(userLabel);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        card.add(userLabel, gbc);

        userField = new JTextField();
        UITheme.styleTextField(userField);
        gbc.gridy = 4;
        card.add(userField, gbc);

        JLabel passLabel = new JLabel("Password");
        UITheme.styleLabel(passLabel);
        gbc.gridy = 5;
        card.add(passLabel, gbc);

        passField = new JPasswordField();
        UITheme.styleTextField(passField);
        gbc.gridy = 6;
        card.add(passField, gbc);

        // Login submit button
        loginButton = new JButton("Login");
        UITheme.stylePrimaryButton(loginButton);
        loginButton.setPreferredSize(new Dimension(360, 42));
        loginButton.addActionListener(this);
        gbc.gridy = 7;
        gbc.insets = new Insets(18, 5, 10, 5);
        card.add(loginButton, gbc);

        // Registration redirect link
        registerLink = new JLabel("Need an account? Register Now", SwingConstants.CENTER);
        registerLink.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        registerLink.setForeground(UITheme.TEXT_MUTED);
        registerLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                NavigationController.getInstance().openRegistrationView(LoginView.this);
            }
        });
        gbc.gridy = 8;
        gbc.insets = new Insets(4, 5, 5, 5);
        card.add(registerLink, gbc);

        centerWrapper.add(card);
        add(centerWrapper, BorderLayout.CENTER);

        add(UITheme.createFooter(), BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            // Authenticate user via AuthController
            User user = authController.loginUser(username, password);
            if (user != null) {
                JOptionPane.showMessageDialog(this, "Login Successful! Welcome, " + user.getFullName());
                NavigationController.getInstance().openSearchBloodView(this);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password!", "Authentication Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
