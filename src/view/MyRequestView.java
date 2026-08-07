package view;

import controller.NavigationController;
import model.BloodRequest;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyRequestView extends JFrame implements ActionListener {
    private JTextArea requestArea;
    private JButton backButton;

    public MyRequestView(BloodRequest request) {
        setTitle("My Blood Requests - Blood Bank System");
        setSize(720, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UITheme.applyWindowStyle(this);
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = UITheme.createHeaderPanel(
                "MY BLOOD REQUEST DETAILS",
                "Clinical confirmation and urgent status tracking"
        );
        add(headerPanel, BorderLayout.NORTH);

        // Main Panel Card
        JPanel mainWrapper = new JPanel(new GridBagLayout());
        mainWrapper.setOpaque(false);
        mainWrapper.setBorder(new EmptyBorder(15, 20, 15, 20));

        JPanel card = UITheme.createCardPanel();
        card.setLayout(new BorderLayout(15, 15));
        card.setPreferredSize(new Dimension(620, 380));

        JLabel cardTitle = new JLabel("Submitted Request Summary", SwingConstants.CENTER);
        cardTitle.setFont(UITheme.FONT_TITLE);
        cardTitle.setForeground(UITheme.TEXT_MAIN);
        card.add(cardTitle, BorderLayout.NORTH);

        requestArea = new JTextArea();
        requestArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        requestArea.setForeground(UITheme.TEXT_MAIN);
        requestArea.setBackground(new Color(250, 252, 255));
        requestArea.setMargin(new Insets(15, 15, 15, 15));
        requestArea.setEditable(false);

        if (request != null) {
            requestArea.setText(
                    "===========================================================\n" +
                    "                 CLINICAL BLOOD REQUEST DETAILS           \n" +
                    "===========================================================\n\n" +
                    "  Request ID   : " + request.getRequestId() + "\n" +
                    "  Patient Name : " + request.getPatientName() + "\n" +
                    "  Blood Group  : " + request.getBloodGroup() + "\n" +
                    "  Blood Bags   : " + request.getBloodBags() + "\n" +
                    "  Location     : " + request.getLocation() + "\n" +
                    "  Hospital     : " + request.getHospital() + "\n" +
                    "  Contact No   : " + request.getContactNo() + "\n" +
                    "  Status       : " + request.getStatus() + "\n\n" +
                    "==========================================================="
            );
        } else {
            requestArea.setText("No active blood requests found.");
        }

        JScrollPane scrollPane = new JScrollPane(requestArea);
        scrollPane.setBorder(new LineBorder(UITheme.BORDER_COLOR, 1, true));
        card.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);

        backButton = new JButton("Back to Search");
        UITheme.stylePrimaryButton(backButton);
        backButton.setPreferredSize(new Dimension(170, 38));
        backButton.addActionListener(this);
        buttonPanel.add(backButton);

        card.add(buttonPanel, BorderLayout.SOUTH);

        mainWrapper.add(card);
        add(mainWrapper, BorderLayout.CENTER);

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
        if (e.getSource() == backButton) {
            NavigationController.getInstance().openSearchBloodView(this);
        }
    }
}
