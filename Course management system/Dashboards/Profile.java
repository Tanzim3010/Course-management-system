package Dashboards;
import Dashboards.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;

public class Profile extends JFrame implements ActionListener{
    private JTextField search;
    private JButton profile, course, home, sc, lgout, editBtn;
    private JLabel title, profilePictureLabel;
    private String dpPath;
    private JLabel nameLabel, emailLabel, idLabel;
    private JLabel addressLabel, bloodGroupLabel, fatherNameLabel, motherNameLabel, dobLabel, genderLabel;
    private JTextField addressField, bloodGroupField, dobField, genderField, fatherNameField, motherNameField, nameField, emailField;
    private JPanel panel;
    private JLabel profileBg;
    private boolean isEditing = false;


  Font infoFont = new Font("Helvetica", Font.PLAIN, 15);

  public Profile() {
    super("Profile");

    ImageIcon icon = new ImageIcon("image/icon-01.png");
    setIconImage(icon.getImage());

    // Set the window size
    setSize(1080, 650);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);

    // Center the frame on the screen
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);

    // Panel for the HomePage form
    panel = new JPanel();
    panel.setLayout(null);
    this.add(panel);

    // Add background image
    ImageIcon homeImg = new ImageIcon("image/profilepage.jpg");
    Image img = homeImg.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
    profileBg = new JLabel(new ImageIcon(img));
    profileBg.setBounds(0, 0, getWidth(), getHeight());
    panel.add(profileBg);

    profile = new JButton("Profile");
    profile.setBounds(67, 559, 120, 40);
    profile.setFont(new Font("Helvetica", Font.BOLD, 22));
    profile.setOpaque(false);
    profile.setForeground(Color.BLACK);
    profile.setContentAreaFilled(false);
    profile.setBorderPainted(false);
    profile.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    profileBg.add(profile);

        nameLabel = new JLabel("Name: ");
        nameLabel.setBounds(300, 150, 200, 30);
        nameLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        profileBg.add(nameLabel);

        emailLabel = new JLabel("Email: ");
        emailLabel.setBounds(300, 200, 300, 30);
        emailLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        profileBg.add(emailLabel);

        idLabel = new JLabel("ID: ");
        idLabel.setBounds(300, 250, 200, 30);
        idLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        profileBg.add(idLabel);

        addressLabel = new JLabel("Address:");
        addressLabel.setBounds(300, 300, 200, 30);
        addressLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        profileBg.add(addressLabel);

        bloodGroupLabel = new JLabel("Blood Group:");
        bloodGroupLabel.setBounds(300, 350, 200, 30);
        bloodGroupLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        profileBg.add(bloodGroupLabel);

        fatherNameLabel = new JLabel("Father's Name:");
        fatherNameLabel.setBounds(300, 400, 200, 30);
        fatherNameLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        profileBg.add(fatherNameLabel);

        motherNameLabel = new JLabel("Mother's Name:");
        motherNameLabel.setBounds(300, 450, 200, 30);
        motherNameLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        profileBg.add(motherNameLabel);

        dobLabel = new JLabel("Date of Birth:");
        dobLabel.setBounds(300, 500, 500, 30);
        dobLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        profileBg.add(dobLabel);

        genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(300, 550, 200, 30);
        genderLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        profileBg.add(genderLabel);


        // Populate user information
        String currentUser = UserSession.getCurrentUser();
        populateUserInfo(currentUser);

        editBtn = new JButton("Edit");
        editBtn.setBounds(850, 300, 80, 30);
        editBtn.setFont(new Font("Helvetica", Font.BOLD, 15));
        profileBg.add(editBtn);

        editBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!isEditing) {
                    startEditing();
                } else {
                    finishEditing();
                }
            }
        });

        JButton changePasswordBtn = new JButton("Change Password");
        changePasswordBtn.setBounds(850, 250, 190, 30);
        changePasswordBtn.setFont(new Font("Helvetica", Font.BOLD, 15));
        profileBg.add(changePasswordBtn);

        changePasswordBtn.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        String newPassword = JOptionPane.showInputDialog(null, "Enter your new password:");

        if (newPassword != null && !newPassword.isEmpty()) { // Check if the user provided a new password
            if (changePassword(UserSession.getCurrentUser(), newPassword)) {
                JOptionPane.showMessageDialog(null, "Password changed successfully!");
                Login frame = new Login();
                frame.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Failed to change password. User not found.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No password entered. Password change cancelled.");
        }
    }
});
    

    course = new JButton("Course");
    course.setBounds(67, 417, 120, 40);
    course.setFont(new Font("Helvetica", Font.BOLD, 22));
    course.setOpaque(false);
    course.setForeground(Color.BLACK);
    course.setContentAreaFilled(false);
    course.setBorderPainted(false);
    course.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    profileBg.add(course);

    course.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent e){
        Course frame = new Course();
        frame.setVisible(true);
        dispose();
      }
    });

    sc = new JButton("Search");
    sc.setBounds(67, 489, 120, 40);
    sc.setFont(new Font("Helvetica", Font.BOLD, 22));
    sc.setOpaque(false);
    sc.setForeground(Color.BLACK);
    sc.setContentAreaFilled(false);
    sc.setBorderPainted(false);
    sc.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    profileBg.add(sc);

    sc.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e){
        search frame = new search();
        frame.setVisible(true);
        dispose();
      }
    });


    home = new JButton("Home");
    home.setBounds(67, 352, 120, 40);
    home.setFont(new Font("Helvetica", Font.BOLD, 22));
    home.setOpaque(false);
    home.setForeground(Color.BLACK);
    home.setContentAreaFilled(false);
    home.setBorderPainted(false);
    home.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    profileBg.add(home);

    home.addActionListener(new ActionListener() {
   
      public void actionPerformed(ActionEvent e){
        HomePage frame = new HomePage();
        frame.setVisible(true);
        dispose();
      }
    });

    JButton uploadImg = new JButton("Upload Image");
    uploadImg.setBounds(70,240,130,25);
    uploadImg.setOpaque(false);
    uploadImg.setBorderPainted(false);
    uploadImg.setFocusable(false);
    uploadImg.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    profileBg.add(uploadImg);

    uploadImg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    dpPath = selectedFile.getAbsolutePath(); // Save the selected file path
                    JOptionPane.showMessageDialog(null, "Image Uploaded Successfully");

                    // Update profile picture label and save the path in the user's text file
                    updateProfilePicturePath(UserSession.getCurrentUser(), dpPath);
                    showProfilePicture(dpPath);
                }
            }
        });

    profilePictureLabel = new JLabel();
    profilePictureLabel.setBounds(58, 85, 150, 150);
    profilePictureLabel.setHorizontalAlignment(JLabel.CENTER);
    profilePictureLabel.setVerticalAlignment(JLabel.CENTER);
    profileBg.add(profilePictureLabel);



    lgout=new JButton("LogOut");
    lgout.setBounds(980,8,100,20);
    lgout.setBackground(Color.RED);
    lgout.setOpaque(true);
    lgout.setForeground(Color.BLACK);
    lgout.setOpaque(true);
    infoFont = lgout.getFont().deriveFont(Font.BOLD);
    lgout.setFont(infoFont);
    lgout.setBorderPainted(false);
    profileBg.add(lgout);


      lgout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    Login f = new Login();
                    f.setVisible(true);
                    dispose();
                }
            }
        });

    title = new JLabel("Profile");
    title.setBounds(580, 35, 330, 50);
    title.setFont(new Font("Century",Font.BOLD,27));
    profileBg.add(title);

  //..................... Picture upload .............................

    String profilePicturePath = getLatestProfilePicturePath(currentUser);
    if (!profilePicturePath.isEmpty()) {
    showProfilePicture(profilePicturePath);
}
}
    private void updateProfilePicturePath(String username, String dpPath) {
    removeProfilePicturePath(username);
    
    try {
        FileWriter writer = new FileWriter("data/" + username + ".txt", true);
        writer.write("\nProfilePicturePath:" + dpPath);
        writer.close();
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error while updating profile picture path!");
        e.printStackTrace();
    }
}
    private String getLatestProfilePicturePath(String username) {
    String profilePicturePath = "";
    try (BufferedReader reader = new BufferedReader(new FileReader("data/" + username + ".txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("ProfilePicturePath:")) {
                profilePicturePath = line.substring("ProfilePicturePath:".length());
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return profilePicturePath;
}

    private void showProfilePicture(String dpPath) {
    ImageIcon profilePic = new ImageIcon(dpPath);
    profilePic.setImage(profilePic.getImage().getScaledInstance(profilePictureLabel.getWidth(), profilePictureLabel.getHeight(), Image.SCALE_SMOOTH));
    profilePictureLabel.setIcon(profilePic);
}
    private String getProfilePicturePath(String username) {
    String profilePicturePath = "";
    try (BufferedReader reader = new BufferedReader(new FileReader("data/" + username + ".txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("ProfilePicturePath:")) {
                profilePicturePath = line.substring("ProfilePicturePath:".length());
                break;
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return profilePicturePath;
}
    private void removeProfilePicturePath(String username) {
    try {
        File file = new File("data/" + username + ".txt");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            if (!line.startsWith("ProfilePicturePath:")) {
                sb.append(line + "\n");
            }
        }
        fr.close();
        br.close();
        FileWriter fw = new FileWriter(file);
        fw.write(sb.toString());
        fw.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}   

    private JLabel createLabel(String text, int x, int y, JPanel panel) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 200, 30);
        label.setFont(new Font("Helvetica", Font.PLAIN, 20));
        panel.add(label);
        return label;
    }

    private JButton createButton(String text, int x, int y, int width, int height, JPanel panel) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setFont(new Font("Helvetica", Font.BOLD, 15));
        panel.add(button);
        return button;
    }

    private void startEditing() {
    isEditing = true;
    editBtn.setText("Save");

    // Replace labels with editable text fields
    nameField = createTextField(getFieldValue(nameLabel), 500, 150, 250, 30, panel);
    emailField = createTextField(getFieldValue(emailLabel), 500, 200, 250, 30, panel);
    addressField = createTextField(getFieldValue(addressLabel), 500, 300, 250, 30, panel);
    bloodGroupField = createTextField(getFieldValue(bloodGroupLabel), 500, 350, 250, 30, panel);
    fatherNameField = createTextField(getFieldValue(fatherNameLabel), 500, 400, 250, 30, panel);
    motherNameField = createTextField(getFieldValue(motherNameLabel), 500, 450, 250, 30, panel);
    dobField = createTextField(getFieldValue(dobLabel), 500, 500, 250, 30, panel);
    genderField = createTextField(getFieldValue(genderLabel), 500, 550, 250, 30, panel);

    // Refresh the panel
    profileBg.revalidate();
    profileBg.repaint();
}

private void finishEditing() {
    isEditing = false;
    editBtn.setText("Edit");

    // Update labels with edited information
    nameLabel.setText("Name: " + nameField.getText());
    emailLabel.setText("Email: " + emailField.getText());
    addressLabel.setText("Address: " + addressField.getText());
    bloodGroupLabel.setText("Blood Group: " + bloodGroupField.getText());
    fatherNameLabel.setText("Father's Name: " + fatherNameField.getText());
    motherNameLabel.setText("Mother's Name: " + motherNameField.getText());
    dobLabel.setText("Date of Birth: " + dobField.getText());
    genderLabel.setText("Gender: " + genderField.getText());

    // Remove editable text fields
    profileBg.remove(nameField);
    profileBg.remove(emailField);
    profileBg.remove(addressField);
    profileBg.remove(bloodGroupField);
    profileBg.remove(fatherNameField);
    profileBg.remove(motherNameField);
    profileBg.remove(dobField);
    profileBg.remove(genderField);

    // Refresh the panel
    profileBg.revalidate();
    profileBg.repaint();

    // Save edited information to the text file
    String currentUser = UserSession.getCurrentUser();
    saveEditedUserInfo(currentUser, nameField.getText(), emailField.getText(), addressField.getText(), dobField.getText(), genderField.getText(), fatherNameField.getText(), motherNameField.getText(), bloodGroupField.getText());
}

private JTextField createTextField(String text, int x, int y, int width, int height, JPanel panel) {
    JTextField textField = new JTextField(text);
    textField.setBounds(x, y, width, height);
    textField.setFont(new Font("Helvetica", Font.PLAIN, 20)); // Set font size
    panel.add(textField);
    return textField;
}

private String getFieldValue(JLabel label) {
    String[] labelTextParts = label.getText().split(":");
    if (labelTextParts.length > 1) {
        return labelTextParts[1].trim();
    }
    return "";
}

    // Method to save edited user information to the text file
    private void populateUserInfo(String username) {
    try (BufferedReader reader = new BufferedReader(new FileReader("data/" + username + ".txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("Info:")) {
                String[] profileInfo = line.substring("Info:".length()).split(",");
                nameLabel.setText("Name: " + getField(profileInfo, 0));
                emailLabel.setText("Email: " + getField(profileInfo, 1));
                idLabel.setText("ID: " + getField(profileInfo, 2));
                
                addressLabel.setText("Address: " + getField(profileInfo, 4));
                dobLabel.setText("Date of Birth: " + getField(profileInfo, 5));
                genderLabel.setText("Gender: " + getField(profileInfo, 6));
                fatherNameLabel.setText("Father's Name: " + getField(profileInfo, 7));
                motherNameLabel.setText("Mother's Name: " + getField(profileInfo, 8));
                bloodGroupLabel.setText("Blood Group: " + getField(profileInfo, 9));
                break; // Break once user information is populated
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

private String getField(String[] profileInfo, int index) {
    if (index >= 0 && index < profileInfo.length) {
        return profileInfo[index];
    } else {
        return "";
    }
}

    private void saveEditedUserInfo(String username, String name, String email, String address, String dob, String gender, String fatherName, String motherName, String bloodGroup) {
    try {
        // Read existing user info from file
        BufferedReader reader = new BufferedReader(new FileReader("data/" + username + ".txt"));
        StringBuilder userInfo = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.startsWith("Info:")) {
                
                userInfo.append(line).append("\n");
            } else {
                // Update only the specified fields
                userInfo.append("Info:");
                userInfo.append(name).append(",");
                userInfo.append(email).append(",");
                userInfo.append(line.split(",")[2]).append(","); // Keep id field
                userInfo.append(line.split(",")[3]).append(","); // Keep password field
                userInfo.append(address).append(",");
                userInfo.append(dob).append(",");
                userInfo.append(gender).append(",");
                userInfo.append(fatherName).append(",");
                userInfo.append(motherName).append(",");
                userInfo.append(bloodGroup).append("\n");
            }
        }
        reader.close();

        // Write updated user info to file
        FileWriter writer = new FileWriter("data/" + username + ".txt");
        writer.write(userInfo.toString());
        writer.close();

        JOptionPane.showMessageDialog(null, "Information updated successfully.");
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error while updating information.");
        e.printStackTrace();
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
                if (line.startsWith("Info:")) {
                    // Split the line into fields
                    String[] data = line.substring("Info:".length()).split(",");
                    if (data.length >= 4 && data[2].equals(username)) {
                        data[3] = newPassword; // Update the password
                    }
                    // Reconstruct the line with "Info:" prefix and updated password
                    line = "Info:" + String.join(",", data);
                }
                // Append the line to fileContent
                fileContent.append(line).append("\n");
            }

            // Write the updated content back to the file
            try (FileWriter writer = new FileWriter(txtFile)) {
                writer.write(fileContent.toString());
            }

            // Password changed successfully
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    // User not found or failed to change password
    return false;
}



 public void actionPerformed(ActionEvent e){
    
 }
}