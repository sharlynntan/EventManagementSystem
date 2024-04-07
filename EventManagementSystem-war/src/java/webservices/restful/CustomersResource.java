package webservices.restful;

import ejb.session.stateless.EventSessionBeanLocal;
import ejb.session.stateless.PersonSessionBeanLocal;
import entity.Person;
import java.security.Principal;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import util.exception.NoResultException;

@Path("customers")
public class CustomersResource {

    @EJB
    private PersonSessionBeanLocal personSessionBeanLocal;

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

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomer(@PathParam("id") Long cId) {
        try {
            Person p = personSessionBeanLocal.getPerson(cId);
            return Response.status(200).entity(
                    p
            ).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException e) {
            JsonObject exception = Json.createObjectBuilder()
                    .add("error", "Not found")
                    .build();
            return Response.status(404).entity(exception)
                    .type(MediaType.APPLICATION_JSON).build();
        }
    }

}
