package view;

import model.BloodInventory;
import model.BloodRequest;
import model.DataStore;

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
 * Admin & Hospital Management Dashboard for managing blood stock & request fulfillment.
 */
public class ManageInventoryView extends JFrame implements ActionListener {
    private JTable requestsTable, inventoryTable;
    private DefaultTableModel requestsModel, inventoryModel;
    private JButton approveBtn, fulfillBtn, rejectBtn, addStockBtn;

    private JTextField bgField, locField, hospField, distField, bagsField;

    private DataStore dataStore;

    public ManageInventoryView() {
        dataStore = DataStore.getInstance();

        setTitle("Hospital & Stock Management Dashboard - BBMS");
        setSize(1050, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UITheme.applyWindowStyle(this);
        setLayout(new BorderLayout());

        // Top nav bar
        add(UITheme.createTopNavBar(this, "Manage Stock & Requests"), BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(UITheme.FONT_BODY_BOLD);

        // TAB 1: Request Fulfillment & Status Control
        JPanel requestsPanel = new JPanel(new BorderLayout(15, 15));
        requestsPanel.setOpaque(false);
        requestsPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel reqTitle = new JLabel("Patient Blood Requests Fulfillment");
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

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < reqCols.length; i++) {
            requestsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane reqScroll = new JScrollPane(requestsTable);
        reqScroll.setBorder(new LineBorder(UITheme.CARD_BORDER, 1));
        requestsPanel.add(reqScroll, BorderLayout.CENTER);

        JPanel reqBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        reqBtnPanel.setOpaque(false);

        approveBtn = new JButton("Approve Request");
        UITheme.stylePrimaryButton(approveBtn);
        approveBtn.addActionListener(this);
        reqBtnPanel.add(approveBtn);

        fulfillBtn = new JButton("Fulfill & Deduct Stock");
        UITheme.stylePrimaryButton(fulfillBtn);
        fulfillBtn.setBackground(new Color(0, 121, 107));
        fulfillBtn.addActionListener(this);
        reqBtnPanel.add(fulfillBtn);

        rejectBtn = new JButton("Reject Request");
        UITheme.styleOutlineButton(rejectBtn);
        rejectBtn.addActionListener(this);
        reqBtnPanel.add(rejectBtn);

        requestsPanel.add(reqBtnPanel, BorderLayout.SOUTH);
        tabbedPane.addTab("Manage Requests", requestsPanel);

        // TAB 2: Inventory Stock Management (Add / Update Stock)
        JPanel invPanel = new JPanel(new BorderLayout(15, 15));
        invPanel.setOpaque(false);
        invPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        // Add Stock Form Card
        JPanel formCard = UITheme.createCardPanel();
        formCard.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 5));

        bgField = new JTextField(4); UITheme.styleTextField(bgField);
        locField = new JTextField(8); UITheme.styleTextField(locField);
        hospField = new JTextField(12); UITheme.styleTextField(hospField);
        distField = new JTextField(4); UITheme.styleTextField(distField);
        bagsField = new JTextField(4); UITheme.styleTextField(bagsField);

        formCard.add(new JLabel("Blood Group:")); formCard.add(bgField);
        formCard.add(new JLabel("Location:")); formCard.add(locField);
        formCard.add(new JLabel("Hospital:")); formCard.add(hospField);
        formCard.add(new JLabel("Dist(KM):")); formCard.add(distField);
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

        for (int i = 0; i < invCols.length; i++) {
            inventoryTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane invScroll = new JScrollPane(inventoryTable);
        invScroll.setBorder(new LineBorder(UITheme.CARD_BORDER, 1));
        invPanel.add(invScroll, BorderLayout.CENTER);

        tabbedPane.addTab("Manage Inventory Stock", invPanel);

        add(tabbedPane, BorderLayout.CENTER);
        add(UITheme.createFooter(), BorderLayout.SOUTH);

        loadTableData();

        setVisible(true);
    }

    private void loadTableData() {
        requestsModel.setRowCount(0);
        List<BloodRequest> reqList = dataStore.getRequests();
        for (BloodRequest r : reqList) {
            requestsModel.addRow(new Object[]{r.getRequestId(), r.getPatientName(), r.getBloodGroup(), r.getBloodBags(), r.getLocation(), r.getHospital(), r.getContactNo(), r.getStatus()});
        }

        inventoryModel.setRowCount(0);
        List<BloodInventory> invList = dataStore.getAllInventories();
        for (BloodInventory i : invList) {
            inventoryModel.addRow(new Object[]{i.getBloodGroup(), i.getLocation(), i.getHospitalName(), i.getDistanceKm(), i.getAvailableBags() + " Bags"});
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
                loadTableData();
            }
        } else if (e.getSource() == addStockBtn) {
            try {
                String bg = bgField.getText().trim();
                String loc = locField.getText().trim();
                String hosp = hospField.getText().trim();
                double dist = Double.parseDouble(distField.getText().trim());
                int bags = Integer.parseInt(bagsField.getText().trim());

                if (bg.isEmpty() || loc.isEmpty() || hosp.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill out all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                dataStore.addOrUpdateInventory(bg, loc, hosp, dist, bags);
                JOptionPane.showMessageDialog(this, "Stock added/updated successfully!");
                loadTableData();

                bgField.setText(""); locField.setText(""); hospField.setText(""); distField.setText(""); bagsField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid number format for Distance or Bags.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
