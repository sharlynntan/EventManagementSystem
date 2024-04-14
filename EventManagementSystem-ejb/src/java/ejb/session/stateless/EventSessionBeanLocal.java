/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.Event;
import util.exception.NoResultException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sharlynn
 */
@Local
public interface EventSessionBeanLocal {

    public Event getEvent(Long id) throws NoResultException;

    public void deleteEvent(Long id) throws NoResultException;

    public void updateEvent(Event e) throws NoResultException;

    public void createEvent(Event e);

    public List<Event> getAllEvents();

    public List<Event> getUserCreatedEvent(long pid);

    public List<Event> getFilteredEvent(String searchTerm);

    public List<Event> getCategorisedEvent(String enumStr);

    public List<Event> filterCategory(String enumStr);

}
