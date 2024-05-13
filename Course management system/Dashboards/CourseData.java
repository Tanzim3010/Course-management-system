package Dashboards;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CourseData {
    private String[][] courses;
    private int numOfCourses;

    public CourseData() {
        courses = new String[100][4];
        loadCoursesFromFile();
    }

    private void loadCoursesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("data/Courses/courses.txt"))) {
            String line;
            int index = 0;
            while ((line = reader.readLine()) != null && index < courses.length) {
                String[] courseInfo = line.split(",");
                if (courseInfo.length == 4) {
                    courses[index++] = courseInfo;
                }
            }
            numOfCourses = index;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] searchCourse(String courseName) {
        for (int i = 0; i < numOfCourses; i++) {
            if (courses[i][0].equalsIgnoreCase(courseName)) {
                return courses[i];
            }
        }
        return null; // Course not found
    }
}
