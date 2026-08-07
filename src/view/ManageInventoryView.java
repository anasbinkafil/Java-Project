package view;

import model.BloodInventory;
import model.BloodRequest;
import model.DataStore;
import model.User;

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
 * Full Management Dashboard supporting complete CRUD operations (Add, Edit, Delete)
 * for Blood Stock, Patient Requests, and User Accounts.
 */
public class ManageInventoryView extends JFrame implements ActionListener {
    private JTable requestsTable, inventoryTable, usersTable;
    private DefaultTableModel requestsModel, inventoryModel, usersModel;
    
    // Request buttons
    private JButton approveBtn, fulfillBtn, rejectBtn, deleteReqBtn;

    // Inventory controls
    private JTextField bgField, locField, hospField, distField, bagsField;
    private JButton addStockBtn, deleteStockBtn;

    // User controls
    private JButton promoteUserBtn, deleteUserBtn;

    private DataStore dataStore;

    public ManageInventoryView() {
        dataStore = DataStore.getInstance();

        setTitle("System & Inventory Management Dashboard - BBMS");
        setSize(1080, 780);
        setMinimumSize(new Dimension(950, 680));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UITheme.applyWindowStyle(this);
        setLayout(new BorderLayout());

        // Top navigation bar
        add(UITheme.createTopNavBar(this, "Manage Stock & Requests"), BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(UITheme.FONT_BODY_BOLD);

        // Center Table Alignment & Badge Renderers
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        UITheme.PillBadgeRenderer badgeRenderer = new UITheme.PillBadgeRenderer();

        // TAB 1: Manage Blood Stock (Full CRUD)
        JPanel invPanel = new JPanel(new BorderLayout(15, 15));
        invPanel.setOpaque(false);
        invPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        // Add/Update Form Card
        JPanel formCard = UITheme.createCardPanel();
        formCard.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));

        bgField = new JTextField(4); UITheme.styleTextField(bgField);
        locField = new JTextField(8); UITheme.styleTextField(locField);
        hospField = new JTextField(12); UITheme.styleTextField(hospField);
        distField = new JTextField(4); UITheme.styleTextField(distField);
        bagsField = new JTextField(4); UITheme.styleTextField(bagsField);

        formCard.add(new JLabel("Group:")); formCard.add(bgField);
        formCard.add(new JLabel("Location:")); formCard.add(locField);
        formCard.add(new JLabel("Hospital:")); formCard.add(hospField);
        formCard.add(new JLabel("KM:")); formCard.add(distField);
        formCard.add(new JLabel("Bags:")); formCard.add(bagsField);

        addStockBtn = new JButton("Add / Update Stock");
        UITheme.stylePrimaryButton(addStockBtn);
        addStockBtn.addActionListener(this);
        formCard.add(addStockBtn);

        invPanel.add(formCard, BorderLayout.NORTH);

