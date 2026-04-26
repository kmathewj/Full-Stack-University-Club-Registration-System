

package ryerson.ca.club.persistence;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Club_CRUD {
    public static List<String[]> getClubs() {
        List<String[]> clubs = new ArrayList<>();
        try (Connection con = ClubDBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM club");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                clubs.add(new String[]{
                    String.valueOf(rs.getInt("club_id")),
                    rs.getString("club_name"),
                    rs.getString("description")
                });
            }
        } catch (Exception e) { e.printStackTrace(); }
        return clubs;
    }
    
    
    public static boolean addMember(int clubId, String username) {
    String query = "INSERT INTO club_members (club_id, username) VALUES (?, ?)";
    
    try (Connection con = ClubDBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(query)) {
        
        ps.setInt(1, clubId);
        ps.setString(2, username);
        
        int rowsAffected = ps.executeUpdate();
        return rowsAffected > 0; 
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
}

