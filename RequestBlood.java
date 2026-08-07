import javax.swing.*;
import java.awt.event.*;

public class RequestBlood extends JFrame implements ActionListener
{
    JLabel titleLabel;
    JLabel nameLabel, bloodLabel, bagLabel, locationLabel, hospitalLabel, contactLabel;

    JTextField nameField;
    JTextField bagField;
    JTextField contactField;

    JComboBox<String> bloodCombo;
    JComboBox<String> locationCombo;
    JComboBox<String> hospitalCombo;

    JButton requestButton;
    JButton clearButton;
    JButton backButton;

    String bloodGroups[] =
    {
        "A+","A-","B+","B-","AB+","AB-","O+","O-"
    };

    String locations[] =
    {
        "DHANMONDI",
        "PURAN DHAKA",
        "MIRPUR",
        "SAVAR",
        "BANANI"
    };

    String hospitals[] =
    {
        "SQUARE HOSPITAL",
        "UNITED HOSPITAL",
        "EVERCARE HOSPITAL",
        "DHAKA MEDICAL COLLEGE HOSPITAL",
        "SIR SALIMULLAH MEDICAL COLLEGE HOSPITAL",
        "ENAM MEDICAL COLLEGE HOSPITAL"
    };

    public RequestBlood()
    {
        setTitle("Request Blood");
        setSize(750,600);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        titleLabel = new JLabel("REQUEST BLOOD");
        titleLabel.setBounds(300,20,200,30);
        add(titleLabel);

        nameLabel = new JLabel("Patient Name");
        nameLabel.setBounds(120,80,120,25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(260,80,220,25);
        add(nameField);

        bloodLabel = new JLabel("Blood Group");
        bloodLabel.setBounds(120,130,120,25);
        add(bloodLabel);

        bloodCombo = new JComboBox<String>(bloodGroups);
        bloodCombo.setBounds(260,130,220,25);
        add(bloodCombo);

        bagLabel = new JLabel("Blood Bags");
        bagLabel.setBounds(120,180,120,25);
        add(bagLabel);

        bagField = new JTextField();
        bagField.setBounds(260,180,220,25);
        add(bagField);

        locationLabel = new JLabel("Location");
        locationLabel.setBounds(120,230,120,25);
        add(locationLabel);

        locationCombo = new JComboBox<String>(locations);
        locationCombo.setBounds(260,230,220,25);
        add(locationCombo);

        hospitalLabel = new JLabel("Hospital");
        hospitalLabel.setBounds(120,280,120,25);
        add(hospitalLabel);

        hospitalCombo = new JComboBox<String>(hospitals);
        hospitalCombo.setBounds(260,280,300,25);
        add(hospitalCombo);

        contactLabel = new JLabel("Contact No.");
        contactLabel.setBounds(120,330,120,25);
        add(contactLabel);

        contactField = new JTextField();
        contactField.setBounds(260,330,220,25);
        add(contactField);

        requestButton = new JButton("Request");
        requestButton.setBounds(130,420,100,35);
        requestButton.addActionListener(this);
        add(requestButton);

        clearButton = new JButton("Clear");
        clearButton.setBounds(290,420,100,35);
        clearButton.addActionListener(this);
        add(clearButton);

        backButton = new JButton("Back");
        backButton.setBounds(450,420,100,35);
        backButton.addActionListener(this);
        add(backButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==requestButton)
{
    String patientName = nameField.getText();
    String bloodGroup = bloodCombo.getSelectedItem().toString();
    String bloodBags = bagField.getText();
    String location = locationCombo.getSelectedItem().toString();
    String hospital = hospitalCombo.getSelectedItem().toString();
    String contact = contactField.getText();

    JOptionPane.showMessageDialog(this,
            "Blood Request Submitted Successfully!");

    new MyRequest(patientName, bloodGroup, bloodBags, location, hospital, contact);

    dispose();
}

        else if(e.getSource()==clearButton)
        {
            nameField.setText("");
            bagField.setText("");
            contactField.setText("");

            bloodCombo.setSelectedIndex(0);
            locationCombo.setSelectedIndex(0);
            hospitalCombo.setSelectedIndex(0);
        }

        else if(e.getSource()==backButton)
        {
            new SearchBlood();
            dispose();
        }
    }
}