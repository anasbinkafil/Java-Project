import javax.swing.*;
import java.awt.event.*;

public class Registration extends JFrame implements ActionListener
{
    JLabel titleLabel;
    JLabel nameLabel, emailLabel, phoneLabel, bloodLabel, userLabel, passLabel;

    JTextField nameField;
    JTextField emailField;
    JTextField phoneField;
    JTextField bloodField;
    JTextField userField;

    JPasswordField passField;

    JButton registerButton;
    JButton clearButton;
    JButton backButton;

    public Registration()
    {
        setTitle("Blood Bank Registration");
        setSize(700,550);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        titleLabel = new JLabel("BLOOD BANK REGISTRATION");
        titleLabel.setBounds(220,20,250,30);
        add(titleLabel);

       
        nameLabel = new JLabel("Full Name");
        nameLabel.setBounds(150,80,100,25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(280,80,220,25);
        add(nameField);

        
        emailLabel = new JLabel("Email");
        emailLabel.setBounds(150,120,100,25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(280,120,220,25);
        add(emailField);

        
        phoneLabel = new JLabel("Phone");
        phoneLabel.setBounds(150,160,100,25);
        add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(280,160,220,25);
        add(phoneField);

        
        bloodLabel = new JLabel("Blood Group");
        bloodLabel.setBounds(150,200,100,25);
        add(bloodLabel);

        bloodField = new JTextField();
        bloodField.setBounds(280,200,220,25);
        add(bloodField);

        
        userLabel = new JLabel("Username");
        userLabel.setBounds(150,240,100,25);
        add(userLabel);

        userField = new JTextField();
        userField.setBounds(280,240,220,25);
        add(userField);

        
        passLabel = new JLabel("Password");
        passLabel.setBounds(150,280,100,25);
        add(passLabel);

        passField = new JPasswordField();
        passField.setBounds(280,280,220,25);
        add(passField);

        
        registerButton = new JButton("Register");
        registerButton.setBounds(150,360,100,35);
        registerButton.addActionListener(this);
        add(registerButton);

        
        clearButton = new JButton("Clear");
        clearButton.setBounds(300,360,100,35);
        clearButton.addActionListener(this);
        add(clearButton);

        
        backButton = new JButton("Back");
        backButton.setBounds(450,360,100,35);
        backButton.addActionListener(this);
        add(backButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == registerButton)
        {
            JOptionPane.showMessageDialog(this,
                    "Registration Successful!");

            new SearchBlood();
            dispose();
        }

        else if(e.getSource() == clearButton)
        {
            nameField.setText("");
            emailField.setText("");
            phoneField.setText("");
            bloodField.setText("");
            userField.setText("");
            passField.setText("");
        }

        else if(e.getSource() == backButton)
        {
            new BloodBankManagementSystem();
            dispose();
        }
    }
}