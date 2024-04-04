/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package webservices.restful;

import ejb.session.stateless.EventSessionBeanLocal;
import entity.Event;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author sharl
 */
@Path("events")
public class EventsResource {

    @EJB
    private EventSessionBeanLocal eventSessionBeanLocal;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Event> getAllEvents() {
        List<Event> listOfEvent = eventSessionBeanLocal.getAllEvents();
        for (Event e : listOfEvent) {
            e.getOrganiser().setListOfEvent(null);
        }
        return listOfEvent;
    }

    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchEvent(@QueryParam("searchTerm") String searchTerm) {
        GenericEntity<List<Event>> entity;
        if (searchTerm == null) {
            entity = new GenericEntity<List<Event>>(getAllEvents()) {
            };
            return Response.status(200).entity(entity).build();
        } else {
            List<Event> filteredResult = eventSessionBeanLocal.getFilteredEvent(searchTerm);
            if (!filteredResult.isEmpty()) {
                for (Event e : filteredResult) {
                    e.getOrganiser().setListOfEvent(null);
                }
                entity = new GenericEntity<List<Event>>(filteredResult) {
                };
                return Response.status(200).entity(entity).build();
            } else {
                JsonObject exception = Json.createObjectBuilder().add("error", "No query conditions").build();
                return Response.status(400).entity(exception).build();
            }
        }

    }

//    @GET
//    @Path("/query")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getCategorisedEvents(@QueryParam("category") String category) {
//        List<Event> filteredEvent = eventSessionBeanLocal.getCategorisedEvent(category);
//        if (!filteredEvent.isEmpty()) {
//            for (Event e : filteredEvent) {
//                e.getOrganiser().setListOfEvent(null);
//            }
//            GenericEntity<List<Event>> entity = new GenericEntity<List<Event>>(filteredEvent) {
//            };
//            return Response.status(200).entity(entity).build();
//        } else {
//            JsonObject exception = Json.createObjectBuilder().add("error", "No query conditions").build();
//            return Response.status(400).entity(exception).build();
//        }
//    }
}
