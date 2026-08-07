package view;

import controller.NavigationController;
import model.DataStore;
import model.Donor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Donor registration view matching the reference project (beDonor.java).
 */
public class BeDonorView extends JFrame implements ActionListener {
    private JTextField nameField, phoneField, emailField, addressField, ageField;
    private JComboBox<String> genderCombo, bloodCombo;
    private JButton submitBtn, cancelBtn, backBtn;

    private String[] genders = {"Select Gender", "Male", "Female"};
    private String[] bloodGroups = {"Select Blood Group", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};

    public BeDonorView() {
        setTitle("Become a Blood Donor - BBMS");
        setSize(780, 720);
        setMinimumSize(new Dimension(700, 620));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UITheme.applyWindowStyle(this);
        setLayout(new BorderLayout());

        add(UITheme.createTopNavBar(this, "Request"), BorderLayout.NORTH);

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        centerWrapper.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel card = UITheme.createCardPanel();
        card.setLayout(new GridBagLayout());
        card.setPreferredSize(new Dimension(520, 560));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Back link
        JLabel backLink = new JLabel("←  BACK TO SEARCH");
        backLink.setFont(new Font("Segoe UI", Font.BOLD, 11));
        backLink.setForeground(UITheme.TEXT_MUTED);
        backLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                NavigationController.getInstance().openSearchBloodView(BeDonorView.this);
            }
        });
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 5, 12, 5);
        card.add(backLink, gbc);

        // Header titles
        JLabel titleLabel = new JLabel("Donor Registration Form", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(UITheme.TEXT_DARK);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 5, 4, 5);
        card.add(titleLabel, gbc);

        JLabel subLabel = new JLabel("Register your details in Record.txt to help save lives.", SwingConstants.CENTER);
        subLabel.setFont(UITheme.FONT_SUBTITLE);
        subLabel.setForeground(UITheme.TEXT_MUTED);
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 5, 15, 5);
        card.add(subLabel, gbc);

        gbc.insets = new Insets(5, 5, 5, 5);

        // Form fields
        JLabel nl = new JLabel("Name*"); UITheme.styleLabel(nl);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1;
        card.add(nl, gbc);

        JLabel fnl = new JLabel("Phone No*"); UITheme.styleLabel(fnl);
        gbc.gridx = 1; gbc.gridy = 3;
        card.add(fnl, gbc);

        nameField = new JTextField(); UITheme.styleTextField(nameField);
        gbc.gridx = 0; gbc.gridy = 4;
        card.add(nameField, gbc);

        phoneField = new JTextField(); UITheme.styleTextField(phoneField);
        gbc.gridx = 1; gbc.gridy = 4;
        card.add(phoneField, gbc);

        JLabel anl = new JLabel("Email ID*"); UITheme.styleLabel(anl);
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        card.add(anl, gbc);

        emailField = new JTextField(); UITheme.styleTextField(emailField);
        gbc.gridy = 6;
        card.add(emailField, gbc);

        JLabel adl = new JLabel("Address*"); UITheme.styleLabel(adl);
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 1;
        card.add(adl, gbc);

        JLabel agl = new JLabel("Age*"); UITheme.styleLabel(agl);
        gbc.gridx = 1; gbc.gridy = 7;
        card.add(agl, gbc);

        addressField = new JTextField(); UITheme.styleTextField(addressField);
        gbc.gridx = 0; gbc.gridy = 8;
        card.add(addressField, gbc);

        ageField = new JTextField(); UITheme.styleTextField(ageField);
        gbc.gridx = 1; gbc.gridy = 8;
        card.add(ageField, gbc);

        JLabel gl = new JLabel("Sex*"); UITheme.styleLabel(gl);
        gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 1;
        card.add(gl, gbc);

        JLabel bgl = new JLabel("Blood Group*"); UITheme.styleLabel(bgl);
        gbc.gridx = 1; gbc.gridy = 9;
        card.add(bgl, gbc);

        genderCombo = new JComboBox<>(genders); UITheme.styleComboBox(genderCombo);
        gbc.gridx = 0; gbc.gridy = 10;
        card.add(genderCombo, gbc);

        bloodCombo = new JComboBox<>(bloodGroups); UITheme.styleComboBox(bloodCombo);
        gbc.gridx = 1; gbc.gridy = 10;
        card.add(bloodCombo, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);

        submitBtn = new JButton("Submit Donor Profile");
        UITheme.stylePrimaryButton(submitBtn);
        submitBtn.addActionListener(this);
        buttonPanel.add(submitBtn);

        cancelBtn = new JButton("Cancel");
        UITheme.styleOutlineButton(cancelBtn);
        cancelBtn.addActionListener(this);
        buttonPanel.add(cancelBtn);

        gbc.gridx = 0; gbc.gridy = 11; gbc.gridwidth = 2;
        gbc.insets = new Insets(18, 5, 5, 5);
        card.add(buttonPanel, gbc);

        centerWrapper.add(card);
        add(centerWrapper, BorderLayout.CENTER);

        add(UITheme.createFooter(), BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitBtn) {
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();
            String address = addressField.getText().trim();
            String age = ageField.getText().trim();
            String sex = (String) genderCombo.getSelectedItem();
            if (genderCombo.getSelectedIndex() == 0) sex = "";
            String bg = (String) bloodCombo.getSelectedItem();
            if (bloodCombo.getSelectedIndex() == 0) bg = "";

            if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || address.isEmpty() || age.isEmpty() || sex.isEmpty() || bg.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All required fields must be filled out.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Donor temp = new Donor(name, age, sex, email, phone, address, bg);
            DataStore.getInstance().addDonor(temp);

            JOptionPane.showMessageDialog(this, "Donor account registered successfully in Record.txt!");
            NavigationController.getInstance().openSearchBloodView(this);
        } else if (e.getSource() == cancelBtn) {
            NavigationController.getInstance().openSearchBloodView(this);
        }
    }
}
