package Dashboards;
import Dashboards.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Registration extends JFrame implements ActionListener{
  private JTextField userTf, userEmail, userID;
  private JPasswordField confirmPasswordField;
  private JButton back,registerButton;
  private String dpPath;
  Font infoFont = new Font("Helvetica", Font.PLAIN, 15);

  public Registration() {
    super("AIUB Registration");

    ImageIcon icon = new ImageIcon("image/icon-01.png");
    setIconImage(icon.getImage());

    // Set the window size
    setSize(1080, 650);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);

    // Center the frame on the screen
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);

    // Panel for the registration form
    JPanel panel2 = new JPanel();
    panel2.setLayout(null);
    this.add(panel2);

    // Add background image
    ImageIcon registerImg = new ImageIcon("image/regbg-01.jpg");
    Image img = registerImg.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
    JLabel regBg = new JLabel(new ImageIcon(img));
    regBg.setBounds(0, 0, getWidth(), getHeight());
    panel2.add(regBg);
     
    //logo back button
     
    back = new JButton();
    back.setBounds(40,40,130,40);
    back.setContentAreaFilled(false);
    back.setBorderPainted(false);
    back.setFocusable(false);
    back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    regBg.add(back);

    back.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent e){
        Login frame = new Login();
        frame.setVisible(true);
        dispose();
      }
    });
     

    
    userTf = new JTextField();
    userTf.setBounds(190, 175, 200, 40);
    userTf.setForeground(Color.BLACK);
    userTf.setOpaque(false);
    userTf.setBorder(BorderFactory.createEmptyBorder());
    userTf.setFont(infoFont);
    regBg.add(userTf);

    userEmail = new JTextField();
    userEmail.setBounds(190, 245, 210, 40);
    userEmail.setForeground(Color.BLACK);
    userEmail.setOpaque(false);
    userEmail.setBorder(BorderFactory.createEmptyBorder());
    userEmail.setFont(infoFont);
    regBg.add(userEmail);

    userID = new JTextField();
    userID.setBounds(190, 310, 210, 40);
    userID.setForeground(Color.BLACK);
    userID.setOpaque(false);
    userID.setBorder(BorderFactory.createEmptyBorder());
    userID.setFont(infoFont);
    regBg.add(userID);

    confirmPasswordField = new JPasswordField();
    confirmPasswordField.setBounds(190, 375, 210, 40);
    confirmPasswordField.setForeground(Color.BLACK);
    confirmPasswordField.setOpaque(false);
    confirmPasswordField.setBorder(BorderFactory.createEmptyBorder());
    confirmPasswordField.setFont(infoFont);
    confirmPasswordField.setEchoChar((char)0);
    regBg.add(confirmPasswordField);

    // Register button
    registerButton = new JButton("Register");
    registerButton.setBounds(160, 480, 288, 40);
    registerButton.setFont(new Font("Helvetica", Font.BOLD, 22));
    registerButton.setOpaque(false);
    registerButton.setForeground(Color.BLACK);
    registerButton.setContentAreaFilled(false);
    registerButton.setBorderPainted(false);
    registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    regBg.add(registerButton);

    // Action listener for the register button
    registerButton.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent e) {
         
        String username = userTf.getText();
        String username2 = userEmail.getText();
        String username3 = userID.getText();
        String confirmPassword = new String(confirmPasswordField.getPassword());

         
        if (username.isEmpty() || username2.isEmpty() || username3.isEmpty() || confirmPassword.isEmpty()) {
          JOptionPane.showMessageDialog(Registration.this, "Please fill all the criteria");
        } 
        else if (confirmPassword.length() < 6) {
          JOptionPane.showMessageDialog(Registration.this, "Password must be at least 6 characters long.");
        }
        else {
          saveUserData(username,username2,username3,confirmPassword);
           
          //switching to login page when registration is done
          Login loginFrame = new Login();
          loginFrame.setVisible(true);
          dispose();
        }
      }
    });
  }

  private void saveUserData(String username, String username2, String username3, String confirmPassword) {
  try {
    FileWriter writer = new FileWriter("data/" + username3 + ".txt", true); 
    writer.write("Info:"+username + "," + username2 + "," + username3 + "," + confirmPassword);
    writer.close();

    JOptionPane.showMessageDialog(null, "Registration Successful");
  } catch (IOException e) {
    JOptionPane.showMessageDialog(null, "Error while saving user data!");
    e.printStackTrace();
  }
}

    public void actionPerformed(ActionEvent e) {

    }
}