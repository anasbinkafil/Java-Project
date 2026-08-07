import javax.swing.*;
import java.awt.event.*;

public class BloodBankManagementSystem extends JFrame implements ActionListener
{
    JLabel titleLabel, welcomeLabel, infoLabel;
    JButton loginButton, registerButton;

    public BloodBankManagementSystem()
    {
        setTitle("Blood Bank Management System");
        setSize(700,450);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        titleLabel = new JLabel("BLOOD BANK MANAGEMENT SYSTEM");
        titleLabel.setBounds(180,40,350,30);
        add(titleLabel);

        
        welcomeLabel = new JLabel("Welcome To Blood Bank System");
        welcomeLabel.setBounds(230,90,250,25);
        add(welcomeLabel);

       
        infoLabel = new JLabel("Please Login or Register to Continue");
        infoLabel.setBounds(210,130,300,25);
        add(infoLabel);

       
        loginButton = new JButton("Login");
        loginButton.setBounds(200,250,100,35);
        loginButton.addActionListener(this);
        add(loginButton);

       
        registerButton = new JButton("Register");
        registerButton.setBounds(350,250,120,35);
        registerButton.addActionListener(this);
        add(registerButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==registerButton)
        {
            new Registration();
            dispose();
        }

        else if(e.getSource()==loginButton)
        {
            JOptionPane.showMessageDialog(this,
                    "Login Page Will Be Added Later.");
        }
    }
}