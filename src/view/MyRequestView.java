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
        setTitle("My Requests - Blood Bank Management System");
        setSize(780, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UITheme.applyWindowStyle(this);
        setLayout(new BorderLayout());

        // Top Navigation Bar
        add(UITheme.createTopNavBar(this, "My Requests"), BorderLayout.NORTH);

        // Center Content Card Wrapper
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        centerWrapper.setBorder(new EmptyBorder(25, 25, 25, 25));

        JPanel card = UITheme.createCardPanel();
        card.setLayout(new BorderLayout(15, 15));
        card.setPreferredSize(new Dimension(620, 420));

        JLabel titleLabel = new JLabel("Submitted Blood Request Details", SwingConstants.CENTER);
        titleLabel.setFont(UITheme.FONT_TITLE);
        titleLabel.setForeground(UITheme.TEXT_DARK);
        card.add(titleLabel, BorderLayout.NORTH);

        requestArea = new JTextArea();
        requestArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        requestArea.setForeground(UITheme.TEXT_DARK);
        requestArea.setBackground(new Color(250, 252, 255));
        requestArea.setMargin(new Insets(15, 15, 15, 15));
        requestArea.setEditable(false);

        if (request != null) {
            requestArea.setText(
                    "===========================================================\n" +
                    "                 BLOOD REQUEST CONFIRMATION                \n" +
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
            requestArea.setText("No recent blood request submitted in this session.");
        }

        JScrollPane scrollPane = new JScrollPane(requestArea);
        scrollPane.setBorder(new LineBorder(UITheme.CARD_BORDER, 1, true));
        card.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);

        backButton = new JButton("Back to Search");
        UITheme.stylePrimaryButton(backButton);
        backButton.setPreferredSize(new Dimension(170, 38));
        backButton.addActionListener(this);
        buttonPanel.add(backButton);

        card.add(buttonPanel, BorderLayout.SOUTH);

        centerWrapper.add(card);
        add(centerWrapper, BorderLayout.CENTER);

        // Footer
        add(UITheme.createFooter(), BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            NavigationController.getInstance().openSearchBloodView(this);
        }
    }
}
