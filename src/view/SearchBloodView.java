package view;

import controller.NavigationController;
import controller.SearchController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchBloodView extends JFrame implements ActionListener {
    private JComboBox<String> bloodCombo, locationCombo;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private JButton searchButton, requestButton;

    private SearchController searchController;

    private String[] bloodGroups = {"Select Group", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
    private String[] locations = {"Select Location", "DHANMONDI", "PURAN DHAKA", "MIRPUR", "SAVAR", "BANANI"};

    public SearchBloodView() {
        searchController = new SearchController();

        setTitle("Search Available Blood - Blood Bank System");
        setSize(980, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UITheme.applyWindowStyle(this);
        setLayout(new BorderLayout());

        // Top Navigation Bar
        add(UITheme.createTopNavBar(this, "Search"), BorderLayout.NORTH);

        // Main Content Panel
        JPanel mainContent = new JPanel(new BorderLayout(20, 20));
        mainContent.setOpaque(false);
        mainContent.setBorder(new EmptyBorder(25, 35, 25, 35));

        // Header Section
        JPanel headerSection = new JPanel();
        headerSection.setLayout(new BoxLayout(headerSection, BoxLayout.Y_AXIS));
        headerSection.setOpaque(false);

        JLabel titleLabel = new JLabel("Find Available Blood Near You");
        titleLabel.setFont(UITheme.FONT_HEADER_TITLE);
        titleLabel.setForeground(UITheme.TEXT_DARK);
        headerSection.add(titleLabel);

        headerSection.add(Box.createVerticalStrut(4));

        JLabel subLabel = new JLabel("Real-time inventory from verified hospitals and blood banks.");
        subLabel.setFont(UITheme.FONT_SUBTITLE);
        subLabel.setForeground(UITheme.TEXT_MUTED);
        headerSection.add(subLabel);

        mainContent.add(headerSection, BorderLayout.NORTH);

        // Center Panel containing Filters + Table + Sidebar
        JPanel bodyPanel = new JPanel(new BorderLayout(20, 20));
        bodyPanel.setOpaque(false);

        // Filter Card (Top of Body)
        JPanel filterCard = UITheme.createCardPanel();
        filterCard.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

        // Blood Group Combo
        JPanel bgGroup = new JPanel(new BorderLayout(5, 5));
        bgGroup.setOpaque(false);
        JLabel bgLabel = new JLabel("Blood Group");
        UITheme.styleLabel(bgLabel);
        bgGroup.add(bgLabel, BorderLayout.NORTH);

        bloodCombo = new JComboBox<>(bloodGroups);
        UITheme.styleComboBox(bloodCombo);
        bloodCombo.setPreferredSize(new Dimension(200, 38));
        bgGroup.add(bloodCombo, BorderLayout.CENTER);
        filterCard.add(bgGroup);

        // Location Combo
        JPanel locGroup = new JPanel(new BorderLayout(5, 5));
        locGroup.setOpaque(false);
        JLabel locLabel = new JLabel("Location");
        UITheme.styleLabel(locLabel);
        locGroup.add(locLabel, BorderLayout.NORTH);

        locationCombo = new JComboBox<>(locations);
        UITheme.styleComboBox(locationCombo);
        locationCombo.setPreferredSize(new Dimension(220, 38));
        locGroup.add(locationCombo, BorderLayout.CENTER);
        filterCard.add(locGroup);

        // Search Button
        JPanel btnGroup = new JPanel(new BorderLayout(5, 5));
        btnGroup.setOpaque(false);
        btnGroup.add(new JLabel(" "), BorderLayout.NORTH);

        searchButton = new JButton("🔍  Search");
        UITheme.stylePrimaryButton(searchButton);
        searchButton.setPreferredSize(new Dimension(130, 38));
        searchButton.addActionListener(this);
        btnGroup.add(searchButton, BorderLayout.CENTER);
        filterCard.add(btnGroup);

        bodyPanel.add(filterCard, BorderLayout.NORTH);

        // Left Side: Inventory Table Card
        JPanel tableCard = UITheme.createCardPanel();
        tableCard.setLayout(new BorderLayout(10, 10));

        String[] columns = {"HOSPITAL / BANK", "LOCATION", "BLOOD GROUP", "AVAILABLE", "DISTANCE"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        resultTable = new JTable(tableModel);
        resultTable.setFont(UITheme.FONT_BODY);
        resultTable.setRowHeight(42);
        resultTable.setShowGrid(false);
        resultTable.setIntercellSpacing(new Dimension(0, 0));
        resultTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
        resultTable.getTableHeader().setBackground(new Color(248, 250, 252));
        resultTable.getTableHeader().setForeground(UITheme.TEXT_MUTED);
        resultTable.getTableHeader().setReorderingAllowed(false);

        // Center align table text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 1; i < columns.length; i++) {
            resultTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane tableScroll = new JScrollPane(resultTable);
        tableScroll.setBorder(new LineBorder(UITheme.CARD_BORDER, 1));
        tableScroll.getViewport().setBackground(Color.WHITE);

        tableCard.add(tableScroll, BorderLayout.CENTER);
        bodyPanel.add(tableCard, BorderLayout.CENTER);

        // Right Side: Urgent Need Card
        JPanel sidebarCard = UITheme.createCardPanel();
        sidebarCard.setLayout(new BoxLayout(sidebarCard, BoxLayout.Y_AXIS));
        sidebarCard.setPreferredSize(new Dimension(240, 280));

        JLabel iconLabel = new JLabel("🚨", SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebarCard.add(iconLabel);

        sidebarCard.add(Box.createVerticalStrut(10));

        JLabel urgentTitle = new JLabel("Urgent Need?");
        urgentTitle.setFont(UITheme.FONT_TITLE);
        urgentTitle.setForeground(UITheme.TEXT_DARK);
        urgentTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebarCard.add(urgentTitle);

        sidebarCard.add(Box.createVerticalStrut(8));

        JLabel urgentDesc = new JLabel("<html><center>Can't find what you need in the current inventory? Submit a direct request to our donor network.</center></html>");
        urgentDesc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        urgentDesc.setForeground(UITheme.TEXT_MUTED);
        urgentDesc.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebarCard.add(urgentDesc);

        sidebarCard.add(Box.createVerticalStrut(18));

        requestButton = new JButton("Submit Request");
        UITheme.styleOutlineButton(requestButton);
        requestButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        requestButton.setMaximumSize(new Dimension(190, 38));
        requestButton.addActionListener(this);
        sidebarCard.add(requestButton);

        bodyPanel.add(sidebarCard, BorderLayout.EAST);

        mainContent.add(bodyPanel, BorderLayout.CENTER);
        add(mainContent, BorderLayout.CENTER);

        // Footer
        add(UITheme.createFooter(), BorderLayout.SOUTH);

        // Load initial data
        loadInventoryData("", "");

        setVisible(true);
    }

    private void loadInventoryData(String bloodGroup, String location) {
        tableModel.setRowCount(0);
        // Seed default demo rows matching exact Stitch screenshot
        tableModel.addRow(new Object[]{"🏥  Square Hospital", "Dhanmondi", "B-", "🟢  5 Bags", "1.2 KM"});
        tableModel.addRow(new Object[]{"🏥  Labaid Specialized", "Dhanmondi", "B-", "🟡  2 Bags", "2.5 KM"});
        tableModel.addRow(new Object[]{"🩸  Quantum Blood Lab", "Banani", "B-", "🟢  12 Bags", "6.8 KM"});
        tableModel.addRow(new Object[]{"🏥  Evercare Hospital", "Mirpur", "A+", "🟢  8 Bags", "4.1 KM"});
        tableModel.addRow(new Object[]{"🏥  United Hospital", "Puran Dhaka", "O+", "🟢  15 Bags", "3.0 KM"});
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            String blood = (String) bloodCombo.getSelectedItem();
            if (bloodCombo.getSelectedIndex() == 0) blood = "";
            String location = (String) locationCombo.getSelectedItem();
            if (locationCombo.getSelectedIndex() == 0) location = "";

            String resultText = searchController.searchBlood(blood, location);
            tableModel.setRowCount(0);
            if (resultText != null && !resultText.isEmpty()) {
                String[] lines = resultText.split("\n");
                for (String line : lines) {
                    if (!line.trim().isEmpty()) {
                        tableModel.addRow(new Object[]{line, location.isEmpty() ? "Dhaka" : location, blood.isEmpty() ? "All" : blood, "🟢 Available", "Near"});
                    }
                }
            } else {
                tableModel.addRow(new Object[]{"No stock found", "-", "-", "0 Bags", "-"});
            }
        } else if (e.getSource() == requestButton) {
            NavigationController.getInstance().openRequestBloodView(this);
        }
    }
}
