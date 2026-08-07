package view;

import controller.NavigationController;
import controller.RequestController;
import model.BloodRequest;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RequestBloodView extends JFrame implements ActionListener {
    private JTextField nameField, bagField, contactField;
    private JComboBox<String> bloodCombo, locationCombo, hospitalCombo;
    private JButton requestButton, clearButton, backButton;

    private RequestController requestController;

    private String[] bloodGroups = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
    private String[] locations = {"DHANMONDI", "PURAN DHAKA", "MIRPUR", "SAVAR", "BANANI"};
    private String[] hospitals = {
            "SQUARE HOSPITAL",
            "UNITED HOSPITAL",
            "EVERCARE HOSPITAL",
            "DHAKA MEDICAL COLLEGE HOSPITAL",
            "SIR SALIMULLAH MEDICAL COLLEGE HOSPITAL",
            "ENAM MEDICAL COLLEGE HOSPITAL"
    };

    public RequestBloodView() {
        requestController = new RequestController();

        setTitle("Submit Urgent Blood Request - Blood Bank System");
        setSize(750, 640);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UITheme.applyWindowStyle(this);
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = UITheme.createHeaderPanel(
                "SUBMIT URGENT BLOOD REQUEST",
                "Fill out details to alert nearby donor centers & hospitals"
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
        JLabel formTitle = new JLabel("Patient & Transfusion Details", SwingConstants.CENTER);
        formTitle.setFont(UITheme.FONT_TITLE);
        formTitle.setForeground(UITheme.TEXT_MAIN);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 10, 15, 10);
        card.add(formTitle, gbc);

        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.gridwidth = 1;

        // Patient Name
        JLabel nameLabel = new JLabel("Patient Name");
        UITheme.styleLabel(nameLabel);
        gbc.gridx = 0; gbc.gridy = 1;
        card.add(nameLabel, gbc);

        nameField = new JTextField(22);
        UITheme.styleTextField(nameField);
        gbc.gridx = 1; gbc.gridy = 1;
        card.add(nameField, gbc);

        // Blood Group
        JLabel bloodLabel = new JLabel("Required Blood Group");
        UITheme.styleLabel(bloodLabel);
        gbc.gridx = 0; gbc.gridy = 2;
        card.add(bloodLabel, gbc);

        bloodCombo = new JComboBox<>(bloodGroups);
        UITheme.styleComboBox(bloodCombo);
        gbc.gridx = 1; gbc.gridy = 2;
        card.add(bloodCombo, gbc);

        // Blood Bags
        JLabel bagLabel = new JLabel("Number of Bags");
        UITheme.styleLabel(bagLabel);
        gbc.gridx = 0; gbc.gridy = 3;
        card.add(bagLabel, gbc);

        bagField = new JTextField(22);
        UITheme.styleTextField(bagField);
        gbc.gridx = 1; gbc.gridy = 3;
        card.add(bagField, gbc);

        // Location
        JLabel locationLabel = new JLabel("Location Area");
        UITheme.styleLabel(locationLabel);
        gbc.gridx = 0; gbc.gridy = 4;
        card.add(locationLabel, gbc);

        locationCombo = new JComboBox<>(locations);
        UITheme.styleComboBox(locationCombo);
        gbc.gridx = 1; gbc.gridy = 4;
        card.add(locationCombo, gbc);

        // Hospital
        JLabel hospitalLabel = new JLabel("Preferred Hospital");
        UITheme.styleLabel(hospitalLabel);
        gbc.gridx = 0; gbc.gridy = 5;
        card.add(hospitalLabel, gbc);

        hospitalCombo = new JComboBox<>(hospitals);
        UITheme.styleComboBox(hospitalCombo);
        gbc.gridx = 1; gbc.gridy = 5;
        card.add(hospitalCombo, gbc);

        // Contact No
        JLabel contactLabel = new JLabel("Contact Number");
        UITheme.styleLabel(contactLabel);
        gbc.gridx = 0; gbc.gridy = 6;
        card.add(contactLabel, gbc);

        contactField = new JTextField(22);
        UITheme.styleTextField(contactField);
        gbc.gridx = 1; gbc.gridy = 6;
        card.add(contactField, gbc);

        // Buttons Row
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);

        requestButton = new JButton("Submit Request");
        UITheme.stylePrimaryButton(requestButton);
        requestButton.setPreferredSize(new Dimension(150, 38));
        requestButton.addActionListener(this);
        buttonPanel.add(requestButton);

        clearButton = new JButton("Clear Form");
        UITheme.styleOutlineButton(clearButton);
        clearButton.setPreferredSize(new Dimension(110, 38));
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
        footer.setBorder(new EmptyBorder(5, 10, 10, 10));
        JLabel footerLabel = new JLabel("© 2026 Clinical Integrity BBMS");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footerLabel.setForeground(UITheme.TEXT_MUTED);
        footer.add(footerLabel);
        add(footer, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == requestButton) {
            try {
                String patientName = nameField.getText();
                String bloodGroup = (String) bloodCombo.getSelectedItem();
                String bloodBags = bagField.getText();
                String location = (String) locationCombo.getSelectedItem();
                String hospital = (String) hospitalCombo.getSelectedItem();
                String contact = contactField.getText();

                BloodRequest req = requestController.submitRequest(patientName, bloodGroup, bloodBags, location, hospital, contact);

                JOptionPane.showMessageDialog(this, "Blood Request Submitted Successfully!\nRequest ID: " + req.getRequestId());
                NavigationController.getInstance().openMyRequestView(this, req);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == clearButton) {
            nameField.setText("");
            bagField.setText("");
            contactField.setText("");
            bloodCombo.setSelectedIndex(0);
            locationCombo.setSelectedIndex(0);
            hospitalCombo.setSelectedIndex(0);
        } else if (e.getSource() == backButton) {
            NavigationController.getInstance().openSearchBloodView(this);
        }
    }
}
