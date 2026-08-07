package view;

import controller.NavigationController;
import model.BloodInventory;
import model.DataStore;
import model.Donor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Main dashboard screen for searching available blood stocks across hospitals and registered donors.
 */
public class SearchBloodView extends JFrame implements ActionListener {
    private JComboBox<String> bloodCombo, locationCombo;
    private JTable resultTable, donorTable;
    private DefaultTableModel tableModel, donorModel;
    private JButton searchButton, requestButton, beDonorButton;

    private String[] bloodGroups = {"Select Group", "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
    private String[] locations = {"Select Location", "DHANMONDI", "PURAN DHAKA", "MIRPUR", "SAVAR", "BANANI"};

    public SearchBloodView() {
        setTitle("Search Available Blood & Donors - Blood Bank System");
        setSize(1040, 750);
        setMinimumSize(new Dimension(900, 650));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UITheme.applyWindowStyle(this);
        setLayout(new BorderLayout());

        // Header navigation bar
        add(UITheme.createTopNavBar(this, "Search"), BorderLayout.NORTH);

        // Main content wrapper
        JPanel mainContent = new JPanel(new BorderLayout(20, 20));
        mainContent.setOpaque(false);
        mainContent.setBorder(new EmptyBorder(20, 30, 20, 30));

        // Header text section
        JPanel headerSection = new JPanel();
        headerSection.setLayout(new BoxLayout(headerSection, BoxLayout.Y_AXIS));
        headerSection.setOpaque(false);

        JLabel titleLabel = new JLabel("Find Available Blood & Donors Near You");
        titleLabel.setFont(UITheme.FONT_HEADER_TITLE);
        titleLabel.setForeground(UITheme.TEXT_DARK);
        headerSection.add(titleLabel);

        headerSection.add(Box.createVerticalStrut(4));

        JLabel subLabel = new JLabel("Real-time inventory from verified hospitals & registered donors list (Record.txt).");
        subLabel.setFont(UITheme.FONT_SUBTITLE);
        subLabel.setForeground(UITheme.TEXT_MUTED);
        headerSection.add(subLabel);

        mainContent.add(headerSection, BorderLayout.NORTH);

        // Body section layout
        JPanel bodyPanel = new JPanel(new BorderLayout(15, 15));
        bodyPanel.setOpaque(false);

        // Filters bar card
        JPanel filterCard = UITheme.createCardPanel();
        filterCard.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 8));

        // Dropdown controls
        JPanel bgGroup = new JPanel(new BorderLayout(5, 5));
        bgGroup.setOpaque(false);
        JLabel bgLabel = new JLabel("Blood Group");
        UITheme.styleLabel(bgLabel);
        bgGroup.add(bgLabel, BorderLayout.NORTH);

        bloodCombo = new JComboBox<>(bloodGroups);
        UITheme.styleComboBox(bloodCombo);
        bloodCombo.setPreferredSize(new Dimension(180, 38));
        bgGroup.add(bloodCombo, BorderLayout.CENTER);
        filterCard.add(bgGroup);

        JPanel locGroup = new JPanel(new BorderLayout(5, 5));
        locGroup.setOpaque(false);
        JLabel locLabel = new JLabel("Location");
        UITheme.styleLabel(locLabel);
        locGroup.add(locLabel, BorderLayout.NORTH);

        locationCombo = new JComboBox<>(locations);
        UITheme.styleComboBox(locationCombo);
        locationCombo.setPreferredSize(new Dimension(200, 38));
        locGroup.add(locationCombo, BorderLayout.CENTER);
        filterCard.add(locGroup);

        // Search action button
        JPanel btnGroup = new JPanel(new BorderLayout(5, 5));
        btnGroup.setOpaque(false);
        btnGroup.add(new JLabel(" "), BorderLayout.NORTH);

        searchButton = new JButton("Search");
        UITheme.stylePrimaryButton(searchButton);
        searchButton.setPreferredSize(new Dimension(120, 38));
        searchButton.addActionListener(this);
        btnGroup.add(searchButton, BorderLayout.CENTER);
        filterCard.add(btnGroup);

        // Become a donor button
        JPanel donorBtnGroup = new JPanel(new BorderLayout(5, 5));
        donorBtnGroup.setOpaque(false);
        donorBtnGroup.add(new JLabel(" "), BorderLayout.NORTH);

        beDonorButton = new JButton("Become a Donor");
        UITheme.styleOutlineButton(beDonorButton);
        beDonorButton.setPreferredSize(new Dimension(150, 38));
        beDonorButton.addActionListener(this);
        donorBtnGroup.add(beDonorButton, BorderLayout.CENTER);
        filterCard.add(donorBtnGroup);

        bodyPanel.add(filterCard, BorderLayout.NORTH);

        // Tabbed Pane for Hospital Inventory and Registered Donors
        JTabbedPane resultTabs = new JTabbedPane();
        resultTabs.setFont(UITheme.FONT_BODY_BOLD);

        // Column cell alignment & Pill Badge styling
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        UITheme.PillBadgeRenderer badgeRenderer = new UITheme.PillBadgeRenderer();

