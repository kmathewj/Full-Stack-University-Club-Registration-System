
package ryerson.ca.club.business;
import java.util.List;
import ryerson.ca.club.persistence.Club_CRUD;

public class ClubBusiness {
    public String getClubsXML() {
        List<String[]> clubs = Club_CRUD.getClubs();
        StringBuilder xml = new StringBuilder("<clubs>");
        for (String[] c : clubs) {
            xml.append("<club>")
               .append("<id>").append(c[0]).append("</id>")
               .append("<name>").append(c[1]).append("</name>")
               .append("<description>").append(c[2]).append("</description>")
               .append("</club>");
        }
        xml.append("</clubs>");
        return xml.toString();
    }
}