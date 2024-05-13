package Dashboards;
import Dashboards.*;
import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

public class Login extends JFrame implements ActionListener{
    private JButton signIn,forbtn,signUp;
    private JTextField userTf;
    private JPasswordField passTf;
    private JLabel loginbg;
    private JToggleButton passToggle;
    private JPanel panel;
    private String user;
    private boolean flag;



    public Login() {
        super("AIUB Login");

        ImageIcon icon = new ImageIcon("image/icon-01.png");
        setIconImage(icon.getImage());

        this.setSize(1080,650);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        
        // Calculate top-left corner of the frame
        int x = (screenWidth - 1080) / 2;
        int y = (screenHeight - 650) / 2;
        
        setLocation(x,y);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setTitle("Login");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        CardLayout loginLayout = new CardLayout();
        
        panel = new JPanel(loginLayout);
        panel.setLayout(null);

        ImageIcon loginbgImg = new ImageIcon("image/loginbg-01.jpg");
        Image img = loginbgImg.getImage();
        img = img.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        loginbgImg = new ImageIcon(img);

        loginbg = new JLabel(loginbgImg);
        loginbg.setBounds(0, 0, getWidth(), getHeight());
        add(loginbg);

        Font infoFont = new Font("Helvetica", Font.PLAIN, 15);
        
        userTf = new JTextField();
        userTf.setBounds(750,240,200,40);
        userTf.setForeground(Color.BLACK);
        userTf.setOpaque(false);
        userTf.setBorder(BorderFactory.createEmptyBorder());
        userTf.setFont(infoFont);
        loginbg.add(userTf);


        passTf = new JPasswordField();
        passTf.setBounds(750,320,200,40);
        passTf.setForeground(Color.BLACK);
        passTf.setOpaque(false);
        passTf.setBorder(BorderFactory.createEmptyBorder());
        passTf.setFont(infoFont);
        passTf.setEchoChar('\u2022');
        loginbg.add(passTf);

        final ImageIcon show = new ImageIcon("image/show-01.png");
        final ImageIcon scaledShow = new ImageIcon(show.getImage().getScaledInstance(28, 15, Image.SCALE_SMOOTH));

        final ImageIcon hide = new ImageIcon("image/hide-01.png");
        final ImageIcon scaledHide = new ImageIcon(hide.getImage().getScaledInstance(28, 20, Image.SCALE_SMOOTH));

        passToggle = new JToggleButton();
        passToggle.setBounds(952,330,35,25);
        passToggle.setOpaque(false);
        passToggle.setContentAreaFilled(false);
        passToggle.setBorderPainted(false);
        passToggle.setFocusable(false);
        passToggle.setIcon(scaledHide);
        passToggle.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginbg.add(passToggle);

        //passtoggle command
        passToggle.addItemListener(new ItemListener() {
          public void itemStateChanged(ItemEvent itemEvent) {
            int state = itemEvent.getStateChange();
            if (state == ItemEvent.SELECTED) {
              passToggle.setIcon(scaledShow);
              passTf.setEchoChar((char)0);
            } else {
              passToggle.setIcon(scaledHide);
              passTf.setEchoChar('\u2022');
            }
          }
        });
      
    
        signIn = new JButton("Sign In");
        signIn.setBounds(720,410,297,47);
        signIn.setFont(new Font("Helvetica",Font.BOLD, 22));
        signIn.setOpaque(false);
        signIn.setForeground(Color.WHITE);
        signIn.setContentAreaFilled(false);
        signIn.setFocusable(false);
        signIn.setBorderPainted(false);
        signIn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginbg.add(signIn);


        signUp = new JButton("Sign Up");
        signUp.setBounds(810,470,110,15);
        signUp.setOpaque(false);
        signUp.setFocusable(false);
        signUp.setContentAreaFilled(false);
        signUp.setBorderPainted(false);
        signUp.setForeground(Color.WHITE);
        signUp.setFont(new Font("Helvetica", Font.BOLD, 14));
        signUp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginbg.add(signUp);

        //register
        signUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                Registration registerFrame = new Registration();
                registerFrame.setVisible(true);
                dispose();
            }
        });

        forbtn = new JButton("Forgot Password");
        forbtn.setBounds(780,500,170,15);
        forbtn.setOpaque(false);
        forbtn.setFocusable(false);
        forbtn.setContentAreaFilled(false);
        forbtn.setBorderPainted(false);
        forbtn.setForeground(Color.RED);
        forbtn.setFont(new Font("Helvetica", Font.BOLD, 14));
        forbtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginbg.add(forbtn);
        
        //forgot password operation
        forbtn.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        String userID = userTf.getText();
        String newPassword = JOptionPane.showInputDialog(null, "Enter your new password:");
        
        if (newPassword != null && !newPassword.isEmpty()) { // Check if the user provided a new password
            if (changePassword(userID, newPassword)) {
                JOptionPane.showMessageDialog(null, "Password changed successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to change password. User not found.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No password entered. Password change cancelled.");
        }
    }

    private boolean changePassword(String username, String newPassword) {
        String txtFilename = "data/" + username + ".txt";
        File txtFile = new File(txtFilename);

        if (txtFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(txtFile))) {
                StringBuilder fileContent = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data.length >= 4 && data[2].equals(username)) {
                        data[3] = newPassword; // Update the password
                    }
                    fileContent.append(String.join(",", data)).append("\n");
                }

                // Write the updated content back to the file
                try (FileWriter writer = new FileWriter(txtFile)) {
                    writer.write(fileContent.toString());
                }

                return true; // Password changed successfully
            }   catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return false; // User not found or failed to change password
    }
});
        
        this.add(panel);


    //sign in process
    signIn.addActionListener(new ActionListener(){   
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signIn) {
            String username = userTf.getText();
            String password = new String(passTf.getPassword());

            
            String validationMessage = validateLogin(username, password);
            if (validationMessage.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Login Successful!");
                // Set the current user in the session
                UserSession.setCurrentUser(username);
                HomePage h1 = new HomePage();
                h1.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, validationMessage);
            }
        }   
    }
    private String validateLogin(String username, String password) {
    String message = ""; // Variable to store the message
    
    String txtFilename = "data/" + username + ".txt";
    File txtFile = new File(txtFilename);

    if (txtFile.exists()) { // Check if the file exists for the user
        try (BufferedReader reader = new BufferedReader(new FileReader(txtFile))) {
            String line;
            boolean passwordMatch = false; // Flag to check if password matches
            while ((line = reader.readLine()) != null) {
                if(line.startsWith("Info:")){
                String[] data = line.split(","); 
                if (data.length >= 3 && data[2].equals(username) && data[3].equals(password)) {
                    passwordMatch = true;
                    break;
                }
            }
        }
            if (!passwordMatch) {
                message = "Invalid userID or password.";
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    } else {
        
        message = "User not found.";
    }

    return message;
    }});
}
            
    public void actionPerformed(ActionEvent e) {
    } 

}
