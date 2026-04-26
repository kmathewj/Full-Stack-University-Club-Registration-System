package ryerson.ca.registration.endpoint;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import ryerson.ca.registration.business.RegistrationBusiness;
import ryerson.ca.registration.persistence.Application_CRUD;

@Path("registration")
public class RegistrationResource {

    // Student Application Endpoint
    @POST
    @Path("apply")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_XML)
    public String apply(@FormParam("user") String user, 
                        @FormParam("clubId") int clubId, 
                        @FormParam("statement") String statement) {
        
        RegistrationBusiness business = new RegistrationBusiness();
        return business.processApplication(user, clubId, statement);
    }

    // Admin View Endpoint
    @GET
    @Path("allApplications")
    @Produces(MediaType.APPLICATION_XML)
    public String getAllApplications() {
        List<String[]> apps = Application_CRUD.getPendingApplications();
        StringBuilder xml = new StringBuilder("<applications>");
        for (String[] a : apps) {
            xml.append("<application>")
               .append("<id>").append(a[0]).append("</id>")
               .append("<user>").append(a[1]).append("</user>")
               .append("<clubId>").append(a[2]).append("</clubId>")
               .append("<statement>").append(a[3]).append("</statement>")
               .append("</application>");
        }
        xml.append("</applications>");
        return xml.toString();
    }

    // Admin Status Update Endpoint
    @POST
    @Path("updateStatus")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String updateStatus(@FormParam("appId") int appId, 
                               @FormParam("status") String status) {
        
        boolean success = Application_CRUD.updateStatus(appId, status);
        return success ? "SUCCESS" : "FAILED";
    }
}