import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Admin extends JFrame implements ActionListener {
    private JTextArea infoTextArea;
    private JComboBox<String> userComboBox;
    private JButton readCourseInfoBtn;
    private JButton readUserInfoBtn;
    private JButton editCourseBtn;
    private JButton editUserBtn;
    private JButton saveButton;
    private boolean isLoggedIn;

    public Admin() {
        setTitle("Admin Panel");
        setSize(1050, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame
        setLayout(new BorderLayout());
        setResizable(false);
        isLoggedIn = false;

        // Create panel for login
        JPanel loginPanel = new JPanel(new GridLayout(3, 1));
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(this);
        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordField);
        loginPanel.add(loginBtn);
        add(loginPanel, BorderLayout.NORTH);

        // Create panel for information
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoTextArea = new JTextArea();
        infoTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(infoTextArea);
        infoPanel.add(scrollPane);
        add(infoPanel, BorderLayout.CENTER);

        // Initialize components
        readCourseInfoBtn = new JButton("Read Course Info");
        readCourseInfoBtn.addActionListener(this);
        readCourseInfoBtn.setEnabled(false); // Disable until logged in
        readUserInfoBtn = new JButton("Read User Info");
        readUserInfoBtn.addActionListener(this);
        readUserInfoBtn.setEnabled(false); // Disable until logged in
        editCourseBtn = new JButton("Edit Course");
        editCourseBtn.addActionListener(this);
        editCourseBtn.setEnabled(false); // Disable until logged in
        editUserBtn = new JButton("Edit User");
        editUserBtn.addActionListener(this);
        editUserBtn.setEnabled(false); // Disable until logged in
        saveButton = new JButton("Save");
        saveButton.addActionListener(this);
        saveButton.setActionCommand("Save");
        saveButton.setEnabled(false); // Disable until editing
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(readCourseInfoBtn);
        buttonPanel.add(readUserInfoBtn);
        buttonPanel.add(editCourseBtn);
        buttonPanel.add(editUserBtn);
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Create combo box for selecting users
        userComboBox = new JComboBox<>();
        userComboBox.setEnabled(false); // Disable until logged in
        add(userComboBox, BorderLayout.WEST);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Login")) {
            login();
        } else if (isLoggedIn) {
            if (e.getSource() == readCourseInfoBtn) {
                readCourseInfo();
            } else if (e.getSource() == readUserInfoBtn) {
                readUserInfo();
            } else if (e.getSource() == editCourseBtn) {
                editCourse();
            } else if (e.getSource() == editUserBtn) {
                editUser();
            } else if (e.getSource() == saveButton) {
                String actionCommand = saveButton.getActionCommand();
                if ("Save".equals(actionCommand)) {
                    saveChanges("data/Courses/courses.txt"); // Save course changes
                } else if ("Save User Changes".equals(actionCommand)) {
                    String selectedUser = (String) userComboBox.getSelectedItem();
                    if (selectedUser != null) {
                        saveChanges("data/" + selectedUser + ".txt");
                    }
                }
                infoTextArea.setEditable(false); // Disable editing after saving
                saveButton.setEnabled(false); // Disable save button after saving
            }
        }
    }

    private void login() {
        // Simulate a login process
        isLoggedIn = true;
        JOptionPane.showMessageDialog(this, "Login successful!");
        readCourseInfoBtn.setEnabled(true); // Enable buttons after login
        readUserInfoBtn.setEnabled(true);
        editCourseBtn.setEnabled(true);
        editUserBtn.setEnabled(true);
        userComboBox.setEnabled(true);

        // Populate user combo box
        File dataFolder = new File("data");
        if (dataFolder.exists() && dataFolder.isDirectory()) {
            File[] userFiles = dataFolder.listFiles((dir, name) -> name.endsWith(".txt"));
            if (userFiles != null) {
                for (File userFile : userFiles) {
                    String username = userFile.getName().replace(".txt", "");
                    userComboBox.addItem(username);
                }
            }
        }
    }

    private void readCourseInfo() {
        // Clear previous content
        infoTextArea.setText("");

        // Read course information from file and append to the text area
        File courseFile = new File("data/Courses/courses.txt");
        if (courseFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(courseFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    infoTextArea.append(line + "\n");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            infoTextArea.setText("Course file not found.");
        }

        // Disable user combo box
        userComboBox.setEnabled(false);
    }

    private void readUserInfo() {
        // Clear previous content
        infoTextArea.setText("");

        // Get the selected user's file
        String selectedUser = (String) userComboBox.getSelectedItem();
        if (selectedUser != null) {
            File userFile = new File("data/" + selectedUser + ".txt");
            if (userFile.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        infoTextArea.append(line + "\n");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                infoTextArea.setText("User file not found.");
            }
        }

        // Enable user combo box
        userComboBox.setEnabled(true);
    }

    private void editCourse() {
        // Enable editing in the text area
        infoTextArea.setEditable(true);

        
        JOptionPane.showMessageDialog(this, "Remember to save changes after editing.");

        // Enable the save button
        saveButton.setEnabled(true);
        saveButton.setActionCommand("Save");
    }

    private void editUser() {
        // Enable editing in the text area
        infoTextArea.setEditable(true);

        
        JOptionPane.showMessageDialog(this, "Remember to save changes after editing.");

        // Enable the save button
        saveButton.setEnabled(true);
        saveButton.setActionCommand("Save User Changes");
    }

    
    private void saveChanges(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(infoTextArea.getText());
            JOptionPane.showMessageDialog(this, "Changes saved successfully.");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error occurred while saving changes.");
        }
    }

    public static void main(String[] args) {
        Admin a = new Admin();
        a.setVisible(true);
    }
}
