package Dashboards;
import java.lang.*;

public class UserSession {
    private static String currentUser;

    public static String getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(String user) {
        currentUser = user;
    }

}