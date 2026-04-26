package ryerson.ca.club.endpoint;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import ryerson.ca.club.business.ClubBusiness;
import ryerson.ca.club.persistence.Club_CRUD;

@Path("clubs")
public class ClubResource {

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getClubs() {
        ClubBusiness business = new ClubBusiness();
        return business.getClubsXML();
    }

    @POST
    @Path("addMember")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_XML)
    public String addMember(@FormParam("clubId") int clubId, 
                            @FormParam("username") String username) {
        
        boolean success = Club_CRUD.addMember(clubId, username);
        
        if (success) {
            return "<status>SUCCESS: Member added to Club_DB</status>";
        } else {
            return "<status>ERROR: Could not add member</status>";
        }
    }
}