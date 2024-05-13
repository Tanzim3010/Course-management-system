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

public class HomePage extends JFrame implements ActionListener{
  private JTextField search; 
  private JButton profile,course,home,sc,lgout;
  private JLabel title,profilePictureLabel;
  private String dpPath;
  private JPanel completedCoursesPanel;
  Font infoFont = new Font("Helvetica", Font.PLAIN, 15);

  public HomePage() {
    super("HomePage");

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
    JPanel panel = new JPanel();
    panel.setLayout(null);
    this.add(panel);

    // Add background image
    ImageIcon homeImg = new ImageIcon("image/profilepage.jpg");
    Image img = homeImg.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
    JLabel homeBg = new JLabel(new ImageIcon(img));
    homeBg.setBounds(0, 0, getWidth(), getHeight());
    panel.add(homeBg);

    home = new JButton("Home");
    home.setBounds(67, 352, 120, 40);
    home.setFont(new Font("Helvetica", Font.BOLD, 22));
    home.setOpaque(false);
    home.setForeground(Color.BLACK);
    home.setContentAreaFilled(false);
    home.setBorderPainted(false);
    home.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    homeBg.add(home);


        completedCoursesPanel = new JPanel();
        completedCoursesPanel.setBounds(290, 170, 800, 200);
        completedCoursesPanel.setOpaque(false);
        homeBg.add(completedCoursesPanel);

        displayCompletedCourses();

    course = new JButton("Course");
    course.setBounds(67, 417, 120, 40);
    course.setFont(new Font("Helvetica", Font.BOLD, 22));
    course.setOpaque(false);
    course.setForeground(Color.BLACK);
    course.setContentAreaFilled(false);
    course.setBorderPainted(false);
    course.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    homeBg.add(course);

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
    homeBg.add(sc);

    sc.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e){
        search frame = new search();
        frame.setVisible(true);
        dispose();
      }
    });


    profile = new JButton("Profile");
    profile.setBounds(67, 559, 120, 40);
    profile.setFont(new Font("Helvetica", Font.BOLD, 22));
    profile.setOpaque(false);
    profile.setForeground(Color.BLACK);
    profile.setContentAreaFilled(false);
    profile.setBorderPainted(false);
    profile.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    homeBg.add(profile);

    profile.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e){
        Profile frame = new Profile();
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
    homeBg.add(uploadImg);

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
    homeBg.add(profilePictureLabel);



    lgout=new JButton("LogOut");
    lgout.setBounds(980,8,100,20);
    lgout.setBackground(Color.RED);
    lgout.setOpaque(true);
    lgout.setForeground(Color.BLACK);
    lgout.setOpaque(true);
    infoFont = lgout.getFont().deriveFont(Font.BOLD);
    lgout.setFont(infoFont);
    lgout.setBorderPainted(false);
    homeBg.add(lgout);


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

    title = new JLabel("DashBoard");
    title.setBounds(580, 35, 330, 50);
    title.setFont(new Font("Century",Font.BOLD,27));
    homeBg.add(title);

   //..................... Picture upload .............................

    String currentUser = UserSession.getCurrentUser();
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

    private void displayCompletedCourses() {
    String currentUser = UserSession.getCurrentUser();
    try (BufferedReader reader = new BufferedReader(new FileReader("data/" + currentUser + ".txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("Course:")) {
                String[] courseInfo = line.substring("Course:".length()).split(",");
                JLabel courseLabel;
                if (courseInfo.length >= 3) { 
                    StringBuilder courseDetails = new StringBuilder();
                    courseDetails.append("Course name: ").append(courseInfo[0]).append("\n");
                    courseDetails.append("Course code: ").append(courseInfo[1]).append("\n");
                    courseDetails.append("Credits: ").append(courseInfo[2]).append("\n");
                    if (courseInfo.length >= 4) { // Check if there is a prerequisite
                        courseDetails.append("Pre-requisite: ").append(courseInfo[3]);
                    }
                    courseLabel = new JLabel(courseDetails.toString());
                } else {
                    
                    System.err.println("Invalid format for course information: " + line);
                    continue; // Skip processing this line
                }
                courseLabel.setFont(new Font("Helvetica", Font.BOLD, 16));
                completedCoursesPanel.add(courseLabel);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
 public void actionPerformed(ActionEvent e){

 }

}       