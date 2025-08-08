import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class About extends JFrame{
    About(){
        setBounds(400, 110, 600, 500);
        getContentPane().setBackground(new Color(10, 0 , 15));
        setLayout(null);
        try {
            Image i = ImageIO.read(new File("./images/cupanim.gif"));
            setIconImage(i);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setTitle("About ABHIS Notepad");
            setResizable(false);
            setLocationRelativeTo(null);

            Image scaledImage = i.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            JLabel label = new JLabel(new ImageIcon(scaledImage));
            label.setBounds(20, 20, 100, 100);
            add(label);

            JLabel title = new JLabel("ABHIS Notepad");
            title.setFont(new Font("Arial", Font.BOLD, 30));
            title.setForeground(Color.WHITE);
            title.setBounds(140, 80, 300, 30);
            add(title);

            JLabel version = new JLabel("Version 1.0");
            version.setFont(new Font("Arial", Font.PLAIN, 20));
            version.setForeground(Color.WHITE);
            version.setBounds(140, 120, 300, 30);
            add(version);

            JLabel developer = new JLabel("Developed by Abhishek Kumar");
            developer.setFont(new Font("Arial", Font.PLAIN, 20));
            developer.setForeground(Color.WHITE);
            developer.setBounds(140, 160, 300, 30);
            add(developer);

            JLabel contact = new JLabel("Contact: +91 9561852247");
            contact.setFont(new Font("Arial", Font.PLAIN, 20));
            contact.setForeground(Color.WHITE);
            contact.setBounds(140, 200, 300, 30);
            add(contact);

            JLabel email = new JLabel("Email: abhishekpatil5072@gmail.com");
            email.setFont(new Font("Arial", Font.PLAIN, 20));
            email.setForeground(Color.WHITE);
            email.setBounds(140, 240, 400, 30);
            add(email);

            JLabel thanks = new JLabel("Thanks for using ABHIS Notepad!");
            thanks.setFont(new Font("Arial", Font.PLAIN, 20));
            thanks.setForeground(Color.WHITE);
            thanks.setBounds(140, 280, 400, 30);
            add(thanks);

        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] ar){
        new About();
    }
}