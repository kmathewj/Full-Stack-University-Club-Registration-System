package ryerson.ca.registration.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Application_CRUD {
    
    // Used by Students to apply
    public static boolean createApplication(String user, int clubId, String statement) {
        String query = "INSERT INTO applications (username, club_id, application_statement, status, application_date) "
                     + "VALUES (?, ?, ?, 'Pending', CURDATE())";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, user);
            ps.setInt(2, clubId);
            ps.setString(3, statement);
            
            int result = ps.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Used by Admin to view all pending requests
    public static List<String[]> getPendingApplications() {
        List<String[]> apps = new ArrayList<>();
        String query = "SELECT * FROM applications WHERE status = 'Pending'";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                apps.add(new String[]{
                    String.valueOf(rs.getInt("application_id")),
                    rs.getString("username"),
                    String.valueOf(rs.getInt("club_id")),
                    rs.getString("application_statement")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return apps;
    }

    // Used by Admin to Approve or Decline
    public static boolean updateStatus(int appId, String status) {
        String query = "UPDATE applications SET status = ? WHERE application_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            
            ps.setString(1, status);
            ps.setInt(2, appId);
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}