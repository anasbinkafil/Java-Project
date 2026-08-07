package view;

import controller.NavigationController;
import controller.SearchController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchBloodView extends JFrame implements ActionListener {
    private JComboBox<String> bloodCombo, locationCombo;
    private JTextArea resultArea;
    private JScrollPane scrollPane;
    private JButton searchButton, requestButton, clearButton, backButton;

    private SearchController searchController;

    private String[] bloodGroups = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
    private String[] locations = {"DHANMONDI", "PURAN DHAKA", "MIRPUR", "SAVAR", "BANANI"};

    public SearchBloodView() {
        searchController = new SearchController();

        setTitle("Search Blood - Blood Bank System");
        setSize(850, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UITheme.applyWindowStyle(this);
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = UITheme.createHeaderPanel(
                "SEARCH BLOOD INVENTORY",
                "Find real-time available blood bags across nearby hospitals & centers"
        );
        add(headerPanel, BorderLayout.NORTH);

        // Main Container
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        // Filter Controls Card
        JPanel filterCard = UITheme.createCardPanel();
        filterCard.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Blood Group Combo
        JLabel bloodLabel = new JLabel("Blood Group");
        UITheme.styleLabel(bloodLabel);
        gbc.gridx = 0; gbc.gridy = 0;
        filterCard.add(bloodLabel, gbc);

        bloodCombo = new JComboBox<>(bloodGroups);
        UITheme.styleComboBox(bloodCombo);
        gbc.gridx = 1; gbc.gridy = 0;
        filterCard.add(bloodCombo, gbc);

        // Location Combo
        JLabel locationLabel = new JLabel("Location Area");
        UITheme.styleLabel(locationLabel);
        gbc.gridx = 2; gbc.gridy = 0;
        filterCard.add(locationLabel, gbc);

        locationCombo = new JComboBox<>(locations);
        UITheme.styleComboBox(locationCombo);
        gbc.gridx = 3; gbc.gridy = 0;
        filterCard.add(locationCombo, gbc);

        // Action Buttons Row
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 5));
        buttonPanel.setOpaque(false);

        searchButton = new JButton("Search Available");
        UITheme.stylePrimaryButton(searchButton);
        searchButton.setPreferredSize(new Dimension(160, 36));
        searchButton.addActionListener(this);
        buttonPanel.add(searchButton);

        requestButton = new JButton("Request Blood");
        UITheme.styleSecondaryButton(requestButton);
        requestButton.setPreferredSize(new Dimension(150, 36));
        requestButton.addActionListener(this);
        buttonPanel.add(requestButton);

        clearButton = new JButton("Clear");
        UITheme.styleOutlineButton(clearButton);
        clearButton.setPreferredSize(new Dimension(90, 36));
        clearButton.addActionListener(this);
        buttonPanel.add(clearButton);

        backButton = new JButton("Back");
        UITheme.styleOutlineButton(backButton);
        backButton.setPreferredSize(new Dimension(90, 36));
        backButton.addActionListener(this);
        buttonPanel.add(backButton);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 4;
        gbc.insets = new Insets(12, 5, 5, 5);
        filterCard.add(buttonPanel, gbc);

        mainPanel.add(filterCard, BorderLayout.NORTH);

        // Results Card Panel
        JPanel resultCard = UITheme.createCardPanel();
        resultCard.setLayout(new BorderLayout(10, 10));

        JLabel resultTitle = new JLabel("Available Inventory & Search Results");
        resultTitle.setFont(UITheme.FONT_HEADER);
        resultTitle.setForeground(UITheme.TEXT_MAIN);
        resultCard.add(resultTitle, BorderLayout.NORTH);

        resultArea = new JTextArea();
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        resultArea.setForeground(UITheme.TEXT_MAIN);
        resultArea.setBackground(new Color(250, 252, 255));
        resultArea.setMargin(new Insets(10, 12, 10, 12));
        resultArea.setEditable(false);

        scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(new LineBorder(UITheme.BORDER_COLOR, 1, true));
        resultCard.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(resultCard, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

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
        if (e.getSource() == searchButton) {
            String blood = (String) bloodCombo.getSelectedItem();
            String location = (String) locationCombo.getSelectedItem();
            String resultText = searchController.searchBlood(blood, location);
            resultArea.setText(resultText);
        } else if (e.getSource() == requestButton) {
            NavigationController.getInstance().openRequestBloodView(this);
        } else if (e.getSource() == clearButton) {
            bloodCombo.setSelectedIndex(0);
            locationCombo.setSelectedIndex(0);
            resultArea.setText("");
        } else if (e.getSource() == backButton) {
            NavigationController.getInstance().openWelcomeView(this);
        }
    }
}
