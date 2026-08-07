package view;

import controller.NavigationController;
import controller.RequestController;
import model.BloodRequest;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Screen form for submitting an urgent patient blood request.
 */
public class RequestBloodView extends JFrame implements ActionListener {
    private JTextField nameField, bagField, contactField;
    private JComboBox<String> bloodCombo, locationCombo, hospitalCombo;
    private JButton requestButton;
    private JLabel backLink;

    private RequestController requestController;

    private String[] bloodGroups = {"Select Group", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
    private String[] locations = {"Select Location", "DHANMONDI", "PURAN DHAKA", "MIRPUR", "SAVAR", "BANANI"};
    private String[] hospitals = {
            "Select Hospital",
            "SQUARE HOSPITAL",
            "UNITED HOSPITAL",
            "EVERCARE HOSPITAL",
            "DHAKA MEDICAL COLLEGE HOSPITAL",
            "SIR SALIMULLAH MEDICAL COLLEGE HOSPITAL",
            "ENAM MEDICAL COLLEGE HOSPITAL"
    };

    public RequestBloodView() {
        requestController = new RequestController();

        // Main frame config
        setTitle("Submit Blood Request - Blood Bank Management System");
        setSize(700, 740);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UITheme.applyWindowStyle(this);
        setLayout(new BorderLayout());

        add(UITheme.createTopNavBar(this, "Request"), BorderLayout.NORTH);

        // Center card wrapper
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        centerWrapper.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel card = UITheme.createCardPanel();
        card.setLayout(new GridBagLayout());
        card.setPreferredSize(new Dimension(500, 580));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Navigation back link
        backLink = new JLabel("←  BACK TO SEARCH");
        backLink.setFont(new Font("Segoe UI", Font.BOLD, 11));
        backLink.setForeground(UITheme.TEXT_MUTED);
        backLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                NavigationController.getInstance().openSearchBloodView(RequestBloodView.this);
            }
        });
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 5, 12, 5);
        card.add(backLink, gbc);

        // Section titles
        JLabel titleLabel = new JLabel("Submit Urgent Blood Request", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(UITheme.TEXT_DARK);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 5, 4, 5);
        card.add(titleLabel, gbc);

        JLabel subLabel = new JLabel("Fill out patient details to alert verified hospitals & donor network.", SwingConstants.CENTER);
        subLabel.setFont(UITheme.FONT_SUBTITLE);
        subLabel.setForeground(UITheme.TEXT_MUTED);
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 5, 15, 5);
        card.add(subLabel, gbc);

        gbc.insets = new Insets(5, 5, 5, 5);

        // Input form layout
        JLabel nameLabel = new JLabel("Patient Name");
        UITheme.styleLabel(nameLabel);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        card.add(nameLabel, gbc);

        nameField = new JTextField();
        UITheme.styleTextField(nameField);
        gbc.gridy = 4;
        card.add(nameField, gbc);

        JLabel bloodLabel = new JLabel("Required Blood Group");
        UITheme.styleLabel(bloodLabel);
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 1;
        card.add(bloodLabel, gbc);

        JLabel bagLabel = new JLabel("Number of Bags");
        UITheme.styleLabel(bagLabel);
        gbc.gridx = 1; gbc.gridy = 5;
        card.add(bagLabel, gbc);

        bloodCombo = new JComboBox<>(bloodGroups);
        UITheme.styleComboBox(bloodCombo);
        gbc.gridx = 0; gbc.gridy = 6;
        card.add(bloodCombo, gbc);

        bagField = new JTextField();
        UITheme.styleTextField(bagField);
        gbc.gridx = 1; gbc.gridy = 6;
        card.add(bagField, gbc);

        JLabel locLabel = new JLabel("Location Area");
        UITheme.styleLabel(locLabel);
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 1;
        card.add(locLabel, gbc);

        JLabel hospLabel = new JLabel("Preferred Hospital");
        UITheme.styleLabel(hospLabel);
        gbc.gridx = 1; gbc.gridy = 7;
        card.add(hospLabel, gbc);

        locationCombo = new JComboBox<>(locations);
        UITheme.styleComboBox(locationCombo);
        gbc.gridx = 0; gbc.gridy = 8;
        card.add(locationCombo, gbc);

        hospitalCombo = new JComboBox<>(hospitals);
        UITheme.styleComboBox(hospitalCombo);
        gbc.gridx = 1; gbc.gridy = 8;
        card.add(hospitalCombo, gbc);

        JLabel contactLabel = new JLabel("Contact Phone Number");
        UITheme.styleLabel(contactLabel);
        gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 2;
        card.add(contactLabel, gbc);

        contactField = new JTextField();
        UITheme.styleTextField(contactField);
        gbc.gridy = 10;
        card.add(contactField, gbc);

        // Request submission button
        requestButton = new JButton("Submit Request");
        UITheme.stylePrimaryButton(requestButton);
        requestButton.setPreferredSize(new Dimension(400, 42));
        requestButton.addActionListener(this);
        gbc.gridy = 11;
        gbc.insets = new Insets(20, 5, 10, 5);
        card.add(requestButton, gbc);

        centerWrapper.add(card);
        add(centerWrapper, BorderLayout.CENTER);

        add(UITheme.createFooter(), BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == requestButton) {
            try {
                String patientName = nameField.getText();
                String bloodGroup = (String) bloodCombo.getSelectedItem();
                if (bloodCombo.getSelectedIndex() == 0) bloodGroup = "";
                String bloodBags = bagField.getText();
                String location = (String) locationCombo.getSelectedItem();
                if (locationCombo.getSelectedIndex() == 0) location = "";
                String hospital = (String) hospitalCombo.getSelectedItem();
                if (hospitalCombo.getSelectedIndex() == 0) hospital = "";
                String contact = contactField.getText();

                // Validate and submit blood request
                BloodRequest req = requestController.submitRequest(patientName, bloodGroup, bloodBags, location, hospital, contact);

                JOptionPane.showMessageDialog(this, "Blood Request Submitted Successfully!\nRequest ID: " + req.getRequestId());
                NavigationController.getInstance().openMyRequestView(this, req);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
