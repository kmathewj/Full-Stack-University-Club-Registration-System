package ryerson.ca.registration.endpoint;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Provider
public class AuthFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();
        
        if (path.contains("allApplications") || path.contains("updateStatus")) {
            String authHeader = requestContext.getHeaderString("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                        .entity("<error>Authentication required for Admin actions</error>")
                        .build());
            }

        }
    }
}