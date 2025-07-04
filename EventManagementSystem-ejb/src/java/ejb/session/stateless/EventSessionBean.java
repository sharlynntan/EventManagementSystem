/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.Event;
import util.enumeration.eventCategory;
import util.exception.NoResultException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Sharlynn
 */
@Stateless
public class EventSessionBean implements EventSessionBeanLocal {

    @PersistenceContext(unitName = "EventManagementSystem-ejbPU")
    private EntityManager em;

    public Event getEvent(Long id) throws NoResultException {
        Event event = em.find(Event.class, id);
        if (event != null) {
            return event;
        } else {
            throw new NoResultException("Event cannot be found!");
        }

    }

    public List<Event> getAllEvents() {
        Query query = em.createQuery("SELECT e FROM Event e");
        return query.getResultList();
    }

    public List<Event> getFilteredEvent(String searchTerm) {
        Query query = em.createQuery("SELECT e FROM Event e WHERE LOWER(e.description) LIKE LOWER(:searchTerm) OR  LOWER(e.title) LIKE LOWER(:searchTerm)");
        query.setParameter("searchTerm", "%" + searchTerm + "%");
        return query.getResultList();
    }

    public List<Event> getCategorisedEvent(String enumStr) {
        eventCategory ec = getEnumCategory(enumStr);
        System.out.println("-------------------" + enumStr);
        Query query = em.createQuery("SELECT e FROM Event e WHERE e.eventCategory = :enumCat");
        query.setParameter("enumCat", ec.toString());
        return query.getResultList();
    }

    public List<Event> filterCategory(String enumStr) {
        eventCategory ec = getQueryCat(enumStr);
        System.out.println("-------------------" + enumStr);
        Query query = em.createQuery("SELECT e FROM Event e WHERE e.eventCategory = :enumCat");
        query.setParameter("enumCat", ec);
        return query.getResultList();
    }

    public void createEvent(Event e) {
        try {
            System.out.println("Creating event");
            em.persist(e);
            em.flush();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            throw ex;

        }

    }

    public void updateEvent(Event e) throws NoResultException {
        try {
            Event eventToUpdate = getEvent(e.getId());
            eventToUpdate.setDate(e.getDate());
            eventToUpdate.setDeadline(e.getDeadline());
            eventToUpdate.setDescription(e.getDescription());
            eventToUpdate.setTitle(e.getTitle());
            eventToUpdate.setEventCategory(e.getEventCategory());
            eventToUpdate.setLocation(e.getLocation());
            eventToUpdate.setEventImage(e.getEventImage());

        } catch (NoResultException ex) {
            throw ex;
        }
    }

    public void deleteEvent(Long id) throws NoResultException {
        try {
            Event eventToDelete = getEvent(id);
            em.remove(eventToDelete);
        } catch (NoResultException ex) {
            throw ex;
        }
    }

    public List<Event> getUserCreatedEvent(long pid) {
        Query query = em.createQuery("SELECT e FROM Event e WHERE e.organiser.id = :pid");
        query.setParameter("pid", pid);
        return query.getResultList();
    }

    public eventCategory getQueryCat(String eventCat) {
        eventCategory ec;

        switch (eventCat.toLowerCase()) {
            case "music":
                ec = eventCategory.MUSIC;
                break;
            case "nightlife":
                ec = eventCategory.NIGHTLIFE;
                break;
            case "performingart":
                ec = eventCategory.PERFORMINGART;
                break;
            case "holidays":
                ec = eventCategory.HOLIDAYS;
                break;
            case "health":
                ec = eventCategory.HEALTH;
                break;
            case "hobbies":
                ec = eventCategory.HOBBIES;
                break;
            case "business":
                ec = eventCategory.BUSINESS;
                break;
            case "foodanddrink":
                ec = eventCategory.FOODANDDRINK;
                break;
            default:
                ec = eventCategory.OTHER;
        }
        return ec;
    }

    public eventCategory getEnumCategory(String eventCat) {
        eventCategory ec;

        switch (eventCat.toLowerCase()) {
            case "music":
                ec = eventCategory.MUSIC;
                break;
            case "nightlife":
                ec = eventCategory.NIGHTLIFE;
                break;
            case "performing art":
                ec = eventCategory.PERFORMINGART;
                break;
            case "holidays":
                ec = eventCategory.HOLIDAYS;
                break;
            case "health":
                ec = eventCategory.HEALTH;
                break;
            case "hobbies":
                ec = eventCategory.HOBBIES;
                break;
            case "business":
                ec = eventCategory.BUSINESS;
                break;
            case "food and drink":
                ec = eventCategory.FOODANDDRINK;
                break;
            default:
                ec = eventCategory.OTHER;
        }
        return ec;
    }

}
