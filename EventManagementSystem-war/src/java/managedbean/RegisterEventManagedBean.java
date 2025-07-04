/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package managedbean;

import entity.Event;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import util.exception.PersonExistException;
import util.exception.NoResultException;
import ejb.session.stateless.EventAttendanceSessionBeanLocal;

/**
 *
 * @author sharl
 */
@Named(value = "registerEventManagedBean")
@ViewScoped
public class RegisterEventManagedBean implements Serializable {

//    private Event selectedEvent;
    @Inject
    private AuthenticationManagedBean authenticationManagedBean;

    @EJB
    private EventAttendanceSessionBeanLocal eventAttendanceLocal;

    private List<Event> listOfRegisteredEvents;

    private long eventId;

    public RegisterEventManagedBean() {
    }

    @PostConstruct
    public void populateRegisteredEvent() {
        long pId = authenticationManagedBean.getUserId();
        listOfRegisteredEvents = eventAttendanceLocal.getRegisteredEventList(pId);
        System.out.println("Testingggg ---------------------" + listOfRegisteredEvents.size());
    }

//    public void signUpEvent() {
//        FacesContext context = FacesContext.getCurrentInstance();
//        long pId = authenticationManagedBean.getUserId();
//        System.out.println("twetwfdfsfdsfdsdfdfdssgdsgd" + pId);
//        Map<String, String> params = context.getExternalContext()
//                .getRequestParameterMap();
//        String eIdStr = params.get("eId");
//        Long eId = Long.parseLong(eIdStr);
//        try {
//            eventAttendanceLocal.setAttendance(eId, pId);
//            context.addMessage(null, new FacesMessage("Success", "Successfully signed up"));
//            populateRegisteredEvent();
//
//        } catch (PersonExistException | NoResultException ex) {
//            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage()));
//        }
//    }
    public void signUpEvent(long eId) {
        System.out.println("testingggggggggggggggggggg" + eventId);
        FacesContext context = FacesContext.getCurrentInstance();
        long pId = authenticationManagedBean.getUserId();
//        System.out.println(";lllllllllllllllllllllllllllllllllll" + eStr + "              " + "dddddd");
//        Long eId = Long.parseLong(eventId);
        try {
            eventAttendanceLocal.setAttendance(eventId, pId);
            context.addMessage(null, new FacesMessage("Success", "Successfully signed up"));
            System.out.println("Successful");
            populateRegisteredEvent();

        } catch (PersonExistException | NoResultException ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", ex.getMessage()));
        }
    }

    public void unregisterEvent() {
        FacesContext context = FacesContext.getCurrentInstance();
        long pId = authenticationManagedBean.getUserId();
        System.out.println("twetwfdfsfdsfdsdfdfdssgdsgd" + pId);
        Map<String, String> params = context.getExternalContext()
                .getRequestParameterMap();
        String eIdStr = params.get("eId");
        Long eId = Long.parseLong(eIdStr);

        eventAttendanceLocal.unregisterEvent(eId, pId);
        context.addMessage(null, new FacesMessage("Success", "Successfully unregister"));
        populateRegisteredEvent();
    }

    public List<Event> getListOfRegisteredEvents() {
        return listOfRegisteredEvents;
    }

    public void setListOfRegisteredEvents(List<Event> listOfRegisteredEvents) {
        this.listOfRegisteredEvents = listOfRegisteredEvents;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

}
