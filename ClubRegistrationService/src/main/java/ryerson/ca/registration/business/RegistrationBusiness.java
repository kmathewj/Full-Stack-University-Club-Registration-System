package ryerson.ca.registration.business;

import ryerson.ca.registration.persistence.Application_CRUD;

public class RegistrationBusiness {
    public String processApplication(String user, int clubId, String statement) {
        boolean isSaved = Application_CRUD.createApplication(user, clubId, statement);
        
        if (isSaved) {
            return "<registration>"
                 + "<status>SUCCESS</status>"
                 + "<message>Application stored in Registration_DB</message>"
                 + "</registration>";
        } else {
            return "<registration><status>FAILED</status></registration>";
        }
    }
}