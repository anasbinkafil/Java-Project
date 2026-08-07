package view;

import controller.NavigationController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Landing screen window for initial navigation (Login / Register).
 */
public class WelcomeView extends JFrame implements ActionListener {
    private JButton loginButton, registerButton;

    public WelcomeView() {
        // Setup window properties & standard dimensions
        setTitle("Welcome - Blood Bank Management System");
        setSize(780, 640);
        setMinimumSize(new Dimension(700, 580));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UITheme.applyWindowStyle(this);
        setLayout(new BorderLayout());

        // Center Content Card Wrapper
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        centerWrapper.setBorder(new EmptyBorder(25, 25, 25, 25));

        JPanel card = UITheme.createCardPanel();
        card.setLayout(new GridBagLayout());
        card.setPreferredSize(new Dimension(520, 400));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel logoLabel = new JLabel("🩸 BBMS", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        logoLabel.setForeground(UITheme.PRIMARY_RED);
        gbc.gridy = 0;
        card.add(logoLabel, gbc);

        JLabel welcomeLabel = new JLabel("Welcome to Blood Bank System", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        welcomeLabel.setForeground(UITheme.TEXT_DARK);
        gbc.gridy = 1;
        card.add(welcomeLabel, gbc);

        JLabel infoLabel = new JLabel("<html><center>Join the verified hospital network to manage and request life-saving blood resources.</center></html>", SwingConstants.CENTER);
        infoLabel.setFont(UITheme.FONT_SUBTITLE);
        infoLabel.setForeground(UITheme.TEXT_MUTED);
        gbc.gridy = 2;
        card.add(infoLabel, gbc);

        // Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setOpaque(false);

        loginButton = new JButton("Login to Account");
        UITheme.stylePrimaryButton(loginButton);
        loginButton.setPreferredSize(new Dimension(180, 42));
        loginButton.addActionListener(this);
        buttonPanel.add(loginButton);

        registerButton = new JButton("Register New User");
        UITheme.styleOutlineButton(registerButton);
        registerButton.setPreferredSize(new Dimension(180, 42));
        registerButton.addActionListener(this);
        buttonPanel.add(registerButton);

        gbc.gridy = 3;
        gbc.insets = new Insets(20, 15, 5, 15);
        card.add(buttonPanel, gbc);

        centerWrapper.add(card);
        add(centerWrapper, BorderLayout.CENTER);

        // Footer
        add(UITheme.createFooter(), BorderLayout.SOUTH);

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
