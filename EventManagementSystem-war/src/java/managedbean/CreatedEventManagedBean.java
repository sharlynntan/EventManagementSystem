/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package managedbean;

import ejb.session.stateless.EventSessionBeanLocal;
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

/**
 *
 * @author sharl
 */
@Named(value = "createdEventManagedBean")
@ViewScoped
public class CreatedEventManagedBean implements Serializable {

    @EJB
    private EventSessionBeanLocal eventSessionBeanLocal;

    @Inject
    private AuthenticationManagedBean authenticationManagedBean;

    private List<Event> listOfCreatedEvents;

    private Event selectedEvent;

    private Long eId;

    public CreatedEventManagedBean() {
    }

    @PostConstruct
    public void populateCreatedEvents() {
        long pId = authenticationManagedBean.getUserId();
        listOfCreatedEvents = eventSessionBeanLocal.getUserCreatedEvent(pId);

    }

    public void deleteSelectedEvents() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext()
                .getRequestParameterMap();
        String eIdStr = params.get("eId");
        Long eId = Long.parseLong(eIdStr);
        try {
            eventSessionBeanLocal.deleteEvent(eId);
        } catch (Exception ex) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Unable to delete customer"));
            return;
        }

        context.addMessage(null, new FacesMessage("Success", "Successfully deleted event"));
        populateCreatedEvents();

    }

    public List<Event> getListOfCreatedEvents() {
        return listOfCreatedEvents;
    }

    public void setListOfCreatedEvents(List<Event> listOfCreatedEvents) {
        this.listOfCreatedEvents = listOfCreatedEvents;
    }

    public Event getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public Long geteId() {
        return eId;
    }

    public void seteId(Long eId) {
        this.eId = eId;
    }

}
