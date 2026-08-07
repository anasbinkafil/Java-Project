import javax.swing.*;
import java.awt.event.*;

public class SearchBlood extends JFrame implements ActionListener
{
    JLabel titleLabel;
    JLabel bloodLabel;
    JLabel locationLabel;
    JLabel resultLabel;

    JComboBox<String> bloodCombo;
    JComboBox<String> locationCombo;

    JTextArea resultArea;

    JButton searchButton;
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

    public SearchBlood()
    {
        setTitle("Search Blood");
        setSize(800,600);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        titleLabel = new JLabel("SEARCH BLOOD");
        titleLabel.setBounds(330,20,200,30);
        add(titleLabel);

        
        bloodLabel = new JLabel("Blood Group");
        bloodLabel.setBounds(120,80,120,25);
        add(bloodLabel);

        bloodCombo = new JComboBox<String>(bloodGroups);
        bloodCombo.setBounds(250,80,180,30);
        add(bloodCombo);

        
        locationLabel = new JLabel("Location");
        locationLabel.setBounds(120,140,120,25);
        add(locationLabel);

        locationCombo = new JComboBox<String>(locations);
        locationCombo.setBounds(250,140,180,30);
        add(locationCombo);

        
        searchButton = new JButton("Search");
        searchButton.setBounds(80,220,100,35);
        searchButton.addActionListener(this);
        add(searchButton);

        
        requestButton = new JButton("Request Blood");
        requestButton.setBounds(210,220,140,35);
        requestButton.addActionListener(this);
        add(requestButton);

        
        clearButton = new JButton("Clear");
        clearButton.setBounds(390,220,100,35);
        clearButton.addActionListener(this);
        add(clearButton);

        
        backButton = new JButton("Back");
        backButton.setBounds(530,220,100,35);
        backButton.addActionListener(this);
        add(backButton);

        
        resultLabel = new JLabel("Search Result");
        resultLabel.setBounds(120,300,120,25);
        add(resultLabel);

        
        resultArea = new JTextArea();
        resultArea.setBounds(120,330,550,180);
        resultArea.setEditable(false);
        add(resultArea);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==searchButton)
        {
            String blood = bloodCombo.getSelectedItem().toString();
            String location = locationCombo.getSelectedItem().toString();

            if(location.equals("DHANMONDI"))
            {
                resultArea.setText(
                "Blood Group : "+blood+
                "\nLocation : DHANMONDI"+
                "\nNearest Hospital : SQUARE HOSPITAL"+
                "\nDistance : 1.5 KM"+
                "\nAvailable Blood Bags : 22");
            }

            else if(location.equals("PURAN DHAKA"))
            {
                resultArea.setText(
                "Blood Group : "+blood+
                "\nLocation : PURAN DHAKA"+
                "\nNearest Hospital : SIR SALIMULLAH MEDICAL COLLEGE HOSPITAL"+
                "\nDistance : 1.8 KM"+
                "\nAvailable Blood Bags : 15");
            }

            else if(location.equals("MIRPUR"))
            {
                resultArea.setText(
                "Blood Group : "+blood+
                "\nLocation : MIRPUR"+
                "\nNearest Hospital : EVERCARE HOSPITAL"+
                "\nDistance : 2.8 KM"+
                "\nAvailable Blood Bags : 18");
            }

            else if(location.equals("SAVAR"))
            {
                resultArea.setText(
                "Blood Group : "+blood+
                "\nLocation : SAVAR"+
                "\nNearest Hospital : ENAM MEDICAL COLLEGE HOSPITAL"+
                "\nDistance : 2.2 KM"+
                "\nAvailable Blood Bags : 20");
            }

            else if(location.equals("BANANI"))
            {
                resultArea.setText(
                "Blood Group : "+blood+
                "\nLocation : BANANI"+
                "\nNearest Hospital : UNITED HOSPITAL"+
                "\nDistance : 1.3 KM"+
                "\nAvailable Blood Bags : 25");
            }
        }

        else if(e.getSource()==requestButton)
        {
            new RequestBlood();
            dispose();
        }

        else if(e.getSource()==clearButton)
        {
            bloodCombo.setSelectedIndex(0);
            locationCombo.setSelectedIndex(0);
            resultArea.setText("");
        }

        else if(e.getSource()==backButton)
        {
            new Registration();
            dispose();
        }
    }
}