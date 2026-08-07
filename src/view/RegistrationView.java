package view;

import controller.AuthController;
import controller.NavigationController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationView extends JFrame implements ActionListener {
    private JTextField nameField, emailField, phoneField, userField;
    private JComboBox<String> bloodCombo;
    private JPasswordField passField;
    private JButton registerButton, clearButton, backButton;

    private AuthController authController;
    private String[] bloodGroups = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};

    public RegistrationView() {
        authController = new AuthController();

        setTitle("Donor & User Registration - Blood Bank System");
        setSize(720, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UITheme.applyWindowStyle(this);
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = UITheme.createHeaderPanel(
                "CREATE YOUR BBMS ACCOUNT",
                "Join our clinical donor & hospital requests network"
        );
        add(headerPanel, BorderLayout.NORTH);

        // Center Content Card
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);

        JPanel card = UITheme.createCardPanel();
        card.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 12, 6, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel formTitle = new JLabel("Registration Details", SwingConstants.CENTER);
        formTitle.setFont(UITheme.FONT_TITLE);
        formTitle.setForeground(UITheme.TEXT_MAIN);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 10, 15, 10);
        card.add(formTitle, gbc);

        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.gridwidth = 1;

        // Full Name
        JLabel nameLabel = new JLabel("Full Name");
        UITheme.styleLabel(nameLabel);
        gbc.gridx = 0; gbc.gridy = 1;
        card.add(nameLabel, gbc);

        nameField = new JTextField(20);
        UITheme.styleTextField(nameField);
        gbc.gridx = 1; gbc.gridy = 1;
        card.add(nameField, gbc);

        // Email
        JLabel emailLabel = new JLabel("Email Address");
        UITheme.styleLabel(emailLabel);
        gbc.gridx = 0; gbc.gridy = 2;
        card.add(emailLabel, gbc);

        emailField = new JTextField(20);
        UITheme.styleTextField(emailField);
        gbc.gridx = 1; gbc.gridy = 2;
        card.add(emailField, gbc);

        // Phone
        JLabel phoneLabel = new JLabel("Phone Number");
        UITheme.styleLabel(phoneLabel);
        gbc.gridx = 0; gbc.gridy = 3;
        card.add(phoneLabel, gbc);

        phoneField = new JTextField(20);
        UITheme.styleTextField(phoneField);
        gbc.gridx = 1; gbc.gridy = 3;
        card.add(phoneField, gbc);

        // Blood Group
        JLabel bloodLabel = new JLabel("Blood Group");
        UITheme.styleLabel(bloodLabel);
        gbc.gridx = 0; gbc.gridy = 4;
        card.add(bloodLabel, gbc);

        bloodCombo = new JComboBox<>(bloodGroups);
        UITheme.styleComboBox(bloodCombo);
        gbc.gridx = 1; gbc.gridy = 4;
        card.add(bloodCombo, gbc);

        // Username
        JLabel userLabel = new JLabel("Username");
        UITheme.styleLabel(userLabel);
        gbc.gridx = 0; gbc.gridy = 5;
        card.add(userLabel, gbc);

        userField = new JTextField(20);
        UITheme.styleTextField(userField);
        gbc.gridx = 1; gbc.gridy = 5;
        card.add(userField, gbc);

        // Password
        JLabel passLabel = new JLabel("Password");
        UITheme.styleLabel(passLabel);
        gbc.gridx = 0; gbc.gridy = 6;
        card.add(passLabel, gbc);

        passField = new JPasswordField(20);
        UITheme.styleTextField(passField);
        gbc.gridx = 1; gbc.gridy = 6;
        card.add(passField, gbc);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);

        registerButton = new JButton("Register Now");
        UITheme.stylePrimaryButton(registerButton);
        registerButton.setPreferredSize(new Dimension(140, 38));
        registerButton.addActionListener(this);
        buttonPanel.add(registerButton);

        clearButton = new JButton("Clear");
        UITheme.styleOutlineButton(clearButton);
        clearButton.setPreferredSize(new Dimension(90, 38));
        clearButton.addActionListener(this);
        buttonPanel.add(clearButton);

        backButton = new JButton("Back");
        UITheme.styleOutlineButton(backButton);
        backButton.setPreferredSize(new Dimension(90, 38));
        backButton.addActionListener(this);
        buttonPanel.add(backButton);

        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
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
        if (e.getSource() == registerButton) {
            String name = nameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String blood = (String) bloodCombo.getSelectedItem();
            String username = userField.getText();
            String password = new String(passField.getPassword());

            String errorMsg = authController.registerUser(name, email, phone, blood, username, password);

            if (errorMsg != null) {
                JOptionPane.showMessageDialog(this, errorMsg, "Registration Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Registration Successful!");
                NavigationController.getInstance().openSearchBloodView(this);
            }
        } else if (e.getSource() == clearButton) {
            nameField.setText("");
            emailField.setText("");
            phoneField.setText("");
            bloodCombo.setSelectedIndex(0);
            userField.setText("");
            passField.setText("");
        } else if (e.getSource() == backButton) {
            NavigationController.getInstance().openWelcomeView(this);
        }
    }
}
