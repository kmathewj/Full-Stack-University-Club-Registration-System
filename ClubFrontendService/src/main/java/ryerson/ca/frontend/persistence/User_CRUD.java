package ryerson.ca.frontend.persistence;

import java.sql.*;

public class User_CRUD {
    public static String validateLogin(String user, String pass) {
        String url = "jdbc:mysql://localhost:3306/User_DB?serverTimezone=UTC";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, "root", "student");

            // Admin Query
            PreparedStatement psA = con.prepareStatement("SELECT username FROM admin WHERE username=? AND password=?");
            psA.setString(1, user); 
            psA.setString(2, pass);
            if (psA.executeQuery().next()) return "admin";

            // Student Query
            PreparedStatement psS = con.prepareStatement("SELECT username FROM student WHERE username=? AND password=?");
            psS.setString(1, user); 
            psS.setString(2, pass);
            if (psS.executeQuery().next()) return "student";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}