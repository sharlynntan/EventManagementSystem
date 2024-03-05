/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import Class.PersonAttendance;
import entity.Event;
import entity.Person;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.NoResultException;
import util.exception.PersonExistException;

/**
 *
 * @author Sharlynn
 */
@Stateless
public class EventAttendanceSessionBean implements EventAttendanceSessionBeanLocal {
    
    @EJB
    private EventSessionBeanLocal eventSessionBeanLocal;
    
    @EJB
    private PersonSessionBeanLocal personSessionBeanlocal;
    
    @PersistenceContext(unitName = "EventManagementSystem-ejbPU")
    private EntityManager em;

//    public List<PersonAttendance> getPersonAttendanceList(Long id) {
//        Query query = em.createQuery("SELECT e.attendanceList FROM Event e WHERE e.id = :id");
//        query.setParameter("id", id);
//        return query.getResultList();
//    }
    public List<Event> getRegisteredEventList(Long pId) {
        List<Event> allEvents = eventSessionBeanLocal.getAllEvents();
        System.out.println("ehhehehhhehehhehehehheehehheeh");
        List<Event> registeredEvent = new ArrayList<Event>();
        for (Event e : allEvents) {
            List<PersonAttendance> attendance = e.getAttendanceList();
            for (PersonAttendance pa : attendance) {
                if (pa.getPerson().getId().equals(pId)) {
                    registeredEvent.add(e);
                }
            }
        }
        
        return registeredEvent;
    }
    
    public List<PersonAttendance> getAttendanceListOfEvents(Long eId) throws NoResultException {
        try {
            Event event = eventSessionBeanLocal.getEvent(eId);
            return event.getAttendanceList();
        } catch (NoResultException ex) {
            throw ex;
        }
    }
    
    public void setAttendance(Long eId, Long pId) throws PersonExistException, NoResultException {
        try {
            Event e = em.find(Event.class, eId);
            
            List<PersonAttendance> aList = e.getAttendanceList();
            for (PersonAttendance pa : aList) {
                if (pa.getPerson().getId().equals(pId)) {
                    System.out.println("hesjfhoehwfhewifhoewi  testeing");
                    throw new PersonExistException("You have signed up for event!");
                    
                }
            }
            
            Person p = personSessionBeanlocal.getPerson(pId);
            PersonAttendance newPa = new PersonAttendance(p);
            e.getAttendanceList().add(newPa);
//            System.out.println("hesjfhoehwfhewifhoewi " + e.getAttendanceList().size());

        } catch (NoResultException ex) {
            throw ex;
        }
        
    }
    
    public void unregisterEvent(Long eId, Long pId) {
        Event e = em.find(Event.class, eId);
        List<PersonAttendance> aList = e.getAttendanceList();
        for (PersonAttendance pa : aList) {
            if (pa.getPerson().getId().equals(pId)) {
                e.getAttendanceList().remove(pa);
                break;
            }
        }
        
    }
    
    public void updateAttendance(Long eId, Long pId, boolean attendance) {
        Event e = em.find(Event.class, eId);
        List<PersonAttendance> aList = e.getAttendanceList();
        for (PersonAttendance pa : aList) {
            if (pa.getPerson().getId().equals(pId)) {
                pa.setAttendance(attendance);
                break;
            }
        }
    }
    
}
