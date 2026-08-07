import javax.swing.*;
import java.awt.event.*;

public class MyRequest extends JFrame implements ActionListener
{
    JLabel titleLabel;
    JTextArea requestArea;
    JButton backButton;

    public MyRequest(String name,
                     String blood,
                     String bags,
                     String location,
                     String hospital,
                     String contact)
    {
        setTitle("My Requests");
        setSize(700,550);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        titleLabel = new JLabel("MY BLOOD REQUEST");
        titleLabel.setBounds(280,20,200,30);
        add(titleLabel);

        requestArea = new JTextArea();
        requestArea.setBounds(70,70,550,320);
        requestArea.setEditable(false);

        requestArea.setText(
               " MY REQUEST "+
                "Request ID : 1001\n\n"+
                "Patient Name : "+name+
                "\nBlood Group : "+blood+
                "\nBlood Bags : "+bags+
                "\nLocation : "+location+
                "\nHospital : "+hospital+
                "\nContact No : "+contact+
                "\nStatus : PENDING");

        add(requestArea);

        backButton = new JButton("Back");
        backButton.setBounds(280,430,100,35);
        backButton.addActionListener(this);
        add(backButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==backButton)
        {
            new SearchBlood();
            dispose();
        }
    }
}