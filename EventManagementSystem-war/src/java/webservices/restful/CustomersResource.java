package webservices.restful;

import java.security.Principal;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("customers")
public class CustomersResource {

    @GET
    @Secured
    @Path("/me")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyProfile(@Context SecurityContext securityContext) {
        Principal principal = securityContext.getUserPrincipal();
        String userId = principal.getName();

        //from this userId, we can then get the data from the session bean accordingly
        System.out.println("userId : " + userId);
        return Response.status(200).entity(
                null
        ).type(MediaType.APPLICATION_JSON).build();
    }
}
