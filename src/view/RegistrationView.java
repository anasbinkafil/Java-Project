package view;

import controller.AuthController;
import controller.NavigationController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegistrationView extends JFrame implements ActionListener {
    private JTextField nameField, emailField, phoneField, userField;
    private JComboBox<String> bloodCombo;
    private JPasswordField passField;
    private JButton registerButton;
    private JLabel loginLink, backLink;

    private AuthController authController;
    private String[] bloodGroups = {"Select Group", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};

    public RegistrationView() {
        authController = new AuthController();

        setTitle("Register - Blood Bank Management System");
        setSize(700, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UITheme.applyWindowStyle(this);
        setLayout(new BorderLayout());

        // Center Content Card
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        centerWrapper.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel card = UITheme.createCardPanel();
        card.setLayout(new GridBagLayout());
        card.setPreferredSize(new Dimension(480, 560));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Back Link
        backLink = new JLabel("←  BACK TO WELCOME");
        backLink.setFont(new Font("Segoe UI", Font.BOLD, 11));
        backLink.setForeground(UITheme.TEXT_MUTED);
        backLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                NavigationController.getInstance().openWelcomeView(RegistrationView.this);
            }
        });
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 5, 12, 5);
        card.add(backLink, gbc);

        // Title
        JLabel titleLabel = new JLabel("Create Your BBMS Account", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(UITheme.TEXT_DARK);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 5, 4, 5);
        card.add(titleLabel, gbc);

        // Subtitle
        JLabel subLabel = new JLabel("Join the network to manage and request life-saving resources.", SwingConstants.CENTER);
        subLabel.setFont(UITheme.FONT_SUBTITLE);
        subLabel.setForeground(UITheme.TEXT_MUTED);
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 5, 15, 5);
        card.add(subLabel, gbc);

        gbc.insets = new Insets(5, 5, 5, 5);

        // Row 1: Full Name | Username
        JLabel nameLabel = new JLabel("Full Name");
        UITheme.styleLabel(nameLabel);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1;
        card.add(nameLabel, gbc);

        JLabel userLabel = new JLabel("Username");
        UITheme.styleLabel(userLabel);
        gbc.gridx = 1; gbc.gridy = 3;
        card.add(userLabel, gbc);

        nameField = new JTextField();
        UITheme.styleTextField(nameField);
        gbc.gridx = 0; gbc.gridy = 4;
        card.add(nameField, gbc);

        userField = new JTextField();
        UITheme.styleTextField(userField);
        gbc.gridx = 1; gbc.gridy = 4;
        card.add(userField, gbc);

        // Row 2: Email Address (Full Width)
        JLabel emailLabel = new JLabel("Email Address");
        UITheme.styleLabel(emailLabel);
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        card.add(emailLabel, gbc);

        emailField = new JTextField();
        UITheme.styleTextField(emailField);
        gbc.gridy = 6;
        card.add(emailField, gbc);

        // Row 3: Phone Number | Blood Group
        JLabel phoneLabel = new JLabel("Phone Number");
        UITheme.styleLabel(phoneLabel);
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 1;
        card.add(phoneLabel, gbc);

        JLabel bloodLabel = new JLabel("Blood Group");
        UITheme.styleLabel(bloodLabel);
        gbc.gridx = 1; gbc.gridy = 7;
        card.add(bloodLabel, gbc);

        phoneField = new JTextField();
        UITheme.styleTextField(phoneField);
        gbc.gridx = 0; gbc.gridy = 8;
        card.add(phoneField, gbc);

        bloodCombo = new JComboBox<>(bloodGroups);
        UITheme.styleComboBox(bloodCombo);
        gbc.gridx = 1; gbc.gridy = 8;
        card.add(bloodCombo, gbc);

        // Row 4: Password (Full Width)
        JLabel passLabel = new JLabel("Password");
        UITheme.styleLabel(passLabel);
        gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 2;
        card.add(passLabel, gbc);

        passField = new JPasswordField();
        UITheme.styleTextField(passField);
        gbc.gridy = 10;
        card.add(passField, gbc);

        // Button: Register Now (Full Width)
        registerButton = new JButton("Register Now");
        UITheme.stylePrimaryButton(registerButton);
        registerButton.setPreferredSize(new Dimension(380, 42));
        registerButton.addActionListener(this);
        gbc.gridy = 11;
        gbc.insets = new Insets(18, 5, 10, 5);
        card.add(registerButton, gbc);

        // Already have an account? Login
        loginLink = new JLabel("Already have an account? Login", SwingConstants.CENTER);
        loginLink.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        loginLink.setForeground(UITheme.TEXT_MUTED);
        loginLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                NavigationController.getInstance().openLoginView(RegistrationView.this);
            }
        });
        gbc.gridy = 12;
        gbc.insets = new Insets(4, 5, 5, 5);
        card.add(loginLink, gbc);

        centerWrapper.add(card);
        add(centerWrapper, BorderLayout.CENTER);

        // Footer
        add(UITheme.createFooter(), BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            String name = nameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String blood = (String) bloodCombo.getSelectedItem();
            if (bloodCombo.getSelectedIndex() == 0) blood = "";
            String username = userField.getText();
            String password = new String(passField.getPassword());

            String errorMsg = authController.registerUser(name, email, phone, blood, username, password);

            if (errorMsg != null) {
                JOptionPane.showMessageDialog(this, errorMsg, "Registration Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Registration Successful!");
                NavigationController.getInstance().openSearchBloodView(this);
            }
        }
    }
}
