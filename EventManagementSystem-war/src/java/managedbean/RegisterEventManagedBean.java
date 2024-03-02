/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package managedbean;

import ejb.session.stateless.EventAttendanceLocal;
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
    private EventAttendanceLocal eventAttendanceLocal;

    private List<Event> listOfRegisteredEvents;

    public RegisterEventManagedBean() {
    }

    @PostConstruct
    public void populateRegisteredEvent() {
        long pId = authenticationManagedBean.getUserId();
        listOfRegisteredEvents = eventAttendanceLocal.getRegisteredEventList(pId);
        System.out.println("Testingggg ---------------------" + listOfRegisteredEvents.size());
    }

    public void signUpEvent() {
        FacesContext context = FacesContext.getCurrentInstance();
        long pId = authenticationManagedBean.getUserId();
        System.out.println("twetwfdfsfdsfdsdfdfdssgdsgd" + pId);
        Map<String, String> params = context.getExternalContext()
                .getRequestParameterMap();
        String eIdStr = params.get("eId");
        Long eId = Long.parseLong(eIdStr);
        try {
            eventAttendanceLocal.setAttendance(eId, pId);
            context.addMessage(null, new FacesMessage("Success", "Successfully signed up"));
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

}