        // TAB 1: Hospital Stock Table
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
        resultTable.setRowHeight(40);
        resultTable.setShowGrid(false);
        resultTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));

        resultTable.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer());
        resultTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        resultTable.getColumnModel().getColumn(2).setCellRenderer(badgeRenderer);
        resultTable.getColumnModel().getColumn(3).setCellRenderer(badgeRenderer);
        resultTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

        JScrollPane tableScroll = new JScrollPane(resultTable);
        tableScroll.setBorder(new LineBorder(UITheme.CARD_BORDER, 1));
        tableCard.add(tableScroll, BorderLayout.CENTER);
        resultTabs.addTab("Hospital Blood Stock", tableCard);

        // TAB 2: Registered Donors List Table (Record.txt)
        JPanel donorCard = UITheme.createCardPanel();
        donorCard.setLayout(new BorderLayout(10, 10));

        String[] donorCols = {"DONOR NAME", "AGE", "SEX", "EMAIL", "PHONE NO", "ADDRESS", "BLOOD GROUP"};
        donorModel = new DefaultTableModel(donorCols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        donorTable = new JTable(donorModel);
        donorTable.setFont(UITheme.FONT_BODY);
        donorTable.setRowHeight(40);
        donorTable.setShowGrid(false);
        donorTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));

        for (int i = 0; i < donorCols.length; i++) {
            if (i == 6) donorTable.getColumnModel().getColumn(i).setCellRenderer(badgeRenderer);
            else donorTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane donorScroll = new JScrollPane(donorTable);
        donorScroll.setBorder(new LineBorder(UITheme.CARD_BORDER, 1));
        donorCard.add(donorScroll, BorderLayout.CENTER);
        resultTabs.addTab("Registered Donors Network (Record.txt)", donorCard);

        bodyPanel.add(resultTabs, BorderLayout.CENTER);

        // Right side action widget
        JPanel sidebarCard = UITheme.createCardPanel();
        sidebarCard.setLayout(new BoxLayout(sidebarCard, BoxLayout.Y_AXIS));
        sidebarCard.setPreferredSize(new Dimension(220, 260));

        JLabel iconLabel = new JLabel("Direct Request", SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        iconLabel.setForeground(UITheme.PRIMARY_RED);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebarCard.add(iconLabel);

        sidebarCard.add(Box.createVerticalStrut(10));

        JLabel urgentTitle = new JLabel("Urgent Need?");
        urgentTitle.setFont(UITheme.FONT_TITLE);
        urgentTitle.setForeground(UITheme.TEXT_DARK);
        urgentTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebarCard.add(urgentTitle);

        sidebarCard.add(Box.createVerticalStrut(8));

        JLabel urgentDesc = new JLabel("<html><center>Can't find what you need in current stock? Submit a direct request to our network.</center></html>");
        urgentDesc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        urgentDesc.setForeground(UITheme.TEXT_MUTED);
        urgentDesc.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebarCard.add(urgentDesc);

        sidebarCard.add(Box.createVerticalStrut(18));

        requestButton = new JButton("Submit Request");
        UITheme.styleOutlineButton(requestButton);
        requestButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        requestButton.setMaximumSize(new Dimension(180, 38));
        requestButton.addActionListener(this);
        sidebarCard.add(requestButton);

        bodyPanel.add(sidebarCard, BorderLayout.EAST);

        mainContent.add(bodyPanel, BorderLayout.CENTER);
        add(mainContent, BorderLayout.CENTER);

        add(UITheme.createFooter(), BorderLayout.SOUTH);

        // Populate initial table data
        loadInventoryData("", "");

        setVisible(true);
    }

    // Loads inventory & donor rows into tables
    private void loadInventoryData(String bloodGroup, String location) {
        tableModel.setRowCount(0);
        List<BloodInventory> results = DataStore.getInstance().searchInventory(bloodGroup, location);
        if (!results.isEmpty()) {
            for (BloodInventory item : results) {
                tableModel.addRow(new Object[]{
                    item.getHospitalName(),
                    item.getLocation(),
                    item.getBloodGroup(),
                    item.getAvailableBags() + " Bags",
                    item.getDistanceKm() + " KM"
                });
            }
        } else {
            tableModel.addRow(new Object[]{"No stock found", "-", "-", "0 Bags", "-"});
        }

        // Load Registered Donors (Record.txt)
        donorModel.setRowCount(0);
        List<Donor> donorList = DataStore.getInstance().getDonors();
        for (Donor d : donorList) {
            if ((bloodGroup.isEmpty() || d.getBg().equalsIgnoreCase(bloodGroup)) &&
                (location.isEmpty() || d.getAdr().toLowerCase().contains(location.toLowerCase()))) {
                donorModel.addRow(new Object[]{d.getName(), d.getAge(), d.getSex(), d.getEmail(), d.getPn(), d.getAdr(), d.getBg()});
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            String blood = (String) bloodCombo.getSelectedItem();
            if (bloodCombo.getSelectedIndex() == 0) blood = "";
            String location = (String) locationCombo.getSelectedItem();
            if (locationCombo.getSelectedIndex() == 0) location = "";

            loadInventoryData(blood, location);
        } else if (e.getSource() == requestButton) {
            NavigationController.getInstance().openRequestBloodView(this);
        } else if (e.getSource() == beDonorButton) {
            NavigationController.getInstance().openBeDonorView(this);
        }
    }
}