        // Inventory Table
        String[] invCols = {"BLOOD GROUP", "LOCATION", "HOSPITAL / BANK", "DISTANCE (KM)", "AVAILABLE BAGS"};
        inventoryModel = new DefaultTableModel(invCols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        inventoryTable = new JTable(inventoryModel);
        inventoryTable.setFont(UITheme.FONT_BODY);
        inventoryTable.setRowHeight(38);
        inventoryTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));

        inventoryTable.getColumnModel().getColumn(0).setCellRenderer(badgeRenderer);
        inventoryTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        inventoryTable.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer());
        inventoryTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        inventoryTable.getColumnModel().getColumn(4).setCellRenderer(badgeRenderer);

        JScrollPane invScroll = new JScrollPane(inventoryTable);
        invScroll.setBorder(new LineBorder(UITheme.CARD_BORDER, 1));
        invPanel.add(invScroll, BorderLayout.CENTER);

        JPanel invBtnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
        invBtnPanel.setOpaque(false);
        deleteStockBtn = new JButton("Delete Selected Stock");
        UITheme.styleOutlineButton(deleteStockBtn);
        deleteStockBtn.setForeground(new Color(185, 28, 28));
        deleteStockBtn.addActionListener(this);
        invBtnPanel.add(deleteStockBtn);
        invPanel.add(invBtnPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Manage Blood Stock", invPanel);

        // TAB 2: Manage Blood Requests
        JPanel requestsPanel = new JPanel(new BorderLayout(15, 15));
        requestsPanel.setOpaque(false);
        requestsPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel reqTitle = new JLabel("Patient Blood Requests Fulfillment & Management");
        reqTitle.setFont(UITheme.FONT_TITLE);
        reqTitle.setForeground(UITheme.TEXT_DARK);
        requestsPanel.add(reqTitle, BorderLayout.NORTH);

        String[] reqCols = {"REQUEST ID", "PATIENT NAME", "BLOOD GROUP", "BAGS", "LOCATION", "HOSPITAL", "CONTACT", "STATUS"};
        requestsModel = new DefaultTableModel(reqCols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        requestsTable = new JTable(requestsModel);
        requestsTable.setFont(UITheme.FONT_BODY);
        requestsTable.setRowHeight(38);
        requestsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));

        for (int i = 0; i < reqCols.length; i++) {
            if (i == 2 || i == 7) requestsTable.getColumnModel().getColumn(i).setCellRenderer(badgeRenderer);
            else requestsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane reqScroll = new JScrollPane(requestsTable);
        reqScroll.setBorder(new LineBorder(UITheme.CARD_BORDER, 1));
        requestsPanel.add(reqScroll, BorderLayout.CENTER);

        JPanel reqBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));
        reqBtnPanel.setOpaque(false);

        approveBtn = new JButton("Approve Request");
        UITheme.stylePrimaryButton(approveBtn);
        approveBtn.addActionListener(this);
        reqBtnPanel.add(approveBtn);

        fulfillBtn = new JButton("Fulfill & Deduct Stock");
        UITheme.stylePrimaryButton(fulfillBtn);
        fulfillBtn.setBackground(new Color(15, 118, 110));
        fulfillBtn.addActionListener(this);
        reqBtnPanel.add(fulfillBtn);

        rejectBtn = new JButton("Reject Request");
        UITheme.styleOutlineButton(rejectBtn);
        rejectBtn.addActionListener(this);
        reqBtnPanel.add(rejectBtn);

        deleteReqBtn = new JButton("Delete Request");
        UITheme.styleOutlineButton(deleteReqBtn);
        deleteReqBtn.setForeground(new Color(185, 28, 28));
        deleteReqBtn.addActionListener(this);
        reqBtnPanel.add(deleteReqBtn);

        requestsPanel.add(reqBtnPanel, BorderLayout.SOUTH);
        tabbedPane.addTab("Manage Requests", requestsPanel);

        // TAB 3: User & System Accounts Management
        JPanel usersPanel = new JPanel(new BorderLayout(15, 15));
        usersPanel.setOpaque(false);
        usersPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel userTitle = new JLabel("Registered System Users & Donors Management");
        userTitle.setFont(UITheme.FONT_TITLE);
        userTitle.setForeground(UITheme.TEXT_DARK);
        usersPanel.add(userTitle, BorderLayout.NORTH);

        String[] userCols = {"FULL NAME", "EMAIL", "PHONE", "BLOOD GROUP", "USERNAME", "ACCOUNT ROLE"};
        usersModel = new DefaultTableModel(userCols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        usersTable = new JTable(usersModel);
        usersTable.setFont(UITheme.FONT_BODY);
        usersTable.setRowHeight(38);
        usersTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));

        for (int i = 0; i < userCols.length; i++) {
            if (i == 3 || i == 5) usersTable.getColumnModel().getColumn(i).setCellRenderer(badgeRenderer);
            else usersTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane userScroll = new JScrollPane(usersTable);
        userScroll.setBorder(new LineBorder(UITheme.CARD_BORDER, 1));
        usersPanel.add(userScroll, BorderLayout.CENTER);

        JPanel userBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        userBtnPanel.setOpaque(false);

        promoteUserBtn = new JButton("Toggle Admin Role");
        UITheme.stylePrimaryButton(promoteUserBtn);
        promoteUserBtn.addActionListener(this);
        userBtnPanel.add(promoteUserBtn);

        deleteUserBtn = new JButton("Delete User Account");
        UITheme.styleOutlineButton(deleteUserBtn);
        deleteUserBtn.setForeground(new Color(185, 28, 28));
        deleteUserBtn.addActionListener(this);
        userBtnPanel.add(deleteUserBtn);

        usersPanel.add(userBtnPanel, BorderLayout.SOUTH);
        tabbedPane.addTab("Manage Users & Donors", usersPanel);

        add(tabbedPane, BorderLayout.CENTER);
        add(UITheme.createFooter(), BorderLayout.SOUTH);

        loadAllTableData();

        setVisible(true);
    }

    private void loadAllTableData() {
        // Load Requests
        requestsModel.setRowCount(0);
        List<BloodRequest> reqList = dataStore.getRequests();
        for (BloodRequest r : reqList) {
            requestsModel.addRow(new Object[]{r.getRequestId(), r.getPatientName(), r.getBloodGroup(), r.getBloodBags(), r.getLocation(), r.getHospital(), r.getContactNo(), r.getStatus()});
        }

        // Load Inventory
        inventoryModel.setRowCount(0);
        List<BloodInventory> invList = dataStore.getAllInventories();
        for (BloodInventory i : invList) {
            inventoryModel.addRow(new Object[]{i.getBloodGroup(), i.getLocation(), i.getHospitalName(), i.getDistanceKm(), i.getAvailableBags() + " Bags"});
        }

        // Load Users
        usersModel.setRowCount(0);
        List<User> userList = dataStore.getUsers();
        for (User u : userList) {
            usersModel.addRow(new Object[]{u.getFullName(), u.getEmail(), u.getPhone(), u.getBloodGroup(), u.getUsername(), u.getRole()});
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == approveBtn || e.getSource() == fulfillBtn || e.getSource() == rejectBtn) {
            int selRow = requestsTable.getSelectedRow();
            if (selRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a request row first.", "Selection Required", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String reqId = (String) requestsModel.getValueAt(selRow, 0);
            String status = (e.getSource() == approveBtn) ? "APPROVED" : (e.getSource() == fulfillBtn) ? "FULFILLED" : "REJECTED";

            boolean success = dataStore.updateRequestStatus(reqId, status);
            if (success) {
                JOptionPane.showMessageDialog(this, "Request " + reqId + " status updated to " + status + "!");
                loadAllTableData();
            }
        } else if (e.getSource() == deleteReqBtn) {
            int selRow = requestsTable.getSelectedRow();
            if (selRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a request to delete.", "Selection Required", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String reqId = (String) requestsModel.getValueAt(selRow, 0);
            if (dataStore.deleteBloodRequest(reqId)) {
                JOptionPane.showMessageDialog(this, "Blood request " + reqId + " deleted successfully!");
                loadAllTableData();
            }
        } else if (e.getSource() == addStockBtn) {
            try {
                String bg = bgField.getText().trim();
                String loc = locField.getText().trim();
                String hosp = hospField.getText().trim();
                double dist = Double.parseDouble(distField.getText().trim());
                int bags = Integer.parseInt(bagsField.getText().trim());

                if (bg.isEmpty() || loc.isEmpty() || hosp.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill out all stock fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                dataStore.addOrUpdateInventory(bg, loc, hosp, dist, bags);
                JOptionPane.showMessageDialog(this, "Stock added/updated successfully!");
                loadAllTableData();

                bgField.setText(""); locField.setText(""); hospField.setText(""); distField.setText(""); bagsField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid number format for Distance or Bags.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == deleteStockBtn) {
            int selRow = inventoryTable.getSelectedRow();
            if (selRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select an inventory stock row to delete.", "Selection Required", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (dataStore.deleteInventoryItem(selRow)) {
                JOptionPane.showMessageDialog(this, "Stock item deleted successfully!");
                loadAllTableData();
            }
        } else if (e.getSource() == promoteUserBtn) {
            int selRow = usersTable.getSelectedRow();
            if (selRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a user row.", "Selection Required", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String username = (String) usersModel.getValueAt(selRow, 4);
            String currentRole = (String) usersModel.getValueAt(selRow, 5);
            String newRole = "ADMIN".equalsIgnoreCase(currentRole) ? "PATIENT" : "ADMIN";

            if (dataStore.updateUserRole(username, newRole)) {
                JOptionPane.showMessageDialog(this, "User " + username + " role updated to " + newRole);
                loadAllTableData();
            }
        } else if (e.getSource() == deleteUserBtn) {
            int selRow = usersTable.getSelectedRow();
            if (selRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a user to delete.", "Selection Required", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String username = (String) usersModel.getValueAt(selRow, 4);
            if (dataStore.deleteUser(username)) {
                JOptionPane.showMessageDialog(this, "User " + username + " deleted successfully!");
                loadAllTableData();
            }
        }
    }
}
