/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package webservices.restful;

import Class.PersonAttendance;
import ejb.session.stateless.EventAttendanceSessionBeanLocal;
import ejb.session.stateless.EventSessionBeanLocal;
import entity.Event;
import entity.Person;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.exception.NoResultException;
import util.exception.PersonExistException;

/**
 * REST Web Service
 *
 * @author sharl
 */
@Path("events")
public class EventsResource {

    @EJB
    private EventSessionBeanLocal eventSessionBeanLocal;

    @EJB
    private EventAttendanceSessionBeanLocal eventAttendanceSessionBeanLocal;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Event> getAllEvents() {
        System.out.println("2");
        List<Event> listOfEvent = eventSessionBeanLocal.getAllEvents();
        for (Event e : listOfEvent) {
            e.getOrganiser().setListOfEvent(null);
            e.setAttendanceList(new ArrayList<>());
        }
        if (listOfEvent.isEmpty()) {
            return Collections.emptyList(); // Return an empty list
        } else {
            return listOfEvent;
        }
    }

    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchEvent(@QueryParam("searchTerm") String searchTerm) {
        GenericEntity<List<Event>> entity;
        System.out.println("testing");
        if (searchTerm == null) {
            System.out.println(searchTerm);
            entity = new GenericEntity<List<Event>>(getAllEvents()) {
            };
            return Response.status(200).entity(entity).build();
        } else {
            List<Event> filteredResult = eventSessionBeanLocal.getFilteredEvent(searchTerm);
            if (!filteredResult.isEmpty()) {
                for (Event e : filteredResult) {
                    e.getOrganiser().setListOfEvent(null);
                    e.setAttendanceList(new ArrayList<>());
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

    @GET
    @Path("/id")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEventDetails(@QueryParam("id") Long cId) {
        if (cId == null || cId <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid user ID")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
        try {
            Event e = eventSessionBeanLocal.getEvent(cId);

            System.out.println("testing");

            e.getOrganiser().setListOfEvent(null);
            e.setAttendanceList(new ArrayList<>());

            if (e == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No events found for user with ID: " + cId)
                        .type(MediaType.TEXT_PLAIN)
                        .build();
            }

            return Response.status(200).entity(
                    e
            ).type(MediaType.APPLICATION_JSON).build();
        } catch (NoResultException ex) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No events found for user with ID: " + cId)
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }

    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCreatedEvent(@PathParam("id") Long cId) {
        if (cId == null || cId <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid user ID")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }

        List<Event> listOfEvent = eventSessionBeanLocal.getUserCreatedEvent(cId);

        System.out.println("testing");
        for (Event e : listOfEvent) {
            e.getOrganiser().setListOfEvent(null);
            e.setAttendanceList(new ArrayList<>());
        }

        if (listOfEvent.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Collections.emptyList())
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        return Response.status(200).entity(
                listOfEvent
        ).type(MediaType.APPLICATION_JSON).build();

    }

    @GET
    @Path("/registered/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRegisteredEvent(@PathParam("id") Long cId) {
        if (cId == null || cId <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid user ID")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }

        List<Event> listOfEvent = eventAttendanceSessionBeanLocal.getRegisteredEventList(cId);

        System.out.println("testing");
        for (Event e : listOfEvent) {
            e.getOrganiser().setListOfEvent(null);
            e.setAttendanceList(new ArrayList<>());
        }

        if (listOfEvent.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Collections.emptyList())
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        return Response.status(200).entity(
                listOfEvent
        ).type(MediaType.APPLICATION_JSON).build();

    }

    @POST
    @Path("/{eventId}/userId")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response SignUp(@PathParam("eventId") Long eventId, @QueryParam("userId") Long userId) {
        try {
            eventAttendanceSessionBeanLocal.setAttendance(eventId, userId);
            Event e = eventSessionBeanLocal.getEvent(eventId);
            System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;" + e.getAttendanceList().size());
            for (PersonAttendance a : e.getAttendanceList()) {
                System.out.println(a.getPerson().getFirstName());
            }
            JsonObject responseJson = Json.createObjectBuilder()
                    .add("message", "Successful")
                    .build();

            return Response.status(Response.Status.CREATED).entity(responseJson.toString()).build();

        } catch (PersonExistException | NoResultException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid data").build();
        }

    }

    @DELETE
    @Path("/{eventId}/userId")
    public Response Unregister(@PathParam("eventId") Long eventId, @QueryParam("userId") Long userId) {
        try {
            eventAttendanceSessionBeanLocal.unregisterEvent(eventId, userId);
            Event e = eventSessionBeanLocal.getEvent(eventId);
            System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;" + e.getAttendanceList().size());
            for (PersonAttendance a : e.getAttendanceList()) {
                System.out.println(a.getPerson().getFirstName());
            }
            JsonObject responseJson = Json.createObjectBuilder()
                    .add("message", "Successful")
                    .build();

            return Response.status(Response.Status.CREATED).entity(responseJson.toString()).build();

        } catch (NoResultException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid data").build();
        }

    }

    @DELETE
    @Path("/id")
    public Response DeleteEvent(@QueryParam("id") Long eventId) {
        try {
            eventSessionBeanLocal.deleteEvent(eventId);
            JsonObject responseJson = Json.createObjectBuilder()
                    .add("message", "Successful")
                    .build();

            return Response.status(Response.Status.CREATED).entity(responseJson.toString()).build();

        } catch (NoResultException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid data").build();
        }

    }

    @GET
    @Path("/attendance")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAttendanceList(@QueryParam("id") Long eventId) {
        if (eventId == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid Event ID")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }

        try {

            Event e = eventSessionBeanLocal.getEvent(eventId);
            List<PersonAttendance> attendances = e.getAttendanceList();
            for (PersonAttendance a : attendances) {
                a.getPerson().setListOfEvent(new ArrayList<>());
            }

            return Response.status(200).entity(
                    attendances
            ).type(MediaType.APPLICATION_JSON).build();

        } catch (NoResultException ex) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No events found for event with ID: " + eventId)
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }

    }

    @POST
    @Path("/{eventId}/attendance")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response MarkPresent(@PathParam("eventId") Long eventId, @QueryParam("id") Long userId) {

        if (eventId == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid Event ID")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }

        eventAttendanceSessionBeanLocal.updateAttendance(eventId, userId, true);
        try {
            Event e = eventSessionBeanLocal.getEvent(eventId);
            List<PersonAttendance> a = e.getAttendanceList();
            for (PersonAttendance p : a) {
                System.out.println(p.getPerson().getId() + " " + p.isAttendance());
            }
        } catch (NoResultException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid Event ID")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }

        JsonObject responseJson = Json.createObjectBuilder()
                .add("message", "Successful")
                .build();

        return Response.status(Response.Status.CREATED).entity(responseJson.toString()).build();

    }

    @POST
    @Path("/{eventId}/absent")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response MarkAbsent(@PathParam("eventId") Long eventId, @QueryParam("id") Long userId) {

        if (eventId == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid Event ID")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }

        eventAttendanceSessionBeanLocal.updateAttendance(eventId, userId, false);
        try {
            Event e = eventSessionBeanLocal.getEvent(eventId);
            List<PersonAttendance> a = e.getAttendanceList();
            for (PersonAttendance p : a) {
                System.out.println(p.getPerson().getId() + " " + p.isAttendance());
            }
        } catch (NoResultException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid Event ID")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }

        JsonObject responseJson = Json.createObjectBuilder()
                .add("message", "Successful")
                .build();

        return Response.status(Response.Status.CREATED).entity(responseJson.toString()).build();

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
